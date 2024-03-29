(ns co-who.layout.main
  (:require ["mr-who/dom" :as dom]
            ["./header.mjs" :as h]
            ["./router.mjs" :as rc]
            #_["./footer.mjs" :as f]
            ["./wizard_modal.mjs" :as wm]
            #_["./../mutations.mjs" :as m]))

(defn root-comp [app {:keys [router header wizard-modal] :or {router ((first (rc/router-comp {})))
                                                              header ((first (h/header-comp {})))
                                                              wizard-modal ((first (wm/modal-comp {})))}}]
  (list (fn [] {:router router
                :header header
                :wizard-modal wizard-modal})
        (fn [] (dom/div {:id :root
                         :class "bg-black w-screen h-screen text-white dark"}
                 ((second (h/header-comp header)))
                 ((second (rc/router-comp router)))
                 ((second (wm/modal-comp wizard-modal)))))))
