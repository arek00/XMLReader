package com.arek00.xmlReader.helpers;

/**
 * Log to server output
 */
public class MyLogger {

    public static void logMessage(String messageTag, String message) {
        System.out.println(messageTag + '\t' + message);
    }

    public static void logError(String messageTag, String message) {
        System.err.println(messageTag + '\t' + message);
    }

}
