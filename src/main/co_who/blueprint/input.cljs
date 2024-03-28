(ns co-who.blueprint.input
  (:require [mr-who.dom :as dom :refer [div button img]]
            [co-who.blueprint.label :as l]))

(defn on-change [app ident]
  (fn  [e]
    (swap! app assoc-in ident e.target.value)))

(defn input [{:keys [id label placeholder on-submit on-change required?] :or {required? false}} children]
  (dom/div {:id id
            :class "mb-6"}
    (l/label label)
    (dom/input {:id :nice-input
                :class "bg-gray-50 border border-gray-300 text-gray-900 text-md rounded-lg focus:ring-blue-500
                        focus:border-blue-500 block w-full p-3 dark:bg-black dark:border-gray-600 dark:placeholder-gray-400
                        dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500 invalid:[&:not(:placeholder-shown):not(:focus)]:border-red-500"
                :placeholder placeholder
                :on-change on-change
                :on-submit on-submit
                :value children
                :required required?
                })))

(defn number-input [{:keys [id label placeholder on-submit on-change required?]} & children]
  (dom/div {:class "mb-6"}
    (l/label label)
    (dom/input
     {:type "number"
      :aria-describedby "helper-text-explanation"
      :class "bg-gray-50 border border-gray-300 text-gray-900 text-md rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full
              p-3 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
      :placeholder placeholder
      :on-change on-change
      :on-submit on-submit
      :value children
      :required required?})))
