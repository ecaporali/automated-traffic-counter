package com.ecaporali.trafficcounter.utils;

import org.junit.Test;

import static com.ecaporali.trafficcounter.utils.AssertUtils.checkNonNull;
import static com.ecaporali.trafficcounter.utils.AssertUtils.checkCondition;

public class AssertUtilsTest {

    @Test
    public void shouldDoNothingWhenObjectIsNotNull() {
        checkNonNull("test", "context", "all good");
    }

    @Test(expected = AssertionError.class)
    public void shouldThrowExceptionWhenObjectIsNull() {
        checkNonNull(null, "context", "very bad");
    }

    @Test
    public void shouldDoNothingWhenConditionIsFalse() {
        checkCondition(false, "context", "all good");
    }

    @Test(expected = AssertionError.class)
    public void shouldThrowExceptionWhenConditionIsTrue() {
        checkCondition(true, "context", "very bad");
    }
}
