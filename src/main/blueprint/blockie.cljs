(ns co-who.blueprint.blockie
  (:require ["mr-who/dom" :as dom :refer [img]]
            ["mr-who/utils" :as u]
            ["ethereum-blockies-base64" :as makeBlockie]))

(defn blockie-comp [{:keys [blockie/id address]
                     :or {blockie/id (u/random-uuid)
                          address "0x0"}}]
  (img {:class "w-10 h-10 rounded"
        :src (makeBlockie address)}))
