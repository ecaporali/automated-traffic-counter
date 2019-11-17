package com.ecaporali.trafficcounter.models;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.Objects;

import static com.ecaporali.trafficcounter.utils.AssertUtils.checkNonNull;
import static com.ecaporali.trafficcounter.utils.DateTimeUtils.extractDate;
import static com.ecaporali.trafficcounter.utils.DateTimeUtils.formatToISO;
import static java.time.LocalDateTime.parse;

public final class LogCounter {

    private final LocalDateTime timestamp;
    private final int carsCount;

    public LogCounter(LocalDateTime timestamp, int carsCount) {
        checkNonNull(timestamp, "LogCounter", "timestamp cannot be null");
        this.timestamp = timestamp;
        this.carsCount = carsCount;
    }

    public LogCounter(String timestamp, int carsCount) {
        this(parse(timestamp), carsCount);
    }

    public String toStringWithDate() {
        return extractDate(timestamp) + " " + carsCount;
    }

    public String toStringWithDateTime() {
        return formatToISO(timestamp) + " " + carsCount;
    }

    public LocalDate getLogDate() {
        return extractDate(timestamp);
    }

    public int getCarsCount() {
        return carsCount;
    }

    public static boolean addMinutesAndMatchTimestamp(LogCounter lcToAddMinutes, int minutesToAdd, LogCounter lcToCompare) {
        checkNonNull(lcToAddMinutes, "LogCounter.addMinutesAndMatchTimestamp", "lcToAddMinutes cannot be null");
        checkNonNull(lcToCompare, "LogCounter.addMinutesAndMatchTimestamp", "lcToCompare cannot be null");
        return lcToAddMinutes.timestamp.plusMinutes(minutesToAdd).equals(lcToCompare.timestamp);
    }

    public static Comparator<LogCounter> TimestampComparatorAsc = Comparator.comparing(o -> o.timestamp);
    public static Comparator<LogCounter> NumberOfCarsComparatorDesc = (o1, o2) -> o2.carsCount - o1.carsCount;

    @Override
    public String toString() {
        return toStringWithDateTime();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LogCounter that = (LogCounter) o;
        return carsCount == that.carsCount &&
                timestamp.equals(that.timestamp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(timestamp, carsCount);
    }
}
