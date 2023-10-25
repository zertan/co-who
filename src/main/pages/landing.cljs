(ns co-who.pages.landing
  (:require ["mr-who/dom" :as dom]
            ["../blueprint/datepicker.mjs" :refer [date-picker-comp]]))

(defn landing-comp []
  (list (fn [] {})
        (fn [] (dom/div {}
                 (dom/span {} "A pretty cool comp, with a datepicker.")
                 (date-picker-comp)))))
