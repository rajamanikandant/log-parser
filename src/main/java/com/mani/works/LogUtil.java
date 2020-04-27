package com.mani.works;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

public class LogUtil {
    private static final String filepath = "sample.log";
    private static List<String> logList;


    public static void parseLog(String procID, List<String> keywords) {

        //Get list by procID
        //Filter by Fatal Exception
            //Filter by Fatal Exception - Unique exception & count
            //Filter by Fatal Exception - Unique exception & stacktrace
        //Filter by Errors
            //Filter by Errors - Unique Errors & count
        //Filter by Given List of Keywords
            //Filter by Given List of Keywords - lines & count

        


//        logList = filterByNew(procID, keywords.get(0));
        logList = filterBy(procID);
        Stream<String> filteredLogs = logList.stream();
        filteredLogs.forEach(System.out::println);


        Map<String, Long> result = logList.stream()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        System.out.println("#----------#");
        String exceptionStr = logList.stream().findFirst().get();
        System.out.println(exceptionStr);
        System.out.println(logList.indexOf(exceptionStr));
        System.out.println("#----------#");
        result.forEach((k,v) -> System.out.println(k + " | " + v));

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
                    .map(line -> testTrim(line))
                    .collect(toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
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
                    .map(line -> testTrim(line))
                    .collect(toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return filteredLines;

    }


    private static String trimLogLine(String logEntry) {
        return "";
    }

    public static String testTrim(String input) {

        final String logRegex = "[\\d-]+\\s+[\\d:\\.]+\\s+[\\d]+\\s+[\\d]+\\s+([A-Z]\\s+.+)";
        final Pattern logPattern = Pattern.compile(logRegex);
        Matcher matcher = logPattern.matcher(input);

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
