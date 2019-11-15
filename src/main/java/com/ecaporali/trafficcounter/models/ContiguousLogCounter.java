package com.ecaporali.trafficcounter.models;

import java.util.Comparator;

import static com.ecaporali.trafficcounter.utils.AssertUtils.checkNonNull;
import static com.ecaporali.trafficcounter.utils.DateTimeUtils.MINUTES_IN_HALF_HOUR;
import static com.ecaporali.trafficcounter.utils.DateTimeUtils.formatToISO;
import static com.ecaporali.trafficcounter.utils.DateTimeUtils.formatToTime;

public class ContiguousLogCounter {

    private final LogCounter startContiguousLogCounter;
    private final LogCounter endContiguousLogCounter;
    private final int totalCarsCount;

    public ContiguousLogCounter(LogCounter startContiguousLogCounter, LogCounter endContiguousLogCounter, int totalCarsCount) {
        checkNonNull(startContiguousLogCounter, "startContiguousLogCounter cannot be null");
        checkNonNull(endContiguousLogCounter, "endContiguousLogCounter cannot be null");
        this.startContiguousLogCounter = startContiguousLogCounter;
        this.endContiguousLogCounter = endContiguousLogCounter;
        this.totalCarsCount = totalCarsCount;
    }

    @Override
    public String toString() {
        return formatToISO(startContiguousLogCounter.getTimestamp())
                + " | "
                + formatToTime(endContiguousLogCounter.getTimestamp().plusMinutes(MINUTES_IN_HALF_HOUR))
                + " "
                + totalCarsCount;
    }

    public static Comparator<ContiguousLogCounter> NumberOfCarsComparatorAsc = Comparator.comparingInt(o -> o.totalCarsCount);
}
