(ns co-who.blueprint.modal
  (:require ["mr-who/dom" :as dom :refer [div button img]]
            ["./utils.mjs" :as u]
            ["blockies-ts" :as blockies]
            #_["heroicons/20/solid/x-mark" :as x-mark]
            ["./button.mjs" :refer [icon-button]]
            #_["./icons/x-mark.mjs" :refer [x-mark]]
            ["co-blue/icons" :refer [x-mark]]))

(defn modal [id on-close hidden? & children]
  (div {:id id
        :tabindex "-1"
        :aria-hidden "true"
        :class (u/add-hidden hidden? "fixed top-0 left-0 right-0 z-50 w-full p-4 overflow-x-hidden overflow-y-auto md:inset-0
                h-modal md:h-full bg-black items-center justify-center flex backdrop-blur-sm bg-opacity-75")}
    (div {:class "relative max-w-md md:h-auto"}
      (div {:class "relative rounded-lg shadow dark:bg-gray-700"}
        (icon-button {:data-modal-hide id} x-mark #(fn [] (println "mu")))
        (div {:class "px-6 py-6 lg:px-8"}
          children)))))
