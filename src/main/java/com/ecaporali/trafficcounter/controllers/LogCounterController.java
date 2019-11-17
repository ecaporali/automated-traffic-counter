package com.ecaporali.trafficcounter.controllers;

import com.ecaporali.trafficcounter.models.CalculationResult;
import com.ecaporali.trafficcounter.models.LogCounter;
import com.ecaporali.trafficcounter.services.MetricService;

import java.util.List;

import static com.ecaporali.trafficcounter.config.AppConfig.CONTIGUOUS_LEAST_HALF_HOURS_COUNT;
import static com.ecaporali.trafficcounter.config.AppConfig.TOP_HALF_HOURS_COUNT;
import static com.ecaporali.trafficcounter.utils.AssertUtils.checkNonNull;

public class LogCounterController {

    private MetricService metricService;

    public LogCounterController(MetricService metricService) {
        checkNonNull(metricService, "TrafficCounterController", "metricService cannot be null");
        this.metricService = metricService;
    }

    public CalculationResult calculateResults(List<LogCounter> logCounters) {
        checkNonNull(logCounters, "TrafficCounterController.calculateResults", "logCounters cannot be null");
        return CalculationResult.Builder.newInstance()
                .withTotalCarsCount(metricService.calculateTotalCarsCount(logCounters))
                .withTotalCarsPerDay(metricService.calculateTotalCarsInEachDay(logCounters))
                .withTopNHalfHours(metricService.calculateTopNHalfHours(logCounters, TOP_HALF_HOURS_COUNT))
                .withContiguousNLeastHalfHours(metricService.calculateContiguousNLeastHalfHours(logCounters, CONTIGUOUS_LEAST_HALF_HOURS_COUNT))
                .build();
    }
}
