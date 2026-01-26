package acetoys;

import static io.gatling.javaapi.http.HttpDsl.*;

import acetoys.simulation.TestPopulation;
import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;

public class AceToysSimulation extends Simulation {

  private static final String DOMAIN = "acetoys.uk";
  private static final String TEST_TYPE = System.getProperty("TEST_TYPE", "CLOSED_MODEL_INJECTION");

  // HTTP Config
  private HttpProtocolBuilder httpProtocol =
      http.baseUrl("https://" + DOMAIN)
          .acceptEncodingHeader("gzip, deflate")
          .acceptLanguageHeader("en-GB,en;q=0.9");

  /*
   * private static ChainBuilder homePage =
   * exec(
   * http("01_NavigateToHomePage")
   * .get("/")
   * .check(substring("<title>Ace Toys Online Shop</title>"))
   * .check(css("meta[name='_csrf']", "content").saveAs("csrfToken"))
   * .check(status().is(200)));
   *
   * private static ChainBuilder ourStoryPage =
   * exec(
   * http("02_NavigateToOurStoryPage")
   * .get("/our-story")
   * .check(regex("was founded online in \\d{4}")));
   *
   * private static ChainBuilder getInTouchPage =
   * exec(http("03_NavigateToGetInTouchPage").get("/get-in-touch"));
   *
   * private static ChainBuilder allCategoryPage =
   * exec(http("04_NavigateToAllCategoryPage").get("/category/all"));
   *
   * private static ChainBuilder allCategoryPage1 =
   * exec(http("05_NavigateToAllCategoryPage1").get("/category/all?page=1"));
   *
   * private static ChainBuilder allCategoryPage2 =
   * exec(http("06_NavigateToAllCategoryPage2").get("/category/all?page=2"));
   *
   * private static ChainBuilder babyToysCategoryPage =
   * exec(http("09_NavigateToBabyToysCategoryPage").get("/category/babies-toys"));
   *
   * private static ChainBuilder productDetailsPage =
   * exec(http("07_NavigateToproductDetailsPage").get("/product/darts-board"));
   *
   * private static ChainBuilder addProductToCart =
   * exec(http("08_AddProductToCart").get("/cart/add/19"));
   *
   * private static ChainBuilder addProductToCart1 =
   * exec(http("10_AddProductToCart").get("/cart/add/4"));
   *
   * private static ChainBuilder viewCartPage =
   * exec(http("11_NavigateToViewCartPage").get("/cart/view"));
   *
   * private static ChainBuilder checkoutPage =
   * exec(
   * http("13_NavigateToCheckoutPage")
   * .get("/cart/checkout")
   * .check(substring("Order complete!")));
   *
   * private static ChainBuilder loginUser =
   * exec(
   * http("12_UserLogin")
   * .post("/login")
   * .formParam("_csrf", "#{csrfToken}")
   * .formParam("username", "user1")
   * .formParam("password", "pass")
   * .check(css("meta[name='_csrf']", "content").saveAs("csrfTokenLogIn")));
   *
   * private static ChainBuilder logoutUser =
   * exec(http("14_UserLogout").post("/logout").formParam("_csrf",
   * "#{csrfTokenLogIn}"));
   */

  // Scenario Definition
  // private ScenarioBuilder scenarioDefinition =
  //     scenario("AceToys Test").exec(UserJourney.completePurchase);

  /*
  scenario("AceToys Test").exitBlockOnFail().on(homePage);

  scenario("AceToys Test")
      .forever()
      .on(
          exec(UserSession.initSession)
              .exec(StaticPages.homePage)
              .pause(1)
              .exec(StaticPages.ourStoryPage)
              .pause(1)
              .exec(StaticPages.getInTouchPage)
              .pause(1)
              .exec(Category.categoryPage)
              .pause(1)
              .exec(Category.cyclePagesOfProducts)
              .pause(1)
              .exec(Category.cyclePagesOfProducts)
              .pause(1)
              .exec(Product.productDetailsPage)
              .pause(1)
              .exec(Product.addProductToCart)
              .pause(1)
              .exec(Category.categoryPage)
              .pause(1)
              .exec(Product.addProductToCart)
              .pause(1)
              .exec(Cart.viewCartPage)
              .pause(1)
              .exec(Cart.increaseQtyInCart)
              .pause(1)
              .exec(Cart.increaseQtyInCart)
              .pause(1)
              .exec(Cart.viewCartPage)
              .pause(1)
              .exec(Cart.checkoutPage)
              .pause(1)
              .exec(Authentication.logoutUser));
  */

  // Load Simulation
  {
    if (TEST_TYPE.equalsIgnoreCase("INSTANT_USERS")) {
      setUp(TestPopulation.instantUsers).protocols(httpProtocol);
    } else if (TEST_TYPE.equalsIgnoreCase("RAMP_UP_USERS")) {
      setUp(TestPopulation.rampUpusers).protocols(httpProtocol);
    } else if (TEST_TYPE.equalsIgnoreCase("COMPLEX_INJECTION")) {
      setUp(TestPopulation.complexInjection).protocols(httpProtocol);
    } else if (TEST_TYPE.equalsIgnoreCase("CLOSED_MODEL_INJECTION")) {
      setUp(TestPopulation.closedModelInjection).protocols(httpProtocol);
    } else {
      System.out.println("Invalid Test Type provided. Please use one of the following:");
    }

    /*

    setUp(TestScenario.highPurchaseLoadTest.injectOpen(atOnceUsers(10))).protocols(httpProtocol);

    setUp(
    scenarioDefinition
    .injectOpen(nothingFor(5), rampUsers(USER_COUNT).during(RAMP_DURATION))
    .protocols(httpProtocol))
    .maxDuration(MAX_DURATION);

    setUp(scenarioDefinition.injectOpen(atOnceUsers(1)).protocols(httpProtocol));
    setUp(scenarioDefinition.injectOpen(atOnceUsers(1)).protocols(httpProtocol)).maxDuration(60);
    */
  }
}
