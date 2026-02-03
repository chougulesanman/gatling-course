package io.performance.demo.web.pageobjects;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

import io.gatling.javaapi.core.ChainBuilder;
import io.gatling.javaapi.core.FeederBuilder;
import io.performance.demo.common.DataFileConfig;
import io.performance.demo.common.HttpHeaders;

public class AuthPage {

  private static final FeederBuilder<Object> usersFeeder =
      jsonFile(DataFileConfig.userCredentialsJson()).circular();

  public static final ChainBuilder loginUser =
      feed(usersFeeder)
          .exec(
              http("12_UserLogin")
                  .post("/login")
                  .headers(HttpHeaders.DEFAULT_HTML_HEADERS)
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
                      .headers(HttpHeaders.DEFAULT_HTML_HEADERS)
                      .formParam("_csrf", "#{csrfTokenLogIn}")
                      .check(css("a#NavbarHeaderLink[href='/login']").is("Login"))
              )
          );
}
