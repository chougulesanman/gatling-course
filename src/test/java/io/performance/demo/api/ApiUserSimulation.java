package io.performance.demo.api;

import static io.gatling.javaapi.core.CoreDsl.*;

import io.gatling.javaapi.core.Simulation;
import io.gatling.javaapi.http.HttpProtocolBuilder;
import io.performance.demo.api.logging.ApiTestLogger;
import io.performance.demo.api.scenarios.ApiScenario;
import io.performance.demo.common.EnvironmentConfig;
import io.performance.demo.common.HttpProtocolConfig;
import io.performance.demo.common.LoadProfileConfig;

public class ApiUserSimulation extends Simulation {

  private final HttpProtocolBuilder httpProtocol = HttpProtocolConfig.apiProtocol();

  {
    ApiTestLogger.initialize();
    ApiTestLogger.info("DummyJson API Performance Test Starting...");

    System.out.println("\n╔════════════════════════════════════════════════════════════╗");
    System.out.println("║        DUMMYJSON API PERFORMANCE TEST - STARTING           ║");
    System.out.println("║  Environment: " + EnvironmentConfig.envName());
    System.out.println("║  Base URL: " + EnvironmentConfig.baseUrl());
    System.out.println("║  Users: " + LoadProfileConfig.userCount());
    System.out.println("╚════════════════════════════════════════════════════════════╝\n");

    int userCount = LoadProfileConfig.userCount();
    int loadFromUserCount = LoadProfileConfig.fromUserCount();
    int rampSeconds = LoadProfileConfig.rampDurationSeconds();
    int testDuration = LoadProfileConfig.testDurationSeconds();

    setUp(
        ApiScenario.buildUserScenario()
            .injectClosed(
                rampConcurrentUsers(loadFromUserCount).to(userCount).during(rampSeconds),
                constantConcurrentUsers(userCount).during(testDuration),
                rampConcurrentUsers(userCount).to(0).during(rampSeconds)
            )
    )
        .protocols(httpProtocol)
        .assertions(
            // Shared SLA
            global().responseTime().percentile3().lt(LoadProfileConfig.responseTimeP95Ms()),
            global().responseTime().percentile4().lt(LoadProfileConfig.responseTimeP99Ms()),
            global().failedRequests().percent().lt(LoadProfileConfig.failedRequestsPercentMax()),

            // API-specific checks
            global().responseTime().max().lt(LoadProfileConfig.responseTimeMaxMs()),
            global().responseTime().mean().lt(LoadProfileConfig.responseTimeMeanMs()),
            global().successfulRequests().percent().gt(LoadProfileConfig.successfulRequestsPercentMin()),
            global().requestsPerSec().gt(LoadProfileConfig.requestsPerSecMin()),

            forAll().responseTime().max().lt(LoadProfileConfig.forAllResponseTimeMaxMs()),

            details("Read Operations").responseTime().mean().lt(LoadProfileConfig.readOpsResponseTimeMeanMs()),
            details("Write Operations").responseTime().mean().lt(LoadProfileConfig.writeOpsResponseTimeMeanMs()),
            details("Product Operations").responseTime().mean().lt(LoadProfileConfig.productOpsResponseTimeMeanMs())
        );

    ApiTestLogger.info("Assertions configured successfully");
  }

  @Override
  public void after() {
    ApiTestLogger.info("Test execution completed");
    ApiTestLogger.close();
  }
}
