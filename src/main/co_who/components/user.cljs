(ns co-who.components.user
    (:require ["mr-who/dom" :as dom]
              ["mr-who/utils" :as u]
              ["../blueprint/avatar.mjs" :refer [avatar-comp]]
              ["../blueprint/popover.mjs" :refer [popover-comp]]
              ["../blueprint/blockie.mjs" :as b :refer [blockie-comp]]))

(defn user-comp [{:keys [address] :or {:address "0x0"}}]
  (list (fn [] {:address address})
        (fn [] (dom/div {:id :user
                         :data-popover-target :pop}
                 (popover-comp :pop address nil
                  (avatar-comp (b/make-blockie address)))))))
