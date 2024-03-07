(ns co-who.component
  (:require [squint.core :refer [defclass]])
  #_(:require-macros [co-who.component :refer [dod]]))

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
   (q [this -query])

   #_(render [this]
             (-render-fn))) ;;

#_(def c (Component [:router/id 0]))

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
