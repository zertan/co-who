(ns co-who.component)

#_(def c (Component [:router/id 0]))
(defmacro dod [n & body]
   `(defn ~(symbol n) [] (println "a") ~@body))

#_(defmacro defc [n args ident-query & dom-tree]
  (list 'defclass (symbol n)
        (list 'extends 'Component)
        (list 'constructor [`this]

              `(let [{:keys [ident query]} ~ident-query]
                 (super ident query nil)))
        'Object
        #_(list 'dude [_] (println "a"))
        #_(render [args]
                  (dom/tree))))

#_(defc NewComp [this {:keys [id]}]
    {:ident [:new-comp/id 0]
     :query []}
    (dom/div {} "blah"))


#_(println NewComp)


#_(defmacro defc [n args ident-query render-fn]
    `(defclass ~(symbol n)
       (extends Component)
       (constructor [this]
                    (let [{:keys [ident query]} ident-query]
                      (super ident query render-fn)))
       Object))

#_(defn landing-comp-factory [comp-class ident query render-fn]
  (new comp-class ident query render-fn))
(defmacro defc [])
