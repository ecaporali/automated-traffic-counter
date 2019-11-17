package com.ecaporali.trafficcounter.utils;

public class AssertUtils {

    public static void checkNonNull(Object object, String context, String message) {
        if (object == null) {
            throw new AssertionError(buildErrorMessage(context, message));
        }
    }

    public static void checkCondition(boolean condition, String context, String message) {
        if (condition) {
            throw new AssertionError(buildErrorMessage(context, message));
        }
    }

    private static String buildErrorMessage(String context, String message) {
        return String.format("Context: %s - Message: %s", context, message);
    }
}
