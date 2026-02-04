package io.performance.demo.web.pageobjects;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

import io.gatling.javaapi.core.ChainBuilder;
import io.gatling.javaapi.core.FeederBuilder;
import io.performance.demo.common.DataFileConfig;
import io.performance.demo.common.HttpHeaders;

import java.util.Map;

public class CategoryPage {

  private static final FeederBuilder<String> categoryFeeder =
      csv(DataFileConfig.categoryDetailsCsv()).random();

  public static final ChainBuilder categoryPage =
      feed(categoryFeeder)
          .exec(
              http("04_NavigateToCategoryPage")
                  .get("/category/#{categorySlug}")
                  .headers(HttpHeaders.DEFAULT_HTML_HEADERS)
                  .check(css("#CategoryName").isEL("#{categoryName}"))
          );

  public static final ChainBuilder cyclePagesOfProducts =
      exec(session -> {
            int currentPageNumber = session.getInt("productsListPageNumber");
            int totalPages = session.getInt("categoryPages");
            boolean morePages = currentPageNumber < totalPages;
            return session.setAll(
                Map.of(
                    "currentPageNumber", currentPageNumber,
                    "nextPageNumber", currentPageNumber + 1,
                    "morePages", morePages
                )
            );
          })
          .asLongAs("#{morePages}")
          .on(
              exec(
                  http("Load page #{currentPageNumber} of Products - Category: #{categoryName}")
                      .get("/category/#{categorySlug}?page=#{currentPageNumber}")
                      .headers(HttpHeaders.DEFAULT_HTML_HEADERS)
                      .check(css(".page-item.active").isEL("#{nextPageNumber}"))
              )
                  .exec(session -> {
                    int currentPageNumber = session.getInt("currentPageNumber");
                    int totalPages = session.getInt("categoryPages");
                    currentPageNumber++;
                    boolean morePages = currentPageNumber < totalPages;
                    return session.setAll(
                        Map.of(
                            "currentPageNumber", currentPageNumber,
                            "nextPageNumber", currentPageNumber + 1,
                            "morePages", morePages
                        )
                    );
                  })
          );
}
