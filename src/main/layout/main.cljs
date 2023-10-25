(ns co-who.layout.main
  (:require ["mr-who/dom" :as dom]
            ["mr-who/utils" :as u]
            ["./header.mjs" :as h]
            ["./router.mjs" :as r]
            ["../blueprint/blockie.mjs" :refer [blockie-comp]]
            ["../blueprint/timeline.mjs" :refer [timeline-comp]]
            ["../evm_util.mjs" :as eu]))


(defn root-comp [app {:keys [router header] :or {router ((first (r/router-comp {})))
                                                 header ((first (h/header-comp {})))}}]
  (list (fn [] {:router router
                :header header})
        (fn [] (dom/div {:id :root
                         :class "bg-black w-screen h-screen text-white dark"}
                 ((second (h/header-comp header)))
                 (timeline-comp [{:event/id (u/random-uuid)
                                  :blockie (:data (blockie-comp {:blockie/id :b1
                                                                 :address (:address (eu/account-from-private-key (eu/generate-private-key)))}))}
                                 {:event/id (u/random-uuid)
                                  :blockie (:data (blockie-comp {:blockie/id :b2
                                                                 :address (:address (eu/account-from-private-key (eu/generate-private-key)))}))}])                 
                 #_(dom/button {:on-click (replace-mutation [:root :simple-id-1]
                                                          (second (simple-comp {:simple/id 1
                                                                                :simple/text "If you can see me, I am mounted a second time, new data."})))} "Replace")
                 #_(doall (for [simple simples]
                          ((second (simple-comp simple)))))
                 #_((second (counter-comp counter)))
                 ((second (r/router-comp router)))))))
