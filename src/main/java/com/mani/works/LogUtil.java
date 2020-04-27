package com.mani.works;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

public class LogUtil {
    private static final String filepath = "/Users/rthiagarajan/Downloads/temp.log";
    private static List<String> logList;


    public static void parseLog(String procID, List<String> keywords) {
        String logRegex = "([\\d-]+)\\s+([\\d:\\.]+)\\s+([\\d]+)\\s+([\\d]+)\\s+([A-Z])\\s+([\\w]+)\\s+:(.+)";
        Pattern logPattern = Pattern.compile(logRegex);


        filterBy(procID);
        Stream<String> filteredLogs = logList.stream();
        filteredLogs.distinct().forEach(System.out::println);

    }

    private static void filterBy(String procID) {
        final String logRegex = String.format("([\\d-]+)\\s+([\\d:\\.]+)\\s+(%s)\\s+([\\d]+)\\s+([A-Z])\\s+([\\w]+)\\s+:(.+)", procID);
        final Pattern logPattern = Pattern.compile(logRegex);

        try {
            Stream<String> logEntries = Files.lines(Paths.get(filepath));
            logList = logEntries
                    .filter(logPattern.asPredicate())
//                    .filter(line -> line.contains(procID))
                    .collect(toList());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private String trimTimeStamp(String input) {
        return "";

    }

    private static Stream listUniqueExceptions(Stream input) {
        return null;
    }


    private static Stream printStackTrace(Stream input) {
        return null;
    }


    private static Stream keywordsFinder(Stream input, List<String> keywords) {
        return null;
    }

}
