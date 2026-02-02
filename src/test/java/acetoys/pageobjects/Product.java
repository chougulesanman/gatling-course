package acetoys.pageobjects;

import static acetoys.session.UserSession.incrementItemsInBasket;
import static acetoys.session.UserSession.incrementSessionBasketTotal;
import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

import common.utils.DataFiles;
import common.utils.Headers;
import io.gatling.javaapi.core.ChainBuilder;
import io.gatling.javaapi.core.FeederBuilder;

public class Product {

  private static final FeederBuilder<Object> productFeeder =
      jsonFile(DataFiles.productDetailsJson()).random();

  public static final ChainBuilder productDetailsPage =
      feed(productFeeder)
          .exec(
              http("07_NavigateToProductDetailsPage")
                  .get("/product/#{slug}")
                  .headers(Headers.DEFAULT_HTML_HEADERS)
                  .check(css("#ProductDescription").isEL("#{description}"))
          );

  public static final ChainBuilder addProductToCart =
      exec(incrementItemsInBasket)
          .exec(
              http("08_AddProductToCart")
                  .get("/cart/add/#{id}")
                  .headers(Headers.DEFAULT_HTML_HEADERS)
                  .check(
                      substring("You have <span>#{itemsInBasket}</span> products in your Basket"))
          )
          .exec(incrementSessionBasketTotal);
}
