(ns co-who.blueprint.blockie
  (:require ["mr-who/dom" :as dom :refer [img]]
            ["mr-who/utils" :as u]
            ["blockies-ts" :as blockies]
            #_["ethereum-blockies-base64" :refer [makeBlockie]]))

(defn make-blockie [address]
  (.toDataURL (blockies/create #js {:seed address})))

(defn blockie-comp [{:keys [blockie/id address]
                     :or {blockie/id (u/random-uuid)
                          address "0x0"}}]
  (let [node (dom/img {:src (make-blockie address)})
        node-id "asd-2";(u/random-uuid)
        data {:blockie/id id
              :address address
              :mr-who/mounted-elements [{:mr-who/id (assoc {} node-id node)}]}]
    {:render node
     :data data}))
