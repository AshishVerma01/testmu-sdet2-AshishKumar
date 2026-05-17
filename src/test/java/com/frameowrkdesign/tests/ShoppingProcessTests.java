package com.frameowrkdesign.tests;

import com.frameowrkdesign.dataprovider.ShoppingProductDataProvider;
import com.frameworkdesign.ui.actions.*;
import com.frameworkdesign.ui.utility.ConfigManager;
import com.frameworkdesign.ui.utility.FrameworkRetry;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class ShoppingProcessTests extends BaseActions {
    List<String> products = List.of("iphone 13 pro");
    String PAGE_URL = ConfigManager.getProperty("page.url");
    HomePageActions homePageActions;
    CartPageActions cartPageActions;
    FinalCheckoutPageActions finalCheckoutPage;
    OrdersPageActions ordersPageActions;

    @BeforeMethod(alwaysRun = true)
    public void goToPage() throws IOException {
        initiateDriver(PAGE_URL);
        homePageActions = new HomePageActions(getDriver(), getWait());
        cartPageActions = new CartPageActions(getDriver(), getWait());
        finalCheckoutPage = new FinalCheckoutPageActions(getDriver(), getWait());
        ordersPageActions = new OrdersPageActions(getDriver(), getWait());
    }

    @AfterMethod(alwaysRun = true)
    public void closeBrowser() {
        tearDown();
    }

    @Test(testName = "Verify Empty Cart", groups = {"Error Handling"})
    public void checkEmptyCartErrorValidation () {
        String confirmationMessage = login("testframeworkdesign@gmail.com","Test@123");
        Assert.assertTrue(confirmationMessage.contains("Login Successfully"),"Login Failed!");
        cartPageActions.goToCartPage();
        Assert.assertFalse(cartPageActions.getEmptyCartErrorMessage().contains("No Product in Your Cart"), "Empty Cart Error!");
    }

    @Test(testName = "Place Your Order", groups = {"SmokeTest"}, dataProvider = "getProducts", dataProviderClass = ShoppingProductDataProvider.class)
    public void placeOrder (List<String> products, String username, String password) throws Exception {
        String confirmationMessage = login(username, password);
        Assert.assertTrue(confirmationMessage.contains("Login Successfully"),"Login Failed!");
        WebElement product = homePageActions.findProduct(products.get(0));
        Assert.assertFalse((product == null), "Product Not Found!");
        Assert.assertTrue(homePageActions.addProductToCart(product, products.get(0)).equals("Product Added To Cart"), "Failed To Add Product To Cart!");
        cartPageActions.goToCartPage();
        Assert.assertTrue(cartPageActions.verifyCartProducts(products),"All Items Not Found In Cart!");
        cartPageActions.proceedToCheckout();
        Assert.assertTrue(finalCheckoutPage.placeOrder("India").contains("THANKYOU FOR THE ORDER."),"Failed To Place Order!");
    }

    @Test(testName = "Verify Cart Items", groups = {"Error Handling"})
    public void verifyCartItems () throws Exception {
        String confirmationMessage = login("testframeworkdesign@gmail.com","Test@123");
        Assert.assertTrue(confirmationMessage.contains("Login Successfully"),"Login Failed!");
        WebElement product = homePageActions.findProduct(products.get(0));
        Assert.assertFalse((product == null), "Product Not Found!");
        Assert.assertTrue(homePageActions.addProductToCart(product, products.get(0)).equals("Product Added To Cart"), "Failed To Add Product To Cart!");
        cartPageActions.goToCartPage();
        Assert.assertTrue(cartPageActions.verifyCartProducts(List.of("Incorrect Product")),"All Items Not Found In Cart!");
    }

    @Test(testName = "Get Order History List", dependsOnMethods = {"placeOrder"})
    public void OrderHistoryTest()
    {
        String confirmationMessage = login("testframeworkdesign@gmail.com","Test@123");
        Assert.assertTrue(confirmationMessage.contains("Login Successfully"),"Login Failed!");
        ordersPageActions.goToOrdersPage();
        Assert.assertTrue(ordersPageActions.verifyOrderDisplay(products.get(0)));
    }

    @Test(testName = "Place Your Order Using Json Data", groups = {"SmokeTest"}, dataProvider = "getProductsUsingHashMap", dataProviderClass = ShoppingProductDataProvider.class)
    public void placeOrderUsingJsonData (HashMap<String, String> data) throws Exception{
        String confirmationMessage = login(data.get("email"), data.get("password"));
        Assert.assertTrue(confirmationMessage.contains("Login Successfully"),"Login Failed!");
        WebElement product = homePageActions.findProduct(data.get("product"));
        Assert.assertFalse((product == null), "Product Not Found!");
        Assert.assertTrue(homePageActions.addProductToCart(homePageActions.findProduct(data.get("product")), data.get("product")).equals("Product Added To Cart"), "Failed To Add Product To Cart!");
        cartPageActions.goToCartPage();
        Assert.assertTrue(cartPageActions.verifyCartProducts(List.of(data.get("product"))),"All Items Not Found In Cart!");
        cartPageActions.proceedToCheckout();
        Assert.assertTrue(finalCheckoutPage.placeOrder("India").contains("THANKYOU FOR THE ORDER."),"Failed To Place Order!");
    }

}
