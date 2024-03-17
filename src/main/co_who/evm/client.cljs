(ns co-who.evm.client
  (:require ["viem" :as v :refer [createWalletClient createPublicClient custom http]]
            ["viem/chains" :refer [mainnet polygon optimism optimismGoerli arbitrum arbitrumGoerli hardhat
                                   polygonMumbai avalanche avalancheFuji sepolia]]))

(defonce public-client (atom nil))
(defonce wallet-client (atom nil))
(defonce chains (atom nil))

(defn init-client []
  (reset! wallet-client (createWalletClient (clj->js {:chain mainnet
                                                   :transport (custom js/window.ethereum)})))
  (reset! public-client (createPublicClient (clj->js {:chain mainnet
                                                   :transport (http)})))

  (reset! chains [mainnet hardhat]))
