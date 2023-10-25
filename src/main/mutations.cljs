(ns co-who.mutations
  (:require ["mr-who/utils" :as u]
            ["mr-who/dom" :as dom]))

(defn replace-mutation [app path comp]
  (let [render (first (u/vals (comp)))
        replace-element (get-in @app (conj path :node))]
    (dom/replace-node replace-element (:node render))
    (swap! app assoc-in path render)))

#_(defn merge-comp [app comp comp-data path]
    (let [new-comp (comp/init-state comp)]
      (swap! app assoc-in [:chain-menu/id "1"] (first x))))

#_(defn merge-comp [app comp path]
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
