(ns gozip.web
  (:require [compojure.core :refer [defroutes GET PUT POST DELETE ANY]]
            [compojure.handler :refer [site]]
            [compojure.route :as route]
            [clojure.java.io :as io]
            [ring.adapter.jetty :as jetty]
            [environ.core :refer [env]]
            [gozip.victorops :as victorops]
            [ring.util.response :as response])
  (:gen-class))

(defroutes
  app
  (GET "/" []
    (slurp (io/resource "form.html")))
  (POST "/alert" req
    (let [author (get (:params req) :author)
          type (get (:params req) :type)
          message (get (:params req) :message)]
      (victorops/alert
        {:message-type type
         :state-message message
         :ack-author author}))
    (response/redirect "/"))
  (ANY "*" []
    (route/not-found (slurp (io/resource "404.html")))))

(defn -main [& [port]]
  (let [port (Integer. (or port (env :port) 5000))]
    (victorops/set-api-key! (env :victorops-api-key))
    (jetty/run-jetty (site #'app) {:port port :join? false})))