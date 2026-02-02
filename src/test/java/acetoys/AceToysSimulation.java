package acetoys;

import static io.gatling.javaapi.core.CoreDsl.global;

import acetoys.simulation.TestPopulation;
import common.utils.EnvironmentConfig;
import common.utils.HttpProtocolFactory;
import common.utils.UserLoadConfig;
import io.gatling.javaapi.core.Simulation;
import io.gatling.javaapi.http.HttpProtocolBuilder;

public class AceToysSimulation extends Simulation {

  private static final String TEST_TYPE = UserLoadConfig.testType();

  private final HttpProtocolBuilder httpProtocol = HttpProtocolFactory.defaultHtmlProtocol();

  {
    System.out.println("Running AceToysSimulation");
    System.out.println("Environment: " + EnvironmentConfig.envName());
    System.out.println("Base URL: " + EnvironmentConfig.baseUrl());
    System.out.println("Test type: " + TEST_TYPE);
    System.out.println("Assertions: p95 < " + UserLoadConfig.responseTimeP95Ms()
        + " ms, p99 < " + UserLoadConfig.responseTimeP99Ms()
        + " ms, failed < " + UserLoadConfig.failedRequestsPercentMax() + " %");

    switch (TEST_TYPE.toUpperCase()) {
      case "INSTANT_USERS" -> setUp(TestPopulation.instantUsers)
          .protocols(httpProtocol)
          .assertions(
              global().responseTime().percentile3().lt(UserLoadConfig.responseTimeP95Ms()),
              global().responseTime().percentile4().lt(UserLoadConfig.responseTimeP99Ms()),
              global().failedRequests().percent().lt(UserLoadConfig.failedRequestsPercentMax())
          );
      case "RAMP_UP_USERS" -> setUp(TestPopulation.rampUpUsers)
          .protocols(httpProtocol)
          .assertions(
              global().responseTime().percentile3().lt(UserLoadConfig.responseTimeP95Ms()),
              global().responseTime().percentile4().lt(UserLoadConfig.responseTimeP99Ms()),
              global().failedRequests().percent().lt(UserLoadConfig.failedRequestsPercentMax())
          );
      case "COMPLEX_INJECTION" -> setUp(TestPopulation.complexInjection)
          .protocols(httpProtocol)
          .assertions(
              global().responseTime().percentile3().lt(UserLoadConfig.responseTimeP95Ms()),
              global().responseTime().percentile4().lt(UserLoadConfig.responseTimeP99Ms()),
              global().failedRequests().percent().lt(UserLoadConfig.failedRequestsPercentMax())
          );
      case "CLOSED_MODEL_INJECTION" -> setUp(TestPopulation.closedModelInjection)
          .protocols(httpProtocol)
          .assertions(
              global().responseTime().percentile3().lt(UserLoadConfig.responseTimeP95Ms()),
              global().responseTime().percentile4().lt(UserLoadConfig.responseTimeP99Ms()),
              global().failedRequests().percent().lt(UserLoadConfig.failedRequestsPercentMax())
          );
      default -> System.out.println(
          "Invalid TEST_TYPE. Use one of: INSTANT_USERS, RAMP_UP_USERS, COMPLEX_INJECTION, CLOSED_MODEL_INJECTION");
    }
  }
}
