(ns co-who.blueprint.chart
  (:require ["mr-who/dom" :as dom]))

(defn chart-comp []
  (dom/div
      {:class
       "max-w-sm w-full bg-white rounded-lg shadow dark:bg-gray-800 p-4 md:p-6"}
      (dom/div
          {:class "flex justify-between"}
          (dom/div
              {}
            [:h5
             {:class
              "leading-none text-3xl font-bold text-gray-900 dark:text-white pb-2"}
             "32.4k"]
            [:p 
             {:class "text-base font-normal text-gray-500 dark:text-gray-400"}
             "Users this week"])
          (dom/div
              {:class
               "flex items-center px-2.5 py-0.5 text-base font-semibold text-green-500 dark:text-green-500 text-center"}
              "\n      12%\n      "
              (dom/svg
                  {:class "w-3 h-3 ml-1",
                   :aria-hidden "true",
                   :xmlns "http://www.w3.org/2000/svg",
                   :fill "none",
                   :viewBox "0 0 10 14"}
                  (dom/path
                      {:stroke "currentColor",
                       :stroke-linecap "round",
                       :stroke-linejoin "round",
                       :stroke-width 2,
                       :d "M5 13V1m0 0L1 5m4-4 4 4"}))))
      (dom/div {:id "area-chart"})
      (dom/div
          {:class
           "grid grid-cols-1 items-center border-gray-200 border-t dark:border-gray-700 justify-between"}
          (dom/div
              {:class "flex justify-between items-center pt-5"}
              (dom/button
                  {:id "dropdownDefaultButton",
                   :data-dropdown-toggle "lastDaysdropdown",
                   :data-dropdown-placement "bottom",
                   :class
                   "text-sm font-medium text-gray-500 dark:text-gray-400 hover:text-gray-900 text-center inline-flex items-center dark:hover:text-white",
                   :type "button"}
                  "\n        Last 7 days\n        "
                  #_(dom/svg
                      {:class "w-2.5 m-2.5 ml-1.5",
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
                  {:id "lastDaysdropdown",
                   :class
                   "z-10 hidden bg-white divide-y divide-gray-100 rounded-lg shadow w-44 dark:bg-gray-700"}
                  (dom/ul
                      {:class "py-2 text-sm text-gray-700 dark:text-gray-200",
                       :aria-labelledby "dropdownDefaultButton"}
                      (dom/li
                          {}
                          (dom/a
                              {:href "#",
                               :class
                               "block px-4 py-2 hover:bg-gray-100 dark:hover:bg-gray-600 dark:hover:text-white"}
                              "Yesterday"))
                      (dom/li
                          {}
                          (dom/a
                              {:href "#",
                               :class
                               "block px-4 py-2 hover:bg-gray-100 dark:hover:bg-gray-600 dark:hover:text-white"}
                              "Today"))
                      (dom/li
                          {}
                          (dom/a
                              {:href "#",
                               :class
                               "block px-4 py-2 hover:bg-gray-100 dark:hover:bg-gray-600 dark:hover:text-white"}
                              "Last 7 days"))
                      (dom/li
                          {}
                          (dom/a
                              {:href "#",
                               :class
                               "block px-4 py-2 hover:bg-gray-100 dark:hover:bg-gray-600 dark:hover:text-white"}
                              "Last 30 days"))
                      (dom/li
                          {}
                          (dom/a
                              {:href "#",
                               :class
                               "block px-4 py-2 hover:bg-gray-100 dark:hover:bg-gray-600 dark:hover:text-white"}
                              "Last 90 days"))))
              (dom/a
                  {:href "#",
                   :class
                   "uppercase text-sm font-semibold inline-flex items-center rounded-lg text-blue-600 hover:text-blue-700 dark:hover:text-blue-500  hover:bg-gray-100 dark:hover:bg-gray-700 dark:focus:ring-gray-700 dark:border-gray-700 px-3 py-2"}
                  "\n        Users Report\n        "
                  (dom/svg
                      {:class "w-2.5 h-2.5 ml-1.5",
                       :aria-hidden "true",
                       :xmlns "http://www.w3.org/2000/svg",
                       :fill "none",
                       :viewBox "0 0 6 10"}
                      (dom/path
                          {:stroke "currentColor",
                           :stroke-linecap "round",
                           :stroke-linejoin "round",
                           :stroke-width 2, 
                           :d "m1 9 4-4-4-4"})))))))
