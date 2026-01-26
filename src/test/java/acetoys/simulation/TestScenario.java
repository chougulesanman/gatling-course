package acetoys.simulation;

import static io.gatling.javaapi.core.CoreDsl.*;

import io.gatling.javaapi.core.*;
import java.time.Duration;

public class TestScenario {

  private static final Duration TEST_DURATION =
      Duration.ofSeconds(Integer.parseInt(System.getProperty("TEST_DURATION_IN_SECONDS", "600")));

  public static ScenarioBuilder defaultLoadTest =
      scenario("Default Load Test")
          .during(TEST_DURATION)
          .on(
              exec(UserJourney.browseStoreGuestUser) // always browse
                  // ~30% also leave basket
                  .doIf(session -> Math.random() < 0.3)
                  .then(exec(UserJourney.leaveBasketGuestUser))
                  // ~10% also complete purchase
                  .doIf(session -> Math.random() < 0.1)
                  .then(exec(UserJourney.completePurchase)));

  public static ScenarioBuilder highPurchaseLoadTest =
      scenario("High Purchase Load Test")
          .during(TEST_DURATION)
          .on(
              exec(UserJourney.browseStoreGuestUser)
                  // ~30% leave basket
                  .doIf(session -> Math.random() < 0.3)
                  .then(exec(UserJourney.leaveBasketGuestUser))
                  // ~40% complete purchase
                  .doIf(session -> Math.random() < 0.4)
                  .then(exec(UserJourney.completePurchase)));
}
