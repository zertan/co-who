(ns co-who.blueprint.avatar
    (:require [mr-who.dom :as dom]))

(defn avatar-comp [src]
  (dom/img {:class "w-10 h-10 rounded-lg"
            :src src
            :alt "Rounded avatar"}))
