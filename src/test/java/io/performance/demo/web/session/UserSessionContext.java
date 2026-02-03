package io.performance.demo.web.session;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

import io.gatling.javaapi.core.ChainBuilder;

import java.text.DecimalFormat;

public class UserSessionContext {

  private static final DecimalFormat df = new DecimalFormat("#,##0.00");

  public static final ChainBuilder initSession =
      exec(flushCookieJar())
          .exec(session -> session.set("productsListPageNumber", 1))
          .exec(session -> session.set("itemsInBasket", 0))
          .exec(session -> session.set("basketTotal", 0.00))
          .exec(session -> session.set("customerLoggedIn", false));

  public static final ChainBuilder incrementItemsInBasket =
      exec(session -> {
        int currentItemsInBasket = session.getInt("itemsInBasket");
        return session.set("itemsInBasket", currentItemsInBasket + 1);
      });

  public static final ChainBuilder incrementSessionBasketTotal =
      exec(session -> {
        double currentBasketTotal = session.getDouble("basketTotal");
        double productPrice = session.getDouble("price");
        return session.set("basketTotal", df.format(currentBasketTotal + productPrice));
      });
}
