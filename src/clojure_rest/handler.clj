(ns clojure-rest.handler
  (:import com.mchange.v2.c3p0.ComboPooledDataSource)
  (:use compojure.core)
  (:use cheshire.core)
  (:use ring.util.response)
  (:require [compojure.handler :as handler]
            [ring.middleware.json :as middleware]
            [clojure.java.jdbc :as sql]
            [compojure.route :as route]))

(defroutes app-routes
  (context "/promotions" [] (defroutes promotionsRoutes
                              (GET "/" [] (getAllPromotions))
                              (POST "/" {body :body} (addNewPromotion))
                              (context "/:id" [id] (defroutes promotionRoutes
                                                     (GET "/" [] (getPromotionById id))
                                                     (PUT "/" {body :body} (modifyPromotionById id body))
                                                     (PATCH "/" {body :body} (updatePromotionById id body))
                                                     (DELETE "/" [] (deletePromotionById))))))
  (context "/shops" [] (defroutes shopsRoutes
                         (GET "/" [] (getAllShops))
                         (POST "/" {body :body} (addNewShop body))
                         (context "/:id" [id] (defroutes shopRoutes
                                                (GET "/" [] (getShopById id))
                                                (PUT "/" {body :body} (modifyShopById id body))
                                                (PATCH "/" {body :body} (updateShopById id body))
                                                (DELETE "/" [] (deleteShopById id))))))
  (route/not-found "Error: Ressource Not Found"))

(def app
  (-> (handler/api app-routes)        
      (middleware/wrap-json-body)
      (middleware/wrap-json-response)))
