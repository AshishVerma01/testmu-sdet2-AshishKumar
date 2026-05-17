package com.frameworkdesign.ui.utility;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class FrameworkReporting {

    public static ExtentReports generateReport() {
        ExtentSparkReporter extentSparkReporter = new ExtentSparkReporter(System.getProperty("user.dir") + "//testOutputData//reports//index.html");
        extentSparkReporter.config().setReportName("Framework Automation Results");
        extentSparkReporter.config().setDocumentTitle("Test Results");

        ExtentReports extentReports = new ExtentReports();
        extentReports.attachReporter(extentSparkReporter);
        extentReports.setSystemInfo("Tester", "Ashish Kumar");
        return extentReports;
    }

}
