(ns co-who.layout.main
  (:require ["mr-who/dom" :as dom]
            ["./header.mjs" :as h]
            ["./router.mjs" :as rc]))

(defn root-comp [app {:keys [router header] :or {router ((first (rc/router-comp {})))
                                                 header ((first (h/header-comp {})))}}]
  (list (fn [] {:router router
                :header header})
        (fn [] (dom/div {:id :root
                         :class "bg-black w-screen h-screen text-white dark"}
                 ((second (h/header-comp header)))                 
                 ((second (rc/router-comp router)))))))
