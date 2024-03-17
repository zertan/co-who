(ns co-who.composedb.auth
  (:require [co-who.evm.client :refer [wallet-client]]
            [co-who.evm.util :as eu]

            ["did-session" :refer [DIDSession]]
            ["@didtools/pkh-ethereum" :refer [EthereumWebAuth getAccountId]]))

(declare ethProvider)
(declare addresses)
(declare accountId)
(declare authMethod)

(defn ^async init-auth []
  (let [addresses (.then (@wallet-client.request {:method "eth_requestAccounts"}))]
    (def accountId (js/await (getAccountId ethProvider (first addresses))))
    (def authMethod (js/await (EthereumWebAuth.getAuthMethod@ wallet-client accountId)))))

(defn authenticate-user []
  (let [account (eu/request-addresses @wallet-client
                                      (fn [x] (.then (getAccountId @wallet-client (first x)))))
        ethProvider @wallet-client
        auth-method (eu/request-addresses @wallet-client
                                          (fn [x] (-> x
                                                      (#(.then (getAccountId @wallet-client (first %))))
                                                      (#(.then (EthereumWebAuth.getAuthMethod @wallet-client %)))
                                                      #_(#(DIDSession.get % )))
                                            ))
        ;;   session (DIDSession.get account-id)
        ]
    #_(println "accc-id: " account-id)
    #_(println "adr: " authMethod)
    [account auth-method]
    ))

(defn get-session [client account-id auth-method resources]
  (DIDSession.get account-id auth-method {:resources resources}))

;; const accountId = await getAccountId(ethProvider, addresses[0])
;; const authMethod = await EthereumWebAuth.getAuthMethod(ethprovider, accountId)

;; const session = await DIDSession.get(accountId, authMethod, { resources: compose.resources})
;; compose.setDID(session.did)
