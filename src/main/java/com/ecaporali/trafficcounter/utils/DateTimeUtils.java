package com.ecaporali.trafficcounter.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static com.ecaporali.trafficcounter.utils.AssertUtils.checkNonNull;

public class DateTimeUtils {

    public static final int MINUTES_IN_HALF_HOUR = 30;

    public static String formatToISO(LocalDateTime localDateTime) {
        checkNonNull(localDateTime, "DateTimeUtils.formatToISO", "localDateTime must not be null");
        return localDateTime.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    }

    public static LocalDate extractDate(LocalDateTime localDateTime) {
        checkNonNull(localDateTime, "DateTimeUtils.extractDate", "localDateTime must not be null");
        return localDateTime.toLocalDate();
    }
}
