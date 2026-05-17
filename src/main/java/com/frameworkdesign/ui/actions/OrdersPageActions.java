package com.frameworkdesign.ui.actions;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

import static com.frameworkdesign.ui.pages.ShoppingProcessPage.*;

public class OrdersPageActions {
    WebDriver driver;
    WebDriverWait wait;

    public OrdersPageActions(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
    }

    public void goToOrdersPage() {
        driver.findElement(orderHeader).click();
    }

    public Boolean verifyOrderDisplay(String productName) {
        List<WebElement> productNames = driver.findElements(productNamesElement);
        Boolean match = productNames.stream().anyMatch(product -> product.getText().equalsIgnoreCase(productName));
        return match;

    }
}
