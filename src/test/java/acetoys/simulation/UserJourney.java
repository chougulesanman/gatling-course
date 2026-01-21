package acetoys.simulation;

import static acetoys.session.UserSession.*;
import static io.gatling.javaapi.core.CoreDsl.*;

import acetoys.pageobjects.Authentication;
import acetoys.pageobjects.Cart;
import acetoys.pageobjects.Category;
import acetoys.pageobjects.Product;
import acetoys.pageobjects.StaticPages;
import io.gatling.javaapi.core.*;
import java.time.Duration;

public class UserJourney {
  private static final Duration MIN_PAUSE = Duration.ofMillis(1000);
  private static final Duration MAX_PAUSE = Duration.ofMillis(5000);

  public static ChainBuilder browseStoreGuestUser =
      exec(initSession)
          .pause(MIN_PAUSE)
          .exec(StaticPages.homePage)
          .pause(MIN_PAUSE, MAX_PAUSE)
          .exec(StaticPages.ourStoryPage)
          .pause(MIN_PAUSE, MAX_PAUSE)
          .exec(StaticPages.getInTouchPage)
          .pause(MAX_PAUSE)
          .repeat(3)
          .on(
              exec(Category.categoryPage)
                  .pause(MIN_PAUSE, MAX_PAUSE)
                  .exec(Category.cyclePagesOfProducts)
                  .pause(MIN_PAUSE, MAX_PAUSE)
                  .exec(Product.productDetailsPage)
                  .pause(MIN_PAUSE, MAX_PAUSE));

  public static ChainBuilder leaveBasketGuestUser =
      exec(initSession)
          .pause(MIN_PAUSE)
          .exec(StaticPages.homePage)
          .pause(MIN_PAUSE, MAX_PAUSE)
          .exec(Category.categoryPage)
          .pause(MIN_PAUSE, MAX_PAUSE)
          .exec(Product.productDetailsPage)
          .pause(MIN_PAUSE, MAX_PAUSE)
          .exec(Product.addProductToCart)
          .pause(MIN_PAUSE, MAX_PAUSE);

  public static ChainBuilder completePurchase =
      exec(initSession)
          .pause(MIN_PAUSE)
          .exec(StaticPages.homePage)
          .pause(MIN_PAUSE, MAX_PAUSE)
          .exec(Category.categoryPage)
          .pause(MIN_PAUSE, MAX_PAUSE)
          .exec(Product.productDetailsPage)
          .pause(MIN_PAUSE, MAX_PAUSE)
          .exec(Product.addProductToCart)
          .pause(MIN_PAUSE, MAX_PAUSE)
          .exec(Cart.viewCartPage)
          .pause(MIN_PAUSE, MAX_PAUSE)
          .exec(Cart.increaseQtyInCart)
          .pause(MIN_PAUSE, MAX_PAUSE)
          .exec(Cart.checkoutPage)
          .pause(MIN_PAUSE, MAX_PAUSE)
          .exec(Authentication.logoutUser);
}
