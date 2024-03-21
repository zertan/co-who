(ns co-who.component
  (:require [pyramid.core :as py]
            [mr-who.dom :as dom]
            [co-who.blueprint.dropdown :as d])
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


(defrecord Comp [app ident query local render])

(defn comp-factory [app ident query local render]
  (fn []
    (->Comp app ident query render local)))

;comp (comp-factory app ident query local render)

(defn defc-helper [app ident-query-local render]
  (let [{:keys [ident query]} ident-query-local
        render-fn (fn [app]
                    (let [data (py/pull @app query)
                          comp (apply render (get data ident))]
                      (println "data: " (get data ident))
                      (dom/append-helper (get-in @co-who.app/render [:dropdown :mr-who/node]) (:mr-who/node (:dropdown comp )) {:action dom/replace-node})
                      ))

        watcher-fn (fn [_key _atom old new] ;;do diff here
                     (println "run render")
                     (let [render (render-fn _atom)]
                       (swap! co-who.app/render py/add render)))
        watcher (add-watch app :watch watcher-fn)
        ]
    #(render (get (py/pull @app query) ident))))

(def dropdown-select (defc-helper co-who.app/app {:ident [:some/id 0]
                                                  :query [{[:some/id 0] [:some/id :some/more]}]
                                                  ;:local #:local{:title title :on-change #(swap! app assoc-in (conj ident :value) (.target.value %))}
                                                  }
                       (fn [{:some/keys [more] :keys [items on-change] :as data}]
                         (println data)
                         (dom/div {:id :dropdown
                                   :class ""}
                           (dom/label {:for "countries"
                                       :class "block mb-2 text-sm font-medium text-gray-900 dark:text-white"}
                             more)
                           (dom/select {:id "countries"
                                        :on-change on-change
                                        :class "bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500
                           focus:border-blue-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400
                           dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"}
                                       (for [{:keys [value]} items]
                                         (dom/option {:selected nil} value))
                                       )))))

(comment
  (swap! co-who.app/app assoc-in [:some/id 0] {:some/id 0 :some/more "cool"})

  (let [comp (dropdown-select {:some/more "Select contracta"
                               :items (mapv (fn [c] {:ident [:contract/id (:contract/id c)]
                                                     :value (:contract/name c)}) [{:contract/id "bla" :contract/name "blaa"}])
                               :on-change (fn []
                                            (swap! co-who.app/app assoc-in [:some/id 0] {:some/id 0 :some/more "cool2"})
                                            )})]
    (swap! co-who.app/render py/add comp)
   (dom/append-helper (js/document.getElementById "app") (:mr-who/node (:dropdown comp )) {:action dom/replace-node})
    #_(swap! co-who.app/app py/add comp)))
