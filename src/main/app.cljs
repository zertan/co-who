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
            ["./pages/profile.mjs" :as p]
            ["./components/wizards/project/main.mjs" :as wzp]
            ["./components/wizards/project/info.mjs" :as info-step]
            ["./components/wizards/project/contract_step.mjs" :as contract-step]
            ["./composedb/client.mjs" :as cdb]
            ["flowbite" :as fb]))

(defonce app (atom {}))

(defn init []
  (fb/initFlowbite)
  
  #_(cdba/authenticate-user)
  (swap! app conj (let [root-comp ((second (main/root-comp app {:header ((first (hc/header-comp {:modal-open-fn #(m/replace-classes-mutation app [:root :wizard-modal] {:remove ["hidden"]})})))
                                                                :wizard-modal ((first (wm/modal-comp {:close-fn #(m/replace-classes-mutation app [:root :wizard-modal] {:add ["hidden"]})})))
                                                                :router ((first (rc/router-comp {:id :router
                                                                                                 :route-id :route
                                                                                                 :active-path "/wizards/new-project"
                                                                                                 :path-children [(let [comp (second (l/landing-comp))
                                                                                                                       path "/"]
                                                                                                                   {:path path
                                                                                                                    :listener (rc/add-route app [:root :router :route] path comp [:landing])
                                                                                                                    :comp comp})
                                                                                                                 (let [comp (second (a/activity-comp))
                                                                                                                       path "/activity"]
                                                                                                                   {:path path
                                                                                                                    :listener (rc/add-route app [:root :router :route] path comp [:activity])
                                                                                                                    :comp comp})
                                                                                                                 (let [comp (second (p/profile-comp))
                                                                                                                       path "/profile"]
                                                                                                                   {:path path
                                                                                                                    :listener (rc/add-route app [:root :router :route] path comp [:profile])
                                                                                                                    :comp comp})
                                                                                                                 (let [comp (second (wzp/project-wizard-comp {:step :info
                                                                                                                                                              :wizard-router {:id :wizard-router
                                                                                                                                                                              :route-id :wizard-route
                                                                                                                                                                              :active-path "/wizards/new-project/info"
                                                                                                                                                                              :path-children [(let [comp (second (info-step/form-comp))
                                                                                                                                                                                                    path "/wizards/new-project/info"]
                                                                                                                                                                                                {:path path
                                                                                                                                                                                                 :listener (rc/add-route app [:root :router :route  :new-project :wzr :wizard-router :wizard-route] path comp [:new-project-info])
                                                                                                                                                                                                 :comp comp})
                                                                                                                                                                                              (let [comp (second (contract-step/contract-step))
                                                                                                                                                                                                    path "/wizards/new-project/contract"]
                                                                                                                                                                                                {:path path
                                                                                                                                                                                                 :listener (rc/add-route app [:root :router :route :new-project :wzr :wizard-router :wizard-route] path comp [:new-project-contract])
                                                                                                                                                                                                 :comp comp})]}
                                                                                                                                                              :stepper {:id "stepper"
                                                                                                                                                                        :steps [{:id :info
                                                                                                                                                                                 :heading "Project Information"
                                                                                                                                                                                 :details "Enter basic project information."
                                                                                                                                                                                 :completed? false
                                                                                                                                                                                 :active? true
                                                                                                                                                                                 :href "/wizards/new-project/info"
                                                                                                                                                                                 :icon :clipboard-document-list}
                                                                                                                                                                                {:id :contract
                                                                                                                                                                                 :heading "Deploy Contract"
                                                                                                                                                                                 :details "Deploy the Project to the Blockchain."
                                                                                                                                                                                 :completed? false
                                                                                                                                                                                 :active? false
                                                                                                                                                                                 :href "/wizards/new-project/contract"
                                                                                                                                                                                 :icon :cube}]}}))
                                                                                                                       path "/wizards/new-project"]
                                                                                                                   {:path path
                                                                                                                    :listener (rc/add-route app [:root :router :route] path comp [:new-project])
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

#_(let [query ["query {
                simpleProfile {
                  displayname
               }
             }"]]
  (cdb/run-query cdb/client query))


