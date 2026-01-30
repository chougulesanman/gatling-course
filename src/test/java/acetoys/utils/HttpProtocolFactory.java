package acetoys.utils;

import io.gatling.javaapi.http.HttpProtocolBuilder;

import static io.gatling.javaapi.http.HttpDsl.http;

public final class HttpProtocolFactory {

  private HttpProtocolFactory() {}

  public static HttpProtocolBuilder defaultHttpProtocol() {
    return http
      .baseUrl(EnvironmentConfig.baseUrl())
      .headers(Headers.DEFAULT_HTML_HEADERS)
      .acceptEncodingHeader("gzip, deflate")
      .userAgentHeader("AceToys-Gatling-Demo");
  }
}
