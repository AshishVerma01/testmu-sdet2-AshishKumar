package com.frameworkdesign.ui.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class ShoppingProcessPage {

    public static By cartNavButton = By.cssSelector("button[routerlink='/dashboard/cart']");
    public static By checkoutButton = By.cssSelector(".totalRow .btn-primary");
    public static By productNamesElement = By.cssSelector("tr td:nth-child(3)");
    public static By orderHeader = By.cssSelector("[routerlink*='myorders']");
    public static By countryInput = By.cssSelector(".user__address input");
    public static By countryListElement = By.cssSelector(".ta-results button");
    public static By submitOrder = By.cssSelector(".action__submit");
    public static By allProductsElement = By.className("card-body");
    public static By loginForm = By.className("login-section-wrapper");
    public static By usernameInput = By.id("userEmail");
    public static By passwordInput = By.id("userPassword");
    public static By loginButton = By.id("login");

}