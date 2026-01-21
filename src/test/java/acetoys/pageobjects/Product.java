package acetoys.pageobjects;

import static acetoys.session.UserSession.incrementItemsInBasket;
import static acetoys.session.UserSession.incrementSessionBasketTotal;
import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

import io.gatling.javaapi.core.*;

public class Product {

  private static final FeederBuilder<Object> productFeeder =
      jsonFile("dataFiles/productDetails.json").random();

  public static ChainBuilder productDetailsPage =
      feed(productFeeder)
          .exec(
              http("07_NavigateToProductDetailsPage")
                  .get("/product/#{slug}")
                  .check(css("#ProductDescription").isEL("#{description}")));

  public static ChainBuilder addProductToCart =
      exec(incrementItemsInBasket)
          .exec(
              http("08_AddProductToCart")
                  .get("/cart/add/#{id}")
                  //   .check(bodyString().saveAs("responseBody"))
                  .check(
                      substring("You have <span>#{itemsInBasket}</span> products in your Basket")))
          .exec(incrementSessionBasketTotal);
  //   .exec(
  //       session -> {
  //         System.out.println(">>> Response Body for 08_AddProductToCart: " +
  // session.getString("responseBody"));
  //         return session;
  //       });

  // public static ChainBuilder addProductToCart1 =
  //     exec(
  //         http("10_AddProductToCart")
  //             .get("/cart/add/#{id}")
  //             //   .check(bodyString().saveAs("responseBody"))
  //             .check(substring("You have <span>2</span> products in your Basket")));
  //   .exec(
  //       session -> {
  //         System.out.println(
  //             ">>> Response Body for 10_AddProductToCart: "
  //                 + session.getString("responseBody"));
  //         return session;
  //       });
}
