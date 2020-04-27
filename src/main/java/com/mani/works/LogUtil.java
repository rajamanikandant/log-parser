package com.mani.works;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
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
    private static final String filepath = "sample4667.log";


    public static void parseLog(String procID, List<String> keywords) {

        //Get list by procID
        List<String> logsByProcID;
        logsByProcID = filterBy(procID);

//        Stream<String> filteredLogs = logsByProcID.stream();
//        filteredLogs.forEach(System.out::println);

        //Filter by Fatal Exception

        List<String> logs2;
        logs2 = filterBy(logsByProcID, "Fatal Exception");
        //Filter by Fatal Exception - Unique exception & count
        System.out.println("------------------");
//        filterFatal(logsByProcID);
        filterFatal(logsByProcID).forEach((k,v) -> System.out.println(k + " | " + v));
        System.out.println("------------------");

        Map<String, Long> fatalExceptions = logs2.stream()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        System.out.println("#####");
        System.out.println("FATAL EXCEPTION(S)");
        System.out.println("------------------");
        fatalExceptions.forEach((k,v) -> System.out.println(k + " | " + v));
        System.out.println("#####");
            //Filter by Fatal Exception - Unique exception & stacktrace
        //Filter by Errors
            //Filter by Errors - Unique Errors & count
        //Filter by Given List of Keywords
            //Filter by Given List of Keywords - lines & count






//        logList = filterByNew(procID, keywords.get(0));

        /*
        Map<String, Long> result = logsByProcID.stream()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        System.out.println("#----------#");
        String exceptionStr = logsByProcID.stream().findFirst().get();
        System.out.println(exceptionStr);
        System.out.println(logsByProcID.indexOf(exceptionStr));
        System.out.println("#----------#");
        result.forEach((k,v) -> System.out.println(k + " | " + v));
        */

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
//                    .map(line -> testTrim(line))
                    .collect(toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return filteredLines;

    }


    private static Map<String, Integer> filterFatal(List<String> log) {
        final String keyword = "FATAL EXCEPTION";
        List<String> filteredLines = log.stream()
                .filter(line -> line.toUpperCase().contains(keyword))
                .collect(toList());

        List<String> uniqueLines = new ArrayList<>();
        Map<String, Integer> uniqueLogs = new HashMap<>();

        System.out.println(filteredLines.size());

        filteredLines.forEach(line -> System.out.println(line));
        for (String line : filteredLines) {
            int index = log.indexOf(line);
            System.out.println(index);
            System.out.println(log.get(index + 1));
            System.out.println(log.get(index + 2));

            String exceptionStr = log.get(index + 2);
            System.out.println("exceptionStr ==>" + exceptionStr);
            String trimmedStr = trimLogLine(exceptionStr);
            System.out.println("trimmedStr ==>" + trimmedStr);

            if (!uniqueLogs.containsKey(trimmedStr)) {
                System.out.println("count ==>" + 1);
                uniqueLogs.put(trimmedStr, 1);
            } else {
                System.out.println("count ==>" + (uniqueLogs.get(trimmedStr) + 1));
                uniqueLogs.put(trimmedStr, uniqueLogs.get(trimmedStr) + 1);
            }
        }
        return uniqueLogs;
    }

    private static List<String> filterBy(List<String> log, String keyword) {
        List<String> filteredLines = log.stream()
                .filter(line -> line.toUpperCase().contains(keyword.toUpperCase()))
                .map(line -> trimLogLine(line))
                .collect(toList());
        return filteredLines;
    }


    private static List<String> filterByNew(String procID, String keyword) {
        final String logRegex = String.format("[\\d-]+\\s+[\\d:\\.]+\\s+%s\\s+.+", procID);
        final Pattern logPattern = Pattern.compile(logRegex);
        final ClassLoader classLoader = ClassLoader.getSystemClassLoader();
        List<String> filteredLines = null;

        try {
            Stream<String> logEntries = Files.lines(Paths.get(classLoader.getResource(filepath).getFile()));
            filteredLines = logEntries
                    .filter(logPattern.asPredicate())
                    .filter(line -> line.contains(keyword))
//                    .map(line -> testTrim(line))
                    .collect(toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return filteredLines;

    }



    private static void printStackTrace(List<String> logEntries, String procID) {
        String keyword = "NoSuchMethodException";

//        logEntries.indexOf()


    }


    private static List<String> filterBy(String procID, String keyword) {
        final String logRegex = String.format("[\\d-]+\\s+[\\d:\\.]+\\s+%s\\s+[\\d]+\\s+([A-Z]\\s+[\\w]+\\s+:.+%s.+)", procID, keyword);
        final Pattern logPattern = Pattern.compile(logRegex);
        final ClassLoader classLoader = ClassLoader.getSystemClassLoader();
        List<String> filteredLines = null;

        try {
            Stream<String> logEntries = Files.lines(Paths.get(classLoader.getResource(filepath).getFile()));
            filteredLines = logEntries
                    .filter(logPattern.asPredicate())
                    .map(line -> trimLogLine(line))
                    .collect(toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return filteredLines;

    }


    private static String trimLogLine(String logEntry) {

        final String logRegex = "[\\d-]+\\s+[\\d:\\.]+\\s+[\\d]+\\s+[\\d]+\\s+[A-Z]\\s+[\\w]+:\\s+(.+)";
        final Pattern logPattern = Pattern.compile(logRegex);
        Matcher matcher = logPattern.matcher(logEntry);

        return matcher.matches() ? matcher.group(1) : "";
    }

    private static void loadLogFile() {
        String fileName = "android.log";

        ClassLoader classLoader = ClassLoader.getSystemClassLoader();

        File file = new File(classLoader.getResource(fileName).getFile());

        try {
            Stream<String> temp = Files.lines(Paths.get(classLoader.getResource(fileName).getFile()));
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
