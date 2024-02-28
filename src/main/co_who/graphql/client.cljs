(ns co-who.graphql.client
  (:require ["@urql/core" :refer [gql Client cacheExchange fetchExchange]]))

(defonce client (Client. #js {:url "http://localhost:39947/graphql"
                              :exchanges [cacheExchange fetchExchange]}))


(defn run-query [client query & params]
  (-> client
      (.query query #js {})
      (.toPromise)
      (.then #(println (:data %)))))

(defn run-mutation [client mutation & params]
  (-> client
      (.mutation mutation #js {})
      (.toPromise)
      (.then #(println (:data %)))))


(let [q
      "query {
       simpleProfile 
   }"]
    (run-query client q))

#_(let [m
      "mutation {
          createSimpleProfile(input: {
            content: {
              displayName: \"Kalle\"
            }
          }) 
          {
            document {
              displayName
            }
          }
        }"]
  (run-mutation client m))
