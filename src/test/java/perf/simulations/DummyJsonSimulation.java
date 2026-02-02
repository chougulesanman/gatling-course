package perf.simulations;

import static io.gatling.javaapi.core.CoreDsl.*;
import io.gatling.javaapi.core.Simulation;
import perf.config.HttpConfig;
import perf.scenarios.UserScenario;
import perf.utils.TestLogger;

public class DummyJsonSimulation extends Simulation {

    {
        // Initialize logger at the start
        TestLogger.initialize();
        TestLogger.info("Gatling Performance Test Starting...");
        TestLogger.info("Duration: 10 minutes | Users: 2 per second");

        System.out.println("\n╔════════════════════════════════════════════════════════════╗");
        System.out.println("║          GATLING PERFORMANCE TEST - STARTING               ║");
        System.out.println("║  Duration: 10 minutes | Users: 2 per second               ║");
        System.out.println("╚════════════════════════════════════════════════════════════╝\n");

        setUp(
            UserScenario.buildUserScenario()
                .injectOpen(
                    // Ramp up: 1 user per second for 30 seconds
                    rampUsersPerSec(1).to(2).during(30),
                    // Steady state: 2 users per second for 9 minutes
                    constantUsersPerSec(2).during(540),
                    // Ramp down: 2 to 0 users per second for 30 seconds
                    rampUsersPerSec(2).to(0).during(30)
                )
        )
        .protocols(HttpConfig.httpProtocol)
        
        // Global Assertions
        .assertions(
            // Response time assertions
            global().responseTime().max().lt(5000),           // Max response time < 5 seconds
            global().responseTime().mean().lt(2000),          // Mean response time < 2 seconds
            global().responseTime().percentile3().lt(3000),   // 95th percentile < 3 seconds
            
            // Success rate assertions
            global().successfulRequests().percent().gt(95.0), // Success rate > 95%
            global().failedRequests().percent().lt(5.0),      // Failure rate < 5%
            
            // Request count assertions
            global().requestsPerSec().gt(1.0),                // At least 1 request per second
            
            // Specific request assertions
            forAll().responseTime().max().lt(10000),          // All requests < 10 seconds
            
            // Group-specific assertions
            details("Read Operations").responseTime().mean().lt(2000),
            details("Write Operations").responseTime().mean().lt(3000),
            details("Product Operations").responseTime().mean().lt(2500)
        );
        
        TestLogger.info("Assertions configured successfully");
        
        System.out.println("\n╔════════════════════════════════════════════════════════════╗");
        System.out.println("║          ASSERTIONS CONFIGURED                             ║");
        System.out.println("║  • Max Response Time: < 5s                                ║");
        System.out.println("║  • Mean Response Time: < 2s                               ║");
        System.out.println("║  • Success Rate: > 95%                                    ║");
        System.out.println("║  • Group Response Times: Read<2s, Write<3s, Product<2.5s ║");
        System.out.println("╚════════════════════════════════════════════════════════════╝\n");
    }
    
    @Override
    public void after() {
        TestLogger.info("Test execution completed");
        TestLogger.close();
    }
}
