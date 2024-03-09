(ns co-who.components.chain-manu
  (:require ["co-who.blueprint.dropdown" :refer [dropdown-comp]]
            ["co-who.blueprint.blockie" :refer [blockie-comp]]
            ["co-who.blueprint.button" :refer [button]]
            ["co-who.evm.util" :as eu]
            ["mr-who/mutations" :as m]
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
