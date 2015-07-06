(ns gozip.web-test
  (:use clojure.test
        ring.mock.request
        gozip.web))

(deftest a-test
  (testing "Non existing url not found"
    (let [response (app (request :get "/users"))]
      (is (= 404 (:status response))))))