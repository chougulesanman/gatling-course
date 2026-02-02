package common.utils;

import java.time.Duration;

public final class UserLoadConfig {

  private UserLoadConfig() {}

  // Load profile
  public static int userCount() {
    return Integer.getInteger("USER_COUNT", 10);
  }

  public static int rampDurationSeconds() {
    return Integer.getInteger("RAMP_DURATION_IN_SECONDS", 30);
  }

  public static int testDurationSeconds() {
    return Integer.getInteger("TEST_DURATION_IN_SECONDS", 600);
  }

  // Think times
  public static Duration minPause() {
    long millis = Long.getLong("MIN_PAUSE_MS", 1000L);
    return Duration.ofMillis(millis);
  }

  public static Duration maxPause() {
    long millis = Long.getLong("MAX_PAUSE_MS", 5000L);
    return Duration.ofMillis(millis);
    }

  // Test type selector (for AceToys)
  public static String testType() {
    return System.getProperty("TEST_TYPE", "CLOSED_MODEL_INJECTION");
  }

  // Assertion thresholds (global SLAs)
  public static int responseTimeP95Ms() {
    return Integer.getInteger("ASSERT_P95_MS", 1000);
  }

  public static int responseTimeP99Ms() {
    return Integer.getInteger("ASSERT_P99_MS", 2000);
  }

  public static double failedRequestsPercentMax() {
    return Double.parseDouble(System.getProperty("ASSERT_FAILED_PERCENT_MAX", "1.0"));
  }
}
