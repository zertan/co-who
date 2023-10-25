(ns co-who.pages.landing
  (:require ["mr-who/dom" :as dom]
            ["../blueprint/blockie.mjs" :refer [blockie-comp]]
            ["../blueprint/timeline.mjs" :refer [timeline-comp]]
            ["mr-who/utils" :as u]
            ["../evm/util.mjs" :as eu]))

(defn activity-comp []
  (list (fn [] {})
        (fn [](dom/div {}
                (timeline-comp [{:event/id (u/random-uuid)
                                 :blockie (:data (blockie-comp {:blockie/id :b1
                                                                :address (:address (eu/account-from-private-key (eu/generate-private-key)))}))}
                                {:event/id (u/random-uuid)
                                 :blockie (:data (blockie-comp {:blockie/id :b2
                                                                :address (:address (eu/account-from-private-key (eu/generate-private-key)))}))}])))))
