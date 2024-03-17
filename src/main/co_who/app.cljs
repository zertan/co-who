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
           [co-who.composedb.auth :as cdba]
           [cljs.spec.alpha :as s]
                                        ;["./component" :as cs]
           ["flowbite" :as fb]
           [pyramid.core :as py]
           [co-who.evm.abi :as abi]
           [co-who.evm.specs :as es]
           [co-who.blueprint.input :as in]
           [gadget.inspector :as inspector]
           [co-who.components.evm.smart-contract :as sm])
  #_(:require-macros [co-who.component :as c :refer [dod Component]]))

(defn deep-merge [& maps]
  (apply merge-with (fn [& args]
                      (if (every? map? args)
                        (apply deep-merge args)
                        (last args)))
         maps))

(defonce app (atom {}))
(defonce data (atom {}))

;;;;;;;;;;;;aaaaaaaaaaaaaaa
                                        ;(c/dod asd (+ 1 1 21))
                                        ;(println c/Component)
                                        ;1
#_(c/defc NewComp [this ]a)

#_(c/defc NewComp [this {:keys [id]} & children]
    {:ident [:new-comp/id 0]
     :query []}
    (dom/div {} "blah"))

#_(println NewCompa)

#_(defn ui-landing (landing-comp-factory  ident query))
;
#_(let [cp (new c/Component [:router/id 0] (second (l/landing-comp {})))]
  (cp.render))

(defn render-root []
  (let [root-comp (dom/div {:id :app
                                        ;:class "bg-black w-screen h-screen text-white dark"
                            }
                    (sm/smart-contract {:id :smart-contract
                                        :address "0x0"
                                        :entries (filterv #(s/valid? ::es/function %) abi/token-abi)
                                        :select-on-change (fn [e] (swap! app assoc-in [:smart-contract :selected] e.target.value))
                                        :on-click (sm/append-evm-transaction app)
                                        :on-change (in/on-change app [:smart-contract :input])}))

        #_(main/root-comp {:header ((first (hc/header-comp {:modal-open-fn #(m/replace-classes-mutation app [:root :wizard-modal] {:remove ["hidden"]})})))
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
    #_(println "<aaaaa" root-comp)

    #_(println (js/document.getElementById "app"))
    (dom/append-helper (js/document.getElementById "app") (:mr-who/node (:app root-comp )) {:action dom/replace-node})
    (swap! app py/add (:app root-comp))
    ))

(defn ^:dev/after-load start []
  (render-root))

(defn init []
  (println "init")
  (reset! app (py/db (abi/indexed-abi abi/token-abi)))

  (fb/initFlowbite)

  (r/init-routing)

  #_(cdba/authenticate-user)

  (start)

  (ec/init-client)
  #_(cdb/init-client)

  (inspector/inspect "App state" app))

(comment


  (let [selected (get-in @app [:smart-contract :selected])
        data (get-in @app [:function/id selected])]
    [selected
     data])

  (set! (.-draggable js/window) (keys l/interact))
  (eu/add-accounts-changed js/window.ethereum
                           #(let[address (first %)]
                              (m/replace-mutation app [:root :header :n :n2 :n3 :user] (second (user-comp {:address address})) [:user 0])))
  (eu/request-addresses client
                        #(let[address (first %)]
                           (m/replace-mutation app [:root :header :n :n2 :n3 :user] (second (user-comp {:address address})) [:user 0])))

  (let [query ["query {
                simpleProfile {
                  displayname
               }
             }"]]
    (cdb/run-query cdb/client query))
  )
