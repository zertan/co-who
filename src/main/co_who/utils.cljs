(ns co-who.utils
  (:require [clojure.string :as string]))
  
(def img-url "https://codo-resources.s3.eu-north-1.amazonaws.com")

(defn random-evm []
  (string/join "0x" (.toString (js/crypto.randomBytes 32) "hex")))
