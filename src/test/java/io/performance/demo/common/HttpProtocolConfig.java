package io.performance.demo.common;

import io.gatling.javaapi.http.HttpProtocolBuilder;

import static io.gatling.javaapi.http.HttpDsl.http;

public final class HttpProtocolConfig {

  private HttpProtocolConfig() {
  }

  public static HttpProtocolBuilder webProtocol() {
    return http
        .baseUrl(EnvironmentConfig.baseUrl())
        .headers(HttpHeaders.DEFAULT_HTML_HEADERS)
        .userAgentHeader("Gatling-Web-Performance-Test");
  }

  public static HttpProtocolBuilder apiProtocol() {
    return http
        .baseUrl(EnvironmentConfig.baseUrl())
        .headers(HttpHeaders.DEFAULT_JSON_HEADERS)
        .userAgentHeader("Gatling-API-Performance-Test");
  }

}
