(ns co-who.components.footer
  (:require ["mr-who/dom" :as dom]))

(dom/footer
 {:class "bg-white rounded-lg shadow m-4 dark:bg-gray-800"}
 (dom/div
     {:class
      "w-full mx-auto max-w-screen-xl p-4 md:flex md:items-center md:justify-between"}
     (dom/span
         {:class "text-sm text-gray-500 sm:text-center dark:text-gray-400"}
         "© 2023 "
         (dom/a
             {:href "https://flowbite.com/", :class "hover:underline"}
             "Flowbite™")
         ". All Rights Reserved.\n    ")
     (dom/ul
         {:class
          "flex flex-wrap items-center mt-3 text-sm font-medium text-gray-500 dark:text-gray-400 sm:mt-0"}
         (dom/li
             {}
             (dom/a
                 {:href "#", :class "mr-4 hover:underline md:mr-6 "}
                 "About"))
         (dom/li
             {}
             (dom/a
                 {:href "#", :class "mr-4 hover:underline md:mr-6"}
                 "Privacy Policy"))
         (dom/li
             {}
             (dom/a
                 {:href "#", :class "mr-4 hover:underline md:mr-6"}
                 "Licensing"))
         (dom/li
             {} 
             (dom/a {:href "#", :class "hover:underline"} "Contact")))))

