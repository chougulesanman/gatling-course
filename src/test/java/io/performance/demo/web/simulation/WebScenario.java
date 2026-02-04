package io.performance.demo.web.simulation;

import static io.gatling.javaapi.core.CoreDsl.*;

import io.gatling.javaapi.core.ScenarioBuilder;
import io.performance.demo.common.LoadProfileConfig;
import io.performance.demo.web.journeys.WebUserJourneys;

import java.time.Duration;

public class WebScenario {

  private static final Duration TEST_DURATION =
      Duration.ofSeconds(LoadProfileConfig.testDurationSeconds());

  public static final ScenarioBuilder defaultLoadTest =
      scenario("Default Load Test")
          .during(TEST_DURATION)
          .on(
              exec(WebUserJourneys.browseStoreGuestUser)
                  .doIf(session -> Math.random() < 0.3)
                  .then(exec(WebUserJourneys.leaveBasketGuestUser))
                  .doIf(session -> Math.random() < 0.1)
                  .then(exec(WebUserJourneys.completePurchase))
          );

  public static final ScenarioBuilder highPurchaseLoadTest =
      scenario("High Purchase Load Test")
          .during(TEST_DURATION)
          .on(
              exec(WebUserJourneys.browseStoreGuestUser)
                  .doIf(session -> Math.random() < 0.3)
                  .then(exec(WebUserJourneys.leaveBasketGuestUser))
                  .doIf(session -> Math.random() < 0.4)
                  .then(exec(WebUserJourneys.completePurchase))
          );
}
