package com.frameworkdesign.ui.utility;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class FrameworkRetry implements IRetryAnalyzer {
    boolean retryEnabled = Boolean.parseBoolean(System.getProperty("retryTests", "false"));
    public static int count = 0;
    public static final int MAX_RETRY = 1;

    @Override
    public boolean retry(ITestResult result) {
        if (!retryEnabled) {
            return false;
        }

        if (count < MAX_RETRY) {
            count++;
            return true;
        }
        return false;
    }
}
