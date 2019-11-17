package com.ecaporali.trafficcounter.services;

import com.ecaporali.trafficcounter.models.LogCounter;
import com.ecaporali.trafficcounter.utils.AssertUtils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import static com.ecaporali.trafficcounter.utils.AssertUtils.checkNonNull;
import static java.lang.ClassLoader.getSystemResource;
import static java.lang.Integer.parseInt;
import static java.lang.String.format;

public class LogFileReaderService {

    public static List<LogCounter> readLogFile(String filename) throws URISyntaxException, IOException {
        checkNonNull(filename, "LogFileReaderService.readLogFile","filename cannot be null");
        URL systemResource = getSystemResource(filename);
        if (systemResource == null) throw new FileNotFoundException(format("File '%s' cannot be found", filename));
        else return Files
                .lines(Paths.get(systemResource.toURI()), StandardCharsets.UTF_8)
                .parallel()
                .map(line -> line.trim().split(" "))
                .map(contents -> new LogCounter(contents[0], parseInt(contents[1])))
                .sorted(LogCounter.TimestampComparatorAsc)
                .collect(Collectors.toList());
    }
}
