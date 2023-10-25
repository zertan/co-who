(ns app
  (:require
            ["mr-who/dom" :as dom]
            ["mr-who/utils" :as u]
            ["./routing.mjs" :as r]
            ["./layout/router.mjs" :as rc]
            ["./components/user.mjs" :refer [user-comp]]
            ["./layout/main.mjs" :as main]
            ["./mutations.mjs" :as m]
            ["./blueprint/popover.mjs" :refer [popover-comp]]
            ["./blueprint/blockie.mjs" :refer [blockie-comp]]
            ["./blueprint/timeline.mjs" :refer [timeline-comp]]
            ["./blueprint/search.mjs" :refer [search-comp]]
            ["./blueprint/pagination.mjs" :refer [pagination]]
            ["./evm_client.mjs" :refer [client chains]]
            ["./evm_util.mjs" :as eu]
            ["flowbite" :as fb]
            #_["./blueprint/datepicker.mjs" :refer [date-picker-comp]])
  #_(:require-macros [mr-who.macros :as c]))

(defonce app (atom {}#_{:simple []
                        :mr-who/mounted-elements [:mr-who/id :root]
                        :mr-who/id {:root (js/document.getElementById "app")}}))

#_(println (let [a (assoc {} [:sad 1] 1)] a #_(get ":sad,1" a)))

#_(.then (eu/get-address client) #(swap! app assoc {:current-user %}))

(defn init []
  (swap! app conj (let [rc ((second (m/root-comp app {:router ((first (router-comp {:active-path "/"
                                                                                  :path-children [(let [comp (fn [] (dom/div {:id :route} "Active path: /"))]
                                                                                                    {:path "/"
                                                                                                     :listener (add-route app [:root :router :route] "/" comp)
                                                                                                     :comp comp})
                                                                                                  (let [comp (fn [] (dom/div {:id :route} "Active path: /babaei"))]
                                                                                                    {:path "/babaei"
                                                                                                     :listener (add-route app [:root :router :route] "/babaei" comp)
                                                                                                     :comp comp})]})))
                                                    :counter ((first (counter-comp {:on-click (inc-mutation app
                                                                                                            [:root :counter :value])})))})))]
                    (dom/append-helper (js/document.getElementById "app") (:node (:root rc)))
                    rc))
  
  (r/router.resolve)
  (set! (.-app js/window) app)
  (.then (eu/get-address client) #((replace-mutation [:root :header :n :n2 :n3 :user] (second (user-comp {:address %}))))))

(init)

#_(let [rc (root-comp {})]
    ((second rc)))


#_(merge-comp app (simple-comp {:simple/text "If you can see me, I am mounted a second time, new data."}) [:simple])

#_(println "sp: " (let [a (lazy-seq 1 2)] (nth a 0)))

#_(c/defc)

(defn n-div [attr-map & rest]
  (let [e (js/document.createElement "div")]
    
    ))

#_(defn initial-state [comp & children]
  (if (map? comp)
    (let [ident (:ident comp)]
      (if [id (get-in comp [])]
        (:initial-state comp)
        (merge (:initial-state comp) {ident (u/random-uuid)} )))
    (initial-state (first children) (rest children)))
  )


#_(println "init-state: " (ec/user-menu-2-comp))

#_(println (dom/append-child (js/document.getElementById "app") (dom/div {:class "bg-black w-screen h-screen"})))

#_(println "m: " (-> (js/Map.) (.set "a" 2)))
#_(println (let [aasd "a"] (u/zipmap [aasd "b"] [1 2])))

#_(let [b (:data (blockie-comp {:blockie/id "asd"
                              :address (:address (eu/account-from-private-key (eu/generate-private-key)))}))]
  (println "blockie: " (get-in b [:mr-who/mounted-elements 0 :mr-who/id :asd-2] )))

#_(dom/append-child (js/document.getElementById "app")
                  #_(dom/div {:class "bg-black w-screen h-screen dark"}
                    (h/header-comp)
                    (dom/div {:class "max-w-screen-xl"}
                      (timeline-comp [{:event/id (u/random-uuid)
                                       :blockie (:data (blockie-comp {:address (:address (eu/account-from-private-key (eu/generate-private-key)))}))}
                                      {:event/id (u/random-uuid)
                                       :blockie (:data (blockie-comp {:address (:address (eu/account-from-private-key (eu/generate-private-key)))}))}])
                      (pagination 5)
                      (date-picker-comp)
                      (popover-comp)
                      (ec/chain-menu-comp client chains (:chain client))
                      (ec/user-menu-2-comp app client
                                           {:chain-menu/id "1"
                                            :blockie {:address (:account client)}
                                            }))))

#_(n-div {}
         (date-picker-comp))

#_(reset! app {:counter-list/id {"1" {:counters [[:counter/id 1] [:counter/id 2]]
}}
             :blockie/id {"1" {:blockie/id 1
                               :address "0x0"
                               :mr-who/comp "co-who.blueprint.components/blockie-comp"
}}
             :chain-menu/id {"1" {:chain-menu/id "1"
                                  :blockie [:blockie/id 1]
}}
             :counter/id {"1" {:counter/id "1"
                               :value 1
                               :name "a"
}
                          "2" {:counter/id "2"
                               :value 2
                               :name "b"
}}
             :mr-who/id {"1" {:mr-who/id 1 :address [:mr-who/id 2]}}})

#_(println "dbv: " (u/db-value-at @app [:chain-menu/id 1]))

#_(println(render/render-and-meta-things (js/document.getElementById "app")
                                       (dom/div {:class "dark bg-gray-900 w-screen h-screen"}

                                         (h/header-comp)
                                         (ec/user-menu-2-comp)
                                         #_(date-picker-comp)
                                         #_(user-menu-comp (u/db-value-at @app [:chain-menu/id 1]))
                                         
                                         #_(f/form-comp)
                                         #_(chart-comp))

                                       #_(m/counter-list-comp app {:counter-list/id "1"})
                                       {:app app}))


#_(println @app)

#_(load-blockie app client)

#_(let [e (js/document.getElementById "dp")]
    (Datepicker. e  {:dark nil
                     :autohide true}))

#_(let [e (js/document.getElementById "area-chart")
      a (println "apa: " ApexCharts)
      chart (ApexCharts. e #js (get-in @app [:chart/id 1 :conf]))]
  (.render chart))

#_(reset! vdom (n/db :mr-who/id (r/render-and-meta-things (js/document.getElementById "app")
                                                          (m/counter-comp app vdom {:counter/id "1"})
                                                          {:app app})))


