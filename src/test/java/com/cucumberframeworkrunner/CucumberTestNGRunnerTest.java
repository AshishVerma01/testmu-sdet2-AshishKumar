package com.cucumberframeworkrunner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(features = "src/test/resources/cucumberframeworkdesign",
        glue = "cucumberframeworkdesign.ui.stepdefinations", monochrome = true, tags = "@smoke",
        plugin = {"pretty", "html:target/cucumber.html"})
public class CucumberTestNGRunnerTest extends AbstractTestNGCucumberTests {
}