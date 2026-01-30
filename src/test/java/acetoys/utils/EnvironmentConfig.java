package acetoys.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class EnvironmentConfig {

  private static final String ENV = System.getProperty("env", "local");
  private static final Properties PROPS = new Properties();

  static {
    String resourcePath = "/config/env-" + ENV + ".properties";
    try (InputStream in = EnvironmentConfig.class.getResourceAsStream(resourcePath)) {
      if (in == null) {
        throw new IllegalStateException("Config file not found: " + resourcePath);
      }
      PROPS.load(in);
    } catch (IOException e) {
      throw new IllegalStateException("Failed to load environment config: " + resourcePath, e);
    }
  }

  private EnvironmentConfig() {}

  public static String envName() {
    return ENV;
  }

  public static String baseUrl() {
    return PROPS.getProperty("baseUrl");
  }

  public static int requestTimeoutMs() {
    return Integer.parseInt(PROPS.getProperty("requestTimeoutMs", "5000"));
  }
}
