package acetoys.pageobjects;

import static acetoys.session.UserSession.*;
import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

import common.utils.Headers;
import io.gatling.javaapi.core.ChainBuilder;

public class Cart {

  public static final ChainBuilder viewCartPage =
      doIf(session -> !session.getBoolean("customerLoggedIn"))
          .then(exec(Authentication.loginUser))
          .exec(
              http("11_NavigateToViewCartPage")
                  .get("/cart/view")
                  .headers(Headers.DEFAULT_HTML_HEADERS)
                  .check(css("#CategoryHeader").is("Cart Overview"))
          );

  public static final ChainBuilder increaseQtyInCart =
      exec(incrementItemsInBasket)
          .exec(incrementSessionBasketTotal)
          .exec(
              http("05_IncreaseQtyInCart")
                  .get("/cart/add/#{id}?cartPage=true")
                  .headers(Headers.DEFAULT_HTML_HEADERS)
                  .check(css("#grandTotal").isEL("$#{basketTotal}"))
          );

  public static final ChainBuilder checkoutPage =
      exec(
          http("13_NavigateToCheckoutPage")
              .get("/cart/checkout")
              .headers(Headers.DEFAULT_HTML_HEADERS)
              .check(substring("Order complete!"))
      );
}
