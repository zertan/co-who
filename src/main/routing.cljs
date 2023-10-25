(ns routing
  (:require ["navigo" :as navigo]
            ["mr-who/dom" :as dom]
            ["mr-who/utils" :as u]
            ["mr-who/render" :as render]))

(def router (new navigo.default "/"))

(defn navigate [r path]
  (r.navigate path))

(defn add-route [r path f]
  (r.on path f))

#_(add-route router "/"
           #(println "hi /"))


#_(add-route router "/call"
           #(println "hi"))

#_(navigate router "/call")
