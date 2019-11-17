package com.ecaporali.trafficcounter.services;

import com.ecaporali.trafficcounter.LogCounterTestFactory;
import com.ecaporali.trafficcounter.models.ContiguousLogCounter;
import com.ecaporali.trafficcounter.models.LogCounter;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class MetricServiceTest {

    private List<LogCounter> logCounters;
    private MetricService metricService;

    @Before
    public void setUp() throws Exception {
        logCounters = LogCounterTestFactory.createLogCounterList();
        metricService = new MetricService();
    }

    @Test
    public void shouldCalculateTotalCarsCount() {
        int totalCarsCount = metricService.calculateTotalCarsCount(logCounters);
        assertThat(totalCarsCount, equalTo(36));
    }

    @Test
    public void shouldCalculateTotalCarsInEachDay() {
        List<LogCounter> totalCarsInEachDay = metricService.calculateTotalCarsInEachDay(this.logCounters);
        List<LogCounter> expectedResult = Arrays.asList(
                new LogCounter(LocalDate.of(2019, 11, 18).atStartOfDay(), 3),
                new LogCounter(LocalDate.of(2019, 11, 19).atStartOfDay(), 3),
                new LogCounter(LocalDate.of(2019, 11, 20).atStartOfDay(), 15),
                new LogCounter(LocalDate.of(2019, 11, 21).atStartOfDay(), 7),
                new LogCounter(LocalDate.of(2019, 11, 22).atStartOfDay(), 8)
        );
        expectedResult.sort(LogCounter.TimestampComparatorAsc);
        assertThat(totalCarsInEachDay, equalTo(expectedResult));
    }

    @Test
    public void shouldCalculateTotalCarsInEachDayOnEmptyList() {
        List<LogCounter> totalCarsInEachDay = metricService.calculateTotalCarsInEachDay(emptyList());
        assertThat(totalCarsInEachDay, equalTo(emptyList()));
    }

    @Test
    public void shouldCalculateTopThreeHalfHours() {
        int topHalfHoursCount = 3;
        List<LogCounter> topThreeHalfHours = metricService.calculateTopNHalfHours(this.logCounters, topHalfHoursCount);
        List<LogCounter> expectedResult = Arrays.asList(
                new LogCounter(LocalDateTime.of(2019, 11, 22, 10, 30, 0), 8),
                new LogCounter(LocalDateTime.of(2019, 11, 21, 15, 0, 0), 7),
                new LogCounter(LocalDateTime.of(2019, 11, 20, 9, 0, 0), 6)
        );
        expectedResult.sort(LogCounter.NumberOfCarsComparatorDesc);
        assertThat(topThreeHalfHours, equalTo(expectedResult));
    }

    @Test
    public void shouldCalculateTopThreeHalfHoursOnEmptyList() {
        int topHalfHoursCount = 3;
        List<LogCounter> topThreeHalfHours = metricService.calculateTopNHalfHours(emptyList(), topHalfHoursCount);
        assertThat(topThreeHalfHours, equalTo(emptyList()));
    }

    @Test
    public void shouldCalculateContiguousThreeLeastHalfHours() {
        int contiguousLeastHalfHoursCount = 3;
        List<ContiguousLogCounter> contiguousThreeLogCounter = metricService.calculateContiguousNLeastHalfHours(logCounters, contiguousLeastHalfHoursCount);
        ContiguousLogCounter expectedResult = new ContiguousLogCounter(
                Arrays.asList(
                        new LogCounter(LocalDateTime.of(2019, 11, 20, 8, 0, 0), 4),
                        new LogCounter(LocalDateTime.of(2019, 11, 20, 8, 30, 0), 5),
                        new LogCounter(LocalDateTime.of(2019, 11, 20, 9, 0, 0), 6)
                )
        );
        assertThat(contiguousThreeLogCounter, equalTo(singletonList(expectedResult)));
    }

    @Test
    public void shouldDefaultToCalculateContiguousTwoLeastHalfHoursWhenCountIsLessThanOne() {
        int contiguousLeastHalfHoursCount = 0;
        List<ContiguousLogCounter> contiguousThreeLogCounter = metricService.calculateContiguousNLeastHalfHours(logCounters, contiguousLeastHalfHoursCount);
        ContiguousLogCounter expectedResult = new ContiguousLogCounter(
                Arrays.asList(
                        new LogCounter(LocalDateTime.of(2019, 11, 18, 6, 30, 0), 1),
                        new LogCounter(LocalDateTime.of(2019, 11, 18, 7, 0, 0), 2)
                )
        );
        assertThat(contiguousThreeLogCounter, equalTo(singletonList(expectedResult)));
    }

    @Test
    public void shouldCalculateContiguousThreeLeastHalfHoursOnEmptyList() {
        int contiguousLeastHalfHoursCount = 3;
        List<ContiguousLogCounter> contiguousThreeLogCounter = metricService.calculateContiguousNLeastHalfHours(emptyList(), contiguousLeastHalfHoursCount);
        assertThat(contiguousThreeLogCounter, equalTo(emptyList()));
    }
}
