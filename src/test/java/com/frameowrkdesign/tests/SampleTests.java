package com.frameowrkdesign.tests;

import com.frameworkdesign.ui.actions.BaseActions;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.Test;

import java.time.Duration;

//https://www.cleartrip.com/
public class SampleTests extends BaseActions {
    WebDriver driver;
    @Test
    public void book_flight() throws Exception {
        driver = new ChromeDriver();
        driver.get("https://www.cleartrip.com/");
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
        Actions actions = new Actions(driver);
    }

    //To test CI/CD
}
