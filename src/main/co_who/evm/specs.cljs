(ns co-who.evm.specs
  (:require [cljs.spec.alpha :as s]
            [co-who.evm.abi :refer [token-abi]]))

;; Basic Ethereum Solidity types
(s/def ::basic-type (s/or :uint256 #(= "uint256" %)
                          :address #(= "address" %)
                          :bytes32 #(= "bytes32" %)
                          :string #(= "string" %)
                          :bool #(= "bool" %)))

;; Array type (e.g., "address[]")
(s/def ::array-type (s/and string?
                           #(re-matches #".+\[\]$" %)))

;; Custom or complex type (e.g., a struct or a contract interface)
;; Here we define a custom type loosely, as anything not matching basic or array types,
;; assuming it's a user-defined type or a more complex type.
(s/def ::custom-type (s/and string?
                            (complement ::basic-type)
                            (complement ::array-type)))

;; Combining types
(s/def ::internalType (s/or :basic ::basic-type
                            :array ::array-type
                            :custom ::custom-type))


(s/def ::name string?)

#_(s/def :abi-type/constructor #(= "constructor" %))
#_(s/def :abi-type/function #(= "function" %))
#_(s/def :abi-type/event #(= "event" %))
#_(s/def :abi-type/error #(= "error" %))

#_(s/def ::type #{"constructor" "function" "event" "error"} #_(s/or :constructor #(= "constructor" %)
                    :function #(= "function" %)
                    :event #(= "event" %)
                    :error #(= "error" %)))

(s/def ::stateMutability #{"pure" "view" "nonpayable" "payable"} #_(s/or :pure #(= "pure" %)
                                    :view #(= "view" %)
                                    :nonpayable #(= "nonpayable" %)
                                    :payable #(= "payable" %)))

(s/def ::indexed boolean?)
(s/def ::anonymous boolean?)

(s/def ::inputs (s/coll-of (s/keys :req-un [::internalType ::name]
                                   :opt-un [::indexed])))
;-------------

(s/def ::type string?)


;; Define the multi-spec using the dispatch function


;; Define specs for different components of the contract
;;
;;
;;
;; Constructor
(s/def ::constructor
  (s/keys :req-un [::inputs ::stateMutability ::type]))

;; Error
(s/def ::error
  (s/keys :req-un [::inputs ::name ::type]))

;; Event
(s/def ::event
  (s/keys :req-un [::anonymous ::inputs ::name ::type]))

;; Function
(s/def ::outputs (s/coll-of (s/keys :req-un [::internalType ::name ::type])))
(s/def ::function
  (s/keys :req-un [::inputs ::name ::type ::outputs ::stateMutability]
          ;:opt-un [::anonymous]
          ))

;; Dispatch function based on the :type key
(defmulti type-dispatch :type)
(defmethod type-dispatch "function" [_] ::function)
(defmethod type-dispatch "error" [_] ::error)
(defmethod type-dispatch "event" [_] ::event)
(defmethod type-dispatch "constructor" [_] ::constructor)


(s/def ::abi-entry (s/multi-spec type-dispatch :type) #_(s/or :constructor ::constructor
                         :error ::error
                         :event ::event
                         :function ::function))

;; Define a spec for the top-level structure
(s/def ::smart-contract
  (s/coll-of ::abi-entry))

;; Example function data

#_(def example-function
  {:inputs [{:internalType "uint256", :name "amount", :type "uint256"}]
   :name "deposit"
   :type "function"
   :outputs []
   :stateMutability "nonpayable"})

;; Validate the example function against the spec
; (s/valid? ::function example-function)

(comment
  (mapv #(s/conform ::abi-entry %) (map-indexed #(let [t (:type %2)]
                                                   (conj {(keyword (str t "/id")) %1} %2)) token-abi))
  (s/conform ::abi-entry (last token-abi))
  (s/conform ::abi-entry (first abi))
  (filterv #(s/valid? ::function %) token-abi)

  )
