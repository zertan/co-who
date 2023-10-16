(ns co-who.blueprint.blockie
  (:require ["mr-who/dom" :refer [img]]
            ["ethereum-blockies-base64" :as makeBlockie]))

(defn blockie-comp [{:keys [blockie/id address]}]
  (img {:class "w-10 h-10 rounded"
        :src (makeBlockie address)}))

