(ns co-who.pages.search
  (:require ["mr-who.dom" :as dom]
            [co-who.blueprint.blockie :refer [blockie-comp]]
            [co-who.blueprint.timeline :refer [timeline-comp]]
            [co-who.evm.util :as eu]))

(defn search-comp []
  (list (fn [] {})
        (fn [](dom/div {:id :search}
                (timeline-comp [{:event/id (random-uuid)
                                 :blockie (:data (blockie-comp {:blockie/id :b1
                                                                :address (:address (eu/account-from-private-key (eu/generate-private-key)))}))}
                                {:event/id (random-uuid)
                                 :blockie (:data (blockie-comp {:blockie/id :b2
                                                                :address (:address (eu/account-from-private-key (eu/generate-private-key)))}))}])))))
