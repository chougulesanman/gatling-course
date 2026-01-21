package acetoys.session;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

import io.gatling.javaapi.core.ChainBuilder;
import java.text.DecimalFormat;

public class UserSession {

  private static final DecimalFormat df = new DecimalFormat("#,##0.00");

  public static ChainBuilder initSession =
      exec(flushCookieJar())
          .exec(session -> session.set("productsListPageNumber", 1))
          .exec(session -> session.set("itemsInBasket", 0))
          .exec(session -> session.set("basketTotal", 0.00))
          .exec(session -> session.set("customerLoggedIn", false));

  public static ChainBuilder incrementItemsInBasket =
      exec(
          session -> {
            int currentItemsInBasket = session.getInt("itemsInBasket");
            return session.set("itemsInBasket", currentItemsInBasket + 1);
          });

  public static ChainBuilder incrementSessionBasketTotal =
      exec(
          session -> {
            double currentBasketTotal = session.getDouble("basketTotal");
            double productPrice = session.getDouble("price");
            return session.set("basketTotal", df.format(currentBasketTotal + productPrice));
          });
}
