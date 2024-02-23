(ns co-who.blueprint.icons.folder-open
  (:require ["mr-who/dom" :as dom]))

(def folder-open
  (dom/svg
 {:xmlns "http://www.w3.org/2000/svg",
  :viewBox "0 0 20 20",
  :fill "currentColor",
  :aria-hidden "true",
  :data-slot "icon"}
 (dom/path
  {:d
   "M4.75 3A1.75 1.75 0 0 0 3 4.75v2.752l.104-.002h13.792c.035 0 .07 0 .104.002V6.75A1.75 1.75 0 0 0 15.25 5h-3.836a.25.25 0 0 1-.177-.073L9.823 3.513A1.75 1.75 0 0 0 8.586 3H4.75ZM3.104 9a1.75 1.75 0 0 0-1.673 2.265l1.385 4.5A1.75 1.75 0 0 0 4.488 17h11.023a1.75 1.75 0 0 0 1.673-1.235l1.384-4.5A1.75 1.75 0 0 0 16.896 9H3.104Z"})))