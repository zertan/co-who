(ns co-who.components.evm.smart-contract
  (:require [mr-who.dom :as dom]
            [cljs.spec.alpha :as s]
            [co-who.evm.specs :as es]))

(defn address-comp [address]
  (dom/input {:class "bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg
focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500 invalid:[&:not(:placeholder-shown):not(:focus)]:border-red-500"}))

(defn function-comp [{:keys [name inputs stateMutability type] :as data}]
  (dom/div {}
           (dom/label {:class "block mb-2 text-sm font-medium text-gray-900 dark:text-white"}
                      name)
           #_(for [input inputs]
             (dom/label {:class "block mb-2 text-sm font-medium text-gray-900 dark:text-white"}
                        (str  (:name input) " ")))))

(defn abi-entry [entry]
  (dom/div {} (str  "aba")
           #_(condp = (:type entry)
               "function" (function-comp entry)
               "constructor" ""
               "error" ""
               "event" ""
               :cljs.spec.alpha/invalid "inv"
               "default")))

(defn smart-contract [entries]
  (let [entries-new (filterv #(vector? (s/conform ::es/abi-entry %)) entries)
        ;not-entries (filterv #(not (vector? (s/conform ::es/abi-entry %))) entries)
        ]
    (dom/div {:id :root}
             (dom/div {} "mu1221a31aa1aa12d2aa1aaa231a")
             #_(dom/div {} "----------------------------------------------------")
             #_(map #(abi-entry %) entries-new))))

#_(let [abi
      {:inputs [{:internalType "contract IVotes", :name "_token", :type "address"}], :stateMutability "nonpayable", :type "constructor"}]
  (println "abi: " (first (s/conform ::es/abi-entry abi))))
