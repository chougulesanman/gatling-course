package io.performance.demo.api.logging;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ApiTestLogger {

    // Toggle: default false
    private static final boolean ENABLED =
        Boolean.parseBoolean(System.getProperty("API_LOGGING_ENABLED", "false"));

    private static String testRunFolder;
    private static String logFilePath;
    private static BufferedWriter logWriter;
    private static final String LOG_BASE_DIR = "test-logs";

    public static void initialize() {
        if (!ENABLED) {
            return;
        }
        try {
            SimpleDateFormat folderFormat = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
            String timestamp = folderFormat.format(new Date());
            testRunFolder = LOG_BASE_DIR + File.separator + timestamp;

            File logDir = new File(testRunFolder);
            if (!logDir.exists()) {
                logDir.mkdirs();
            }

            logFilePath = testRunFolder + File.separator + "test-execution.log";
            logWriter = new BufferedWriter(new FileWriter(logFilePath, true));

            logHeader();

            System.out.println("\n╔════════════════════════════════════════════════════════════╗");
            System.out.println("║  Test Logs Location: " + testRunFolder);
            System.out.println("╚════════════════════════════════════════════════════════════╝\n");

        } catch (IOException e) {
            System.err.println("Failed to initialize logger: " + e.getMessage());
        }
    }

    private static void logHeader() throws IOException {
        if (!ENABLED || logWriter == null) {
            return;
        }
        logWriter.write("================================================================================\n");
        logWriter.write("                 GATLING PERFORMANCE TEST EXECUTION LOG                         \n");
        logWriter.write("================================================================================\n");
        logWriter.write("Test Started: " +
            new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + "\n");
        logWriter.write("Log Folder: " + testRunFolder + "\n");
        logWriter.write("================================================================================\n\n");
        logWriter.flush();
    }

    public static void info(String message) {
        if (!ENABLED) {
            return;
        }
        writeLog("INFO", message);
        System.out.println("ℹ️  " + message);
    }

    public static void success(String message) {
        if (!ENABLED) {
            return;
        }
        writeLog("SUCCESS", message);
        System.out.println("✅ " + message);
    }

    public static void warning(String message) {
        if (!ENABLED) {
            return;
        }
        writeLog("WARNING", message);
        System.out.println("⚠️  " + message);
    }

    public static void error(String message) {
        if (!ENABLED) {
            return;
        }
        writeLog("ERROR", message);
        System.err.println("❌ " + message);
    }

    public static void logRequest(String method, String endpoint, String status) {
        if (!ENABLED) {
            return;
        }
        String message = String.format("API Request: %s %s - Status: %s", method, endpoint, status);
        writeLog("REQUEST", message);
        System.out.println("🔹 " + message);
    }

    public static void logResponse(String endpoint, String responseTime, String status) {
        if (!ENABLED) {
            return;
        }
        String message = String.format("API Response: %s - Time: %sms - Status: %s",
            endpoint, responseTime, status);
        writeLog("RESPONSE", message);
        System.out.println("🔸 " + message);
    }

    public static void logScenarioStart(String scenarioName, String userName) {
        if (!ENABLED) {
            return;
        }
        String separator = "================================================================================";
        try {
            logWriter.write("\n" + separator + "\n");
            logWriter.write("🚀 SCENARIO START: " + scenarioName + "\n");
            logWriter.write("👤 User: " + userName + "\n");
            logWriter.write("⏰ Time: " +
                new SimpleDateFormat("HH:mm:ss").format(new Date()) + "\n");
            logWriter.write(separator + "\n");
            logWriter.flush();

            System.out.println("\n" + separator);
            System.out.println("🚀 SCENARIO START: " + scenarioName);
            System.out.println("👤 User: " + userName);
            System.out.println(separator);
        } catch (IOException e) {
            System.err.println("Failed to log scenario start: " + e.getMessage());
        }
    }

    public static void logScenarioEnd(String scenarioName, String userName) {
        if (!ENABLED) {
            return;
        }
        String separator = "================================================================================";
        try {
            logWriter.write(separator + "\n");
            logWriter.write("✅ SCENARIO END: " + scenarioName + "\n");
            logWriter.write("👤 User: " + userName + "\n");
            logWriter.write("⏰ Time: " +
                new SimpleDateFormat("HH:mm:ss").format(new Date()) + "\n");
            logWriter.write(separator + "\n\n");
            logWriter.flush();

            System.out.println(separator);
            System.out.println("✅ SCENARIO END: " + scenarioName);
            System.out.println("👤 User: " + userName);
            System.out.println(separator + "\n");
        } catch (IOException e) {
            System.err.println("Failed to log scenario end: " + e.getMessage());
        }
    }

    public static void logGroupStart(String groupName) {
        if (!ENABLED) {
            return;
        }
        String message = "📂 Group Started: " + groupName;
        writeLog("GROUP_START", message);
        System.out.println("\n" + message);
    }

    public static void logGroupEnd(String groupName) {
        if (!ENABLED) {
            return;
        }
        String message = "📁 Group Completed: " + groupName;
        writeLog("GROUP_END", message);
        System.out.println(message + "\n");
    }

    private static void writeLog(String level, String message) {
        if (!ENABLED) {
            return;
        }
        try {
            if (logWriter != null) {
                String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS")
                    .format(new Date());
                String logEntry = String.format("[%s] [%s] %s\n", timestamp, level, message);
                logWriter.write(logEntry);
                logWriter.flush();
            }
        } catch (IOException e) {
            System.err.println("Failed to write log: " + e.getMessage());
        }
    }

    public static void createSeparateLog(String fileName, String content) {
        if (!ENABLED) {
            return;
        }
        try {
            String filePath = testRunFolder + File.separator + fileName;
            BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
            writer.write(content);
            writer.close();
            info("Created separate log file: " + fileName);
        } catch (IOException e) {
            error("Failed to create separate log: " + e.getMessage());
        }
    }

    public static void close() {
        if (!ENABLED) {
            return;
        }
        try {
            if (logWriter != null) {
                logWriter.write("\n================================================================================\n");
                logWriter.write("Test Completed: " +
                    new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + "\n");
                logWriter.write("================================================================================\n");
                logWriter.flush();
                logWriter.close();

                System.out.println("\n╔════════════════════════════════════════════════════════════╗");
                System.out.println("║  Test logs saved to: " + testRunFolder);
                System.out.println("╚════════════════════════════════════════════════════════════╝\n");
            }
        } catch (IOException e) {
            System.err.println("Failed to close logger: " + e.getMessage());
        }
    }

    public static String getTestRunFolder() {
        return testRunFolder;
    }

    public static String getLogFilePath() {
        return logFilePath;
    }
}
