package com.frameworkdesign.ui.actions;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

import static com.frameworkdesign.ui.pages.ShoppingProcessPage.*;

public class FinalCheckoutPageActions {
    WebDriver driver;
    WebDriverWait wait;
    Actions actions;

    public FinalCheckoutPageActions(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
        actions = new Actions(this.driver);
    }

    public String placeOrder(String country) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".payment__info .details__user")));
        actions.click(driver.findElement(countryInput)).sendKeys(country).build().perform();
        List<WebElement> countryList = driver.findElements(countryListElement);
        WebElement countryElement = countryList.stream().filter(c -> c.findElement(By.cssSelector("span")).getText().equals(country)).findFirst().orElse(null);
        countryElement.click();
        driver.findElement(submitOrder).click();
        return wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".content h1"))).getText();
    }
}
