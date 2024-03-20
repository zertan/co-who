(ns co-who.components.wizards.project.main
  (:require [mr-who.dom :as dom :refer [div p button]]
            [co-who.layout.router :as rc]
            [co-who.blueprint.stepper :refer [stepper-comp]]))

(defn project-wizard-comp [{:keys [step stepper wizard-router] :or {step :info
                                                                    wizard-router {}
                                                                    stepper {:id "stepper"
                                                                             :steps [{:id :info
                                                                                      :heading "Project Information"
                                                                                      :details "Enter basic project information."
                                                                                      :completed? false
                                                                                      :active? true
                                                                                      :href "/wizards/new-project/info"
                                                                                      :icon :clipboard-document-list}
                                                                                     {:id :contract
                                                                                      :heading "Deploy Contract"
                                                                                      :details "Deploy the Project to the Blockchain."
                                                                                      :completed? false
                                                                                      :active? false
                                                                                      :href "/wizards/new-project/contract"
                                                                                      :icon :cube}]}}}]
  (list (fn [] {:step step
                :stepper stepper
                :wizard-router wizard-router})
        (fn []
          (dom/div {:id :new-project
                       :class "flex justify-center"}
            (div {
                  :class "mt-4 w-fit h-fit"}
              (stepper-comp (merge stepper {:click-fns {}#_(click-fns id)})))
            (div {:id :wzr
                  :class "ml-24 relative"}
              (div {:class "my-4"}
                (p {:class "text-2xl mb-4"} "Create Project")
                (p {} "Please enter some basic info about your project. The info in this step can also be updated later."))
              ((second (rc/router-comp wizard-router)))

        #_(div {:class "flex flex-inline"}
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
