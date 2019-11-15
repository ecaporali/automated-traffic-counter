package com.ecaporali.trafficcounter.controllers;

import com.ecaporali.trafficcounter.config.AppConfig;
import com.ecaporali.trafficcounter.models.CalculationResult;
import com.ecaporali.trafficcounter.models.LogCounter;
import com.ecaporali.trafficcounter.services.MetricService;

import java.util.List;

import static com.ecaporali.trafficcounter.utils.AssertUtils.checkNonNull;

public class TrafficCounterController {

    private AppConfig appConfig;
    private MetricService metricService;

    public TrafficCounterController(AppConfig appConfig, MetricService metricService) {
        checkNonNull(appConfig, "appConfig cannot be null");
        checkNonNull(metricService, "metricService cannot be null");
        this.appConfig = appConfig;
        this.metricService = metricService;
    }

    public CalculationResult calculateResults(List<LogCounter> logCounters) {
        checkNonNull(logCounters, "logCounters cannot be null");
        return CalculationResult.Builder.newInstance()
                .withTotalCarsCount(metricService.calculateTotalCarsCount(logCounters))
                .withTotalCarsPerDay(metricService.calculateTotalCarsInEachDay(logCounters))
                .withTopNHalfHours(metricService.calculateTopNHalfHours(logCounters, appConfig.TOP_HALF_HOURS_COUNT))
                .withContiguousNLeastHalfHours(metricService.calculateContiguousNLeastHalfHours(logCounters, appConfig.CONTIGUOUS_LEAST_HALF_HOURS_COUNT))
                .build();
    }
}
