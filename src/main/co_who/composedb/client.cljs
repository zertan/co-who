(ns co-who.composedb.client
  (:require ["@composedb/client" :refer [ComposeClient]]
            ["did-session" :refer [DIDSession]]
            [co-who.composedb.auth :as auth :refer [accountId authMethod]]))

#_(def composite (js/await
                  (->
                   (js/fetch "http://localhost:5173/runtime-composite.json")
                   (.then (fn [response] (.json response))))))

(declare composite)
(declare cmopose)
(declare session)

(defn init-client []
  (def composite {:models {:SimpleProfile {:id "kjzl6hvfrbw6cb1ttgtfyob0aqdxdkprjy598j5rd94ca52kij128y23ede66z5"
                                           :accountRelation {:type "single"}}}
                  :objects {:SimpleProfile {:displayName {:type "string" :required true}}}
                  :enums {}
                  :accountData {:simpleProfile {:type "node" :name "SimpleProfile"}}})

  (def compose (ComposeClient. {:ceramic "http://localhost:7007"
                                :definition (clj->js composite)}))

  (def session (js/await (.then (DIDSession.get accountId authMethod {:resources compose.resources})
                                (fn [session]
                                  (compose.setDID session.did)
                                  session)))))

#_(def session
    (let [[account-id auth-method] (auth/authenticate-user)
          session (.then account-id (fn [a-id]
                                      (.then auth-method (fn [au-m]
                                                           (.then (auth/get-session a-id au-m compose.resources)
                                                                  (fn [r] r))))))
          ]
      (println "session: " session)
      (compose.setDID session.did)
      session))

                                       ;compose.setDID(session.did)

;; const QUERY = `
;;   query Test($id: ID!) {
;;     getUser(id: $id) {
;;       id
;;       name
;;     }
;;   }
;; `;

;; client
;;   .query(QUERY, { id: 'test' })
;;   .toPromise()
;;   .then(result => {
;;     console.log(result); // { data: ... }
;;   });
