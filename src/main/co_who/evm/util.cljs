(ns co-who.evm.util
  (:require ["viem" :refer [parseEther]]
            ["viem/accounts" :as ac :refer [generatePrivateKey privateKeyToAccount]]))

(set! *warn-on-infer* false)

(defn generate-private-key []
  (generatePrivateKey))

(defn account-from-private-key [key]
  (privateKeyToAccount key))

(defn request-addresses  [client f]
  (.then (.requestAddresses client) f))

(defn get-address [client f]
  (.then (.getAddresses client)
         f))

(defn get-chain [client]
  (.then (.get client)
         #(first %)))

(defn add-listener [ethereum event handler]
  (.. ethereum (on event handler)))

(defn add-accounts-changed [ethereum f]
  (add-listener ethereum "accountsChanged" f))

(defn parse-ether [value]
  (parseEther value))
