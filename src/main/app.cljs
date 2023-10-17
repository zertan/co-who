(ns app
  (:require ["./micro_ns.mjs" :as m]
            ["mr-who/render" :as render]
            ["mr-who/dom" :as dom]
            ["mr-who/utils" :as u]
            ["./header.mjs" :as h]
            ["./form.mjs" :as f]
            ["./ceramic.mjs" :as c]
            ["./components/evm_chain_menu.mjs" :refer [chain-menu-comp user-menu-comp load-blockie]]
            ["./evm_client.mjs" :refer [client chains]]
            ["flowbite" :as fb]
                                        ;["apexcharts" :as ApexCharts]
                                        ;["./blueprint/chart.mjs" :refer [chart-comp]]
            ["./blueprint/datepicker.mjs" :refer [date-picker-comp]])
  #_(:require-macros [mr-who.macros :as c]))

(defonce app (atom nil))

(println client)

#_(c/defc)

(defn n-div [attr-map & rest]
  (let [e (js/document.createElement "div")]
    
    ))

#_(n-div {}
       (date-picker-comp))

(reset! app {:counter-list/id {"1" {:counters [[:counter/id 1] [:counter/id 2]]
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
             :button-switch/id {"1" {}}
             :root/id {"1" {:show? true}}
             :mr-who/id {"1" {:mr-who/id 1 :address [:mr-who/id 2]}}})

(defn root-comp [{:keys [root/id]} & children]
  (into (dom/div {:class "dark bg-gray-900 w-screen h-screen"}) children))

(defn button-switch []
  (dom/button {:class "text-white bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium rounded-lg text-sm px-5 py-2.5 text-center inline-flex items-center dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800"
               :on-click #(swap! app update-in [:root/id "1" :show?] not)} "Toggle visibility"))

(println "dbv: " (u/db-value-at @app [:chain-menu/id 1]))

(println "app: "(render/render-and-meta-things (js/document.getElementById "app")
                                               (root-comp {:root/id "1"}
                                                
                                                (h/header-comp)
                                                (button-switch)
                                                (let [show? (u/db-value-at @app [:root/id "1" :show?])]
                                                  (when show?
                                                    (dom/div {}
                                                      (date-picker-comp)
                                                      (user-menu-comp (u/db-value-at @app [:chain-menu/id 1])))))
                                                
                                                #_(f/form-comp)
                                                #_(chart-comp))

                                               #_(m/counter-list-comp app {:counter-list/id "1"})
                                               {:app app}))


(println @app)

(load-blockie app client)

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
