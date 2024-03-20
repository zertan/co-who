(ns co-who.components.evm.smart-contract
  (:require [mr-who.dom :as dom]
            [co-who.blueprint.button :as b]
            [co-who.blueprint.dropdown :as d]
            [co-who.blueprint.input :as i]
            [co-who.mutations :as m]
            [co-who.blueprint.label :as l]
            [co-who.evm.specs :as es]
            [cljs.spec.alpha :as s]))

(defn address-input [{:keys [name address] :as data :or {name "Address" address "0x0"}}]
  (println data)
  (i/input {:id "nunu"
            :label (str name)
            :place-holder "0x..."
                                        ;:on-change on-change
            } address))

(defn number-input [{:keys [name value] :or {name "Value" value 0}}]
  (i/number-input {
            :label name
            :placeholder "0x..."
            ;:on-change on-change
            } value))

(defn input [{:keys [internalType type name] :as entry}]
  (dom/div {}
           (condp = type
             "address" (address-input entry)
             "uint256" (number-input entry)
             "uint8" (number-input entry)
             "uint48" (number-input entry)
             (str entry))))

(defn function-comp [{:keys [function/id name inputs outputs stateMutability type] :as data}]
  (dom/div {}
           (l/label (str  "Name: " name))
           (l/label (str  "Inputs:"))
           (for [entry inputs]
             (input entry))
           (l/label (str  "Outputs:"))
           (for [entry outputs]
             (input entry))))

(defn abi-entry [data]
  (dom/div {:class ""}
           (condp = (:type data)
             "function" (function-comp data)
             "constructor" ""
             "error" ""
             "event" ""
             :cljs.spec.alpha/invalid "inv"
             "default")))

(defn transaction [{:keys [] :as data}]
  (dom/div {:class ""}
           (abi-entry data)
           (b/button "Transact" (fn [e] (println data)))))

(defn append-evm-transaction [app]
  (fn [e]
    (let [selected (get-in @app [:transaction-builder :selected])
          data (get-in @app [:function/id selected])]
      (println data)
      (m/append-mutation app [:transaction-builder :topf2 :list :l2]
                         (fn [] (transaction data)) [] {:use-cache? false}))))

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
                                :class "flex w-full gap-2"}
                               (d/dropdown-select "Select contract" (mapv #(:contract/name %) (vals contracts)) contract-select-on-change))
                     (smart-contract (get contracts selected) #:local{:on-click on-click :on-change on-change :select-on-change select-on-change}))
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
                                           (transaction t))
                                         "")))))))
