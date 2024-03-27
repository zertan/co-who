(ns co-who.app
  (:require [mr-who.dom :as dom]
            #_[co-who.routing :as r]
            [co-who.layout.main :as main]
            #_[co-who.layout.router :as rc]
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
                                        ;["./component" :as cs]
            ["flowbite" :as fb]
            [pyramid.core :as py]
            [pyramid.query :as pq]
            [co-who.evm.abi :as abi]
            [co-who.component :as c]
            [co-who.blueprint.input :as in]
            [gadget.inspector :as inspector]
            [co-who.components.evm.smart-contract :as sm]
            [cljs.spec.alpha :as s])
  #_(:require-macros [co-who.component :as c :refer [dod Component]]))

(defn deep-merge [& maps]
  (apply merge-with (fn [& args]
                      (if (every? map? args)
                        (apply deep-merge args)
                        (last args)))
         maps))

(defonce app (atom {}))
(defonce render-state (atom {}))

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

(defn update-abi-entries [app selected]
  (py/pull @app [{[:contract/id :codo] [{:contract/abi [:function/id]}]}])

  (py/pull @app [{:contract/id [:contract/address]}])

  (py/pull @app [[:contract/id :codo] [:contract/id :codo-governor]])

  )

(comment
  (pq/q
        [:find ?id
         :where [?e :function/id ?id]]  @app))

#_(defn render-root []
  (let [data (sm/transaction-builder true {:id :transaction-builder
                                           :contracts (py/pull @app [{[:contract/id :codo] [:contract/id :contract/address :contract/chain :contract/name {:contract/abi [:name :type :stateMutability :inputs :outputs]} :stateMutability] }
                                                                     {[:contract/id :codo-governor] [:contract/id :contract/address :contract/chain :contract/name {:contract/abi [:name :type :stateMutability :inputs :outputs]}]}])
                                           :transactions []})
        local-data {:local/selected [:contract/id :codo]
                    :local/contract-select-on-change (fn [e] (m/set-value! app [:id :transaction-builder :selected] (keyword e.target.value)))
                    :local/select-on-change (fn [e]
                                              #_(update-abi-entries app :selected)
                                              (swap! app assoc-in [:id :transaction-builder :selected] (key e.target.value))
                                              #_(m/replace-mutation app [:transaction-builder :topf :sp]
                                                                    (fn []
                                                                      (dom/span {:id :sp
                                                                                 :class "flex max-w-2/3 gap-2"}
                                                                                (d/dropdown-select "Select contract" (mapv #(name %) contracts) contract-select-on-change)))))
                    :local/on-click (sm/append-evm-transaction app)
                    :local/on-change (in/on-change app [:transaction-builder :input])}

        root-comp (main/root-comp {:header ((first (hc/header-comp {:modal-open-fn #(m/replace-classes-mutation app [:app :wizard-modal] {:remove ["hidden"]})})))
                                   :wizard-modal ((first (wm/modal-comp {:close-fn #(m/replace-classes-mutation app [:app :wizard-modal] {:add ["hidden"]})})))
                                   :router ((first (rc/router-comp {:id :router
                                                            :route-id :route
                                                            :active-path "/transaction-builder"
                                                            :path-children [
                                                                            (let [comp (second (l/landing-comp {:on-click-mut (l/on-click-mut app [:landing :input])
                                                                                                                :on-change (l/on-change app [:landing :input])
                                                                                                                :on-click (l/on-click app)}))
                                                                                  path "/"]
                                                                              {:path path
                                                                               :listener (rc/add-route app [:app :router :route] path comp [:landing])
                                                                               :comp comp})
                                                                            (let [comp (second (a/activity-comp))
                                                                                  path "/activity"]
                                                                              {:path path
                                                                               :listener (rc/add-route app [:app :router :route] path comp [:activity])
                                                                               :comp comp})
                                                                            (let [comp (second (p/profile-comp))
                                                                                  path "/profile"]
                                                                              {:path path
                                                                               :listener (rc/add-route app [:app :router :route] path comp [:profile])
                                                                               :comp comp})
                                                                            (let [comp (fn [] (sm/transaction-builder (merge data local-data)))
                                                                                  path "/transaction-builder"]
                                                                              {:path path
                                                                               :listener (rc/add-route app [:app :router :route] path comp [:transaction-builder])
                                                                               :comp comp})
                                                                            (let [comp (second (wzp/project-wizard-comp {:step :info
                                                                                                                         :wizard-router {:id :wizard-router
                                                                                                                                         :route-id :wizard-route
                                                                                                                                         :active-path "/wizards/new-project/info"
                                                                                                                                         :path-children [(let [comp (second (info-step/form-comp))
                                                                                                                                                               path "/wizards/new-project/info"]
                                                                                                                                                           {:path path
                                                                                                                                                            :listener (rc/add-route app [:app :router :route  :new-project :wzr :wizard-router :wizard-route] path comp [:new-project-info])
                                                                                                                                                            :comp comp})
                                                                                                                                                         (let [comp (second (contract-step/contract-step {}))
                                                                                                                                                               path "/wizards/new-project/contract"]
                                                                                                                                                           {:path path
                                                                                                                                                            :listener (rc/add-route app [:app :router :route :new-project :wzr :wizard-router :wizard-route] path comp [:new-project-contract])
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
                                                                               :listener (rc/add-route app [:app :router :route] path comp [:new-project])
                                                                               :comp comp})]})))})
        render ((second root-comp))
        ]
    #_(println "a<aaaaa" render)

    #_(println (js/document.getElementById "app"))
    (dom/append-helper (js/document.getElementById "app") (:mr-who/node (:app render )) {:action dom/replace-node})
    #_(swap! app py/add dataa)
    (swap! app py/add render)
    (swap! app py/add ((first root-comp)))
    (swap! app py/add data)
    ))

(comment
  (get (py/pull @app [{[:id :transaction-builder] [:id {:contracts [:contract/id :contract/address :contract/chain :contract/name {:contract/abi [:name :type :stateMutability :inputs :outputs]} :stateMutability]} :transactions]}])
       [:id :transaction-builder]))

(defn render-2 [first?]
  (let [data (if first?
               (sm/transaction-builder true {:id :transaction-builder
                                             :contracts (vals (py/pull @app [{[:contract/id :codo] [:contract/id :contract/address :contract/chain :contract/name {:contract/abi [:name :type :stateMutability :inputs :outputs]} :stateMutability] }
                                                                             {[:contract/id :codo-governor] [:contract/id :contract/address :contract/chain :contract/name {:contract/abi [:name :type :stateMutability :inputs :outputs]}]}]))
                                             :transactions []})
               (get (py/pull @app [{[:id :transaction-builder] [:id {:contracts [:contract/id :contract/address :contract/chain :contract/name {:contract/abi [:name :type :stateMutability :inputs :outputs]} :stateMutability]} :transactions]}])
       [:id :transaction-builder]))
        local-data {:local/selected-contract [:contract/id :codo]
                    :local/contract-select-on-change (fn [e] (m/set-value! app [:id :transaction-builder :selected-contract] (keyword e.target.value)))
                    :local/select-on-change (fn [e]
                                              #_(update-abi-entries app :selected)
                                              (swap! app assoc-in [:id :smart-contract :selected-function] (keyword e.target.value))
                                              #_(m/replace-mutation app [:transaction-builder :topf :sp]
                                                                    (fn []
                                                                      (dom/span {:id :sp
                                                                                 :class "flex max-w-2/3 gap-2"}
                                                                                (d/dropdown-select "Select contract" (mapv #(name %) contracts) contract-select-on-change)))))
                    :local/on-click (sm/append-evm-transaction app)
                    :local/on-change (in/on-change app [:transaction-builder :input])}

        header (hc/header-comp {})
        render (dom/div {:id :app
                         :class "bg-black w-screen h-screen text-white dark"}
                        ((second header))
                        (sm/transaction-builder (merge data local-data)))

        element (if first? (js/document.getElementById "app") (js/document.getElementById "transaction-builder"))]

    (dom/append-helper element (:mr-who/node (:app render)) {:action dom/replace-node})
    (swap! render-state py/add render)
    (swap! app py/add (merge data ((first header))))))

(defn ^:dev/after-load start []
  (render-2 false))

(defn init []
  (println "init");
  (reset! app (py/db [{:contract/id :codo :contract/name "Codo"
                       :contract/address "0x1Ed3c3b6DFb4756c03CCBCBC5407E73682B6E3Db" :contract/chain :sepolia
                       :contract/abi (abi/indexed-abi abi/token-abi)}
                      {:contract/id :codo-governor :contract/name "Codo Governor"
                       :contract/address "0x0d4d1e9665a8BF75869A63e3F45AC465Bc291CBB" :contract/chain :sepolia
                       :contract/abi (abi/indexed-abi abi/governor-abi)}
                      ]))

  (fb/initFlowbite)

  #_(r/init-routing)

  #_(cdba/authenticate-user)


  (render-2 true)

  (ec/init-client)
  #_(cdb/init-client)

  (inspector/inspect "App state" app)
  (inspector/inspect "Render state" render-state)

  #_(eu/add-accounts-changed js/window.ethereum
                           #(let[address (first %)]
                              (m/replace-mutation render-state [:app :header :n :n2 :n3 :user] (second (user-comp {:address address})) [:user 0] {})))
  (eu/request-addresses @ec/wallet-client
                        #(let[address (first %)]
                           (m/replace-mutation render-state [:app :header :n :n2 :n3 :user] (second (user-comp {:address address})) [:user 0] {}))))

(comment


(py/pull @app [:contract/id (get-in @app [:id :transaction-builder :selected-contract])])

  (get-in @app [:contract/id :codo :contract/abi])

  (let [selected (get-in @app [:transaction-builder :selected])
        data (get-in @app [:function/id selected])]
    [selected
     data])

  (set! (.-draggable js/window) (keys l/interact))


  (let [query ["query {
                simpleProfile {
                  displayname
               }
             }"]]
    (cdb/run-query cdb/client query))
  )
