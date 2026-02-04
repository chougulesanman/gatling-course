package io.performance.demo.common;

import java.time.Duration;

public final class LoadProfileConfig {

  private LoadProfileConfig() {}

  // Load profile
  public static int fromUserCount() {
    return Integer.getInteger("LOAD_FROM_USER_COUNT", 10);
  }

  public static int userCount() {
    return Integer.getInteger("LOAD_USER_COUNT", 20);
  }
  
  public static int donothingFor() {
    return Integer.getInteger("DO_NOTHING_FOR_DURATION_SECONDS", 5);
  }

  public static int rampDurationSeconds() {
    return Integer.getInteger("LOAD_RAMP_DURATION_SECONDS", 40);
  }

  public static int testDurationSeconds() {
    return Integer.getInteger("LOAD_TEST_DURATION_SECONDS", 600);
  }

  // Think times
  public static Duration minPause() {
    long millis = Long.getLong("LOAD_MIN_PAUSE_MS", 1000L);
    return Duration.ofMillis(millis);
  }

  public static Duration maxPause() {
    long millis = Long.getLong("LOAD_MAX_PAUSE_MS", 5000L);
    return Duration.ofMillis(millis);
    }

  // Test type selector (for WebStoreSimulation)
  public static String testType() {
    return System.getProperty("WEB_TEST_TYPE", "CLOSED_MODEL_INJECTION");
  }

  // Assertion thresholds (global SLAs)
  public static int responseTimeP95Ms() {
    return Integer.getInteger("SLA_P95_MS", 1000);
  }

  public static int responseTimeP99Ms() {
    return Integer.getInteger("SLA_P99_MS", 2000);
  }

  public static double failedRequestsPercentMax() {
    return Double.parseDouble(System.getProperty("SLA_FAILED_PERCENT_MAX", "1.0"));
  }

  // API specific thresholds
  public static int responseTimeMaxMs() {
    return Integer.getInteger("SLA_RESPONSE_TIME_MAX_MS", 5000);
  }

  public static int responseTimeMeanMs() {
    return Integer.getInteger("SLA_RESPONSE_TIME_MEAN_MS", 2000);
  }

  public static double successfulRequestsPercentMin() {
    return Double.parseDouble(System.getProperty("SLA_SUCCESSFUL_REQUESTS_PERCENT_MIN", "95.0"));
  }

  public static double requestsPerSecMin() {
    return Double.parseDouble(System.getProperty("SLA_REQUESTS_PER_SEC_MIN", "1.0"));
  }

  public static int forAllResponseTimeMaxMs() {
    return Integer.getInteger("SLA_FOR_ALL_RESPONSE_TIME_MAX_MS", 10000);
  }

  public static int readOpsResponseTimeMeanMs() {
    return Integer.getInteger("SLA_READ_OPERATIONS_RESPONSE_TIME_MEAN_MS", 2000);
  }

  public static int writeOpsResponseTimeMeanMs() {
    return Integer.getInteger("SLA_WRITE_OPERATIONS_RESPONSE_TIME_MEAN_MS", 3000);
  }

  public static int productOpsResponseTimeMeanMs() {
    return Integer.getInteger("SLA_PRODUCT_OPERATIONS_RESPONSE_TIME_MEAN_MS", 2500);
  }

}
