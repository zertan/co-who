(ns co-who.blueprint.pagination
  (:require [clojure.string :as string]
            [mr-who.dom :as dom]))


(defn tab-thing [text]
  (dom/li {}
    (dom/a {:href "#"
            :class "flex items-center justify-center px-4 h-10 leading-tight text-gray-500 dark:border-gray-800 border-gray-300 hover:bg-gray-100 hover:text-gray-700 dark:text-gray-400 dark:hover:bg-gray-700 dark:hover:text-white"}
        text)))

(defn pagination [pages]
  (dom/nav {:aria-label "Page navigation example"}
    (dom/ul {:class "inline-flex -space-x-px text-base h-10 bg-white dark:bg-[#101014] border dark:border-gray-800 border-gray-300 rounded-r-lg rounded-l-lg"}
      (tab-thing "Previous")
      (doall
       (for [i (vec (range 1 pages))]
         (dom/li {}
             (tab-thing i))))
      (tab-thing "Next"))))

