(ns co-who.blueprint.icons.arrow-left-circle
  (:require ["mr-who/dom" :as dom]))

(def arrow-left-circle
  (dom/svg
 {:xmlns "http://www.w3.org/2000/svg",
  :viewBox "0 0 20 20",
  :fill "currentColor",
  :aria-hidden "true",
  :data-slot "icon"}
 (dom/g
  {:clip-path "url(#a)"}
  (dom/path
   {:fill-rule "evenodd",
    :d
    "M10 18a8 8 0 1 0 0-16 8 8 0 0 0 0 16Zm3.25-7.25a.75.75 0 0 0 0-1.5H8.66l2.1-1.95a.75.75 0 1 0-1.02-1.1l-3.5 3.25a.75.75 0 0 0 0 1.1l3.5 3.25a.75.75 0 0 0 1.02-1.1l-2.1-1.95h4.59Z",
    :clip-rule "evenodd"}))
 (dom/defs {} (dom/clipPath {:id "a"} (dom/path {:d "M0 0h20v20H0z"})))))
