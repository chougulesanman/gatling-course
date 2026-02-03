package io.performance.demo.web.pageobjects;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;
import static io.performance.demo.web.session.UserSessionContext.*;

import io.gatling.javaapi.core.ChainBuilder;
import io.performance.demo.common.HttpHeaders;

public class CartPage {

  public static final ChainBuilder viewCartPage =
      doIf(session -> !session.getBoolean("customerLoggedIn"))
          .then(exec(AuthPage.loginUser))
          .exec(
              http("11_NavigateToViewCartPage")
                  .get("/cart/view")
                  .headers(HttpHeaders.DEFAULT_HTML_HEADERS)
                  .check(css("#CategoryHeader").is("Cart Overview"))
          );

  public static final ChainBuilder increaseQtyInCart =
      exec(incrementItemsInBasket)
          .exec(incrementSessionBasketTotal)
          .exec(
              http("05_IncreaseQtyInCart")
                  .get("/cart/add/#{id}?cartPage=true")
                  .headers(HttpHeaders.DEFAULT_HTML_HEADERS)
                  .check(css("#grandTotal").isEL("$#{basketTotal}"))
          );

  public static final ChainBuilder checkoutPage =
      exec(
          http("13_NavigateToCheckoutPage")
              .get("/cart/checkout")
              .headers(HttpHeaders.DEFAULT_HTML_HEADERS)
              .check(substring("Order complete!"))
      );
}
