(ns co-who.evm.lib
  (:require ["viem" :as viem :refer [getContract getAbiItem]]
            [co-who.evm.client :refer [wallet-client public-client]]
            [co-who.evm.abi :refer [token-abi]]
            [cljs.core.async :as a :refer [go]]))

(defn get-contract [data]
  (getContract (clj->js data)))

(defn simulate-contract [public-client account address abi function-name & args]
  (public-client.simulateContract (clj->js {:address address :abi abi :account account :functionName function-name
                                            :args args})))

;; #_(defn write-contract [client request]
;;   (simulate-contract )
;;   simulateContract({
;;                     account,
;;                     address: '0xFBA3912Ca04dd458c843e2EE08967fC04f3579c2',
;;                     abi: wagmiAbi,
;;                     functionName: 'mint',
;;                     }))


(comment

  (.then (.getAddresses @wallet-client) #(println (first %)))

  (println "a")

  (let [;acc (<p! (.getAddresses @wallet-client))
        c (get-contract {:address "0xF5072f9F13aC7f5C7FED7f306A3CC26CaD6dD652"
                         :abi (clj->js token-abi)
                         :client (clj->js @public-client)#_(clj->js {:public @public-client :wallet @wallet-client})}) ;
        ]
    (.then (c.read.totalSupply) #(println %)))


  (let [contract-data {:address "0xF5072f9F13aC7f5C7FED7f306A3CC26CaD6dD652"
                       :abi (clj->js token-abi)
                       :client (clj->js {:public @public-client :wallet @wallet-client})}
        c (getContract (clj->js contract-data))]
    (.then ((aget c.read "balanceOf") #js ["0xa8172E99effDA57900e09150f37Fea5860b806B4"]) #(println %))
    (.then ((aget c.read "name") #js []) #(println %))
    (.then ((aget c.read "owner") #js []) #(println %))
    #_(.then ((aget c.write "mint") #js ["0xa8172E99effDA57900e09150f37Fea5860b806B4" 1]) #(println %))
    )

  (simulate-contract public-client  "0xF5072f9F13aC7f5C7FED7f306A3CC26CaD6dD652" token-abi "mint" )



  (getAbiItem #js {:abi (clj->js token-abi)
               :name "mint"}))
