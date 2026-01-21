package acetoys.pageobjects;

import static acetoys.session.UserSession.*;
import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

import io.gatling.javaapi.core.*;

public class Cart {
  public static ChainBuilder viewCartPage =
      doIf(session -> !session.getBoolean("customerLoggedIn"))
          .then(exec(Authentication.loginUser))
          .exec(
              http("11_NavigateToViewCartPage")
                  .get("/cart/view")
                  //   .check(bodyString().saveAs("responseBody"))
                  .check(css("#CategoryHeader").is("Cart Overview")));
  // .exec(
  //     session -> {
  //       System.out.println(
  //           ">>> Response Body for 11_NavigateToViewCartPage: "
  //               + session.getString("responseBody"));
  //       return session;
  //     });

  public static ChainBuilder increaseQtyInCart =
      exec(incrementItemsInBasket)
          .exec(incrementSessionBasketTotal)
          .exec(
              http("05_IncreaseQtyInCart")
                  .get("/cart/add/#{id}?cartPage=true")
                  .check(css("#grandTotal").isEL("$#{basketTotal}")));

  public static ChainBuilder checkoutPage =
      exec(
          http("13_NavigateToCheckoutPage")
              .get("/cart/checkout")
              .check(substring("Order complete!")));
}
