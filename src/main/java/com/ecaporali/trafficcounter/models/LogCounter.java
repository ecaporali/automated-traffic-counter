package com.ecaporali.trafficcounter.models;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Comparator;

import static com.ecaporali.trafficcounter.utils.AssertUtils.checkCondition;
import static com.ecaporali.trafficcounter.utils.AssertUtils.checkNonNull;
import static com.ecaporali.trafficcounter.utils.DateTimeUtils.extractDate;
import static com.ecaporali.trafficcounter.utils.DateTimeUtils.formatToISO;
import static java.time.LocalDateTime.parse;

public final class LogCounter {

    private final LocalDateTime timestamp;
    private final int carsCount;

    public LogCounter(LocalDateTime timestamp, int carsCount) {
        checkNonNull(timestamp, "timestamp cannot be null");
        this.timestamp = timestamp;
        this.carsCount = carsCount;
    }

    public LogCounter(String timestamp, int carsCount) {
        this(parse(timestamp), carsCount);
    }

    public boolean matchesWhenAddingMinutesOf(LogCounter otherLogCounter, int minutesToAdd) {
        checkNonNull(otherLogCounter, "otherLogCounter cannot be null");
        checkCondition(minutesToAdd < 1, "minutesToAdd must be greater than zero");
        return this.timestamp.plusMinutes(minutesToAdd).equals(otherLogCounter.timestamp);
    }

    public String toStringWithDate() {
        return extractDate(this.timestamp) + " " + carsCount;
    }

    public String toStringWithDateTime() {
        return formatToISO(timestamp) + " " + carsCount;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public LocalDate getLogDate() {
        return extractDate(this.timestamp);
    }

    public int getCarsCount() {
        return this.carsCount;
    }

    public static Comparator<LogCounter> TimestampComparatorAsc = Comparator.comparing(o -> o.timestamp);
    public static Comparator<LogCounter> NumberOfCarsComparatorDesc = (o1, o2) -> o2.carsCount - o1.carsCount;
}
