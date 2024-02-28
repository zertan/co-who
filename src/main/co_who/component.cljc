(ns co-who.component
  #_(:require [squint.core :refer [defclass]]))



#_(defclass Component

  (field -ident [])
  (field -query [])
  (field -render-fn [])
  (field -rendered-nodes [])

  #_(field -query {})

  (constructor [this ident query render-fn]
               (set! -ident ident)
               (set! -render-fn render-fn))

  Object
  (i [this] -ident)

  #_(render [this]
            (-render-fn))) ;;


#_(defclass Class2
    (extends class-1)
    (field -y 1)
    (constructor [_ x y]
                 (super (+ x y -y)))

    Object
    (dude [_]
          (str -y (super.get-name-separator)))

    (^:async fetch [_]
     (js/fetch "https://clojure.org"))

    (toString [this] (str "<<<<" (.dude this) ">>>>") ))

#_(def c (Component [:router/id 0]))

(defmacro dod [n & body]
  `(defn ~(symbol n) [] (println "a") ~@body))

#_(defmacro defc [n]
  `(defclass (symbol n)))


#_(defmacro defc [n args ident-query render-fn]
    `(defclass ~(symbol n)
       (extends Component)
       (constructor [this]
                    (let [{:keys [ident query]} ident-query]
                      (super ident query render-fn)))
       Object))

#_(defn landing-comp-factory [comp-class ident query render-fn]
  (new comp-class ident query render-fn))
