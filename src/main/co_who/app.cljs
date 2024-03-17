(ns co-who.app
  (:require [mr-who.dom :as dom]
           [co-who.routing :as r]
           [co-who.layout.main :as main]
           [co-who.layout.router :as rc]
           [co-who.layout.header :as hc]
           [co-who.components.user :refer [user-comp]]
           [co-who.mutations :as m]
           [co-who.evm.client :as ec]
           [co-who.layout.wizard-modal :as wm]
           [co-who.evm.util :as eu]
           [co-who.pages.activity :as a]
           [co-who.pages.landing :as l]
           [co-who.pages.profile :as p]
           [co-who.components.wizards.project.main :as wzp]
           [co-who.components.wizards.project.info :as info-step]
           [co-who.components.wizards.project.contract-step :as contract-step]
           [co-who.composedb.client :as cdb]
           ;["./component" :as cs]
           ["flowbite" :as fb]
           [co-who.evm.abi :refer [token-abi]]
           [co-who.components.evm.smart-contract :as sm])
  #_(:require-macros [co-who.component :as c :refer [dod Component]]))

(defonce app (atom nil))
(defonce data (atom nil))
;;;;;;;;;;;;aaaaaaaaaaaaaaa
                                        ;(c/dod asd (+ 1 1 21))
                                        ;(println c/Component)
                                        ;1
#_(c/defc NewComp [this ])

#_(c/defc NewComp [this {:keys [id]} & children]
    {:ident [:new-comp/id 0]
     :query []}
    (dom/div {} "blah"))

#_(println NewComp)

#_(defn ui-landing (landing-comp-factory  ident query))
;
#_(let [cp (new c/Component [:router/id 0] (second (l/landing-comp {})))]
  (cp.render))

(defn ^:dev/after-load  init []
  (println "init")
  (fb/initFlowbite)

  (r/init-routing)

  #_(cdba/authenticate-user)
  (let [root-comp (sm/smart-contract token-abi) #_(main/root-comp {:header ((first (hc/header-comp {:modal-open-fn #(m/replace-classes-mutation app [:root :wizard-modal] {:remove ["hidden"]})})))
                                                                 :wizard-modal ((first (wm/modal-comp {:close-fn #(m/replace-classes-mutation app [:root :wizard-modal] {:add ["hidden"]})})))
                                                                 :router ((first (rc/router-comp {:id :router
                                                                                                  :route-id :route
                                                                                                  :active-path "/wizards/new-project"
                                                                                                  :path-children [(let [comp (second (l/landing-comp {:on-click-mut (l/on-click-mut app [:landing :input])
                                                                                                                                                      :on-change (l/on-change app [:landing :input])
                                                                                                                                                      :on-click (l/on-click app)}))
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
                                                                                                                     :comp comp})]})))})
        ;render ((second root-comp))
        ]
    (println "<aa" root-comp)

    (println (js/document.getElementById "app"))
    (dom/append-helper (js/document.getElementById "app") (:node (:root root-comp)))
    #_(reset! app render)
    #_(reset! data #_((first root-comp)))
  #_(println ":data " @data)

  (r/router.resolve)

  (set! (.-app js/window) app)))

  #_(ec/init-client)
  #_(cdb/init-client)

  #_(set! (.-draggable js/window) (keys l/interact))
  #_(eu/add-accounts-changed js/window.ethereum
                           #(let[address (first %)]
                              (m/replace-mutation app [:root :header :n :n2 :n3 :user] (second (user-comp {:address address})) [:user 0])))
  #_(eu/request-addresses client
                        #(let[address (first %)]
                           (m/replace-mutation app [:root :header :n :n2 :n3 :user] (second (user-comp {:address address})) [:user 0])))

#_(let [query ["query {
                simpleProfile {
                  displayname
               }
             }"]]
    (cdb/run-query cdb/client query))
#_(init)
