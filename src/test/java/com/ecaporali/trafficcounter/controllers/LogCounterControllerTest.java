package com.ecaporali.trafficcounter.controllers;

import com.ecaporali.trafficcounter.models.CalculationResult;
import com.ecaporali.trafficcounter.models.ContiguousLogCounter;
import com.ecaporali.trafficcounter.models.LogCounter;
import com.ecaporali.trafficcounter.services.MetricService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static com.ecaporali.trafficcounter.LogCounterTestFactory.createLogCounter;
import static java.util.Collections.singletonList;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class LogCounterControllerTest {

    private LogCounterController controller;

    @Mock
    private MetricService metricService;

    @Before
    public void setUp() throws Exception {
        controller = new LogCounterController(metricService);
    }

    @Test
    public void shouldCalculateResults() {
        int totalCarsCount = 15;

        List<LogCounter> totalCarsInEachDay = Arrays.asList(
                new LogCounter(LocalDate.of(2019, 11, 18).atStartOfDay(), 10),
                new LogCounter(LocalDate.of(2019, 11, 19).atStartOfDay(), 5)
        );

        List<LogCounter> topThreeHalfHours = Arrays.asList(
                new LogCounter(LocalDateTime.of(2019, 11, 18, 7, 30, 0), 5),
                new LogCounter(LocalDateTime.of(2019, 11, 18, 8, 30, 0), 4),
                new LogCounter(LocalDateTime.of(2019, 11, 18, 8, 0, 0), 3)
        );

        List<ContiguousLogCounter> contiguousThreeLeastHalfHours = singletonList(new ContiguousLogCounter(
                Arrays.asList(
                        new LogCounter(LocalDateTime.of(2019, 11, 18, 7, 30, 0), 5),
                        new LogCounter(LocalDateTime.of(2019, 11, 18, 8, 0, 0), 3),
                        new LogCounter(LocalDateTime.of(2019, 11, 18, 8, 30, 0), 4)
                )
        ));

        when(metricService.calculateTotalCarsCount(anyList())).thenReturn(totalCarsCount);
        when(metricService.calculateTotalCarsInEachDay(anyList())).thenReturn(totalCarsInEachDay);
        when(metricService.calculateTopNHalfHours(anyList(), anyInt())).thenReturn(topThreeHalfHours);
        when(metricService.calculateContiguousNLeastHalfHours(anyList(), anyInt())).thenReturn(contiguousThreeLeastHalfHours);

        List<LogCounter> logCounters = Arrays.asList(
                createLogCounter(18, 6, 30, 1),
                createLogCounter(18, 7, 0, 2),
                createLogCounter(18, 7, 30, 5),
                createLogCounter(18, 8, 0, 3),
                createLogCounter(19, 8, 30, 4)
        );

        CalculationResult calculationResult = controller.calculateResults(logCounters);

        CalculationResult expectedResult = CalculationResult.Builder.newInstance()
                .withTotalCarsCount(totalCarsCount)
                .withTotalCarsPerDay(totalCarsInEachDay)
                .withTopNHalfHours(topThreeHalfHours)
                .withContiguousNLeastHalfHours(contiguousThreeLeastHalfHours)
                .build();

        assertThat(calculationResult, equalTo(expectedResult));
    }
}
