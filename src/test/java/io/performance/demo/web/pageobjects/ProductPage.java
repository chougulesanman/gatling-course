package io.performance.demo.web.pageobjects;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;
import static io.performance.demo.web.session.UserSessionContext.incrementItemsInBasket;
import static io.performance.demo.web.session.UserSessionContext.incrementSessionBasketTotal;

import io.gatling.javaapi.core.ChainBuilder;
import io.gatling.javaapi.core.FeederBuilder;
import io.performance.demo.common.DataFileConfig;
import io.performance.demo.common.HttpHeaders;

public class ProductPage {

  private static final FeederBuilder<Object> productFeeder =
      jsonFile(DataFileConfig.productDetailsJson()).random();

  public static final ChainBuilder productDetailsPage =
      feed(productFeeder)
          .exec(
              http("07_NavigateToProductDetailsPage")
                  .get("/product/#{slug}")
                  .headers(HttpHeaders.DEFAULT_HTML_HEADERS)
                  .check(css("#ProductDescription").isEL("#{description}"))
          );

  public static final ChainBuilder addProductToCart =
      exec(incrementItemsInBasket)
          .exec(
              http("08_AddProductToCart")
                  .get("/cart/add/#{id}")
                  .headers(HttpHeaders.DEFAULT_HTML_HEADERS)
                  .check(
                      substring("You have <span>#{itemsInBasket}</span> products in your Basket"))
          )
          .exec(incrementSessionBasketTotal);
}
