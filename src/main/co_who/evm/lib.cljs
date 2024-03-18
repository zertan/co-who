(ns co-who.evm.lib
  (:require ["viem" :as viem :refer [getContract]]
            [co-who.evm.client :refer [wallet-client public-client]]
            [co-who.evm.abi :refer [token-abi]]
            [cljs.core.async :as a :refer [go]]))

(defn get-contract [data]
  (getContract (clj->js data)))

(defn simulate-contract [public-client account address abi function-name & args]
  (public-client.simulateContract (clj->js {:address address :abi abi :account account :functionName function-name
                                            :args args})))

#_(defn write-contract [client request]
  (simulate-contract )
  simulateContract({
                    account,
                    address: '0xFBA3912Ca04dd458c843e2EE08967fC04f3579c2',
                    abi: wagmiAbi,
                    functionName: 'mint',
                    }))


(comment

  (.then (.getAddresses @wallet-client) #(println %))
  
  (go
    (let [acc (<p! (.getAddresses @wallet-client))
          c (get-contract {:address "0xF5072f9F13aC7f5C7FED7f306A3CC26CaD6dD652"
                           :abi (clj->js token-abi)
                           :client (clj->js {:public @public-client :wallet @wallet-client})})]
      (println acc)
      #_(c.read.balanceOf "")))
  
  (simulate-contract public-client  "0xF5072f9F13aC7f5C7FED7f306A3CC26CaD6dD652" token-abi "mint" ))
