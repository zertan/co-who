(ns co-who.components.user
    (:require ["mr-who/dom" :as dom]
              ["mr-who/utils" :as u]
              [co-who.blueprint.avatar :refer [avatar-comp]]
              [co-who.blueprint.popover :refer [popover-comp]]
              [co-who.blueprint.blockie :as b :refer [blockie-comp]]))

(defn user-comp [{:keys [address] :or {address "0x0"}}]
  (list (fn [] {:user/id :user
                :address address})
        (fn [] (dom/div {:id :user
                         :data-popover-target :pop}
                 (popover-comp :pop address nil
                  (avatar-comp (b/make-blockie address)))))))
