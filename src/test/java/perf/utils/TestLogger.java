package perf.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TestLogger {
    
    private static String testRunFolder;
    private static String logFilePath;
    private static BufferedWriter logWriter;
    private static final String LOG_BASE_DIR = "test-logs";
    
    // Initialize logger with timestamp folder
    public static void initialize() {
        try {
            // Create timestamp folder: test-logs/2026-01-27_15-30-45/
            SimpleDateFormat folderFormat = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
            String timestamp = folderFormat.format(new Date());
            testRunFolder = LOG_BASE_DIR + File.separator + timestamp;
            
            // Create directory structure
            File logDir = new File(testRunFolder);
            if (!logDir.exists()) {
                logDir.mkdirs();
            }
            
            // Create main log file
            logFilePath = testRunFolder + File.separator + "test-execution.log";
            logWriter = new BufferedWriter(new FileWriter(logFilePath, true));
            
            // Write header
            logHeader();
            
            System.out.println("\n╔════════════════════════════════════════════════════════════╗");
            System.out.println("║  📁 Test Logs Location: " + testRunFolder);
            System.out.println("╚════════════════════════════════════════════════════════════╝\n");
            
        } catch (IOException e) {
            System.err.println("Failed to initialize logger: " + e.getMessage());
        }
    }
    
    // Write log header
    private static void logHeader() throws IOException {
        logWriter.write("================================================================================\n");
        logWriter.write("                    GATLING PERFORMANCE TEST EXECUTION LOG                      \n");
        logWriter.write("================================================================================\n");
        logWriter.write("Test Started: " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + "\n");
        logWriter.write("Log Folder: " + testRunFolder + "\n");
        logWriter.write("================================================================================\n\n");
        logWriter.flush();
    }
    
    // Log info message
    public static void info(String message) {
        writeLog("INFO", message);
        System.out.println("ℹ️  " + message);
    }
    
    // Log success message
    public static void success(String message) {
        writeLog("SUCCESS", message);
        System.out.println("✅ " + message);
    }
    
    // Log warning message
    public static void warning(String message) {
        writeLog("WARNING", message);
        System.out.println("⚠️  " + message);
    }
    
    // Log error message
    public static void error(String message) {
        writeLog("ERROR", message);
        System.err.println("❌ " + message);
    }
    
    // Log API request
    public static void logRequest(String method, String endpoint, String status) {
        String message = String.format("API Request: %s %s - Status: %s", method, endpoint, status);
        writeLog("REQUEST", message);
        System.out.println("🔹 " + message);
    }
    
    // Log API response
    public static void logResponse(String endpoint, String responseTime, String status) {
        String message = String.format("API Response: %s - Time: %sms - Status: %s", endpoint, responseTime, status);
        writeLog("RESPONSE", message);
        System.out.println("🔸 " + message);
    }
    
    // Log user scenario start
    public static void logScenarioStart(String scenarioName, String userName) {
        String separator = "================================================================================";
        try {
            logWriter.write("\n" + separator + "\n");
            logWriter.write("🚀 SCENARIO START: " + scenarioName + "\n");
            logWriter.write("👤 User: " + userName + "\n");
            logWriter.write("⏰ Time: " + new SimpleDateFormat("HH:mm:ss").format(new Date()) + "\n");
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
    
    // Log user scenario end
    public static void logScenarioEnd(String scenarioName, String userName) {
        String separator = "================================================================================";
        try {
            logWriter.write(separator + "\n");
            logWriter.write("✅ SCENARIO END: " + scenarioName + "\n");
            logWriter.write("👤 User: " + userName + "\n");
            logWriter.write("⏰ Time: " + new SimpleDateFormat("HH:mm:ss").format(new Date()) + "\n");
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
    
    // Log group start
    public static void logGroupStart(String groupName) {
        String message = "📂 Group Started: " + groupName;
        writeLog("GROUP_START", message);
        System.out.println("\n" + message);
    }
    
    // Log group end
    public static void logGroupEnd(String groupName) {
        String message = "📁 Group Completed: " + groupName;
        writeLog("GROUP_END", message);
        System.out.println(message + "\n");
    }
    
    // Write log with timestamp
    private static void writeLog(String level, String message) {
        try {
            if (logWriter != null) {
                String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date());
                String logEntry = String.format("[%s] [%s] %s\n", timestamp, level, message);
                logWriter.write(logEntry);
                logWriter.flush();
            }
        } catch (IOException e) {
            System.err.println("Failed to write log: " + e.getMessage());
        }
    }
    
    // Create separate log file for specific purpose
    public static void createSeparateLog(String fileName, String content) {
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
    
    // Close logger
    public static void close() {
        try {
            if (logWriter != null) {
                logWriter.write("\n================================================================================\n");
                logWriter.write("Test Completed: " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + "\n");
                logWriter.write("================================================================================\n");
                logWriter.flush();
                logWriter.close();
                
                System.out.println("\n╔════════════════════════════════════════════════════════════╗");
                System.out.println("║  📊 Test logs saved to: " + testRunFolder);
                System.out.println("╚════════════════════════════════════════════════════════════╝\n");
            }
        } catch (IOException e) {
            System.err.println("Failed to close logger: " + e.getMessage());
        }
    }
    
    // Get current test run folder path
    public static String getTestRunFolder() {
        return testRunFolder;
    }
    
    // Get log file path
    public static String getLogFilePath() {
        return logFilePath;
    }
}
