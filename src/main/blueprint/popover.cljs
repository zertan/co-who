(ns co-who.blueprint.blockie
  (:require ["mr-who/dom" :as dom :refer [img]]
            ["mr-who/utils" :as u]))

(defn popover-comp []
  (dom/div {}
    (dom/button
        {:data-popover-target "popover-default",
         :type "button",
         :class
         "text-white bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium rounded-lg text-sm px-5 py-2.5 text-center dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800"}
      "Default popover")
    (dom/div
        {:data-popover nil,
         :id "popover-default",
         :role "tooltip",
         :class
         "absolute z-10 invisible inline-block w-64 text-sm text-gray-500 transition-opacity duration-300 bg-white border border-gray-200 rounded-lg shadow-sm opacity-0 dark:text-gray-400 dark:border-gray-600 dark:bg-gray-800"}
      (dom/div
          {:class
           "px-3 py-2 bg-gray-100 border-b border-gray-200 rounded-t-lg dark:border-gray-600 dark:bg-gray-700"}
        (dom/re {} :h3
            {:class "font-semibold text-gray-900 dark:text-white"}
          "Popover title"))
      (dom/div
          {:class "px-3 py-2"}
        (dom/p
            {}
          "And here's some amazing content. It's very engaging. Right?"))
      (dom/div {:data-popper-arrow nil}))))
