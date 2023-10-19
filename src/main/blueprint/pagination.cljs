(ns co-who.blueprint.pagination
  (:require ["mr-who/dom" :as dom]))


(defn tab-number [nr]
  (dom/a
      {:href "#",
       :class
       "flex items-center justify-center px-4 h-10 leading-tight text-gray-500 bg-white border border-gray-300 hover:bg-gray-100 hover:text-gray-700 dark:bg-gray-800 dark:border-gray-700 dark:text-gray-400 dark:hover:bg-gray-700 dark:hover:text-white"}
    nr))

(defn pagination [pages]
  (dom/nav
      {:aria-label "Page navigation example"}
    (dom/ul
        {:class "inline-flex -space-x-px text-base h-10"}
      (dom/li
          {}
        (dom/a
            {:href "#",
             :class
             "flex items-center justify-center px-4 h-10 ml-0 leading-tight text-gray-500 bg-white border border-gray-300 rounded-l-lg hover:bg-gray-100 hover:text-gray-700 dark:bg-gray-800 dark:border-gray-700 dark:text-gray-400 dark:hover:bg-gray-700 dark:hover:text-white"}
          "Previous"))
      (doall
       (for [i (vec (range pages))]
         (dom/li {}
             (tab-number i))))
      (dom/li
          {}
        (dom/a
            {:href "#",
             :class
             "flex items-center justify-center px-4 h-10 leading-tight text-gray-500 bg-white border border-gray-300 rounded-r-lg hover:bg-gray-100 hover:text-gray-700 dark:bg-gray-800 dark:border-gray-700 dark:text-gray-400 dark:hover:bg-gray-700 dark:hover:text-white"}
          "Next")))))

