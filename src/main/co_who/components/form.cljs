(ns form
  (:require ["mr-who/dom" :as dom]))

(defn form-comp []
  (dom/form {}
    (dom/div {:class "mb-6"}
            #_(dom/label
                  {:for "email",
                   :class
                   "block mb-2 text-sm font-medium text-gray-900 dark:text-white"}
                  "Your email")
            (dom/input
             {:type "email",
              :id "email",
              :class
              "bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500 invalid:[&:not(:placeholder-shown):not(:focus)]:border-red-500",
              :placeholder "name@flowbite.com",
              :required true
              }))
        (dom/div
            {:class "mb-6"}
            #_(dom/label
                  {:for "password",
                   :class
                   "block mb-2 text-sm font-medium text-gray-900 dark:text-white"}
                  "Your password")
            (dom/input
             {:type "password",
              :id "password",
              :class
              "bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500",
              :required true}))
        (dom/div
            {:class "flex items-start mb-6"}
            (dom/div
                {:class "flex items-center h-5"}
                (dom/input
                 {:id "remember",
                  :type "checkbox",
                  :value "",
                  :class
                  "w-4 h-4 border border-gray-300 rounded bg-gray-50 focus:ring-3 focus:ring-blue-300 dark:bg-gray-700 dark:border-gray-600 dark:focus:ring-blue-600 dark:ring-offset-gray-800 dark:focus:ring-offset-gray-800",
                  :required nil}))
            #_(dom/label
                  {:for "remember",
                   :class "ml-2 text-sm font-medium text-gray-900 dark:text-gray-300"}
                  "Remember me"))
        (dom/button
            {:type "submit",
             :class
             "text-white bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium rounded-lg text-sm w-full sm:w-auto px-5 py-2.5 text-center dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800"}
            "Submit")))
