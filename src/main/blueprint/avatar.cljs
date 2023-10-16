(ns co-who.blueprint.datepicker
    (:require ["mr-who/dom" :as dom]))

(defn avatar-comp [src]
  (let []
    (dom/img {:class "w-10 h-10 rounded-full"
              :src "/docs/images/people/profile-picture-5.jpg"
              :alt "Rounded avatar"})))
