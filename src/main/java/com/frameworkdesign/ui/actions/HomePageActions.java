package com.frameworkdesign.ui.actions;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

import static com.frameworkdesign.ui.pages.ShoppingProcessPage.*;

public class HomePageActions {
    WebDriver driver;
    WebDriverWait wait;

    public HomePageActions(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
    }

    public WebElement findProduct(String productName) {
        List<WebElement> allProducts = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(allProductsElement));
        return allProducts.stream().filter(p -> p.findElement(By.cssSelector("h5 b")).getText().equalsIgnoreCase(productName)).findFirst().orElse(null);
    }

    public String addProductToCart(WebElement product, String productName) throws Exception {
        try {
            product.findElement(By.xpath("./button[contains(text(),'Add To Cart')]")).click();
        } catch (StaleElementReferenceException e) {
            e.printStackTrace();
            Thread.sleep(1000);
            findProduct(productName).findElement(By.xpath("./button[contains(text(),'Add To Cart')]")).click();
        }
        return wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("toast-message"))).getText();
    }


}
