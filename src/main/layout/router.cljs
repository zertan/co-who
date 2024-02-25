(ns co-who.layout.router
  (:require ["mr-who/dom" :as dom]
            ["mr-who/utils" :as u]
            ["../pages/activity.mjs" :as a]
            ["../pages/profile.mjs" :as f]
            ["../pages/landing.mjs" :as l]
            ["../routing.mjs" :as r]
            ["../mutations.mjs" :as m]
            ["../components/wizards/project.mjs" :as wzp]))

(defn add-route [app path route-path comp ident]
  (r/add-route r/router route-path
               #(do
                  (println path)
                  (m/replace-mutation app path comp ident))))

#_(let [render (comp)
       replace-element (get-in @app (conj path :node))]
   (dom/replace-node replace-element (:node (first (u/vals render))))
   (swap! app assoc-in path (conj (first (u/vals render)))))

(defn router-comp [{:keys [id route-id active-path path-children] :or {active-path "/"
                                                           path-children [{:path "/"
                                                                           :comp (second (l/landing-comp))}
                                                                          {:path "/activity"
                                                                           :comp (second (a/activity-comp))}
                                                                          {:path "/form"
                                                                           :comp (second (f/profile-comp))}
                                                                          {:path "/wizards/new-project"
                                                                           :comp (second (wzp/project-wizard-comp))}]}}]
  (list (fn [] {:active-path active-path
                :path-children path-children})
        (fn [] (dom/div {:id id}
                 (dom/div {:id route-id
                           :class "max-w-screen-xl mt-4"}
                   ((:comp
                     (first
                      (filterv #(= active-path (:path %)) path-children)))))))))
