(ns co-who.evm-util
  (:require ["viem/accounts" :as ac :refer [generatePrivateKey privateKeyToAccount]]))

(defn generate-private-key []
  (generatePrivateKey))

(defn account-from-private-key [key]
  (privateKeyToAccount key))
