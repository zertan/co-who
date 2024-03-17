(ns co-who.layout.main
  (:require [mr-who.dom :as dom]
            [co-who.layout.header :as h]
            [co-who.layout.router :as rc]
            [co-who.evm.abi :refer [token-abi]]
            #_["./footer.mjs" :as f]
            [co-who.layout.wizard-modal :as wm]
            #_["./../mutations.mjs" :as m]))

(defn root-comp [{:keys [router header wizard-modal] :or {router ((first (rc/router-comp {})))
                                                          header ((first (h/header-comp {})))
                                                          wizard-modal ((first (wm/modal-comp {})))} :as props}
                 & children]
  (list (fn [] {:router router
                :header header
                :wizard-modal wizard-modal})
        (fn [] (dom/div {:id :root
                         :class "bg-black w-screen h-screen text-white dark"}
                 ((second (h/header-comp header)))
                 ((second (rc/router-comp router
                                          children)))
                 ((second (wm/modal-comp wizard-modal)))))))


(defn simple-comp []
  (list (fn [] {})
        (fn [] (dom/div {:id :root}
                        (dom/div {} (str abi))))))
