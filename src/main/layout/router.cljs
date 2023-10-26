(ns co-who.layout.router
  (:require ["mr-who/dom" :as dom]
            ["mr-who/utils" :as u]
            ["../pages/activity.mjs" :as a]
            ["../pages/landing.mjs" :as l]
            ["../routing.mjs" :as r]
            ["../mutations.mjs" :as m]))

(defn add-route [app path route-path comp ident]
  (r/add-route r/router route-path
               #(m/replace-mutation app path comp ident)))

#_(let [render (comp)
       replace-element (get-in @app (conj path :node))]
   (dom/replace-node replace-element (:node (first (u/vals render))))
   (swap! app assoc-in path (conj (first (u/vals render)))))

(defn router-comp [{:keys [active-path path-children] :or {active-path "/"
                                                           path-children [{:path "/"
                                                                           :comp (second (l/landing-comp))}
                                                                          {:path "/activity"
                                                                           :comp (second (a/activity-comp))}]}}]
  (list (fn [] {:active-path active-path
                :path-children path-children})
        (fn [] (dom/div {:id :router}
                 (dom/div {:id :route}
                   ((:comp
                     (first
                      (filterv #(= active-path (:path %)) path-children)))))))))
