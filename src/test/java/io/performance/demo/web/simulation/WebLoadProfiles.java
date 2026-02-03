package io.performance.demo.web.simulation;

import static io.gatling.javaapi.core.CoreDsl.*;

import io.gatling.javaapi.core.PopulationBuilder;
import io.performance.demo.common.LoadProfileConfig;

import java.time.Duration;

public class WebLoadProfiles {

  private static final int LOAD_USER_COUNT = LoadProfileConfig.userCount();
  private static final Duration LOAD_RAMP_DURATION_SECONDS =
      Duration.ofSeconds(LoadProfileConfig.rampDurationSeconds());

  public static final PopulationBuilder instantUsers =
      WebScenario.defaultLoadTest.injectOpen(
          nothingFor(5),
          atOnceUsers(LOAD_USER_COUNT)
      );

  public static final PopulationBuilder rampUpUsers =
      WebScenario.defaultLoadTest.injectOpen(
          nothingFor(5),
          rampUsers(LOAD_USER_COUNT).during(LOAD_RAMP_DURATION_SECONDS)
      );

  public static final PopulationBuilder complexInjection =
      WebScenario.defaultLoadTest.injectOpen(
          nothingFor(5),
          constantUsersPerSec(10).during(20).randomized(),
          rampUsersPerSec(10).to(20).during(30).randomized()
      );

  public static final PopulationBuilder closedModelInjection =
      WebScenario.highPurchaseLoadTest.injectClosed(
          rampConcurrentUsers(10).to(20).during(LOAD_RAMP_DURATION_SECONDS),
          constantConcurrentUsers(20).during(60)
      );
}
