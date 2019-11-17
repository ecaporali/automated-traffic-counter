package com.ecaporali.trafficcounter.models;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static com.ecaporali.trafficcounter.LogCounterTestFactory.createLogCounter;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.*;

public class ContiguousLogCounterTest {

    @Test
    public void shouldInstantiateAndCalculateTotalCarsCount() {
        List<LogCounter> logCounters = Arrays.asList(
                createLogCounter(18, 6, 30, 1),
                createLogCounter(18, 7, 0, 2),
                createLogCounter(18, 7, 30, 5),
                createLogCounter(18, 8, 0, 3),
                createLogCounter(19, 8, 30, 4)
        );

        ContiguousLogCounter contiguousLogCounter = new ContiguousLogCounter(logCounters);
        assertThat(contiguousLogCounter.getTotalCarsCount(), equalTo(15));
    }
}
