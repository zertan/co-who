(ns co-who.blueprint.button
  (:require ["mr-who/dom" :as dom]))

(defn button [title on-click]
  (dom/button {:class "text-white bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium rounded-lg text-sm px-5 py-2.5 text-center inline-flex items-center dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800"
               :on-click on-click}
    title))

(defn icon-button [attr-map icon on-click]
  (dom/button (merge {:class "absolute top-1.5 right-0.5 text-gray-400 bg-transparent
                      hover:text-gray-900 rounded-lg text-sm p-1.5
                      ml-auto inline-flex items-center dark:hover:text-white"} attr-map)
    (dom/div {:class "w-6 h-6"} icon)))
