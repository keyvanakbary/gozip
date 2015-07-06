(defproject gozip "0.1.0-SNAPSHOT"
  :description "Notification System"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.7.0"]
                 [clj-http "1.1.2"]
                 [compojure "1.3.4"]
                 [ring-mock "0.1.5"]
                 [ring/ring-jetty-adapter "1.3.2"]
                 [environ "1.0.0"]
                 [org.clojure/data.json "0.2.6"]
                 [camel-snake-kebab "0.3.2"]]
  :min-lein-version "2.0.0"
  :plugins [[environ/environ.lein "0.2.1"]]
  :hooks [environ.leiningen.hooks]
  :uberjar-name "gozip-standalone.jar"
  :profiles {:production {:env {:production true}}
             :uberjar {:main gozip.web, :aot :all}})
