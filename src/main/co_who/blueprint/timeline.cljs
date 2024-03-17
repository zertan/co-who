(ns co-who.blueprint.timeline
    (:require ["mr-who/dom" :as dom]
              [co-who.blueprint.blockie :as b :refer [blockie-comp]]))

(defn event-comp [{:keys [id blockie] :or {id (random-uuid)
                                                 blockie (:data (blockie-comp {}))}}]
  (dom/a {:id id
          :href "#"
          :class "items-center block p-3 sm:flex hover:bg-gray-100 dark:hover:bg-gray-700 dark:bg-[#101014]"}
    (dom/img {:class "w-12 h-12 mb-3 mr-3 rounded-full sm:mb-0"
              :src (b/make-blockie (:address blockie))})
    (dom/div {:class "text-gray-600 dark:text-gray-400"}
      (dom/div {:class "text-base font-normal"}
        (dom/span {:class "font-medium text-gray-900 dark:text-white"}
          "Jese Leos")
        (dom/span {} " likes ")
        (dom/span {:class "font-medium text-gray-900 dark:text-white"}
          "Bonnie Green's")
        (dom/span {} " post in ")
        (dom/span {:class "font-medium text-gray-900 dark:text-white"}
          " How to start with Flowbite library"))
      (dom/div {:class "text-sm font-normal"}
        "\"I wanted to share a webinar zeroheight.\"")
      (dom/span {:class
                 "inline-flex items-center text-xs font-normal text-gray-500 dark:text-gray-400"}
        (dom/svg {:class "w-2.5 h-2.5 mr-1",
                  :aria-hidden true
                  :xmlns "http://www.w3.org/2000/svg",
                  :fill "currentColor",
                  :viewBox "0 0 20 20"}
          (dom/path {:d
                     "M10 .5a9.5 9.5 0 1 0 0 19 9.5 9.5 0 0 0 0-19ZM8.374 17.4a7.6 7.6 0 0 1-5.9-7.4c0-.83.137-1.655.406-2.441l.239.019a3.887 3.887 0 0 1 2.082 2.5 4.1 4.1 0 0 0 2.441 2.8c1.148.522 1.389 2.007.732 4.522Zm3.6-8.829a.997.997 0 0 0-.027-.225 5.456 5.456 0 0 0-2.811-3.662c-.832-.527-1.347-.854-1.486-1.89a7.584 7.584 0 0 1 8.364 2.47c-1.387.208-2.14 2.237-2.14 3.307a1.187 1.187 0 0 1-1.9 0Zm1.626 8.053-.671-2.013a1.9 1.9 0 0 1 1.771-1.757l2.032.619a7.553 7.553 0 0 1-3.132 3.151Z"}))
        (dom/text {} "Public")))))

(defn timeline-comp [events]
  (dom/div {:class "p-5 mb-4 border border-gray-100 rounded-lg dark:bg-[#101014] dark:border-gray-800"}
    (dom/time {:class "text-lg font-semibold text-gray-900 dark:text-white"}
              "January 13th, 2022")
    (dom/ol {:class "mt-3 divide-y divider-gray-200 dark:divide-gray-700"}
      (doall
       (for [e events]
         (dom/li {} (event-comp e)))))))

