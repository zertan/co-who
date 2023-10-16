(ns co-who.mutations
  (:require ["mr-who/render" :as render]
            ["mr-who/dom" :as dom]))

#_(defn merge-comp [app comp comp-data path]
  (let [new-comp (comp/init-state comp)]
    (swap! app assoc-in [:chain-menu/id "1"] (first x))))
