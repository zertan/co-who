(ns co-who.blueprint.utils)

(defn add-hidden [hidden? cls]
  (if hidden? (str cls " hidden") cls))
