(ns co-who.layout.router
  (:require [mr-who.dom :as dom]
            [co-who.routing :as r]
            [co-who.mutations :as m]))

;; add the component render element to the render-app state

(defn add-route [app path route-path comp ident]
  #_(println (js-keys @r/router))
  (r/add-route @r/router route-path
               #(do (println "run change route: " path)
                    #_(m/replace-mutation app path comp ident {}))))

#_(let [render (comp)
       replace-element (get-in @app (conj path :node))]
   (dom/replace-node replace-element (:node (first (u/vals render))))
   (swap! app assoc-in path (conj (first (u/vals render)))))

#_(defn route-comp [comp path app ident]
  {:path path
   :listener (add-route app #_(dom/get-dom-paths ident) [] path ident)
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
                      :class "max-w-screen-xl mt-4 items-center justify-items-center justify-center"}
              ((:comp
                (first
                 (filterv #(= active-path (:path %)) path-children)))))))))
