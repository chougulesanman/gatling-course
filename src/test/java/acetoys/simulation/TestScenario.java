package acetoys.simulation;

import static io.gatling.javaapi.core.CoreDsl.*;

import acetoys.groups.UserJourney;
import common.utils.UserLoadConfig;
import io.gatling.javaapi.core.ScenarioBuilder;

import java.time.Duration;

public class TestScenario {

  private static final Duration TEST_DURATION =
      Duration.ofSeconds(UserLoadConfig.testDurationSeconds());

  public static final ScenarioBuilder defaultLoadTest =
      scenario("Default Load Test")
          .during(TEST_DURATION)
          .on(
              exec(UserJourney.browseStoreGuestUser)
                  .doIf(session -> Math.random() < 0.3)
                  .then(exec(UserJourney.leaveBasketGuestUser))
                  .doIf(session -> Math.random() < 0.1)
                  .then(exec(UserJourney.completePurchase))
          );

  public static final ScenarioBuilder highPurchaseLoadTest =
      scenario("High Purchase Load Test")
          .during(TEST_DURATION)
          .on(
              exec(UserJourney.browseStoreGuestUser)
                  .doIf(session -> Math.random() < 0.3)
                  .then(exec(UserJourney.leaveBasketGuestUser))
                  .doIf(session -> Math.random() < 0.4)
                  .then(exec(UserJourney.completePurchase))
          );
}
