package com.frameworkdesign.ui.utility;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;

public class FrameworkUtilityMethods {

    public static List<HashMap<String, String>> getJsonDataToMap(File file) throws IOException {
        String jsonContent = FileUtils.readFileToString(file, StandardCharsets.UTF_8);
        ObjectMapper objectMapper = new ObjectMapper();
        List<HashMap<String, String>> data = objectMapper.readValue(jsonContent, new TypeReference<>() {});
        return data;
    }

    public static String getScreenshot(WebDriver driver, String testCaseName) throws IOException {
        TakesScreenshot screenshot = (TakesScreenshot) driver;
        File screenshotFile = screenshot.getScreenshotAs(OutputType.FILE);
        File saveScreenshot = new File(System.getProperty("user.dir") + "//testOutputData//screenshots//" + testCaseName + ".png");
        FileUtils.copyFile(screenshotFile, saveScreenshot);
        return saveScreenshot.getPath();
    }
}
