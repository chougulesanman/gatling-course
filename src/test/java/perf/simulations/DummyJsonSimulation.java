package perf.simulations;

import static io.gatling.javaapi.core.CoreDsl.*;

import common.utils.EnvironmentConfig;
import common.utils.HttpProtocolFactory;
import common.utils.UserLoadConfig;
import io.gatling.javaapi.core.Simulation;
import io.gatling.javaapi.http.HttpProtocolBuilder;
import perf.scenarios.UserScenario;
import perf.utils.TestLogger;

public class DummyJsonSimulation extends Simulation {

  private final HttpProtocolBuilder httpProtocol = HttpProtocolFactory.defaultJsonProtocol();

  {
    TestLogger.initialize();
    TestLogger.info("DummyJson API Performance Test Starting...");

    System.out.println("\n╔════════════════════════════════════════════════════════════╗");
    System.out.println("║        DUMMYJSON API PERFORMANCE TEST - STARTING           ║");
    System.out.println("║  Environment: " + EnvironmentConfig.envName());
    System.out.println("║  Base URL: " + EnvironmentConfig.baseUrl());
    System.out.println("║  Users: " + UserLoadConfig.userCount());
    System.out.println("╚════════════════════════════════════════════════════════════╝\n");

    int userCount = UserLoadConfig.userCount();
    int rampSeconds = UserLoadConfig.rampDurationSeconds();
    int testDuration = UserLoadConfig.testDurationSeconds();

    setUp(
        UserScenario.buildUserScenario()
            .injectClosed(
                rampConcurrentUsers(1).to(userCount).during(rampSeconds),
                constantConcurrentUsers(userCount).during(testDuration),
                rampConcurrentUsers(userCount).to(0).during(rampSeconds)
            )
    )
        .protocols(httpProtocol)
        .assertions(
            // Shared SLA
            global().responseTime().percentile3().lt(UserLoadConfig.responseTimeP95Ms()),
            global().responseTime().percentile4().lt(UserLoadConfig.responseTimeP99Ms()),
            global().failedRequests().percent().lt(UserLoadConfig.failedRequestsPercentMax()),

            // API-specific checks
            global().responseTime().max().lt(5000),
            global().responseTime().mean().lt(2000),
            global().successfulRequests().percent().gt(95.0),
            global().requestsPerSec().gt(1.0),

            forAll().responseTime().max().lt(10000),

            details("Read Operations").responseTime().mean().lt(2000),
            details("Write Operations").responseTime().mean().lt(3000),
            details("Product Operations").responseTime().mean().lt(2500)
        );

    TestLogger.info("Assertions configured successfully");
  }

  @Override
  public void after() {
    TestLogger.info("Test execution completed");
    TestLogger.close();
  }
}
