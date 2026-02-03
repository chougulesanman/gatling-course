package common.utils;

import java.util.Map;

public final class Headers {

  private Headers() {}

  public static final String ACCEPT_ENCODING = "Accept-Encoding";
  public static final String USER_AGENT = "User-Agent";
  public static final String GZIP_DEFLATE = "gzip, deflate";

  public static final String ACETOYS_USER_AGENT = "Gatling-Web-Performance-Test";
  public static final String API_USER_AGENT = "Gatling-API-Performance-Test";

  public static final Map<CharSequence, String> DEFAULT_HTML_HEADERS = Map.of(
    "Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8",
    "Accept-Language", "en-GB,en;q=0.9"
  );

  public static final Map<CharSequence, String> DEFAULT_JSON_HEADERS = Map.of(
    "Accept", "application/json",
    "Content-Type", "application/json"
  );
}
