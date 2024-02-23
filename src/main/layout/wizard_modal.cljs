(ns co-who.layout.wizard-modal
  (:require ["mr-who/dom" :as dom]
            ["../blueprint/modal.mjs" :refer [modal]]
            ["../mutations.mjs" :as m]))

(defn modal-comp [{:keys [close-fn hidden?] :or {close-fn #(println %)
                                                 hidden? false}}]
  (list (fn [] {:close-fn close-fn
                :hidden? hidden?})
        (fn []
          (modal "wizard-modal" close-fn hidden?
                 (dom/div {} "aba")))))

