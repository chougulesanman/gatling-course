package io.performance.demo.web.journeys;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.performance.demo.web.session.UserSessionContext.*;

import io.gatling.javaapi.core.ChainBuilder;
import io.performance.demo.common.LoadProfileConfig;
import io.performance.demo.web.pageobjects.AuthPage;
import io.performance.demo.web.pageobjects.CartPage;
import io.performance.demo.web.pageobjects.CategoryPage;
import io.performance.demo.web.pageobjects.ProductPage;
import io.performance.demo.web.pageobjects.StaticPages;

import java.time.Duration;

public class WebUserJourneys {

    private static final Duration MIN_PAUSE = LoadProfileConfig.minPause();
    private static final Duration MAX_PAUSE = LoadProfileConfig.maxPause();

    public static final ChainBuilder browseStoreGuestUser = group("browseStoreGuestUser")
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
                                    exec(CategoryPage.categoryPage)
                                            .pause(MIN_PAUSE, MAX_PAUSE)
                                            .exec(CategoryPage.cyclePagesOfProducts)
                                            .pause(MIN_PAUSE, MAX_PAUSE)
                                            .exec(ProductPage.productDetailsPage)
                                            .pause(MIN_PAUSE, MAX_PAUSE)));

    public static final ChainBuilder leaveBasketGuestUser = group("leaveBasketGuestUser")
            .on(
                    exec(initSession)
                            .pause(MIN_PAUSE)
                            .exec(StaticPages.homePage)
                            .pause(MIN_PAUSE, MAX_PAUSE)
                            .exec(CategoryPage.categoryPage)
                            .pause(MIN_PAUSE, MAX_PAUSE)
                            .exec(ProductPage.productDetailsPage)
                            .pause(MIN_PAUSE, MAX_PAUSE)
                            .exec(ProductPage.addProductToCart)
                            .pause(MIN_PAUSE, MAX_PAUSE));

    public static final ChainBuilder completePurchase = group("completePurchase")
            .on(
                    exec(initSession)
                            .pause(MIN_PAUSE)
                            .exec(StaticPages.homePage)
                            .pause(MIN_PAUSE, MAX_PAUSE)
                            .exec(CategoryPage.categoryPage)
                            .pause(MIN_PAUSE, MAX_PAUSE)
                            .exec(ProductPage.productDetailsPage)
                            .pause(MIN_PAUSE, MAX_PAUSE)
                            .exec(ProductPage.addProductToCart)
                            .pause(MIN_PAUSE, MAX_PAUSE)
                            .exec(CartPage.viewCartPage)
                            .pause(MIN_PAUSE, MAX_PAUSE)
                            .exec(CartPage.increaseQtyInCart)
                            .pause(MIN_PAUSE, MAX_PAUSE)
                            .exec(CartPage.checkoutPage)
                            .pause(MIN_PAUSE, MAX_PAUSE)
                            .exec(AuthPage.logoutUser));
}
