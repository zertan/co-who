(ns co-who.blueprint.card
  (:require [mr-who.dom :as dom]))

(defn card [{:keys [heading href] :as props} & children]
  (dom/a
      {:href (or href "#")
       :data-navigo nil
       :aria-current "page"
       :class "block max-w-sm p-6 bg-white border border-gray-200 rounded-lg shadow hover:bg-gray-100 dark:bg-gray-800 dark:border-gray-700 dark:hover:bg-gray-700"}
    (dom/p
        {:class "mb-2 text-2xl font-bold tracking-tight text-gray-900 dark:text-white"}
      heading)
    children
    #_(dom/p
        {:class "font-normal text-gray-700 dark:text-gray-400"}
      
        )))
