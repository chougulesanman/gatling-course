package common.utils;

import java.util.Map;

public final class Headers {

  private Headers() {
  }

  public static final Map<CharSequence, String> DEFAULT_HTML_HEADERS = Map.of(
      "Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8",
      "Accept-Language", "en-GB,en;q=0.9",
      "Accept-Encoding", "gzip, deflate");

  public static final Map<CharSequence, String> DEFAULT_JSON_HEADERS = Map.of(
      "Accept", "application/json",
      "Content-Type", "application/json",
      "Accept-Encoding", "gzip, deflate");

}
