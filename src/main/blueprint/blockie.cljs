(ns co-who.blueprint.blockie
  (:require ["mr-who/dom" :as dom :refer [img]]
            ["mr-who/utils" :as u]
            ["ethereum-blockies-base64" :as makeBlockie]))

(defn simple-blockie [address]
  (makeBlockie address))

(defn blockie-comp [{:keys [blockie/id address]
                     :or {blockie/id (u/random-uuid)
                          address "0x0"}}]
  (let [node (dom/img {:src (makeBlockie address)})
        node-id (u/random-uuid)
        data {:blockie/id id
              :address address
              :mr-who/mounted-elements [{:mr-who/id {node-id node}}]}]
    {:render node
     :data data}))
