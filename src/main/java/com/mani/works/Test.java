package com.mani.works;

import java.util.Arrays;

public class Test {
    public static void main(String[] args) {
        LogUtil.parseLog("4667", Arrays.asList("OutOfMemoryError"));
        LogUtil.parseLog("2079", Arrays.asList("OutOfMemoryError"));
        LogUtil.parseLog("7457", Arrays.asList("OutOfMemoryError"));
    }
}
