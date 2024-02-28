(ns co-who.layout.router
  (:require ["mr-who/dom" :as dom]
            ["../routing.mjs" :as r]
            ["../mutations.mjs" :as m]))

;; add the component render element to the render-app state

(defn add-route [app path route-path comp ident]
  (r/add-route r/router route-path
               #(do (println "run change route: " path)
                    (m/replace-mutation app path comp ident))))

#_(let [render (comp)
       replace-element (get-in @app (conj path :node))]
   (dom/replace-node replace-element (:node (first (u/vals render))))
   (swap! app assoc-in path (conj (first (u/vals render)))))

(defn route-comp [comp path app ident]
  {:path path
   :listener (add-route app (dom/get-dom-paths ident) [] path ident)
   :comp comp})

(defn router-comp [{:keys [id route-id active-path path-children] :or {id :router
                                                                       route-id :route
                                                                       active-path "/"
                                                                       path-children [{:path "/"
                                                                                       :comp (list (fn []) (fn []))}]}}
                   & children]
  (list (fn [] {:id id
                :route-id route-id
                :active-path active-path
                :path-children path-children})
        (fn []
          (dom/div {:id id}
            (dom/div {:id route-id
                      :class "max-w-screen-xl mt-4"}
              ((:comp
                (first
                 (filterv #(= active-path (:path %)) path-children)))))))))
