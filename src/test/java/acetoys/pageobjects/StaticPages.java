package acetoys.pageobjects;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

import io.gatling.javaapi.core.*;

public class StaticPages {
  public static ChainBuilder homePage =
      exec(
          http("01_NavigateToHomePage")
              .get("/")
              .check(substring("<title>Ace Toys Online Shop</title>"))
              .check(css("meta[name='_csrf']", "content").saveAs("csrfToken"))
              .check(status().is(200)));

  public static ChainBuilder ourStoryPage =
      exec(
          http("02_NavigateToOurStoryPage")
              .get("/our-story")
              .check(regex("was founded online in \\d{4}")));

  public static ChainBuilder getInTouchPage =
      exec(
          http("03_NavigateToGetInTouchPage")
              .get("/get-in-touch")
              .check(substring("as we are not actually a real store!")));
}
