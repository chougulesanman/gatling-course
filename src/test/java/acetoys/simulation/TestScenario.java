package acetoys.simulation;

import static io.gatling.javaapi.core.CoreDsl.*;

import io.gatling.javaapi.core.*;
import java.time.Duration;

public class TestScenario {

  private static final Duration TEST_DURATION =
      Duration.ofSeconds(Integer.parseInt(System.getProperty("TEST_DURATION_IN_SECONDS", "60")));

  public static ScenarioBuilder defaultLoadTest =
      scenario("Default Load Test")
          .during(TEST_DURATION)
          .on(
              randomSwitch()
                  .on(
                      percent(60.0).then(exec(UserJourney.browseStoreGuestUser)),
                      percent(30.0).then(exec(UserJourney.leaveBasketGuestUser)),
                      percent(10.0).then(exec(UserJourney.completePurchase))));

  public static ScenarioBuilder highPurchaseLoadTest =
      scenario("High Purchase Load Test")
          .during(TEST_DURATION)
          .on(
              randomSwitch()
                  .on(
                      percent(30.0).then(exec(UserJourney.browseStoreGuestUser)),
                      percent(30.0).then(exec(UserJourney.leaveBasketGuestUser)),
                      percent(40.0).then(exec(UserJourney.completePurchase))));
}
