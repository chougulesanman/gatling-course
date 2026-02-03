package io.performance.demo.web;

import static io.gatling.javaapi.core.CoreDsl.global;

import io.gatling.javaapi.core.Simulation;
import io.gatling.javaapi.http.HttpProtocolBuilder;
import io.performance.demo.common.EnvironmentConfig;
import io.performance.demo.common.HttpProtocolConfig;
import io.performance.demo.common.LoadProfileConfig;
import io.performance.demo.web.simulation.WebLoadProfiles;

public class WebUserSimulation extends Simulation {

  private static final String WEB_TEST_TYPE = LoadProfileConfig.testType();

  private final HttpProtocolBuilder httpProtocol = HttpProtocolConfig.webProtocol();

  {
    System.out.println("Running WebStoreSimulation");
    System.out.println("Environment: " + EnvironmentConfig.envName());
    System.out.println("Base URL: " + EnvironmentConfig.baseUrl());
    System.out.println("Test type: " + WEB_TEST_TYPE);
    System.out.println("Assertions: p95 < " + LoadProfileConfig.responseTimeP95Ms()
        + " ms, p99 < " + LoadProfileConfig.responseTimeP99Ms()
        + " ms, failed < " + LoadProfileConfig.failedRequestsPercentMax() + " %");

    switch (WEB_TEST_TYPE.toUpperCase()) {
      case "INSTANT_USERS" -> setUp(WebLoadProfiles.instantUsers)
          .protocols(httpProtocol)
          .assertions(
              global().responseTime().percentile3().lt(LoadProfileConfig.responseTimeP95Ms()),
              global().responseTime().percentile4().lt(LoadProfileConfig.responseTimeP99Ms()),
              global().failedRequests().percent().lt(LoadProfileConfig.failedRequestsPercentMax())
          );
      case "RAMP_UP_USERS" -> setUp(WebLoadProfiles.rampUpUsers)
          .protocols(httpProtocol)
          .assertions(
              global().responseTime().percentile3().lt(LoadProfileConfig.responseTimeP95Ms()),
              global().responseTime().percentile4().lt(LoadProfileConfig.responseTimeP99Ms()),
              global().failedRequests().percent().lt(LoadProfileConfig.failedRequestsPercentMax())
          );
      case "COMPLEX_INJECTION" -> setUp(WebLoadProfiles.complexInjection)
          .protocols(httpProtocol)
          .assertions(
              global().responseTime().percentile3().lt(LoadProfileConfig.responseTimeP95Ms()),
              global().responseTime().percentile4().lt(LoadProfileConfig.responseTimeP99Ms()),
              global().failedRequests().percent().lt(LoadProfileConfig.failedRequestsPercentMax())
          );
      case "CLOSED_MODEL_INJECTION" -> setUp(WebLoadProfiles.closedModelInjection)
          .protocols(httpProtocol)
          .assertions(
              global().responseTime().percentile3().lt(LoadProfileConfig.responseTimeP95Ms()),
              global().responseTime().percentile4().lt(LoadProfileConfig.responseTimeP99Ms()),
              global().failedRequests().percent().lt(LoadProfileConfig.failedRequestsPercentMax())
          );
      default -> System.out.println(
          "Invalid WEB_TEST_TYPE. Use one of: INSTANT_USERS, RAMP_UP_USERS, COMPLEX_INJECTION, CLOSED_MODEL_INJECTION");
    }
  }
}
