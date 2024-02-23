(ns co-who.composedb.auth
  (:require ["../evm/client.mjs" :refer [compose]]
            ["../evm/util.mjs" :as eu]
            ["did-session" :refer [DIDSession]]
            ["@didtools/pkh-ethereum" :refer [EthereumWebAuth getAccountId]]))


#_(eu/request-addresses compose
                      (fn [x] (println (.then (getAccountId compose (first x))))))

#_(let [address (eu/request-addresses compose
                                      (fn [x] (-> x
                                                  (#(.then (getAccountId compose (first %))))
                                                  (#(.then (EthereumWebAuth.getAuthMethod compose %)))
                                                  (#(.then DIDSession.get % )))
                                        ))
        session (DIDSession.get account-id)]
    (println session)
    )

;; const accountId = await getAccountId(ethProvider, addresses[0])
;; const authMethod = await EthereumWebAuth.getAuthMethod(ethprovider, accountId)

;; const session = await DIDSession.get(accountId, authMethod, { resources: compose.resources})
;; compose.setDID(session.did)
