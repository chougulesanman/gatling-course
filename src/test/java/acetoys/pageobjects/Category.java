package acetoys.pageobjects;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

import io.gatling.javaapi.core.*;
import java.util.Map;

public class Category {

  private static final FeederBuilder<String> categoryFeeder =
      csv("dataFiles/categoryDetails.csv").random();

  public static ChainBuilder categoryPage =
      feed(categoryFeeder)
          .exec(
              http("04_NavigateToCategoryPage")
                  .get("/category/#{categorySlug}")
                  .check(css("#CategoryName").isEL("#{categoryName}")));

  public static ChainBuilder cyclePagesOfProducts =
      exec(session -> {
            int currentPageNumber = session.getInt("productsListPageNumber");
            int totalPages = session.getInt("categoryPages");
            boolean morePages = currentPageNumber < totalPages;
            return session.setAll(
                Map.of(
                    "currentPageNumber", currentPageNumber,
                    "nextPageNumber", (currentPageNumber + 1),
                    "morePages", morePages));
          })
          .asLongAs("#{morePages}")
          .on(
              exec(http("Load page #{currentPageNumber} of Products - Category: #{categoryName}")
                      .get("/category/#{categorySlug}?page=#{currentPageNumber}")
                      .check(css(".page-item.active").isEL("#{nextPageNumber}")))
                  .exec(
                      session -> {
                        int currentPageNumber = session.getInt("currentPageNumber");
                        int totalPages = session.getInt("categoryPages");
                        currentPageNumber++;
                        boolean morePages = currentPageNumber < totalPages;
                        return session.setAll(
                            Map.of(
                                "currentPageNumber", currentPageNumber,
                                "nextPageNumber", (currentPageNumber + 1),
                                "morePages", morePages));
                      }));

  /*
   * public static ChainBuilder categoryPage1 =
   * exec(
   * http("05_NavigateToCategoryPage1")
   * .get("/category/all?page=1")
   * .check(css(".page-item.active").is("2")));
   *
   * public static ChainBuilder categoryPage2 =
   * exec(
   * http("06_NavigateToCategoryPage2")
   * .get("/category/all?page=2")
   * .check(css(".page-item.active").is("3")));
   *
   * public static ChainBuilder babyToysCategoryPage =
   * exec(
   * http("09_NavigateToBabyToysCategoryPage")
   * .get("/category/babies-toys")
   * .check(css("#CategoryName").is("Babies Toys")));
   *
   */
}
