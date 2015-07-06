(ns gozip.web
  (:require [compojure.core :refer [defroutes GET PUT POST DELETE ANY]]
            [compojure.handler :refer [site]]
            [compojure.route :as route]
            [clojure.java.io :as io]
            [ring.adapter.jetty :as jetty]
            [environ.core :refer [env]]
            [gozip.victorops :as victorops])
  (:gen-class))

(defn splash []
  {:status  200
   :headers {"Content-Type" "text/plain"}
   :body    "Under your commands!"})

(defroutes
  app
  (GET "/" []
    (splash))
  (POST "/alert" []
    (victorops/alert))
  (ANY "*" []
    (route/not-found (slurp (io/resource "404.html")))))

(defn -main [& [port]]
  (let [port (Integer. (or port (env :port) 5000))]
    (victorops/set-api-key! (env :victorops-api-key))
    (jetty/run-jetty (site #'app) {:port port :join? false})))
