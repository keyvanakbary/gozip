(ns gozip.victorops
  (:require [clj-http.client :as client]
            [clojure.data.json :as json]
            [camel-snake-kebab.core :refer [->snake_case ->kebab-case-keyword]]
            [camel-snake-kebab.extras :refer [transform-keys]]))

(def end-point "https://alert.victorops.com/integrations/generic/20131114/alert/")

(def api-key (atom nil))

(defn set-api-key! [key]
  (reset! api-key key))

(def success? #(= (:result %) "success"))

(defn request [url parameters]
  {:post [success?]}
  (let [->snake-case #(transform-keys ->snake_case %)
        ->kebab-case #(transform-keys ->kebab-case-keyword %)
        response (client/post url
                   {:content-type :json
                    :body (-> parameters ->snake-case json/write-str)})]
    (-> response :body json/read-str ->kebab-case)))

(defn alert
  ([parameters] (alert parameters "everyone"))
  ([parameters routing-key]
   (let [url (str end-point @api-key "/" routing-key)]
     (request url parameters))))
