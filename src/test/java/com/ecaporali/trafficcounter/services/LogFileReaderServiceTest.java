package com.ecaporali.trafficcounter.services;

import com.ecaporali.trafficcounter.LogCounterTestFactory;
import com.ecaporali.trafficcounter.models.LogCounter;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import static java.util.Collections.emptyList;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class LogFileReaderServiceTest {

    private List<LogCounter> logCounterList = LogCounterTestFactory.createLogCounterList();

    @Test
    public void shouldReadLogFileWhenFileIsFoundAndHasLines() throws IOException, URISyntaxException {
        List<LogCounter> testLogCounters = LogFileReaderService.readLogFile("logbook-test-full.txt");
        logCounterList.sort(LogCounter.TimestampComparatorAsc);
        assertThat(testLogCounters, equalTo(logCounterList));
    }

    @Test
    public void shouldReadLogFileWhenFileIsFoundAndHasNoLines() throws IOException, URISyntaxException {
        List<LogCounter> testLogCounters = LogFileReaderService.readLogFile("logbook-test-empty.txt");
        assertThat(testLogCounters, equalTo(emptyList()));
    }

    @Test(expected = FileNotFoundException.class)
    public void shouldThrowExceptionWhenFileNotFound() throws IOException, URISyntaxException {
        LogFileReaderService.readLogFile("bad-file-name.txt");
    }
}
