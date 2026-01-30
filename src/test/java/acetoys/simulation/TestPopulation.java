package acetoys.simulation;

import static io.gatling.javaapi.core.CoreDsl.*;

import acetoys.utils.UserLoadConfig;
import io.gatling.javaapi.core.PopulationBuilder;

import java.time.Duration;

public class TestPopulation {

  private static final int USER_COUNT = UserLoadConfig.userCount();
  private static final Duration RAMP_DURATION_IN_SECONDS =
      Duration.ofSeconds(UserLoadConfig.rampDurationSeconds());

  public static final PopulationBuilder instantUsers =
      TestScenario.defaultLoadTest.injectOpen(
          nothingFor(5),
          atOnceUsers(USER_COUNT)
      );

  public static final PopulationBuilder rampUpUsers =
      TestScenario.defaultLoadTest.injectOpen(
          nothingFor(5),
          rampUsers(USER_COUNT).during(RAMP_DURATION_IN_SECONDS)
      );

  public static final PopulationBuilder complexInjection =
      TestScenario.defaultLoadTest.injectOpen(
          nothingFor(5),
          constantUsersPerSec(10).during(20).randomized(),
          rampUsersPerSec(10).to(20).during(30).randomized()
      );

  public static final PopulationBuilder closedModelInjection =
      TestScenario.highPurchaseLoadTest.injectClosed(
          rampConcurrentUsers(10).to(20).during(RAMP_DURATION_IN_SECONDS),
          constantConcurrentUsers(20).during(60)
      );
}
