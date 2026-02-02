package common.utils;

import io.gatling.javaapi.http.HttpProtocolBuilder;

import static io.gatling.javaapi.http.HttpDsl.http;

public final class HttpProtocolFactory {

  private HttpProtocolFactory() {}

  public static HttpProtocolBuilder defaultHtmlProtocol() {
    return http
      .baseUrl(EnvironmentConfig.baseUrl())
      .headers(Headers.DEFAULT_HTML_HEADERS)
      .acceptEncodingHeader("gzip, deflate")
      .userAgentHeader("AceToys-Gatling-Demo");
  }

  public static HttpProtocolBuilder defaultJsonProtocol() {
    return http
      .baseUrl(EnvironmentConfig.baseUrl())
      .headers(Headers.DEFAULT_JSON_HEADERS)
      .acceptEncodingHeader("gzip, deflate")
      .userAgentHeader("Gatling-API-Performance-Test");
  }
}
