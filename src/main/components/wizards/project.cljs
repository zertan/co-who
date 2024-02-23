(ns co-who.components.wizards.project
    (:require ["mr-who/dom" :as dom :refer [div h1 p button]]
              ["mr-who/utils" :as u]
              ["../blueprint/avatar.mjs" :refer [avatar-comp]]
              ["../blueprint/popover.mjs" :refer [popover-comp]]
              ["../blueprint/blockie.mjs" :as b :refer [blockie-comp]]))

(defn project-wizard-comp [{:keys [step] :or {:step :info}}]
  (list (fn [] {:step step})
        (fn []
          (dom/button {:class "flex justify-center"}
      (div {:class "mt-4 w-fit h-fit"}
        #_(ui-stepper stepper {:click-fns (click-fns id)}))
      (div {:class "ml-24 relative"}
        (div {:class "my-4"}
          (h1 {:class "text-2xl mb-4"} "Create Project")
          (p {} "Please enter some basic info about your project. The info in this step can also be updated later."))

        #_(ui-wizard-router wizard-router)

        (div {:class "flex flex-inline"}
          (if-let [next (get-in step-route [step :back])]
            (button {:color "gray"
                          :onClick #(let [next-state (get step-route next)]
                                      ((:onEnter next-state) this (:comp next-state) {:id id})
                                      (comp/set-state! this {:active next}))}
              (div :.h-5.w-5 arrow-left)))
          
          (if-let [next (get-in step-route [step :forward])]
            (if-not (tempid/tempid? id)
              (div {:class "absolute right-0"}
                (f/ui-button {:color "gray"
                              :onClick #(let [next-state (get step-route next)]
                                          ((:onEnter next-state) this (:comp next-state) {:id id})
                                          (comp/set-state! this {:active next}))}
                  (div :.h-5.w-5 arrow-right)))))))))))
