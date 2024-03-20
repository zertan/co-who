(ns co-who.components.test
  (:require [mr-who.dom :as dom]
            ["mr-who/utils" :as u]))

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
          value (inc (js/parseInt (:primitive (get-in @app path))))
          render (merge {:node (js/document.createTextNode value)
                         :primitive value})]
      (dom/replace-node replace-element (:node render))
      (swap! app assoc-in path render))))

(defn simple-comp [{:simple/keys [id text] :or {id (u/random-uuid)
                                                text "Simple comp mounted with default data."}}]
  (list (fn [] {:simple/id id
                :simple/text text})
        (fn [] (dom/div {:id (str "simple-id-" id)}
                 (dom/div {:id :simple-text
                           :class "text-white"} text)))))

#_(defn click-factory [app id]
  (fn [e]
    (swap! app update-in [:counter/id id :value] inc)
    
    (let [v (get-in @app [:counter/id id :value])
          elements (get-in @app [:counter/id id :mr-who/mounted-elements])
          mounted-ids (filterv #(not (nil? %)) (mapv #(if (= (first %) :value) (second %)) elements))]
      (doall
       (for [id mounted-ids]
        #_(println "id: " mounted-ids)
        (let [new-node (js/document.createTextNode v)
              node (get-in @app (conj id :element))]
          #_(println "new: " new-node)
          #_(println "old: " (conj id :element))
          (dom/replace-node new-node node)
          #_(println "aasdd")
          (swap! app assoc-in (conj id :element) new-node)))))))

#_(defn counter-comp
  #_([app vdom {:keys [id]} & children] (into (counter-comp app vdom id) children))
  ([app {:counter/keys [id]}]
   [:div {}
    [:button {:on-click (click-factory app id)} "click"]
    [:div {} "Counter " [:app-cursor [:counter/id id :name]] ": " [:app-cursor [:counter/id id :value]]]]))

#_(defn counter-list-comp
  #_([ident & children] (into (counter-list-comp) children))
  ([app {:counter-list/keys [id]}]
   [:div {}
    (for [c (get-in @app [:counter-list/id id :counters]) #_[:app-cursor [:counter-list/id id :counters]]]
      (counter-comp app {:counter/id (second c)}))]))


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


(comment
  #_(dom/button {:on-click (replace-mutation [:root :simple-id-1]
                                             (second (simple-comp {:simple/id 1
                                                                   :simple/text "If you can see me, I am mounted a second time, new data."})))} "Replace")
  #_(doall (for [simple simples]
             ((second (simple-comp simple)))))
  #_((second (counter-comp counter))))
