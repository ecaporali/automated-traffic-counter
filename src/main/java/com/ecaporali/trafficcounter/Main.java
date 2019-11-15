package com.ecaporali.trafficcounter;

import com.ecaporali.trafficcounter.config.AppConfig;
import com.ecaporali.trafficcounter.controllers.TrafficCounterController;
import com.ecaporali.trafficcounter.models.CalculationResult;
import com.ecaporali.trafficcounter.models.LogCounter;
import com.ecaporali.trafficcounter.services.LogFileReaderService;
import com.ecaporali.trafficcounter.services.MetricService;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import static java.lang.String.format;

public class Main {

    public static void main(String[] args) {
        String filename = "logbook.txt";
        try {
            List<LogCounter> logCounters = LogFileReaderService.readLogFile(filename);
            TrafficCounterController trafficCounterController = new TrafficCounterController(new AppConfig(), new MetricService());
            CalculationResult result = trafficCounterController.calculateResults(logCounters);
            System.out.println(result);
        } catch (IOException | URISyntaxException e) {
            System.out.println(format("Exception reading file '%s'. Error message: %s.", filename, e.getMessage()));
        } catch (Exception e) {
            System.out.println(format("Ops! Something went terribly wrong. Error message: %s.", e));
        }
    }
}
