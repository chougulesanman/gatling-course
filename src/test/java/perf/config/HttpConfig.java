package perf.config;

import static io.gatling.javaapi.http.HttpDsl.*;
import io.gatling.javaapi.http.HttpProtocolBuilder;

public class HttpConfig {

    public static final HttpProtocolBuilder httpProtocol = http
        .baseUrl(System.getProperty("baseUrl", "https://dummyjson.com"))
        .acceptHeader("application/json")
        .contentTypeHeader("application/json")
        .userAgentHeader("Gatling Performance Test");
}
