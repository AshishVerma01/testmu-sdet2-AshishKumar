package com.frameworkdesign.ui.utility;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.frameworkdesign.ui.actions.BaseActions;
import org.openqa.selenium.WebDriver;
import org.testng.IRetryAnalyzer;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class FrameworkListeners implements ITestListener {
    ExtentTest test;
    ExtentReports extentReports = FrameworkReporting.generateReport();
    ThreadLocal<ExtentTest> extentTest = new ThreadLocal<>();
    Map<String, ExtentTest> testMap = new ConcurrentHashMap<>();

    @Override
    public void onTestStart(ITestResult result) {

        String name = result.getMethod().getMethodName();
        if (!testMap.containsKey(name)) {
            test = extentReports.createTest(name);
            testMap.put(name, test);
        }
        extentTest.set(testMap.get(name));
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        if (result.getMethod().getRetryAnalyzer(result) != null) {
            IRetryAnalyzer retry = result.getMethod().getRetryAnalyzer(result);
            if (retry instanceof FrameworkRetry) {
                FrameworkRetry frameworkRetry = (FrameworkRetry) retry;
                if (frameworkRetry.count > 0) {
                    extentTest.get().log(Status.PASS, "Test Passed after Retry");
                    return;
                }
            }
        }
        extentTest.get().log(Status.PASS, "Test Passed");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        if (result.getMethod().getRetryAnalyzer(result) != null) {
            IRetryAnalyzer retry = result.getMethod().getRetryAnalyzer(result);
            if (retry instanceof FrameworkRetry) {
                FrameworkRetry frameworkRetry = (FrameworkRetry) retry;

                if(frameworkRetry.count >= frameworkRetry.MAX_RETRY) {
                    extentTest.get().log(Status.INFO,
                            "Test Failed - Retrying Attempt " + frameworkRetry.count);
                    return;
                }
            }
        }

        extentTest.get().fail(result.getThrowable());
        try {
            WebDriver driver = ((BaseActions) result.getInstance()).getDriver();
            if (driver != null) {
                extentTest.get().addScreenCaptureFromPath(FrameworkUtilityMethods.getScreenshot(driver, result.getMethod().getMethodName()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
    }

    @Override
    public void onTestFailedWithTimeout(ITestResult result) {
    }

    @Override
    public void onStart(ITestContext context) {
    }

    @Override
    public void onFinish(ITestContext context) {
        extentReports.flush();
    }
}
