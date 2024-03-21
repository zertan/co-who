(ns co-who.mutations
  (:require [mr-who.dom :as dom]))

(defn replace-mutation [app path comp ident {:keys [use-cache?] :or {use-cache? true}}]
  (let [cache (if use-cache? (get-in @app (conj ident :cache)))
        render (if cache cache (first (vals (comp))))
        replace-element (get-in @app (conj path :mr-who/node))]
    (dom/replace-node replace-element (:mr-who/node render))
    (swap! app assoc-in path render)
    (when-not cache
      (swap! app assoc-in (conj ident :cache) render))
    (js/console.log @app)))

(defn append-mutation [app path comp ident {:keys [use-cache?] :or {use-cache? true}}]
  (let [cache (if use-cache? (get-in @app (conj ident :cache)))
        render (if cache cache (first (vals (comp))))
        replace-element (get-in @app (conj path :mr-who/node))]
    (println "R " replace-element)
    (dom/append-helper replace-element (:mr-who/node render))
    (swap! app assoc-in path render)
    (when-not cache
      (swap! app assoc-in (conj ident :cache) render))))


(defn replace-primitive-mutation [app path primitive ident]
  (let [cache (get-in @app (conj ident :cache))
        render primitive
        replace-element (get-in @app (conj path :mr-who/node))]
    (dom/replace-node replace-element render)
    (swap! app assoc-in path render)
    (when-not cache
      (swap! app assoc-in (conj ident :cache) render))
    (js/console.log @app)))


(defn replace-children-mutation [app path comp ident]
  (let [cache (get-in @app (conj ident :cache))
        render (if cache cache (first (vals (comp))))
        node (get-in @app (conj path :mr-who/node))]
    (mapv #(node.removeChild %) node.children)
    (dom/append-child node (:mr-who/node render))
    (swap! app assoc-in path render)
    (when-not cache
      (swap! app assoc-in (conj ident :cache) render))
    (js/console.log @app)))

(defn replace-child-mutation [app path comp ident]
  (let [cache (get-in @app (conj ident :cache))
        render (if cache cache (first (vals (comp))))
        node (get-in @app (conj path :mr-who/node))]
    (mapv #(node.removeChild %) node.children)
    (dom/append-child node (:mr-who/node render))
    (swap! app assoc-in path render)
    (when-not cache
      (swap! app assoc-in (conj ident :cache) render))
    (js/console.log @app)))


#_(defn merge-replace-style-mutation [app path new-style]
    (let [replace-element (get-in @app (conj path :mr-who/node))
          element-attr (.. replace-element (getAttribute "style"))
          ]
      (println replace-element)
      #_(dom/attr-helper replace-element attr-map)))

(defn replace-classes-mutation [app path add-remove-classes]
  (let [replace-element (get-in @app (conj path :mr-who/node))
        ;; css-string (.. replace-element (getAttribute "class"))
        ;; css-classes (into #{}
        ;;                   (if (u/string? css-string)
        ;;                     (string/split css-string #"\s+")))
        
        ;; css-classes (if-let [r (:remove add-remove-classes)]
        ;;               (set/difference css-classes r)
        ;;               css-classes)
        ;; css-classes (if-let [a (:add add-remove-classes)]
        ;;               (set/union css-classes a)
        ;;               css-classes)
        ]
    (dom/remove-css-from-element replace-element (:remove add-remove-classes))
    (dom/add-css-to-element replace-element (:add add-remove-classes))))

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
      (dom/append-helper (get-in @app replace-element-ident) (:mr-who/node (first (u/vals render))))
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

(defn set-value! [app path value]
  (swap! app assoc-in path value))
