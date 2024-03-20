(ns co-who.layout.header
  (:require [mr-who.dom :as dom]
            [co-who.utils :as u]
            [co-who.blueprint.search :refer [search-comp]]
            ["co-blue/icons" :refer [academic-cap]]
            [co-who.blueprint.button :refer [icon-button]]
            [co-who.components.user :refer [user-comp]]
            [co-who.evm.client :refer [wallet-client chains]]
            [co-who.components.chain-menu :refer [chain-menu-comp]]))

(defn list-comp [item href]
  (dom/li {}
    (dom/a {:href href :data-navigo nil
            :class "block py-2 pr-4 pl-3 text-white rounded bg-primary-700 lg:bg-transparent lg:text-primary-700 lg:p-0 dark:text-white"
            :aria-current "page"}
      item)))

(defn header-comp [{:keys [id logo user modal-open-fn] :or {id :header
                                                            logo (str u/img-url "/images/codo_new_white.svg")
                                                            user ((first (user-comp {:address "0x0"})))
                                                            modal-open-fn #(println %)}}]
  (list (fn [] {:id id
                :logo logo
                :user user
                :modal-open-fn modal-open-fn})
        (fn []
          (dom/header {:id id}
                      (dom/nav {:id :n
                                :class "border-gray-200 text-gray-900 py-2
                                bg-[#f3f4f6] dark:bg-[#101014] select-none
                                dark:border-gray-800 dark:text-gray-400"} 
                        (dom/div {:id :n2
                                  :class "flex flex-wrap justify-between items-center mx-auto max-w-screen-xl"}
                          (dom/a  {:id :n2a
                                   :href "#", :class "flex items-center"}
                                  #_(dom/img  {:id :logo
                                             :src logo
                                             :class "mr-3 h-6 sm:h-9"
                                             :alt "Codo Logo"})
                            #_(dom/span  {:class "self-center text-xl font-semibold whitespace-nowrap dark:text-white"}
                                "Codo"))
                          (dom/div  {:id :n3
                                     :class "flex items-center lg:order-2 lg:space-x-8"}
                            (search-comp)
                            (chain-menu-comp @wallet-client @chains (:chain @wallet-client))
                            #_(dom/a 
                                  {:href "#",
                                   :class
                                   "text-gray-800 dark:text-white hover:bg-gray-50 focus:ring-4 focus:ring-gray-300 font-medium rounded-lg text-sm px-4 lg:px-5 py-2 lg:py-2.5 mr-2 dark:hover:bg-gray-700 focus:outline-none dark:focus:ring-gray-800"}
                                  "Log in")
                            ((second (user-comp user)))
                            (icon-button {:data-modal-target "wizard-modal" :data-modal-show "wizard-modal"} academic-cap (fn [])#_modal-open-fn)
                            (dom/button 
                                {:data-collapse-toggle "mobile-menu-2",
                                 :type "button",
                                 :class
                                 "inline-flex items-center p-2 ml-1 text-sm text-gray-500 rounded-lg lg:hidden hover:bg-gray-100 focus:outline-none focus:ring-2 focus:ring-gray-200 dark:text-gray-400 dark:hover:bg-gray-700 dark:focus:ring-gray-600",
                                 :aria-controls "mobile-menu-2",
                                 :aria-expanded "false"}
                                (dom/span   {:class "sr-only"} "Open main menu")
                                (dom/svg 
                                    {:class "w-6 h-6",
                                     :fill "currentColor",
                                     :viewBox "0 0 20 20",
                                     :xmlns "http://www.w3.org/2000/svg"}
                                    (dom/path 
                                        {:fill-rule "evenodd",
                                         :d
                                         "M3 5a1 1 0 011-1h12a1 1 0 110 2H4a1 1 0 01-1-1zM3 10a1 1 0 011-1h12a1 1 0 110 2H4a1 1 0 01-1-1zM3 15a1 1 0 011-1h12a1 1 0 110 2H4a1 1 0 01-1-1z",
                                         :clip-rule "evenodd"}))
                                (dom/svg  
                                    {:class "hidden w-6 h-6",
                                     :fill "currentColor",
                                     :viewBox "0 0 20 20",
                                     :xmlns "http://www.w3.org/2000/svg"}
                                    (dom/path 
                                        {:fill-rule "evenodd",
                                         :d "M4.293 4.293a1 1 0 011.414 0L10 8.586l4.293-4.293a1 1 0 111.414 1.414L11.414 10l4.293 4.293a1 1 0 01-1.414 1.414L10 11.414l-4.293 4.293a1 1 0 01-1.414-1.414L8.586 10 4.293 5.707a1 1 0 010-1.414z",
                                         :clip-rule "evenodd"}))))
                          (dom/div  {:class "hidden justify-between items-center w-full lg:flex lg:w-auto lg:order-1"
                                     :id "mobile-menu-2"}
                            (dom/ul {:class "flex flex-col mt-4 font-medium lg:flex-row lg:space-x-8 lg:mt-0"}
                              (doall
                               (for [t [ ["/" "/"] ["Activity" "/activity"] ["Profile" "/profile"]]]
                                 (list-comp (first t) (second t))))))))))))
