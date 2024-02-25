(ns co-who.pages.landing
  (:require ["mr-who/dom" :as dom]
             ["../composedb/client.mjs" :refer [on-click on-click-mut]]
             ["../blueprint/button.mjs" :refer [button]]
             ["../blueprint/datepicker.mjs" :refer [date-picker-comp]]))

(defn landing-comp []
  (list (fn [] {})
        (fn [] (dom/div {:id :landing}
                 (dom/span {:id :landing-span} "A pretty cool comp, with a datepicker.")
                 (date-picker-comp)
                 (button "run query"  on-click)
                 (button"run mutation"  on-click-mut) ))))
