package common.utils;

import io.gatling.javaapi.http.HttpProtocolBuilder;

import static io.gatling.javaapi.http.HttpDsl.http;

public final class HttpProtocolFactory {

  private HttpProtocolFactory() {}

  public static HttpProtocolBuilder defaultHtmlProtocol() {
    return http
      .baseUrl(EnvironmentConfig.baseUrl())
      .headers(Headers.DEFAULT_HTML_HEADERS)
      .header(Headers.ACCEPT_ENCODING, Headers.GZIP_DEFLATE)
      .userAgentHeader(Headers.ACETOYS_USER_AGENT);
  }

  public static HttpProtocolBuilder defaultJsonProtocol() {
    return http
      .baseUrl(EnvironmentConfig.baseUrl())
      .headers(Headers.DEFAULT_JSON_HEADERS)
      .header(Headers.ACCEPT_ENCODING, Headers.GZIP_DEFLATE)
      .userAgentHeader(Headers.API_USER_AGENT);
  }
}
