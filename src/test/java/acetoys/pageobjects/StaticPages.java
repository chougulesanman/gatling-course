package acetoys.pageobjects;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

import acetoys.utils.Headers;
import io.gatling.javaapi.core.ChainBuilder;

public class StaticPages {

  public static final ChainBuilder homePage =
      exec(
          http("01_NavigateToHomePage")
              .get("/")
              .headers(Headers.DEFAULT_HTML_HEADERS)
              .check(substring("<title>Ace Toys Online Shop</title>"))
              .check(css("meta[name='_csrf']", "content").saveAs("csrfToken"))
              .check(status().is(200))
      );

  public static final ChainBuilder ourStoryPage =
      exec(
          http("02_NavigateToOurStoryPage")
              .get("/our-story")
              .headers(Headers.DEFAULT_HTML_HEADERS)
              .check(regex("was founded online in \\d{4}"))
      );

  public static final ChainBuilder getInTouchPage =
      exec(
          http("03_NavigateToGetInTouchPage")
              .get("/get-in-touch")
              .headers(Headers.DEFAULT_HTML_HEADERS)
              .check(substring("as we are not actually a real store!"))
      );
}
