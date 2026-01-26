package acetoys.pageobjects;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

import io.gatling.javaapi.core.*;

public class Authentication {

  private static final FeederBuilder<Object> usersFeeder =
      jsonFile("dataFiles/userCredentials.json").circular();

  public static ChainBuilder loginUser =
      feed(usersFeeder)
          .exec(
              http("12_UserLogin")
                  .post("/login")
                  .formParam("_csrf", "#{csrfToken}")
                  .formParam("username", "#{username}")
                  .formParam("password", "#{password}")
                  .check(css("meta[name='_csrf']", "content").saveAs("csrfTokenLogIn"))
                  .check(css("#CategoryHeader").in("Cart Overview", "Categories")))
          .exec(session -> session.set("customerLoggedIn", true));

  public static ChainBuilder logoutUser =
      doIf(session -> Math.random() < 0.1)
          .then(
              exec(
                  http("14_UserLogout")
                      .post("/logout")
                      .formParam("_csrf", "#{csrfTokenLogIn}")
                      .check(css("a#NavbarHeaderLink[href='/login']").is("Login"))));
}
