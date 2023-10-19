(ns co-who.components.evm-chain-manu
  (:require ["../blueprint/dropdown.mjs" :refer [dropdown-comp]]
            ["../blueprint/blockie.mjs" :refer [blockie-comp]]
            #_["../mutations.mjs" :as m]
            ["mr-who/dom" :as dom]
            ["mr-who/mutations" :as m]
            ["mr-who/utils" :as u]
            ;["../blueprint/icons/web3" :refer [ethereum polygon optimism arbitrum hardhat]]
            ))

#_(def chain-icon {"Polygon" polygon
                 "Ethereum" ethereum
                 "Optimism" optimism
                 "Arbitrum One" arbitrum
                 "Hardhat" hardhat})

(defn ^:async request-addresses  [client]
  (js-await (.requestAddresses client)))

(defn ^:async get-address [client]
  (.then (.getAddresses client)
         #(first %)))

(defn get-chain [client]
  (.then (.get client)
         #(first %)))


(defn load-blockie [app client]
  (m/merge-comp app blockie-comp {:blockie/id 1
                                  :address (get-address client)}
                {:f assoc-in :path [:chain-menu/id "1" :blockie]}))

;(get-address client)

(defn user-menu-comp [{:keys [chain-menu/id blockie]}]
  (dropdown-comp
   (blockie-comp blockie #_(get-in @app [:chain-menu/id "1" :address]))
   (for [e ["My Projects"]]
       e)))

(defn chain-menu-comp [client chains chain]
  (dropdown-comp
   chain
   (for [c chains]
     (:name c))))

(defn user-menu-2-comp [{:keys [chain-menu/id blockie]
                         :or {chain-menu/id (u/random-uuid)
                              blockie (:data (blockie-comp))}}]
  (dom/div {}
    (:render (blockie-comp blockie))))
