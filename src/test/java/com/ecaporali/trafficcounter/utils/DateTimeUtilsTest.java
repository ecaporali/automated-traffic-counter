package com.ecaporali.trafficcounter.utils;

import com.ecaporali.trafficcounter.LogCounterTestFactory;
import org.junit.Test;

import java.time.LocalDate;

import static com.ecaporali.trafficcounter.utils.DateTimeUtils.formatToISO;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.*;

public class DateTimeUtilsTest {

    @Test
    public void shouldFormatLocalDateTimeToISO() {
        String isoLocalDateTimeString = formatToISO(LogCounterTestFactory.DEFAULT_TIMESTAMP);
        assertThat(isoLocalDateTimeString, equalTo("2019-11-18T00:00:00"));
    }

    @Test
    public void shouldExtractDateFromLocalDateTime() {
        LocalDate localDate = DateTimeUtils.extractDate(LogCounterTestFactory.DEFAULT_TIMESTAMP);
        assertThat(localDate, equalTo(LocalDate.of(2019, 11, 18)));
    }
}
