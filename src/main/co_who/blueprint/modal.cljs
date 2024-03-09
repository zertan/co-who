(ns co-who.blueprint.modal
  (:require ["mr-who/dom" :as dom :refer [div button img]]
            ["mr-who/utils" :as u]
            ["blockies-ts" :as blockies]
            [co-who.blueprint.button :refer [icon-button]]
            ["co-blue/icons" :refer [x-mark]]))

(defn modal [{:keys [id on-close hidden?] :as props} & children]
  (div {:id id
        :tabindex "-1"
        :aria-hidden "true"
        :class (u/add-hidden hidden? "fixed top-0 left-0 right-0 z-50 w-full p-4 overflow-x-hidden overflow-y-auto md:inset-0
                h-modal md:h-full bg-black items-center justify-center flex backdrop-blur-sm bg-opacity-75")}
    children
    (div {:class "relative max-w-md md:h-auto"}
      (div {:class "relative rounded-lg shadow dark:bg-gray-700"}
        (icon-button {:data-modal-hide id} x-mark #(fn [] (println "mu")))
        (div {:class "px-6 py-6 lg:px-8"}
          children)))))
