package com.frameworkdesign.ui.pages;

import org.openqa.selenium.By;

public class UploadDownloadPage {

    public static By downloadButton = By.id("downloadButton");
    public static By uploadButton = By.id("fileinput");
    public static By toastEle = By.cssSelector(".Toastify__toast-body div:nth-child(2)");
    public static By getColumnElement(String columnToUpdate) {
        return By.xpath("//div[text()='" + columnToUpdate + "']/parent::div");
    }
    public static By getCellElement(String value, String column) {
        return By.xpath("//div[text()='" + value + "']/parent::div/parent::div/div[@id='cell-" + column + "-undefined']");
    }
}
