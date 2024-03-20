(ns co-who.pages.activity
  (:require [mr-who.dom :as dom]
            [co-who.blueprint.blockie :refer [blockie-comp]]
            [co-who.blueprint.timeline :refer [timeline-comp]]
            ["mr-who/utils" :as u]
            [co-who.evm.util :as eu]))

(defn activity-comp []
  (list (fn [] {})
        (fn [](dom/div {:id :acitivity}
                (timeline-comp [{:event/id (u/random-uuid)
                                 :blockie (:data (blockie-comp {:blockie/id :b1
                                                                :address (:address (eu/account-from-private-key (eu/generate-private-key)))}))}
                                {:event/id (u/random-uuid)
                                 :blockie (:data (blockie-comp {:blockie/id :b2
                                                                :address (:address (eu/account-from-private-key (eu/generate-private-key)))}))}])))))
