(ns co-who.layout.wizard-modal
  (:require [mr-who.dom :as dom]
            ["co-blue/icons" :refer [academic-cap]]
            [co-who.blueprint.modal :refer [modal]]
            [co-who.blueprint.card :as c]
            [co-who.mutations :as m]))

(defn modal-comp [{:keys [close-fn hidden?] :or {close-fn #(println %)
                                                 hidden? true}}]
  (list (fn [] {:close-fn close-fn
                :hidden? hidden?})
        (fn []
          (modal {:id "wizard-modal"
                  :on-close close-fn
                  :hidden? hidden?}
                 (dom/div {:class "flex flex-wrap items-center justify-center text-gray-900 dark:text-white"}
                   (c/card {:href "/wizards/new-project"
                            :heading "Create Project"}
                           (dom/div {:class "w-20 h-20"} academic-cap))
                   (c/card {:heading "Get verified"}
                           (dom/div {:class "w-20 h-20"} academic-cap))
                   )
                 
                 ))))

