package io.performance.demo.web.simulation;

import static io.gatling.javaapi.core.CoreDsl.*;

import io.gatling.javaapi.core.PopulationBuilder;
import io.performance.demo.common.LoadProfileConfig;

import java.time.Duration;

public class WebLoadProfiles {

  private static final int LOAD_USER_COUNT = LoadProfileConfig.userCount();
  private static final int LOAD_FROM_USER_COUNT = LoadProfileConfig.fromUserCount();
  private static final Duration DO_NOTHING_FOR_DURATION_SECONDS =
      Duration.ofSeconds(LoadProfileConfig.donothingFor());
  private static final Duration LOAD_RAMP_DURATION_SECONDS =
      Duration.ofSeconds(LoadProfileConfig.rampDurationSeconds());

  public static final PopulationBuilder instantUsers =
      WebScenario.defaultLoadTest.injectOpen(
          nothingFor(DO_NOTHING_FOR_DURATION_SECONDS),
          atOnceUsers(LOAD_USER_COUNT)
      );

  public static final PopulationBuilder rampUpUsers =
      WebScenario.defaultLoadTest.injectOpen(
          nothingFor(DO_NOTHING_FOR_DURATION_SECONDS),
          rampUsers(LOAD_USER_COUNT).during(LOAD_RAMP_DURATION_SECONDS)
      );

  public static final PopulationBuilder complexInjection =
      WebScenario.defaultLoadTest.injectOpen(
          nothingFor(DO_NOTHING_FOR_DURATION_SECONDS),
          constantUsersPerSec(LOAD_FROM_USER_COUNT).during(LOAD_RAMP_DURATION_SECONDS).randomized(),
          rampUsersPerSec(LOAD_FROM_USER_COUNT).to(LOAD_USER_COUNT).during(LOAD_RAMP_DURATION_SECONDS).randomized()
      );

  public static final PopulationBuilder closedModelInjection =
      WebScenario.highPurchaseLoadTest.injectClosed(
          rampConcurrentUsers(LOAD_FROM_USER_COUNT).to(LOAD_USER_COUNT).during(LOAD_RAMP_DURATION_SECONDS),
          constantConcurrentUsers(LOAD_USER_COUNT).during(LOAD_RAMP_DURATION_SECONDS)
      );
}
