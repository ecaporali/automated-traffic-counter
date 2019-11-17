package com.ecaporali.trafficcounter.models;

import org.junit.Test;

import java.time.format.DateTimeParseException;

import static com.ecaporali.trafficcounter.LogCounterTestFactory.createLogCounter;
import static com.ecaporali.trafficcounter.models.LogCounter.addMinutesAndMatchTimestamp;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class LogCounterTest {

    @Test
    public void shouldReturnFalseWhenAddMinutesAndMatchTimestampIsEqual() {
        LogCounter logCounter1 = createLogCounter(18, 6, 30, 1);
        LogCounter logCounter2 = createLogCounter(18, 7, 0, 2);
        assertTrue(addMinutesAndMatchTimestamp(logCounter1, 30, logCounter2));
    }

    @Test
    public void shouldReturnFalseWhenAddMinutesAndMatchTimestampIsDifferent() {
        LogCounter logCounter1 = createLogCounter(18, 6, 30, 1);
        LogCounter logCounter2 = createLogCounter(18, 7, 0, 2);
        assertFalse(addMinutesAndMatchTimestamp(logCounter1, 60, logCounter2));
    }

    @Test(expected = DateTimeParseException.class)
    public void shouldThrowExceptionWhenISODateTimeIsNotWellFormatted() {
        new LogCounter("BAD-FORMAT-ISO-DATE-TIME", 10);
    }
}
