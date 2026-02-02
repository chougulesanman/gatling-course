package common.utils;

public final class DataFiles {

  private DataFiles() {}

  public static String userCredentialsJson() {
    return System.getProperty("USER_CREDENTIALS_FILE", "dataFiles/userCredentials.json");
  }

  public static String categoryDetailsCsv() {
    return System.getProperty("CATEGORY_DETAILS_FILE", "dataFiles/categoryDetails.csv");
  }

  public static String productDetailsJson() {
    return System.getProperty("PRODUCT_DETAILS_FILE", "dataFiles/productDetails.json");
  }

  public static String usersCsv() {
    return System.getProperty("USERS_CSV_FILE", "dataFiles/users.csv");
  }
}
