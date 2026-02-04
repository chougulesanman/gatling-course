package io.performance.demo.web.pageobjects;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

import io.gatling.javaapi.core.ChainBuilder;
import io.performance.demo.common.HttpHeaders;

public class StaticPages {

  public static final ChainBuilder homePage =
      exec(
          http("01_NavigateToHomePage")
              .get("/")
              .headers(HttpHeaders.DEFAULT_HTML_HEADERS)
              .check(substring("<title>Ace Toys Online Shop</title>"))
              .check(css("meta[name='_csrf']", "content").saveAs("csrfToken"))
              .check(status().is(200))
      );

  public static final ChainBuilder ourStoryPage =
      exec(
          http("02_NavigateToOurStoryPage")
              .get("/our-story")
              .headers(HttpHeaders.DEFAULT_HTML_HEADERS)
              .check(regex("was founded online in \\d{4}"))
      );

  public static final ChainBuilder getInTouchPage =
      exec(
          http("03_NavigateToGetInTouchPage")
              .get("/get-in-touch")
              .headers(HttpHeaders.DEFAULT_HTML_HEADERS)
              .check(substring("as we are not actually a real store!"))
      );
}
