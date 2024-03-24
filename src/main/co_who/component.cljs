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

(def components (atom {}))

(defrecord Comp [app ident query local render])

(defn comp-factory [app ident query local render]
  (let [comp (Comp. app ident query (atom local) render)]
    (swap! components conj {ident comp})
    comp))

(defn render-fn [render-state {:keys [app ident query local render] :as comp} & {merge false}]
  (let [data (py/pull @app [{ident query}])
        rendered (render (get data ident) @local)
        ;asd (println "ren" rendered)
        old-node (get-in @render-state [(str ident) :mr-who/node])
        new-node (get  (get rendered (str ident)) :mr-who/node)]
    ;(println "comp:" data)
    (swap! render-state py/add rendered)
    (dom/append-helper old-node new-node {:action dom/replace-node})))

(defn component-render [app ident-query local render]
  (let [{:keys [ident query]} ident-query
        comp (comp-factory app ident query local render)]
    (fn ([data]
         (let [{:keys [app ident query local render]} comp]
           (py/pull @app [{ident query}])))
      ([data data-local]
       (let [{:keys [app ident query local render]} comp]
         @local))
      ([]
       (let [{:keys [app ident query local render]} comp]
         (render (get (py/pull @app [{ident query}]) ident) @local))))))

(defn dropdown-select [app ident local]
  (component-render app
                    {:ident ident
                     :query [:dropdown/id {:dropdown/items [:ident :value]}]}
                    local
                    (fn [ident
                         {:dropdown/keys [id selected items] :or {id (random-uuid)
                                                                  selected 0
                                                                  items []} :as data}
                         {:local/keys [label on-change] :or {label "Select"

                                                             on-change (fn [e]
                                                                                  (swap! app assoc-in ident e.target.value))} :as local}]
                      (dom/div {:id (str ident)
                                :class ""}
                               (dom/label {:for "countries"
                                           :class "block mb-2 text-sm font-medium text-gray-900 dark:text-white"}
                                          label)
                               (dom/select {:id "countries"
                                            :on-change on-change
                                            :class "bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500
                           focus:border-blue-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400
                           dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"}
                                           (for [{:keys [value]} items]
                                             (dom/option {:selected ""} (str value))))))))

(defn init-components [app render components]
  (let [watcher-fn (fn [render components]
                     (fn [_key _atom old new] ;;do diff here
                       (println "runnign watch over comps: " (str (vals @components)))
                       (mapv #(render-fn render %) (vals @components))))]
    (add-watch app :watch (watcher-fn render components))))

(comment

  (do
    (def r (dropdown-select co-who.app/app [:dropdown/id 0]
                            {:local/label "Select contract"}))

    (swap! co-who.app/app py/add {:dropdown/id 0 :dropdown/items [{:ident [{[:contract/id :codo] [:contract/name]}]
                                                                   :value "Codaaoaaaaaaaaaaa"}
                                                                  {:ident [{[:contract/id :codo] [:contract/name]}]
                                                                   :value "Codaaa"}]})

    (let [rr (r)]
      (swap! co-who.app/render py/add rr)
      (dom/append-helper (js/document.getElementById "app") (:mr-who/node (get rr "[:dropdown/id 0]"))))

    (let [dropdown (get @components [:dropdown/id 0])]
      (render-fn co-who.app/render dropdown)))

  (init-components  co-who.app/app co-who.app/render components)

  (get-in @co-who.app/app [:dropdown/id 0])

  (py/pull @co-who.app/app [{[:dropdown/id 0] [:dropdown/id :dropdown/items]}])

  (keys  (py/add-report @co-who.app/app {:dropdown/id 0 :dropdown/items [:asd]}))

  (let [comp

        #_(dropdown-select {:dropdown/id 0
                            :dropdown/items (mapv (fn [c] {:ident [:contract/id (:contract/id c)]
                                                           :value (:contract/name c)}) [{:contract/id ":codo" :contract/name "blaa"}])}
                           {:local/label "Select contract"
                            :local/on-change (fn []
                                               (swap! co-who.app/app assoc-in [:some/id 0] {:some/id 0 :some/more "cool2"}))})]
    (swap! co-who.app/render py/add comp)
    (dom/append-helper (js/document.getElementById "app") (:mr-who/node (:dropdown comp)) {:action dom/replace-node})
    #_(swap! co-who.app/app py/add comp)))
