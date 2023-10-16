(ns co-who.evm-client
  (:require ["viem" :as v :refer [createWalletClient custom  http]]
            ["viem/chains" :refer [mainnet polygon optimism optimismGoerli arbitrum arbitrumGoerli hardhat
                                   polygonMumbai avalanche avalancheFuji sepolia]]))

(def client (createWalletClient #js {:chain mainnet
                                     :transport (custom js/window.ethereum)}))
(def chains [mainnet hardhat])
