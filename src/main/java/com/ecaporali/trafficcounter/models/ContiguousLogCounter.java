package com.ecaporali.trafficcounter.models;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;

import static com.ecaporali.trafficcounter.utils.AssertUtils.checkNonNull;
import static java.util.stream.Collectors.joining;

public final class ContiguousLogCounter {

    private final List<LogCounter> logCounters;
    private final int totalCarsCount;

    public ContiguousLogCounter(List<LogCounter> logCounters) {
        checkNonNull(logCounters, "ContiguousLogCounter", "logCounters cannot be null");
        this.logCounters = logCounters;
        this.totalCarsCount = calculateTotalCarsCount(logCounters);
    }

    private int calculateTotalCarsCount(List<LogCounter> logCounters) {
        return logCounters.stream().mapToInt(LogCounter::getCarsCount).sum();
    }

    public int getTotalCarsCount() {
        return totalCarsCount;
    }

    public static Comparator<ContiguousLogCounter> NumberOfCarsComparatorAsc = Comparator.comparingInt(o -> o.totalCarsCount);

    @Override
    public String toString() {
        return logCounters.stream().map(LogCounter::toStringWithDateTime).collect(joining("\n"));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ContiguousLogCounter that = (ContiguousLogCounter) o;
        return totalCarsCount == that.totalCarsCount &&
                logCounters.equals(that.logCounters);
    }

    @Override
    public int hashCode() {
        return Objects.hash(logCounters, totalCarsCount);
    }
}
