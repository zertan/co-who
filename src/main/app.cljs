(ns app
  (:require ["./micro_ns.mjs" :as m]
            ["mr-who/render" :as render]
            ["mr-who/dom" :as dom]
            ["mr-who/utils" :as u]
            ["./header.mjs" :as h]
            ["./form.mjs" :as f]
            ["./blueprint/blockie.mjs" :refer [blockie-comp]]
            ["./components/evm_chain_menu.mjs" :as ec :refer [chain-menu-comp user-menu-comp load-blockie]]
            ["./evm_client.mjs" :refer [client chains]]
            ["flowbite" :as fb]
                                        ;["apexcharts" :as ApexCharts]
                                        ;["./blueprint/chart.mjs" :refer [chart-comp]]
            ["./blueprint/datepicker.mjs" :refer [date-picker-comp]])
  #_(:require-macros [mr-who.macros :as c]))

(defonce app (atom nil))

#_(println client)

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
#_(println (dom/div {:a 1}
           (dom/div {:a 2} )
           (dom/div {:a 3} )))

#_(println (dom/append-child (js/document.getElementById "app") (dom/div {:class "bg-black w-screen h-screen"})))

(let [root (js/document.getElementById "app")]
  (dom/append-child root
                    (dom/re :div {:class "bg-black w-screen h-screen"}
                            #_(h/header-comp)
                            (ec/user-menu-2-comp
                             {:chain-menu/id "1"
                                        ;:blockie {:address "0x716237123678"}
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

