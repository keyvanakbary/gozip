(ns gozip.victorops
  (:require [clj-http.client :as client]
            [clojure.data.json :as json]))

(def end-point "https://alert.victorops.com/integrations/generic/20131114/alert/")

(def api-key (atom nil))

(defn set-api-key! [key]
  (reset! api-key key))

(defn alert []
  (client/post (str end-point @api-key "/everyone")
               {:body (json/write-str
                        {:message_type "INFO"
                         :state_message "test"
                         :originating_host "gozip"
                         :user "keyvan"})
                :content-type :json}))