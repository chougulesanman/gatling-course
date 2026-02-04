package io.performance.demo.api.scenarios;

import static io.gatling.javaapi.core.CoreDsl.*;

import io.gatling.javaapi.core.FeederBuilder;
import io.gatling.javaapi.core.ScenarioBuilder;
import io.performance.demo.api.logging.ApiTestLogger;
import io.performance.demo.api.requests.ApiRequests;
import io.performance.demo.common.DataFileConfig;
import io.performance.demo.common.LoadProfileConfig;

public class ApiScenario {

  private static final FeederBuilder<String> userFeeder =
      csv(DataFileConfig.usersCsv()).circular();

  public static ScenarioBuilder buildUserScenario() {
    return scenario("User Management Scenario")
        .feed(userFeeder)
        .exec(session -> {
          String userName = session.getString("firstName") + " " + session.getString("lastName");
          ApiTestLogger.logScenarioStart("User Management Scenario", userName);
          return session;
        })

        // Group 1: Read Operations
        .exec(session -> {
          ApiTestLogger.logGroupStart("Read Operations");
          return session;
        })
        .group("Read Operations").on(
            exec(ApiRequests.getUsers())
                .pause(LoadProfileConfig.minPause(), LoadProfileConfig.maxPause())
                .exec(ApiRequests.getUserById())
                .pause(LoadProfileConfig.minPause())
        )
        .exec(session -> {
          ApiTestLogger.logGroupEnd("Read Operations");
          return session;
        })

        // Group 2: Write Operations
        .exec(session -> {
          ApiTestLogger.logGroupStart("Write Operations");
          return session;
        })
        .group("Write Operations").on(
            exec(ApiRequests.addUser())
                .pause(LoadProfileConfig.minPause(), LoadProfileConfig.maxPause())
                .exec(ApiRequests.updateUser())
                .pause(LoadProfileConfig.minPause())
        )
        .exec(session -> {
          ApiTestLogger.logGroupEnd("Write Operations");
          return session;
        })

        // Group 3: Product Operations
        .exec(session -> {
          ApiTestLogger.logGroupStart("Product Operations");
          return session;
        })
        .group("Product Operations").on(
            exec(ApiRequests.getProducts())
                .pause(LoadProfileConfig.minPause())
                .exec(ApiRequests.searchProducts())
                .pause(LoadProfileConfig.minPause())
        )
        .exec(session -> {
          ApiTestLogger.logGroupEnd("Product Operations");
          return session;
        })

        // Group 4: Delete Operation
        .exec(session -> {
          ApiTestLogger.logGroupStart("Delete Operations");
          return session;
        })
        .group("Delete Operations").on(
            exec(ApiRequests.deleteUser())
        )
        .exec(session -> {
          ApiTestLogger.logGroupEnd("Delete Operations");
          return session;
        })

        .exec(session -> {
          String userName = session.getString("firstName") + " " + session.getString("lastName");
          ApiTestLogger.logScenarioEnd("User Management Scenario", userName);
          return session;
        });
  }
}
