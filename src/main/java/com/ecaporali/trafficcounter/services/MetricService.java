package com.ecaporali.trafficcounter.services;

import com.ecaporali.trafficcounter.models.ContiguousLogCounter;
import com.ecaporali.trafficcounter.models.LogCounter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.ecaporali.trafficcounter.utils.AssertUtils.checkNonNull;
import static com.ecaporali.trafficcounter.utils.DateTimeUtils.MINUTES_IN_HALF_HOUR;
import static java.util.stream.Collectors.groupingBy;

public class MetricService {

    // The number of cars seen in total
    public int calculateTotalCarsCount(List<LogCounter> logCounters) {
        checkNonNull(logCounters, "logCounters cannot be null");
        return logCounters.parallelStream()
                .mapToInt(LogCounter::getCarsCount)
                .sum();
    }

    // A sequence of lines where each line contains a date (in yyyy-mm-dd format) and the
    // number of cars seen on that day (eg. 2016-11-23 289) for all days listed in the input file.
    public List<LogCounter> calculateTotalCarsInEachDay(List<LogCounter> logCounters) {
        checkNonNull(logCounters, "logCounters cannot be null");
        Map<LocalDate, List<LogCounter>> logCountersMap = logCounters.stream().collect(groupingBy(LogCounter::getLogDate));
        return logCountersMap.entrySet().parallelStream()
                .map(this::calculateTotalCarsInSingleDay)
                .sorted(LogCounter.TimestampComparatorAsc)
                .collect(Collectors.toList());
    }

    // The top 3 half hours with most cars, in the same format as the input file
    public List<LogCounter> calculateTopNHalfHours(List<LogCounter> logCounters, int topHalfHoursCount) {
        checkNonNull(logCounters, "logCounters cannot be null");
        return logCounters.stream()
                .sorted(LogCounter.NumberOfCarsComparatorDesc)
                .limit(topHalfHoursCount)
                .collect(Collectors.toList());
    }

    // The 1.5 hour period with least cars (i.e. 3 contiguous half hour records)
    public ContiguousLogCounter calculateContiguousNLeastHalfHours(List<LogCounter> logCounters, int contiguousLeastHalfHoursCount) {
        checkNonNull(logCounters, "logCounters cannot be null");

        int numberOfContiguousHalfHours = getNumberOfContiguousHalfHours(contiguousLeastHalfHoursCount);
        int contiguousHalfHourMinutes = MINUTES_IN_HALF_HOUR * numberOfContiguousHalfHours;
        List<ContiguousLogCounter> contiguousLogCounters = new ArrayList<>(logCounters.size());

        for (int i = numberOfContiguousHalfHours; i < logCounters.size(); i++) {
            LogCounter left = logCounters.get(i - numberOfContiguousHalfHours);
            LogCounter right = logCounters.get(i);
            if (left.matchesWhenAddingMinutesOf(right, contiguousHalfHourMinutes)) {
                int totalCount = left.getCarsCount() + right.getCarsCount();
                for (int j = i - (numberOfContiguousHalfHours - 1); j < i; j++) {
                    totalCount += logCounters.get(j).getCarsCount();
                }
                contiguousLogCounters.add(new ContiguousLogCounter(left, right, totalCount));
            }
        }

        return contiguousLogCounters.stream()
                .min(ContiguousLogCounter.NumberOfCarsComparatorAsc)
                .orElse(null);
    }

    private LogCounter calculateTotalCarsInSingleDay(Map.Entry<LocalDate, List<LogCounter>> entry) {
        return new LogCounter(entry.getKey().atStartOfDay(), entry.getValue().stream()
                .map(LogCounter::getCarsCount)
                .reduce(0, Integer::sum));
    }

    private int getNumberOfContiguousHalfHours(int contiguousLeastHalfHoursCount) {
        return (contiguousLeastHalfHoursCount > 1) ? contiguousLeastHalfHoursCount - 1 : 1;
    }
}
