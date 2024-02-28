(ns co-who.components.wizards.project.contract-step
  (:require ["mr-who/dom" :as dom :refer [div p button]]
            ["../../../blueprint/card.mjs" :as c]))

(defn contract-step [{:keys [project active? complted?] :as props :or {active? false
                                                                       completed? false
                                                                       project {:project/id 1
                                                                                :project/contract nil}}}]
  (list (fn [] {})
        (fn []
          (div {:class "mb-4"}
            (if-not (= (:project/id project) -1)
              (c/card {}
                      (div {:class "text-2xl flex gap-4"}
                        (if (or (nil? (:project/contract project)))
                          (div {:class "flex gap-4"}
                            "Deploying contract ..."
                            #_(f/ui-spinner {:size "lg"}))
                          "Contract deployed!"))))))))
