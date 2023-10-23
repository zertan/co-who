(ns app
  (:require #_["./micro_ns.mjs" :as m]
            ["mr-who/render" :as render]
            ["mr-who/dom" :as dom]
            ["mr-who/utils" :as u]
            ["./layout/header.mjs" :as h]
            ["./form.mjs" :as f]
            ["./blueprint/popover.mjs" :refer [popover-comp]]
            ["./blueprint/blockie.mjs" :refer [blockie-comp]]
            ["./blueprint/timeline.mjs" :refer [timeline-comp]]
            ["./blueprint/search.mjs" :refer [search-comp]]
            ["./blueprint/pagination.mjs" :refer [pagination]]
            ["./components/evm_chain_menu.mjs" :as ec :refer [chain-menu-comp user-menu-comp load-blockie]]
            ["./evm_client.mjs" :refer [client chains]]
            ["./evm_util.mjs" :as eu]
            ["flowbite" :as fb]
                                        ;["apexcharts" :as ApexCharts]
                                        ;["./blueprint/chart.mjs" :refer [chart-comp]]
            ["./blueprint/datepicker.mjs" :refer [date-picker-comp]])
  #_(:require-macros [mr-who.macros :as c]))

(defonce app (atom {:simple []
                    :mr-who/mounted-elements [:mr-who/id :root]
                    :mr-who/id {:root (js/document.getElementById "app")}}))


(defn merge-comp [app comp path]
  (let [render ((second comp))
        replace-element-ident (get-in @app (conj path :mr-who/mounted-elements))]
    (println "render: " render)
    #_(println "me:" (conj path :mr-who/mounted-elements))
    #_(println replace-element-ident)
    #_(println "me2: " (get-in @app replace-element-ident))
    (dom/append-helper (get-in @app replace-element-ident) (:node (first (u/vals render))))
    #_(println "data:" (first comp))
    (let [data (first comp)
          render-id (u/random-uuid)
          element render
          ]
      #_(println "new: " (conj data
                             {:mr-who/mounted-elements [[:mr-who/id render-id]]}
                             {:mr-who/id (assoc (assoc {} (second replace-element-ident) element)
                                                render-id render)}))
      #_(swap! app merge (conj data
                             {:mr-who/mounted-elements [[:mr-who/id render-id]]}
                             {:mr-who/id (assoc (assoc {} (second replace-element-ident) element)
                                                render-id render)}))
      #_(println @app)
      [((first comp)) render])))


(defn counter-comp [{:keys [value on-click] :or {value 0
                                                 on-click (fn [e] (println "toc"))}}]
  (list
   (fn [] {:value value
           :on-click on-click})
   (fn [] (dom/div {}
            {:nil (dom/button {:on-click on-click} "inc ")}
            {:value (dom/text {} (str value))}))))

(defn inc-mutation [app path]
  (fn [e]
    (let [replace-element (get-in @app (conj path :node))
          value (js/parseInt (get-in @app (conj path :children :value :children)))
          render ((second (counter-comp {:value (+ value 1)})))]
      (println replace-element)
      (println "v " value)
      (dom/replace-node replace-element (:node render))
      (swap! app assoc-in path render)
      (println @app)
      )
    )
  )

(defn simple-comp [{:simple/keys [id text] :or {id (u/random-uuid)
                                                text "Simple comp mounted with default data."}}]
  (list (fn [] {:simple/id id
                :simple/text text})
        (fn [] (dom/div {}
                 {:nil (dom/div {}
                         {:nil (dom/div {} "ddddddasd")})}
                 {:simple/text (dom/div {:class "text-white"} text)}))))

(defn funny-mutation [e]
  (let [path [:root :children :simple-0]
        render ((second (simple-comp {:simple/text "If you can see me, I am mounted a second time, new data."})))
        replace-element (get-in @app (conj path :node))]
    (println "re: " replace-element)
    (dom/replace-node replace-element (:node render))
    (swap! app assoc-in path render)
    (println @app)))


(defn root-comp [app {:keys [simple counter] :or {simple ((first (simple-comp {})))
                                                  counter ((first (counter-comp {})))}}]
  (list (fn [] {:simple simple
                :counter counter})
        (fn [] (dom/div {:class "bg-black w-screen h-screen text-white dark"}
                 {:nil (dom/div {:class "text-white"} "dsadsd")}
                 
                 {:nil (dom/div {:class "text-white"}
                         {:nil (dom/span {} "dsadsd-a")})}
                 {:nil (dom/button {:on-click funny-mutation} "Replace")}
                 (doall (for [i (range 2)]
                          (assoc {} (str "simple-" i) ((second (simple-comp simple))))))
                 {:counter ((second (counter-comp counter)))}
                 {:nil (dom/div {:class "tet-white"}
                         (str @app))}))))

#_(println (let [a (assoc {} [:sad 1] 1)] a #_(get ":sad,1" a)))

(reset! app (let [rc {:root ((second (root-comp app {:counter ((first (counter-comp {:on-click (inc-mutation app [:root :children :counter])})))})))}]
              (dom/append-helper (js/document.getElementById "app") (:node (:root rc)))
              rc))

(println "app: " @app)

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


