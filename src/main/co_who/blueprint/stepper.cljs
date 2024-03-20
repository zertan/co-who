(ns co-who.blueprint.stepper
    (:require [mr-who.dom :as dom :refer [div p button li ol span h3 a]]
              [co-blue.icons.check :refer [check cube]]
              [co-blue.icons.clipboard-document-list :refer [clipboard-document-list cube]]
              [co-blue.icons.cube :refer [cube]]))

(def icon-map {:check check
               :clipboard-document-list clipboard-document-list
               :cube cube})

#_(def ui-icon (comp/computed-factory Icon))

(defn step-comp [{:keys [id heading details icon completed? active? on-click href] :or {id "step-x"
                                                                                        heading "Step" details "Step description"
                                                                                        icon :check completed? false
                                                                                        active? false href "#"
                                                                                        on-click (fn [e])}}]
  (li {:id id
       :class "mb-10 ml-6"}
    (if completed?
      (span {:class "absolute flex items-center justify-center w-8 h-8
                       bg-green-200 rounded-full -left-4 ring-4 ring-white
                       dark:bg-green-900"}
        (div {:class "w-5 h-5 text-green-500 dark:text-green-400"}
          check))
      (span {:class "absolute flex items-center justify-center w-8 h-8
                       bg-gray-100 rounded-full -left-4 ring-4 ring-white
                       dark:ring-zinc-900 dark:bg-zinc-700"}
        (div {:class (if active?
                       "w-5 h-5 text-black dark:text-white"
                       "w-5 h-5 text-gray-500 dark:text-gray-400")}
          (get icon-map icon))))
    (a {:class "hover:cursor-pointer"
        :href href
        :data-navigo nil
        :draggable "false"
        :on-click on-click}
      (h3 {:class (if active?
                    "font-medium leading-tight text-black dark:text-white"
                    "font-medium leading-tight")} heading)
      (p {:class (if active?
                   "text-sm text-black dark:text-white"
                   "text-sm")} details))))

(defn stepper-comp [{:keys [id steps click-fns] :or {id "stepper-x"
                                                     steps []
                                                     click-fns {}}}]
  (dom/ol {:id id
           :class "select-none relative text-gray-500 border-l border-gray-200
                   dark:border-gray-700 dark:text-gray-400"}
    (doall
     (for [step steps]
       (step-comp (merge step {:on-click (get click-fns (:id step))}))))))
