(ns co-who.blueprint.icons.arrow-long-right
  (:require ["mr-who/dom" :as dom]))

(def arrow-long-right
  (dom/svg
 {:xmlns "http://www.w3.org/2000/svg",
  :viewBox "0 0 20 20",
  :fill "currentColor",
  :aria-hidden "true",
  :data-slot "icon"}
 (dom/path
  {:fill-rule "evenodd",
   :d
   "M2 10a.75.75 0 0 1 .75-.75h12.59l-2.1-1.95a.75.75 0 1 1 1.02-1.1l3.5 3.25a.75.75 0 0 1 0 1.1l-3.5 3.25a.75.75 0 1 1-1.02-1.1l2.1-1.95H2.75A.75.75 0 0 1 2 10Z",
   :clip-rule "evenodd"})))