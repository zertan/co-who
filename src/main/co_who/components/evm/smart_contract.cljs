(ns co-who.components.evm.smart-contract
  (:require [mr-who.dom :as dom]
            [cljs.spec.alpha :as s]
            [co-who.evm.specs :as es]))

(defn function [{:keys [name ] :as data}]
  (dom/div {} ))

(defn abi-entry [entry]
  #_(dom/div {} (str (s/conform ::es/abi-entry %)))
  (dom/div {}
           (condp #(first (keys (s/conform ::es/abi-entry %))) entry
             :function "function"
             :cljs.spec.alpha/invalid "inv"
             :default "default")))

(defn smart-contract [entries]
  (dom/div {:id :root}
           (dom/div {} (str (count entries)))
           (map #(abi-entry %) entries)))
