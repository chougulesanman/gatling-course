package videogamedb;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;

public class VideoGameDbSimulation extends Simulation {
  // HTTP Config
  private HttpProtocolBuilder httpProtocol =
      http.baseUrl("https://videogamedb.uk/api")
          .acceptHeader("application/json")
          .contentTypeHeader("application/json");

  // Runtime Simulation Parameters
  private static final int USER_COUNT = Integer.parseInt(System.getProperty("USER_COUNT", "5"));
  private static final int RAMP_DURATION =
      Integer.parseInt(System.getProperty("RAMP_DURATION", "10"));
  private static final int MAX_DURATION =
      Integer.parseInt(System.getProperty("MAX_DURATION", "60"));

  // Before  starting the simulation, print the runtime parameters
  @Override
  public void before() {
    System.out.println(">>> USER_COUNT: " + USER_COUNT);
    System.out.println(">>> RAMP_DURATION: " + RAMP_DURATION);
    System.out.println(">>> MAX_DURATION: " + MAX_DURATION);
  }

  // Feeder for Test Data
  private static FeederBuilder.FileBased<Object> jsonFeeder =
      jsonFile("dataFiles/gameJsonFile.json").random();

  // HTTP Calls
  private static ChainBuilder getAllVideoGames =
      exec(http("Get all video games").get("/videogame").check(status().is(200)));

  private static ChainBuilder authenticate =
      exec(
          http("Authenticate")
              .post("/authenticate")
              .body(
                  StringBody(
                      "{\n" + "\"password\": \"admin\",\n" + "\"username\": \"admin\"\n" + "}"))
              .check(status().is(200))
              // .check(jsonPath("$.token").saveAs("jwtToken")));
              .check(jmesPath("token").saveAs("jwtToken")));

  private static ChainBuilder createVideoGame =
      feed(jsonFeeder)
          .exec(
              http("Create video game")
                  .post("/videogame")
                  .header("Authorization", "Bearer #{jwtToken}")
                  .body(ElFileBody("dataFiles/createVideoGame.json"))
                  .asJson()
                  .check(status().is(200)));
  // .exec(session -> {
  //     System.out.println(">>> Created game with ID: " + session.getInt("id"));
  //     System.out.println(">>> Name from feeder: " + session.getString("name"));
  //     return session;
  // });

  private static ChainBuilder getVideoGameById =
      exec(
          http("Get last postedvideo game by id")
              .get("/videogame/#{id}")
              .check(bodyString().saveAs("responseBody"))
              // .check(jsonPath("$.name").isEL("#{name}"))
              .check(jmesPath("name").isEL("#{name}"))
              .check(status().is(200)));
  // .exec(session -> {
  //     System.out.println(">>> Response body: " + session.getString("responseBody"));
  //     return session;
  // });

  private static ChainBuilder deleteVideoGame =
      exec(
          http("Delete video game")
              .delete("/videogame/#{id}")
              .header("Authorization", "Bearer #{jwtToken}")
              .check(bodyString().is("Video game deleted"))
              .check(status().is(200)));

  // Scenario Definition
  private ScenarioBuilder scenarioDefinition =
      scenario("Video Game DB API Test")
          .forever()
          .on(
              exec(getAllVideoGames)
                  .pause(1)
                  .exec(authenticate)
                  .pause(1)
                  .exec(createVideoGame)
                  .pause(1)
                  .exec(getVideoGameById)
                  .pause(1)
                  .exec(deleteVideoGame));

  // Load Simulation
  {
    setUp(
            scenarioDefinition
                .injectOpen(nothingFor(5), rampUsers(USER_COUNT).during(RAMP_DURATION))
                .protocols(httpProtocol))
        .maxDuration(MAX_DURATION);
  }
}
