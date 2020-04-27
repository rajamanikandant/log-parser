package com.mani.works;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

public class LogUtil {
    private static final String filepath = "full.log";


    public static void parseLog(String procID, List<String> keywords) {

        //Handling Invalid input
        if(procID == null || procID.trim().isEmpty())
            throw new IllegalArgumentException();

        //Get list by procID
        List<String> logsByProcID;

        System.out.println("Parsing the log file for " + procID);
        logsByProcID = filterBy(procID);

        printFatalExceptions(logsByProcID);
        printErrors(logsByProcID);

        //Handling Invalid input
        if(keywords == null || keywords.isEmpty())
            throw new IllegalArgumentException();
        
        printByKeywords(logsByProcID, keywords);
    }

    private static List<String> filterBy(String procID) {
        final String logRegex = String.format("[\\d-]+\\s+[\\d:\\.]+\\s+%s\\s+.+", procID);
        final Pattern logPattern = Pattern.compile(logRegex);
        final ClassLoader classLoader = ClassLoader.getSystemClassLoader();
        List<String> filteredLines = null;

        try {
            Stream<String> logEntries = Files.lines(Paths.get(classLoader.getResource(filepath).getFile()));
            filteredLines = logEntries
                    .filter(logPattern.asPredicate())
                    .collect(toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return filteredLines;

    }


    private static List<String> filterBy(List<String> log, String keyword) {
        List<String> filteredLines = log.stream()
                .filter(line -> line.toUpperCase().contains(keyword.toUpperCase()))
                .map(line -> trimLogLine(line))
                .collect(toList());
        return filteredLines;
    }


    private static void printFatalExceptions(List<String> log) {
        final String FATAL_EXCEPTION = "FATAL EXCEPTION";
        List<String> filteredLines = log.stream()
                .filter(line -> line.toUpperCase().contains(FATAL_EXCEPTION))
                .collect(toList());

        Map<String, Integer> uniqueLogs = new HashMap<>();
        StringBuilder stackTraces = new StringBuilder();

        int stCounter = 1;
        for (String line : filteredLines) {
            int index = log.indexOf(line);
            String exceptionStr = log.get(index + 2);
            String trimmedStr = trimLogLine(exceptionStr);

            if (!uniqueLogs.containsKey(trimmedStr)) {
                uniqueLogs.put(trimmedStr, 1);

                // Print stack trace
                stackTraces.append("#" + stCounter++ + ") " + trimmedStr + "\n");

                // Get stack trace pattern
                final String stackTracePattern = "([\\d-]+\\s+[\\d:\\.]+\\s+[\\d]+\\s+[\\d]+\\s+[A-Z]\\s+[\\w]+:)\\s*.+";
                Pattern logPattern = Pattern.compile(stackTracePattern);
                Matcher matcher = logPattern.matcher(exceptionStr);
                matcher.matches();
                final String stPrefix = matcher.group(1) + " at ";
                int logLineNum = index + 3;
                String nextLogLine = log.get(logLineNum);
                while(nextLogLine.replaceFirst(":\\s+at ", ": at ").contains(stPrefix)) {
                    stackTraces.append("\t");
                    stackTraces.append(trimLogLine(nextLogLine));
                    stackTraces.append("\n");
                    nextLogLine = log.get(logLineNum++);
                }
                stackTraces.append("\n");

            } else {
                uniqueLogs.put(trimmedStr, uniqueLogs.get(trimmedStr) + 1);
            }
        }

        System.out.println("FATAL EXCEPTION(S)");
        System.out.println("------------------");
        System.out.println("Exception Message| # of Occurrences");
        uniqueLogs.forEach((k,v) -> System.out.println(k + " | " + v));
        System.out.println();
        System.out.println();

        System.out.println("Stacktrace:");
        System.out.println("-----------");
        System.out.println(stackTraces.toString());
        System.out.println("-------------------------------------------------------");
        System.out.println();
        System.out.println();

    }


    private static void printErrors(List<String> log) {
        final String logRegex = "[\\d-]+\\s+[\\d:\\.]+\\s+[\\d]+\\s+[\\d]+\\s+E\\s+[\\w]+\\s*:(?!\\s+at ).+";
        Pattern logPattern = Pattern.compile(logRegex);

        List<String> filteredLines = log.stream()
                .filter(logPattern.asPredicate())
                .map(line -> trimLogLine(line))
                .filter(line -> !line.trim().isEmpty())
                .collect(toList());


        System.out.println("Error(s)");
        System.out.println("--------");
        System.out.println("Error Message| # of Occurrences");
        Map<String, Long> fatalExceptions = filteredLines.stream()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        fatalExceptions.forEach((k,v) -> System.out.println(k + " | " + v));


        System.out.println("-------------------------------------------------------");
        System.out.println();
        System.out.println();

    }


    private static void printByKeywords(List<String> log, List<String> keywords) {

        System.out.println("Matching String(s)");
        System.out.println("------------------");
        System.out.println("Matching String | # of Occurrences");

        for(String s:keywords) {
            printByKeyword(log, s);
        }

        System.out.println("-------------------------------------------------------");
        System.out.println();
        System.out.println();

    }
    private static void printByKeyword(List<String> log, String keyword) {
        final String logRegex = String.format("[\\d-]+\\s+[\\d:\\.]+\\s+[\\d]+\\s+[\\d]+\\s+[A-Z]+\\s+[\\w]+\\s*:\\s+.*%s.*", keyword);
        Pattern logPattern = Pattern.compile(logRegex);
        List<String> filteredLines = null;
        filteredLines = log.stream()
            .filter(logPattern.asPredicate())
            .map(line -> trimLogLine(line))
            .collect(toList());

        Map<String, Long> fatalExceptions = filteredLines.stream()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        fatalExceptions.forEach((k,v) -> System.out.println(k + " | " + v));

    }

    private static String trimLogLine(String logEntry) {

        final String logRegex = "[\\d-]+\\s+[\\d:\\.]+\\s+[\\d]+\\s+[\\d]+\\s+[A-Z]\\s+[\\w]+\\s*:\\s+(.+)";
        Pattern logPattern = Pattern.compile(logRegex);
        Matcher matcher = logPattern.matcher(logEntry);

        return matcher.matches() ? matcher.group(1) : "";
    }

}
