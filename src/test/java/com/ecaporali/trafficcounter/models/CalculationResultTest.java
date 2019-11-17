package com.ecaporali.trafficcounter.models;

import org.junit.Test;

import java.util.Arrays;

import static java.util.Collections.emptyList;
import static org.junit.Assert.assertNotNull;

public class CalculationResultTest {

    @Test
    public void shouldBuildCalculationResult() {
        CalculationResult calculationResult = CalculationResult.Builder.newInstance()
                .withTotalCarsCount(15)
                .withTotalCarsPerDay(emptyList())
                .withTopNHalfHours(emptyList())
                .withContiguousNLeastHalfHours(emptyList())
                .build();

        assertNotNull(calculationResult);
    }

    @Test(expected = AssertionError.class)
    public void shouldThrowExceptionWhenContiguousNLeastHalfHoursIsNull() {
        CalculationResult.Builder.newInstance()
                .withTotalCarsCount(15)
                .withTotalCarsPerDay(emptyList())
                .withTopNHalfHours(emptyList())
                .build();
    }

    @Test(expected = AssertionError.class)
    public void shouldThrowExceptionWhenTotalCarsPerDayIsNull() {
        CalculationResult.Builder.newInstance()
                .withTotalCarsCount(15)
                .withTopNHalfHours(emptyList())
                .withContiguousNLeastHalfHours(emptyList())
                .build();
    }

    @Test(expected = AssertionError.class)
    public void shouldThrowExceptionWhenTopNHalfHoursIsNull() {
        CalculationResult.Builder.newInstance()
                .withTotalCarsCount(15)
                .withTopNHalfHours(emptyList())
                .withContiguousNLeastHalfHours(emptyList())
                .build();
    }

    @Test(expected = AssertionError.class)
    public void shouldThrowExceptionWhenContiguousNLeastHalfHoursHasMoreThanOneElement() {
        CalculationResult.Builder.newInstance()
                .withTotalCarsCount(15)
                .withTotalCarsPerDay(emptyList())
                .withTopNHalfHours(emptyList())
                .withContiguousNLeastHalfHours(
                        Arrays.asList(new ContiguousLogCounter(emptyList()), new ContiguousLogCounter(emptyList()))
                )
                .build();
    }
}
