package perf.scenarios;

import static io.gatling.javaapi.core.CoreDsl.*;
import io.gatling.javaapi.core.ScenarioBuilder;
import io.gatling.javaapi.core.FeederBuilder;
import perf.requests.UserRequests;
import perf.utils.TestLogger;

public class UserScenario {

    // CSV feeder for user data
    private static FeederBuilder<String> userFeeder = csv("dataFiles/users.csv").circular();

    public static ScenarioBuilder buildUserScenario() {
        return scenario("User Management Scenario")
            .feed(userFeeder)
            .exec(session -> {
                String userName = session.getString("firstName") + " " + session.getString("lastName");
                TestLogger.logScenarioStart("User Management Scenario", userName);
                return session;
            })
            
            // Group 1: Read Operations
            .exec(session -> {
                TestLogger.logGroupStart("Read Operations");
                return session;
            })
            .group("Read Operations").on(
                exec(UserRequests.getUsers())
                    .pause(1, 2)
                    .exec(UserRequests.getUserById())
                    .pause(1)
            )
            .exec(session -> {
                TestLogger.logGroupEnd("Read Operations");
                return session;
            })
            
            // Group 2: Write Operations
            .exec(session -> {
                TestLogger.logGroupStart("Write Operations");
                return session;
            })
            .group("Write Operations").on(
                exec(UserRequests.addUser())
                    .pause(1, 2)
                    .exec(UserRequests.updateUser())
                    .pause(1)
            )
            .exec(session -> {
                TestLogger.logGroupEnd("Write Operations");
                return session;
            })
            
            // Group 3: Product Operations
            .exec(session -> {
                TestLogger.logGroupStart("Product Operations");
                return session;
            })
            .group("Product Operations").on(
                exec(UserRequests.getProducts())
                    .pause(1)
                    .exec(UserRequests.searchProducts())
                    .pause(1)
            )
            .exec(session -> {
                TestLogger.logGroupEnd("Product Operations");
                return session;
            })
            
            // Group 4: Delete Operation
            .exec(session -> {
                TestLogger.logGroupStart("Delete Operations");
                return session;
            })
            .group("Delete Operations").on(
                exec(UserRequests.deleteUser())
            )
            .exec(session -> {
                TestLogger.logGroupEnd("Delete Operations");
                return session;
            })
            
            .exec(session -> {
                String userName = session.getString("firstName") + " " + session.getString("lastName");
                TestLogger.logScenarioEnd("User Management Scenario", userName);
                return session;
            });
    }
}
