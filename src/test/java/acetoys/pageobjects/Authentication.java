package acetoys.pageobjects;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

import acetoys.utils.DataFiles;
import acetoys.utils.Headers;
import io.gatling.javaapi.core.ChainBuilder;
import io.gatling.javaapi.core.FeederBuilder;

public class Authentication {

  private static final FeederBuilder<Object> usersFeeder =
      jsonFile(DataFiles.userCredentialsJson()).circular();

  public static final ChainBuilder loginUser =
      feed(usersFeeder)
          .exec(
              http("12_UserLogin")
                  .post("/login")
                  .headers(Headers.DEFAULT_HTML_HEADERS)
                  .formParam("_csrf", "#{csrfToken}")
                  .formParam("username", "#{username}")
                  .formParam("password", "#{password}")
                  .check(css("meta[name='_csrf']", "content").saveAs("csrfTokenLogIn"))
                  .check(css("#CategoryHeader").in("Cart Overview", "Categories"))
          )
          .exec(session -> session.set("customerLoggedIn", true));

  public static final ChainBuilder logoutUser =
      doIf(session -> Math.random() < 0.1)
          .then(
              exec(
                  http("14_UserLogout")
                      .post("/logout")
                      .headers(Headers.DEFAULT_HTML_HEADERS)
                      .formParam("_csrf", "#{csrfTokenLogIn}")
                      .check(css("a#NavbarHeaderLink[href='/login']").is("Login"))
              )
          );
}
