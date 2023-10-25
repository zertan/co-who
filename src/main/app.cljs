(ns app
  (:require #_["./micro_ns.mjs" :as m]
            ["mr-who/render" :as render]
            ["mr-who/dom" :as dom]
            ["mr-who/utils" :as u]
            ["./routing.mjs" :as r]
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

(defn add-route [app path route-path comp]
  (r/add-route r/router route-path
               #(let [render (comp)
                      replace-element (get-in @app (conj path :node))]
                  (dom/replace-node replace-element (:node (first (u/vals render))))
                  (swap! app assoc-in path (conj (first (u/vals render)))))))

(defn router-comp [{:keys [active-path path-children] :or {active-path "/"
                                                           path-children [{:path "/"
                                                                           :comp (fn [] (dom/div {:id :route} "Active path: /"))}
                                                                          {:path "/babaei"
                                                                           :comp (fn [] (dom/div {:id :route} "Active path: /babaei"))}]}}]
  (list (fn [] {:active-path active-path
                :path-children path-children})
        (fn [] (dom/div {:id :router}
                 (dom/div {:href "/" :data-navigo nil} "/ ")
                 (dom/div {:href "/babaei" :data-navigo nil} "babaei")
                 ((:comp
                   (first
                    (filterv #(= active-path (:path %)) path-children))))))))

(defn counter-comp [{:keys [value on-click] :or {value 0
                                                 on-click (fn [e] (println "toc"))}}]
  (list
   (fn [] {:value value
           :on-click on-click})
   (fn [] (dom/div {:id :counter}
            (dom/button  {:on-click on-click} "inc ")
            (dom/text  {:id :value} (str value))))))

(defn inc-mutation [app path]
  (fn [e]
    (let [replace-element (get-in @app (conj path :node))
          value (js/parseInt (first (get-in @app path)))
          render (merge {:node (js/document.createTextNode (inc value))}
                        [(inc value)])]
      (dom/replace-node replace-element (:node render))
      (swap! app assoc-in path render))))

(defn simple-comp [{:simple/keys [id text] :or {id (u/random-uuid)
                                                text "Simple comp mounted with default data."}}]
  (list (fn [] {:simple/id id
                :simple/text text})
        (fn [] (dom/div {:id (str "simple-id-" id)}
                 (dom/div {:id :simple-text
                           :class "text-white"} text)))))

(defn replace-mutation [path comp]
  (fn [e]
    (let [render (first (u/vals (comp)))
          replace-element (get-in @app (conj path :node))]
      (dom/replace-node replace-element (:node render))
      (swap! app assoc-in path render))))

(defn root-comp [app {:keys [simples counter router] :or {simples (mapv #((first (simple-comp {:simple/id %}))) [1 2])
                                                         counter ((first (counter-comp {})))
                                                         router nil #_((first (router-comp {})))}}]
  (list (fn [] {:simples simples
                :counter counter})
        (fn [] (dom/div {:id :root
                         :class "bg-black w-screen h-screen text-white dark"}
                 (h/header-comp)
                 (dom/div {:class "text-white"} "dsadsd")
                 
                 (dom/div {:id :dsa
                                  :class "text-white"}
                          (dom/div {:id "asd"} "dsadsd-a"))
                 (dom/button {:on-click (replace-mutation [:root :simple-id-1]
                                                          (second (simple-comp {:simple/id 1
                                                                                :simple/text "If you can see me, I am mounted a second time, new data."})))} "Replace")
                 (doall (for [simple simples]
                          ((second (simple-comp simple)))))
                 ((second (counter-comp counter)))
                 ((second (router-comp router)))))))

#_(println (let [a (assoc {} [:sad 1] 1)] a #_(get ":sad,1" a)))

(reset! app (let [rc ((second (root-comp app {:router ((first (router-comp {:active-path "/"
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


