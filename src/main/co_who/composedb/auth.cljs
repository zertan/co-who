(ns co-who.composedb.auth
  (:require [co-who.evm.client :refer [client]]
            [co-who.evm.util :as eu]
            ["did-session" :refer [DIDSession]]
            ["@didtools/pkh-ethereum" :refer [EthereumWebAuth getAccountId]]))

(declare ethProvider)
(declare addresses)
(declare accountId)
(declare authMethod)

(defn init-auth []
  (def ethProvider client)

  (def addresses (js/await (client.request {:method "eth_requestAccounts"})))
  (def accountId (js/await (getAccountId ethProvider (first addresses))))
  (def authMethod (js/await (EthereumWebAuth.getAuthMethod ethProvider accountId))))

(defn authenticate-user []
  (let [account (eu/request-addresses client
                                      (fn [x] (.then (getAccountId client (first x)))))
        ethProvider client
        auth-method (eu/request-addresses client
                                          (fn [x] (-> x
                                                      (#(.then (getAccountId client (first %))))
                                                      (#(.then (EthereumWebAuth.getAuthMethod client %)))
                                                      #_(#(DIDSession.get % )))
                                            ))
        ;;   session (DIDSession.get account-id)
        ]
    #_(println "accc-id: " account-id)
    #_(println "adr: " authMethod)
    [account auth-method]
    ))

(defn get-session [account-id auth-method resources]
  (let [ethProvider client
        account account-id]
    (println account-id)
    (println auth-method)
    (DIDSession.get account-id auth-method {:resources resources}))
  )

;; const accountId = await getAccountId(ethProvider, addresses[0])
;; const authMethod = await EthereumWebAuth.getAuthMethod(ethprovider, accountId)

;; const session = await DIDSession.get(accountId, authMethod, { resources: compose.resources})
;; compose.setDID(session.did)
