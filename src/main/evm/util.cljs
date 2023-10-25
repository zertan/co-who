(ns co-who.evm.util
  (:require ["viem/accounts" :as ac :refer [generatePrivateKey privateKeyToAccount]]))

(defn generate-private-key []
  (generatePrivateKey))

(defn account-from-private-key [key]
  (privateKeyToAccount key))

(defn ^:async request-addresses  [client]
  (js-await (.requestAddresses client)))

(defn ^:async get-address [client]
  (.then (.getAddresses client)
         #(first %)))

(defn get-chain [client]
  (.then (.get client)
         #(first %)))

