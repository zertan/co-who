(ns co-who.components.evm-chain-manu
  (:require ["../blueprint/dropdown.mjs" :refer [dropdown-comp]]
            ["../blueprint/blockie.mjs" :refer [blockie-comp]]
            ["../blueprint/button.mjs" :refer [button]]
            ["../evm_util.mjs" :as eu]
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

(defn load-blockie [app client]
  (m/merge-comp app blockie-comp {:blockie/id 1
                                  :address (eu/get-address client)}
                {:f assoc-in :path [:chain-menu/id "1" :blockie]}))

;(get-address client)

#_(defn user-menu-comp [{:keys [chain-menu/id blockie]}]
  (dropdown-comp
   (blockie-comp blockie #_(get-in @app [:chain-menu/id "1" :address]))
   (for [e ["My Projects"]]
       e)))

(defn chain-menu-comp [client chains chain]
  (dropdown-comp (:name chain)
   (for [c chains]
     (:name c))))

(defn connect-event [app client]
  (.then (eu/request-addresses client)
         (fn [r]
           (swap! app assoc-in [:user/id "1" :address] (first r)))))

(defn connect-button [app client]
  (button "Connect" #(connect-event app client)))

#_(defn user-menu-2-comp [id client blockie app {:keys [chain-menu/id ]
                                 :or {chain-menu/id (u/random-uuid)
                                      blockie (:data (blockie-comp))}}]
  (if (:account client)
    (dom/div {}
      (:render (blockie-comp blockie)))
    (connect-button app client)))
