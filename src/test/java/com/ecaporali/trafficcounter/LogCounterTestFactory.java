package com.ecaporali.trafficcounter;

import com.ecaporali.trafficcounter.models.LogCounter;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

public class LogCounterTestFactory {

    public static LocalDateTime DEFAULT_TIMESTAMP = LocalDateTime.of(2019, 11, 18, 0, 0, 0);
    public static int DEFAULT_CARS_COUNT = 10;

    public static List<LogCounter> createLogCounterList() {
        return Arrays.asList(
                createLogCounter(18, 6, 30, 1),
                createLogCounter(18, 7, 0, 2),
                createLogCounter(19, 7, 30, 3),
                createLogCounter(20, 8, 0, 4),
                createLogCounter(20, 8, 30, 5),
                createLogCounter(20, 9, 0, 6),
                createLogCounter(21, 15, 0, 7),
                createLogCounter(22, 10, 30, 8)
        );
    }

    public static LogCounter createDefaultLogCounter() {
        return new LogCounter(DEFAULT_TIMESTAMP, DEFAULT_CARS_COUNT);
    }

    public static LogCounter createLogCounter(int day, int hour, int minute, int carsCount) {
        return new LogCounter(buildTimestamp(day, hour, minute), carsCount);
    }

    private static LocalDateTime buildTimestamp(int day, int hour, int minute) {
        return LocalDateTime.of(2019, 11, day, hour, minute, 0);
    }
}
