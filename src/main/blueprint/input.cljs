(ns co-who.blueprint.input
  (:require ["mr-who/dom" :as dom :refer [div button img]]))

(defn input [{:keys [id placeholder on-submit on-change]} & children]
  (dom/div {:id :idiv
            :class "mb-6"}
    (dom/label {:class "block mb-2 text-sm font-medium text-gray-900 dark:text-white"}
      "Name")
    (dom/input {:id id
                :class "bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500 invalid:[&:not(:placeholder-shown):not(:focus)]:border-red-500"
                :placeholder placeholder
                :on-change on-change
                :on-submit on-submit
                :value children
                :required true
                })))
