package com.frameowrkdesign.tests;

import com.frameworkdesign.ui.actions.BaseActions;
import com.frameworkdesign.ui.actions.ExcelSheetActions;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Paths;

import static com.frameworkdesign.ui.pages.UploadDownloadPage.*;

public class UploadDownloadTests extends BaseActions {
    WebDriver driver;
    String PAGE_URL = "https://rahulshettyacademy.com/upload-download-test/index.html";
    String EXCEL_FILE_PATH = Paths.get("C:","Users", "ASHISHKUMAR", "Downloads", "download.xlsx").toString();
    String fruitName = "Apple";
    String columnToUpdate = "Price";
    int updatedValue = 500;

    @BeforeMethod(alwaysRun = true)
    public void goToPage() throws IOException, URISyntaxException {
        driver=initiateDriver(PAGE_URL, runType);
    }

    @AfterMethod(alwaysRun = true)
    public void closeBrowser() {
        tearDown();
    }

    @Test(testName = "Download excel data and re-upload after modification")
    public void downloadExcelDataAndReUploadAfterModification() throws Exception {
        //Download Excel file
        driver.findElement(downloadButton).click();

        //Edit Excel file
        ExcelSheetActions excelSheetActions = new ExcelSheetActions(EXCEL_FILE_PATH);
        XSSFSheet sheet = excelSheetActions.getExcelSheet(0);
        int col = excelSheetActions.getColumnFromSheet(sheet, columnToUpdate);
        int row = excelSheetActions.getRowFromSheet(sheet, fruitName);
        excelSheetActions.updateCellValue(sheet, row, col, updatedValue);
        excelSheetActions.saveAndCloseExcel(EXCEL_FILE_PATH);


        //Upload Excel file
        WebElement upload = driver.findElement(uploadButton);
        upload.sendKeys(EXCEL_FILE_PATH);

        Assert.assertEquals(getWait().until(ExpectedConditions.visibilityOfElementLocated(toastEle)).getText(), "Updated Excel Data Successfully.");
        getWait().until(ExpectedConditions.invisibilityOfElementLocated(toastEle));

        //Verify Data
        String priceColumn = driver.findElement(getColumnElement(columnToUpdate)).getAttribute("data-column-id");
        String actualPrice = driver.findElement(getCellElement(fruitName, priceColumn)).getText();
        Assert.assertEquals(Integer.parseInt(actualPrice), updatedValue);
    }
}