(ns co-who.components.evm.smart-contract
  (:require [mr-who.dom :as dom]
            [co-who.blueprint.button :as b]
            [co-who.blueprint.dropdown :as d]
            [co-who.blueprint.input :as i]
            [co-who.mutations :as m]
            [co-who.blueprint.label :as l]
            [co-who.evm.specs :as es]
            [co-who.evm.client :as ec]
            [co-who.evm.lib :as el]
            [cljs.spec.alpha :as s]
            [pyramid.core :as py]
            [co-who.evm.util :as eu]))

(defn address-input [{:keys [name value] :as data :or {name "Address"
                                                       value "0x0"}}
                     {:local/keys [on-change]}]
  (i/input {:id "nunu"
            :label (str name)
            :place-holder "0x..."
            :on-change on-change
            } value))

(defn number-input [{:keys [name value] :or {name "Value" value 0}}
                    {:local/keys [on-change]}]
  (i/number-input {
            :label name
            :placeholder 0
            :on-change on-change
            } value))

(defn set-abi-field-fn [path value & convert-fn]
  (swap! co-who.app/app assoc-in (conj path :value) value))


(defn convert-input-filter [input]
  (condp = type
    "address" str
    "uin256" (eu/parse-ether (:value input))
    "uin8" (eu/parse-ether (:value input))
    "uin48" (eu/parse-ether (:value input))))

(defn input [{:keys [internalType type name value] :as entry}
             {:local/keys [on-change] :as local}]
  (dom/div {}
           (condp = type
             "address" (address-input entry local)
             "uint256" (number-input entry local)
             "uint8" (number-input entry local)
             "uint48" (number-input entry local)
             (str entry))))

(defn function-comp [{:keys [function/id name inputs outputs stateMutability type] :as data}
                     {:local/keys [on-change] :as local}]
  (dom/div {}
           (l/label (str  "Name: " name))
           (l/label (str  "Inputs:"))
           (map-indexed (fn [i entry]
                          (input entry {:local/on-change (fn [e]
                                                           (set-abi-field [:function/id id :inputs i] e.target.value))}))
                        inputs)
           (l/label (str  "Outputs:"))
           (map-indexed (fn [i entry]
                          (input entry {:local/on-change (fn [e]
                                                           (set-abi-field [:function/id id :inputs i] e.target.value))}))
                        outputs)))

(defn abi-entry [data & local]
  (dom/div {:class ""}
           (condp = (:type data)
             "function" (function-comp data local)
             "constructor" ""
             "error" ""
             "event" ""
             :cljs.spec.alpha/invalid "inv"
             "default")))

(defn execute-transaction [{:contract/keys [abi address]}
                           transaction-id]
  (fn [e]
    (let [data (get-in @co-who.app/app (get-in @co-who.app/app (conj transaction-id :transaction/function)))
          {:keys [function/id type inputs name outputs stateMutability] :as data} data
          c (el/get-contract (clj->js {:address address
                                       :abi (clj->js abi)
                                       :client (clj->js {:public @ec/public-client :wallet @ec/wallet-client})}))
          cf (if (= stateMutability "view") c.read c.write)
          function (aget cf name)
          args (mapv #(convert-input-filter %) inputs)]
      (println "tr id: " (get-in @co-who.app/app (conj transaction-id :transaction/function)))
      (println "data: " data)
      (println "inputs: " inputs)
      (println "args " (str args))
      (.then (function (clj->js args)) #(println %)))))

(defn transaction [{:transaction/keys [id function] :as data :or {id (random-uuid)
                                                                  function nil}} {:local/keys [execute-fn] :as local}]
  (list (fn [] data)
        (fn [] (dom/div {:class ""}
                        (abi-entry function {:local/on-change-id [:transaction/id id]})
                        (b/button "Transact" execute-fn)))))

(defn append-evm-transaction [app]
  (fn [e]
    (let [selected (get-in @app [:id :smart-contract :selected-function])
          ident [:contract/id (keyword (clojure.string/lower-case (name (get-in @app [:id :transaction-builder :selected-contract]))))]
          contract (get-in @co-who.app/app ident)
          function-data (get-in @app [:function/id (name selected)])
          transaction-data {:transaction/id (random-uuid)
                            :transaction/function (assoc-in function-data [:function/id] (random-uuid))}
          tr (transaction transaction-data {:local/execute-fn (execute-transaction contract [:transaction/id (:transaction/id transaction-data)])})
          ]
      (swap! app py/add ((first tr)))
      (m/append-mutation co-who.app/render-state [:app :transaction-builder :topf2 :list :l2]
                         (second tr) [] {:use-cache? false}))))

(defn smart-contract [{:contract/keys [id address abi chain name]}
                      {:local/keys [select-on-change on-change on-click selected-function]}]
  (dom/div {:class "flex w-full gap-2"}
            (i/input {:id :address
                      :label "Address"
                      :placeholder "0x..."
                      :on-change on-change} address)
            (d/dropdown-select "Select function" (mapv (fn [a]
                                                         {:ident "none"
                                                          :value (:name a)})
                                                       (filterv #(s/valid? ::es/function %) abi)) select-on-change)
            (dom/div {:style {:paddng-top "20px"
                              :height "20px"}}
                     (b/button "Add" on-click))))

(defn transaction-builder ([return-data {:keys [id contracts transactions] :as data
                                         :local/keys [on-click on-change select-on-change
                                                      contract-select-on-change selected-contract] :or {id "none"
                                                      contracts []
                                                      transactions []}}]
                           data)
  ([{:keys [id contracts transactions] :as data
     :local/keys [on-click on-change select-on-change
                  contract-select-on-change selected-contract] :or {id "none"
                  transactions []}}]
   (let [contract (first (filterv (fn [c] (= selected-contract [:contract/id (:contract/id c)])) contracts))]
     (dom/div {:id id
               :class "max-w-4xl flex flex-col  grid grid-cols-2 gap-16"}
              (dom/div {:id :topf
                        :class "flex flex-col"}
                       (dom/span {:id :sp
                                  :class "flex w-full gap-2 mb-6"}
                                 (d/dropdown-select "Select contract" (mapv (fn [c] {:ident [:contract/id (:contract/id c)]
                                                                                     :value (:contract/name c)}) contracts) contract-select-on-change))
                       (smart-contract contract
                                       #:local{:on-click on-click :on-change on-change :select-on-change select-on-change}))
              (dom/div {:id :topf2
                        :class "flex flex-col :col-start-2"}
                       (dom/h1 {:class "mb-2"} "Transactions")
                       (dom/div {:id :list
                                 :class "dark:bg-gray-700 dark:placeholder-gray-400 w-full dark:border-gray-600
                                         dark:text-white dark:focus:ring-blue-500 p-4 text-md rounded-lg overflow-hidden"}
                                (dom/div {:id :l2
                                          :class "position-relative overflow-y-auto overflow-x-hidden flex max-h-64"}
                                         (if transactions
                                           (for [t transactions]
                                             (transaction t {:local/execute-fn (execute-transaction contract t)}))
                                           ""))))))))
