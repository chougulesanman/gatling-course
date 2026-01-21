package acetoys.simulation;

import static io.gatling.javaapi.core.CoreDsl.*;

import io.gatling.javaapi.core.PopulationBuilder;
import java.time.Duration;

public class TestPopulation {

  private static final int USER_COUNT = Integer.parseInt(System.getProperty("USER_COUNT", "10"));
  private static final Duration RAMP_DURATION_IN_SECONDS =
      Duration.ofSeconds(Integer.parseInt(System.getProperty("RAMP_DURATION_IN_SECONDS", "30")));

  public static PopulationBuilder instantUsers =
      TestScenario.defaultLoadTest.injectOpen(nothingFor(5), atOnceUsers(USER_COUNT));

  public static PopulationBuilder rampUpusers =
      TestScenario.defaultLoadTest.injectOpen(
          nothingFor(5), rampUsers(USER_COUNT).during(RAMP_DURATION_IN_SECONDS));

  public static PopulationBuilder complexInjection =
      TestScenario.defaultLoadTest.injectOpen(
          nothingFor(5),
          constantUsersPerSec(10).during(20).randomized(),
          rampUsersPerSec(10).to(20).during(30).randomized());

  public static PopulationBuilder closedModelInjection =
      TestScenario.highPurchaseLoadTest.injectClosed(
          rampConcurrentUsers(10).to(20).during(RAMP_DURATION_IN_SECONDS),
          constantConcurrentUsers(20).during(60));
}
