(ns co-who.components.evm.smart-contract
  (:require [mr-who.dom :as dom]
            [co-who.blueprint.button :as b]
            [co-who.blueprint.dropdown :as d]
            [co-who.blueprint.input :as i]
            [co-who.mutations :as m]
            [co-who.blueprint.label :as l]))

(defn address-comp [address]
  (dom/input {:class "bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg
focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600
dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500 invalid:[&:not(:placeholder-shown):not(:focus)]:border-red-500"}))

(defn function-comp [{:keys [name inputs stateMutability type] :as data}]
  (dom/div {}
           (l/label (str  "Name: " name))
           (dom/div {:class "block mb-2 text-sm font-medium text-gray-900 dark:text-white"}
                      (str  "Inputs:"))
           (for [input inputs]
             (i/input {:label (:name input)
                       :placeholder "..."
                       ;:on-change on-change
                       })
             #_(dom/span {:class "block mb-2 text-sm font-medium text-gray-900 dark:text-white"}
                       #_(condp = (:type input)
                           "uint256"
                           (str  (:name input) " "))))))

(defn abi-entry [entry]
  (dom/div {}
    (condp = (:type entry)
      "function" (function-comp entry)
      "constructor" ""
      "error" ""
      "event" ""
      :cljs.spec.alpha/invalid "inv"
      "default")))

(defn append-evm-transaction! [app]
  (let [selected (get-in @app [:smart-contract :selected])
        data (get-in @app [:function/id selected])]
    (m/append-mutation app [:root :smart-contract :list]
                       (fn [] (function-comp data)) [] {:use-cache? false})))

(defn smart-contract [{:keys [id address entries on-click on-change select-on-change abi-entries]}]
  (dom/div {:id id
            :class "dark bg-black w-screen h-screen text-white flex items-center justify-items-center w-screen justify-center"}
    (dom/div {:class "flex flex-col"}
      (dom/span {:class "flex max-w-2/3 gap-2"}
        (d/dropdown-select (mapv #(:name %) entries) select-on-change)
        (i/input {:id :address
                  :label "Address"
                  :placeholder "0x..."
                  :on-change on-change} address)
        (dom/div {:style {:paddng-top "20px"
                          :height "20px"}}
          (b/button "Add" on-click)))

      (dom/div {:id :list
                :class "dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500 p-2 text-md rounded-lg"}
        (for [a abi-entries]
          (abi-entry a))))))
