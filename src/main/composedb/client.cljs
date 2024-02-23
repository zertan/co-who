(ns co-who.composedb.client
  (:require 
            ["@composedb/client" :refer [ComposeClient]]
            ))

(def composite
  "{\"models\":{\"SimpleProfile\":{\"id\":\"kjzl6hvfrbw6cb1ttgtfyob0aqdxdkprjy598j5rd94ca52kij128y23ede66z5\",\"accountRelation\":{\"type\":\"single\"}}},\"objects\":{\"SimpleProfile\":{\"displayName\":{\"type\":\"string\",\"required\":true}}},\"enums\":{},\"accountData\":{\"simpleProfile\":{\"type\":\"node\",\"name\":\"SimpleProfile\"}}}"
)

(defonce compose (ComposeClient. #js {:ceramic "http://localhost:7007"}
                                 composite))


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
