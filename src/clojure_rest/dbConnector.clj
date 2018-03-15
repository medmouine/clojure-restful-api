(ns clojure-rest.handler
  (:require [monger.core :as mg])
  (:import [com.mongodb MongoOptions ServerAddress]))

(let [conn (mg/connect)])

(let [conn (mg/connect)
      db   (mg/get-db conn "clojure-api-db")])
