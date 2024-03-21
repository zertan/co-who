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
            [pyramid.core :as py]))

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
            :placeholder "0x..."
            :on-change on-change
            } value))

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
           (for [entry inputs]
             (input entry local))
           (l/label (str  "Outputs:"))
           (for [entry outputs]
             (input entry local))))

(defn abi-entry-input [app ident value]
  (swap! app update-in (conj ident :value) value))

(defn abi-entry [ident data & local]
  (dom/div {:class ""}
    (condp = (:type data)
      "function" (function-comp data local)
      "constructor" ""
      "error" ""
      "event" ""
      :cljs.spec.alpha/invalid "inv"
      "default")))

(defn execute-transaction [{:contract/keys [abi address]}
                           {:keys [function/id type inputs name outputs stateMutability] :as data}]
  (fn [e]
    (let [c (el/get-contract (clj->js {:address address
                                       :abi (clj->js abi)
                                       :client (clj->js {:public @ec/public-client :wallet @ec/wallet-client})}))
          cf (if (= stateMutability "view") c.read c.write)
          function (aget cf id)
          args (mapv #(:value %) inputs)]
      (.then (function (clj->js args)) #(println %)))))

(defn set-transaction-field [app ident value]
  (swap! app update-in (conj ident :value) value)
  #_(m/replace-mutation app ident #() value)
  )

(defn transaction [{:keys [id] :as data :or {id (random-uuid)}} {:local/keys [execute-fn] :as local}]
  (println "e" execute-fn)
  (dom/form {:class ""}
    (abi-entry data {:local/on-change-id [:transaction/id id]})
    (b/button "Transact" (execute-fn data))))

(defn append-evm-transaction [app]
  (fn [e]
    (let [selected (get-in @app [:id :transaction-builder :selected])
          contract (py/pull @app [:contract/id (get-in @app [:id :transaction-builder :selected-contract])])
          data (get-in @app [:function/id selected])
          ]
      (m/append-mutation app [:app :router :route :transaction-builder :topf2 :list :l2]
                         #(transaction data {:local/execute-fn (execute-transaction contract data)}) [] {:use-cache? false}))))

(defn smart-contract [{:contract/keys [id address abi chain name]}
                      {:local/keys [select-on-change on-change on-click selected]}]
  (dom/span {:class "flex w-full gap-2"}
    (i/input {:id :address
              :label "Address"
              :placeholder "0x..."
              :on-change on-change} address)
    (d/dropdown-select "Select function" (mapv #(:name %)
                                               (filterv #(s/valid? ::es/function %) abi)) select-on-change)
    (dom/div {:style {:paddng-top "20px"
                      :height "20px"}}
      (b/button "Add" on-click))))

(defn transaction-builder ([return-data {:keys [id contracts transactions] :as data
                                         :local/keys [on-click on-change select-on-change
                                                      contract-select-on-change selected] :or {id "none"
                                                                                               contracts []
                                                                                               transactions []}}]
                           data)
  ([{:keys [id contracts transactions] :as data
     :local/keys [on-click on-change select-on-change
                  contract-select-on-change selected] :or {id "none"
                                                           contracts []
                                                           transactions []}}]
   (dom/div {:id id
             :class "max-w-4xl flex flex-col  grid grid-cols-2 gap-16"}
            (dom/div {:id :topf
                      :class "flex flex-col"}
                     (dom/span {:id :sp
                                :class "flex w-full gap-2 mb-6"}
                       (d/dropdown-select "Select contract" (mapv (fn [c] {:ident [:contract/id (:contract/id c)]
                                                                           :value (:contract/name c)}) (vals contracts)) contract-select-on-change))
                     (smart-contract (get contracts selected)
                                     #:local{:on-click on-click :on-change on-change :select-on-change select-on-change}))
            (dom/div {:id :topf2
                      :class "flex flex-col :col-start-2"}
                     (dom/h1 {:class "mb-2"} "Transactions")
                     (dom/div {:id :list
                               :class "dark:bg-gray-800 dark:border-gray-600 dark:placeholder-gray-400 w-full
                        dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500 p-4 text-md rounded-lg overflow-hidden"}
                              (dom/div {:id :l2
                                        :class "position-relative overflow-y-auto overflow-x-hidden flex max-h-64"}
                                       (if transactions
                                         (for [t transactions]
                                           (let [contract (get contracts selected)]
                                             (transaction t {:local/execute-fn (execute-transaction contract t)})))
                                         "")))))))
