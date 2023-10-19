(ns co-who.blueprint.button
  (:require ["mr-who/dom" :as dom]))

(defn button [title on-click]
  (dom/button {:class "text-white bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium rounded-lg text-sm px-5 py-2.5 text-center inline-flex items-center dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800"
               :on-click on-click}
    title))
