(ns co-who.pages.landing
  (:require ["mr-who/dom" :as dom]
            #_["@shopify/draggable" :refer [Draggable]]
            #_["@interactjs/interact" :as intr]
            #_["@interactjs/actions/drag"]
            [co-who.blueprint.input :refer [input]]
            [co-who.mutations :as m]
            [co-who.blueprint.datepicker :refer [date-picker-comp]]
            [co-who.composedb.client :as cli :refer [compose]]))

#_(def interact (:default intr))

#_(defn init-draggable [node & opts]
  (Draggable. node opts))

;; function serializeObjectForHttpPost(query) {
;;   const result = {};
;;   for (const [key2, value] of Object.entries(query)) {
;;     if (StreamID.isInstance(value)) {
;;       result[key2] = value.toString();
;;     } else {
;;       result[key2] = value;
;;     }
;;   }
;;   return result;
;; }

#_(println
   (js/Object.entries {"account" "did:pkh:eip155:1:0xf39fd6e51aad88f6f4ce6ab8827279cfffb92266"
                       "models" ["kjzl6hvfrbw6cb1ttgtfyob0aqdxdkprjy598j5rd94ca52kij128y23ede66z5"]
                       "last" 1}))
 
(def query "query getSimpleProfile($id: ID!) {
  	          node(id: $id) {
                ... on SimpleProfile {
                  displayName
                }
  	          }
            }")

#_(def query " query {
  viewer {
  	simpleProfile { displayName }
  }
}")

#_(defn on-change [app ident]
  (reset! app ))

(defn on-click [app]
  (fn [e]
    (-> (compose.executeQuery query {:id "k2t6wzhkhabz32jtygv0cx41l8z74lw9dtz42vhs0aki6m9fa8ctaflqeor886"})
        (.then (fn [response]
                 (let [d (-> response :data :node :displayName)]
                   (m/replace-mutation app [:root :router :route :landing-form :idiv]
                                       (fn [] (input {:id :l-input
                                                      :placeholder ""}
                                                     d)) [:landing :l-input])))))))

(def mutation "mutation CreateSimpleProfile($i: CreateSimpleProfileInput!){
  createSimpleProfile(input: $i){
		document {
      displayName
    }
  }
}")

(defn mutation-vars [display-name] {:i {:content {:displayName display-name}}})

(defn on-click-mut [app ident]
  (fn [e]
    (println (mutation-vars (get-in @app ident)))
    (-> (compose.executeQuery mutation (mutation-vars (get-in @app ident)))
        (.then (fn [response] (println response))))))

(defn on-change [app ident]
  (fn  [e]
    (swap! app assoc-in ident e.target.value)))

(defn landing-comp [{:keys [id display-name on-click on-change on-click-mut] :or {id :landing
                                                                                  display-name "John Doe"}}]
  (list (fn [] {:id id
                :display-name display-name})
        (fn [] (let [p (dom/div {:id id}
                       (dom/span {:id :landing-span} "A pretty cool comp, with a datepicker.")
                       (date-picker-comp)
                       (dom/div {:class "item"} "Drag me")
                       (dom/button {:on-click on-click
                                    :class "text-white bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium rounded-lg text-sm w-full sm:w-auto px-5 py-2.5 text-center dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800"}
                         "Get Name")
                       (dom/form {:id :landing-form
                                  :on-submit (fn [e] (.preventDefault e)
                                               (println e)
                                               
                                               (on-click-mut)
                                               (println "w"))}
                         (input {:id :l-input
                                 :on-change on-change
                                 :placeholder "asds"}
                                display-name)
                         (dom/input
                          {:id "remember",
                           :type "checkbox",
                           :value "",
                           :class
                           "w-4 h-4 border border-gray-300 rounded bg-gray-50 focus:ring-3 focus:ring-blue-300 dark:bg-gray-700 dark:border-gray-600 dark:focus:ring-blue-600 dark:ring-offset-gray-800 dark:focus:ring-offset-gray-800",
                           :required nil})
                         ))
                     a (.draggable (interact ".item") #js {:listeners {:start (fn [e] (js/console.log "drag"))
                                                                   :move (fn [e] (js/console.log e.pageX e.pageY))}})
                     ;draggable (init-draggable js/document.body {})
                     ;a (draggable.on "drag:start" (fn [e] (println e)))
                     ]
                 p))))
