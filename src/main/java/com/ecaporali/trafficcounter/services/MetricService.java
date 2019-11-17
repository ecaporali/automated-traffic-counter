package com.ecaporali.trafficcounter.services;

import com.ecaporali.trafficcounter.models.ContiguousLogCounter;
import com.ecaporali.trafficcounter.models.LogCounter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.ecaporali.trafficcounter.models.LogCounter.addMinutesAndMatchTimestamp;
import static com.ecaporali.trafficcounter.utils.AssertUtils.checkNonNull;
import static com.ecaporali.trafficcounter.utils.DateTimeUtils.MINUTES_IN_HALF_HOUR;
import static java.util.stream.Collectors.groupingBy;

public class MetricService {

    // The number of cars seen in total
    public int calculateTotalCarsCount(List<LogCounter> logCounters) {
        checkNonNull(logCounters, "MetricService.calculateTotalCarsCount", "logCounters cannot be null");
        return logCounters.parallelStream()
                .mapToInt(LogCounter::getCarsCount)
                .sum();
    }

    // A sequence of lines where each line contains a date (in yyyy-mm-dd format) and the
    // number of cars seen on that day (eg. 2016-11-23 289) for all days listed in the input file.
    public List<LogCounter> calculateTotalCarsInEachDay(List<LogCounter> logCounters) {
        checkNonNull(logCounters, "MetricService.calculateTotalCarsInEachDay", "logCounters cannot be null");
        Map<LocalDate, List<LogCounter>> logCountersMap = logCounters.stream().collect(groupingBy(LogCounter::getLogDate));
        return logCountersMap.entrySet().parallelStream()
                .map(this::calculateTotalCarsInSingleDay)
                .sorted(LogCounter.TimestampComparatorAsc)
                .collect(Collectors.toList());
    }

    // The top 3 half hours with most cars, in the same format as the input file
    public List<LogCounter> calculateTopNHalfHours(List<LogCounter> logCounters, int topHalfHoursCount) {
        checkNonNull(logCounters, "MetricService.calculateTopNHalfHours", "logCounters cannot be null");
        return logCounters.stream()
                .sorted(LogCounter.NumberOfCarsComparatorDesc)
                .limit(topHalfHoursCount)
                .collect(Collectors.toList());
    }

    // The 1.5 hour period with least cars (i.e. 3 contiguous half hour records)
    public List<ContiguousLogCounter> calculateContiguousNLeastHalfHours(List<LogCounter> logCounters, int contiguousLeastHalfHoursCount) {
        checkNonNull(logCounters, "MetricService.calculateContiguousNLeastHalfHours", "logCounters cannot be null");

        int totalContiguousHalfHours = getNumberOfContiguousHalfHours(contiguousLeastHalfHoursCount);
        int contiguousHalfHourMinutes = MINUTES_IN_HALF_HOUR * totalContiguousHalfHours;
        List<ContiguousLogCounter> contiguousLogCounters = new ArrayList<>(logCounters.size());

        for (int fromIndex = 0, toIndex = totalContiguousHalfHours; toIndex < logCounters.size(); fromIndex++, toIndex++) {
            LogCounter fromLogCounter = logCounters.get(fromIndex);
            LogCounter toLogCounter = logCounters.get(toIndex);
            if (addMinutesAndMatchTimestamp(fromLogCounter, contiguousHalfHourMinutes, toLogCounter)) {
                contiguousLogCounters.add(new ContiguousLogCounter(logCounters.subList(fromIndex, toIndex + 1)));
            }
        }

        return contiguousLogCounters.stream()
                .sorted(ContiguousLogCounter.NumberOfCarsComparatorAsc)
                .limit(1)
                .collect(Collectors.toList());
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
