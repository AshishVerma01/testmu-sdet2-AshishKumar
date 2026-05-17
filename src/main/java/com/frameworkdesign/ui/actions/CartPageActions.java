package com.frameworkdesign.ui.actions;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Iterator;
import java.util.List;

import static com.frameworkdesign.ui.pages.ShoppingProcessPage.*;

public class CartPageActions {
    WebDriver driver;
    WebDriverWait wait;

    public CartPageActions(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
    }

    public void goToCartPage() {
        driver.findElement(cartNavButton).click();
    }

    public boolean verifyCartProducts(List<String> products) {
        List<WebElement> cartProducts = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector(".cart .items")));
        Iterator<String> iterator = products.iterator();
        boolean allProductsFound = true;
        while (iterator.hasNext()) {
            if (!cartProducts.stream().anyMatch(p -> p.findElement(By.cssSelector("h3")).getText().equalsIgnoreCase(iterator.next()))) {
                allProductsFound = false;
                break;
            }
        }
        return allProductsFound;
    }

    public void proceedToCheckout() {
        driver.findElement(checkoutButton).click();
    }

    public String getEmptyCartErrorMessage() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".toast-message"))).getText();
    }

}
