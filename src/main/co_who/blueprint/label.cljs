(ns co-who.blueprint.label
  (:require [mr-who.dom :as dom]))

(defn label [title]
  (dom/label {:class "block mb-2 text-sm font-medium text-gray-900 dark:text-white"}
    title))

