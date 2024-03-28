(ns co-who.blueprint.dropdown
  (:require [mr-who.dom :as dom]))

(defn dropdown-comp [heading items]
  (dom/div {}
    (dom/button  {:id "dropdownInformationButton",
                  :data-dropdown-toggle "dropdownInformation",
                  :class "text-white bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium rounded-md text-sm px-5 py-2.5 text-center inline-flex items-center dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800",
                  :type "button"}
      (dom/span {} heading)
      #_(dom/svg
  {:class "w-2.5 h-2.5 ml-2.5",
   :aria-hidden "true",
   :xmlns "http://www.w3.org/2000/svg",
   :fill "none",
   :viewBox "0 0 10 6"}
  (dom/path
   {:stroke "currentColor",
    :stroke-linecap "round",
    :stroke-linejoin "round", 
    :stroke-width 2, 
    :d "m1 1 4 4 4-4"})))
    (dom/div
        {:id "dropdownInformation",
         :class "z-10 hidden bg-white divide-y divide-gray-100 rounded-sm shadow w-44 dark:bg-gray-700 dark:divide-gray-600"}
        #_(dom/div
            {:class "px-4 py-3 text-sm text-gray-900 dark:text-white"}
            (dom/div {} "Bonnie Green")
            (dom/div {:class "font-medium truncate"} "name@flowbite.com"))
        (dom/ul
            {:class "py-2 text-sm text-gray-700 dark:text-gray-200",
             :aria-labelledby "dropdownInformationButton"}
          (doall
           (for [l items]
             (dom/li {}
               (dom/a
                   {:href "#" :class "block px-4 py-2 hover:bg-gray-100 dark:hover:bg-gray-600 dark:hover:text-white"}
                 l))))))))

(defn dropdown-select [title items on-change]
  (dom/div {:class ""}
      (dom/label {:for "countries"
                  :class "block mb-2 text-sm font-medium text-gray-900 dark:text-white"}
        title)
      (dom/select {:id "countries"
                   :on-change on-change
                   :class "bg-gray-50 border border-gray-300 text-gray-900 text-md rounded-md focus:ring-blue-500
                           focus:border-blue-500 block w-full p-3 dark:bg-black dark:border-gray-600 dark:placeholder-gray-400
                           dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"}
                  (map-indexed (fn [i {:keys [value] :as item}]
                                 (dom/option {:class ""
                                              :selected (= i 0)} value) ) items)
       ))
  )
