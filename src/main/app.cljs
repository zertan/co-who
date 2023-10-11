(ns app
  (:require ["./micro_ns.mjs" :as m]
            ["mr-who/render" :as render]
            ["mr-who/dom" :as dom]
            ["./header.mjs" :as h]
            ["./form.mjs" :as f]
            ["flowbite" :as fb]
            ["flowbite-datepicker" :refer [Datepicker]]
            ["apexcharts" :as ApexCharts]
            ["./blueprint/chart.mjs" :refer [chart-comp]]
            ["./blueprint/datepicker.mjs" :refer [date-picker-comp]])
  #_(:require-macros [mr-who.macros :as c]))

(defonce app (atom nil))

#_(c/defc)

(defn n-div [attr-map & rest]
  (let [e (js/document.createElement "div")]
    
    ))

#_(n-div {}
       (date-picker-comp))

(reset! app {:counter-list/id {"1" {:counters [[:counter/id 1] [:counter/id 2]]}}
             :counter/id {"1" {:counter/id "1"
                               :value 1
                               :name "a"
                               :mr-who/mounted-elements []}
                          "2" {:counter/id "2"
                               :value 2
                               :name "b"
                               :mr-who/mounted-elements []}}
             :chart/id {"1" {:conf {"chart" {"height" "100%" "maxWidth" "100%" "type" "area" "fontFamily" "Inter, sans-serif" "dropShadow" {"enabled" false} "toolbar" {"show" false}} "tooltip" {"enabled" true "x" {"show" false}} "fill" {"type" "gradient" "gradient" {"opacityFrom" 0.55 "opacityTo" 0 "shade" "#1C64F2" "gradientToColors" ["#1C64F2"]}} "dataLabels" {"enabled" false} "stroke" {"width" 6} "grid" {"show" false "strokeDashArray" 4 "padding" {"left" 2 "right" 2 "top" 0}} "series" [{"name" "New users" "data" [6500 6418 6456 6526 6356 6456] "color" "#1A56DB"}] "xaxis" {"categories" ["01 February" "02 February" "03 February" "04 February" "05 February" "06 February" "07 February"] "labels" {"show" false} "axisBorder" {"show" false} "axisTicks" {"show" false}} "yaxis" {"show" false}}
                             }}})

(render/render-and-meta-things (js/document.getElementById "app")
                               (dom/div {:class "dark bg-gray-900 w-screen h-screen"}

                                        (h/header-comp)
                                        (date-picker-comp)
                                        [:div {:id "dp"
                                               :class ""}]
                                        (f/form-comp)
                                        (chart-comp))

                               #_(m/counter-list-comp app {:counter-list/id "1"})
                               {:app app})

(let [e (js/document.getElementById "dp")]
  (Datepicker. e #js {:dark true
                      :autohide true}))

(let [e (js/document.getElementById "area-chart")
      a (println "apa: " ApexCharts)
      chart (ApexCharts. e #js (get-in @app [:chart/id 1 :conf]))]
  (.render chart))

#_(reset! vdom (n/db :mr-who/id (r/render-and-meta-things (js/document.getElementById "app")
                                                          (m/counter-comp app vdom {:counter/id "1"})
                                                          {:app app})))
