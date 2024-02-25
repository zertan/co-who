(ns app
  (:require["mr-who/dom" :as dom]
            ["./routing.mjs" :as r]
            ["./layout/main.mjs" :as main]
            ["./layout/router.mjs" :as rc]
            ["./layout/header.mjs" :as hc]
            ["./components/user.mjs" :refer [user-comp]]
            ["./mutations.mjs" :as m]
            ["./evm/client.mjs" :refer [client chains]]
            ["./layout/wizard_modal.mjs" :as wm]
            ["./evm/util.mjs" :as eu]
            ["./pages/activity.mjs" :as a]
            ["./pages/landing.mjs" :as l]
            ["./blueprint/datepicker.mjs" :as dp]
            ["./pages/profile.mjs" :as p]
            ["flowbite" :as fb]))

(defonce app (atom {}))

(defn init []
  (fb/initFlowbite)
  (swap! app conj (let [root-comp ((second (main/root-comp app {:header ((first (hc/header-comp {:modal-open-fn #(m/replace-classes-mutation app [:root :wizard-modal] {:remove ["hidden"]})})))
                                                                :wizard-modal ((first (wm/modal-comp {:close-fn #(m/replace-classes-mutation app [:root :wizard-modal] {:add ["hidden"]})})))
                                                                :router ((first (rc/router-comp {:active-path "/"
                                                                                                 :path-children [(let [comp (second (l/landing-comp))]
                                                                                                                   {:path "/"
                                                                                                                    :listener (rc/add-route app [:root :router :route] "/" comp [:landing])
                                                                                                                    :comp comp})
                                                                                                                 (let [comp (second (a/activity-comp))]
                                                                                                                   {:path "/activity"
                                                                                                                    :listener (rc/add-route app [:root :router :route] "/activity" comp [:activity])
                                                                                                                    :comp comp})
                                                                                                                 (let [comp (second (p/profile-comp))]
                                                                                                                   {:path "/profile"
                                                                                                                    :listener (rc/add-route app [:root :router :route] "/profile" comp [:profile])
                                                                                                                    :comp comp})]})))})))]
                    (dom/append-helper (js/document.getElementById "app") (:node (:root root-comp)))
                    root-comp))

  (r/router.resolve)
  (set! (.-app js/window) app)
  (eu/add-accounts-changed js/window.ethereum
                           #(let[address (first %)]
                              (m/replace-mutation app [:root :header :n :n2 :n3 :user] (second (user-comp {:address address})) [:user 0])))
  (eu/request-addresses client
                        #(let[address (first %)]
                           (m/replace-mutation app [:root :header :n :n2 :n3 :user] (second (user-comp {:address address})) [:user 0]))))

(init)
