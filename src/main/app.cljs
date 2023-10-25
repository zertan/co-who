(ns app
  (:require["mr-who/dom" :as dom]
            ["./routing.mjs" :as r]
            ["./layout/main.mjs" :as main]
            ["./layout/router.mjs" :as rc]
            ["./components/user.mjs" :refer [user-comp]]
            ["./mutations.mjs" :as m]
            ["./evm/client.mjs" :refer [client chains]]
            ["./evm/util.mjs" :as eu]
            ["./pages/activity.mjs" :as a]
            ["./pages/landing.mjs" :as l]
            ["flowbite" :as fb]))

(defonce app (atom {}))

(defn init []
  (swap! app conj (let [root-comp ((second (main/root-comp app {:router ((first (rc/router-comp {:active-path "/"
                                                                                                 :path-children [(let [comp (second (l/landing-comp))]
                                                                                                                   {:path "/"
                                                                                                                    :listener (rc/add-route app [:root :router :route] "/" comp)
                                                                                                                    :comp comp})
                                                                                                                 (let [comp (second (a/activity-comp))]
                                                                                                                   {:path "/babaei"
                                                                                                                    :listener (rc/add-route app [:root :router :route] "/activity" comp)
                                                                                                                    :comp comp})]})))})))]
                    (dom/append-helper (js/document.getElementById "app") (:node (:root root-comp)))
                    root-comp))
  
  (r/router.resolve)
  (set! (.-app js/window) app)
  (.then (eu/get-address client) #(m/replace-mutation app [:root :header :n :n2 :n3 :user] (second (user-comp {:address %})))))

(init)
