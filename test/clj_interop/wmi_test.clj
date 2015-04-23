(ns clj-interop.wmi-test
  (:require [clojure.test :refer :all]
            [clj-interop.wmi :refer :all]))

(deftest connect-test
  (testing "Trying to create a session"
    (connect "192.168.0.1" "192.168.0.1" "administrator" "password")))

