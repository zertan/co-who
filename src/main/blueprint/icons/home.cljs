(ns co-who.blueprint.icons.home
  (:require ["mr-who/dom" :as dom]))

(def home
  (dom/svg
 {:xmlns "http://www.w3.org/2000/svg",
  :viewBox "0 0 20 20",
  :fill "currentColor",
  :aria-hidden "true",
  :data-slot "icon"}
 (dom/path
  {:fill-rule "evenodd",
   :d
   "M9.293 2.293a1 1 0 0 1 1.414 0l7 7A1 1 0 0 1 17 11h-1v6a1 1 0 0 1-1 1h-2a1 1 0 0 1-1-1v-3a1 1 0 0 0-1-1H9a1 1 0 0 0-1 1v3a1 1 0 0 1-1 1H5a1 1 0 0 1-1-1v-6H3a1 1 0 0 1-.707-1.707l7-7Z",
   :clip-rule "evenodd"})))