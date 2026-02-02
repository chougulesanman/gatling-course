package acetoys.groups;

import static acetoys.session.UserSession.*;
import static io.gatling.javaapi.core.CoreDsl.*;

import acetoys.pageobjects.Authentication;
import acetoys.pageobjects.Cart;
import acetoys.pageobjects.Category;
import acetoys.pageobjects.Product;
import acetoys.pageobjects.StaticPages;
import common.utils.UserLoadConfig;
import io.gatling.javaapi.core.ChainBuilder;

import java.time.Duration;

public class UserJourney {

  private static final Duration MIN_PAUSE = UserLoadConfig.minPause();
  private static final Duration MAX_PAUSE = UserLoadConfig.maxPause();

  public static final ChainBuilder browseStoreGuestUser =
      group("browseStoreGuestUser")
          .on(
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
                          .pause(MIN_PAUSE, MAX_PAUSE)
                  )
          );

  public static final ChainBuilder leaveBasketGuestUser =
      group("leaveBasketGuestUser")
          .on(
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
          );

  public static final ChainBuilder completePurchase =
      group("completePurchase")
          .on(
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
                  .exec(Authentication.logoutUser)
          );
}
