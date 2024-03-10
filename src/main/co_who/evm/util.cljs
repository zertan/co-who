(ns co-who.evm.util
  (:require ["viem/accounts" :as ac :refer [generatePrivateKey privateKeyToAccount]]))

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
