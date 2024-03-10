(ns co-who.routing
  (:require ["navigo" :as navigo]))

#_(def n (aget navigo "default"))

(declare router)

(defn init-routing []
  (def router (new navigo/default "/" true)))

(defn navigate [r path]
  (r.navigate path))

(defn add-route [r path f]
  (r.on path f))

#_(add-route router "/"
           #(println "hi /"))


#_(add-route router "/call"
           #(println "hi"))

#_(navigate router "/call")
