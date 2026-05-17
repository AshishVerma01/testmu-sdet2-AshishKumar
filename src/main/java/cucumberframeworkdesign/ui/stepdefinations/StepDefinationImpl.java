package cucumberframeworkdesign.ui.stepdefinations;

import com.frameworkdesign.ui.actions.*;
import com.frameworkdesign.ui.utility.ConfigManager;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import java.io.IOException;
import java.util.List;

public class StepDefinationImpl extends BaseActions {
    String PAGE_URL = ConfigManager.getProperty("page.url");
    HomePageActions homePageActions;
    CartPageActions cartPageActions;
    FinalCheckoutPageActions finalCheckoutPage;
    OrdersPageActions ordersPageActions;

    @Before
    public void setup() throws Exception {
        initiateDriver(PAGE_URL);
        homePageActions = new HomePageActions(getDriver(), getWait());
        cartPageActions = new CartPageActions(getDriver(), getWait());
        finalCheckoutPage = new FinalCheckoutPageActions(getDriver(), getWait());
        ordersPageActions = new OrdersPageActions(getDriver(), getWait());
    }

    @After
    public void teardown() {
        tearDown();
    }

    @Given("I landed on Ecommerce website")
    public void i_landed_on_ecommerce_website() throws IOException {
        //some code related to landing page
    }

    @Given("^Logged In with username (.+) and password (.+)$")
    public void logged_in_with_username_and_password(String username, String password) {
        String confirmationMessage = login(username, password);
        Assert.assertTrue(confirmationMessage.contains("Login Successfully"),"Login Failed!");
    }

    @When("^I added the product (.+) to cart$")
    public void add_product_to_cart(String productStr) throws Exception{
        WebElement product = homePageActions.findProduct(productStr);
        Assert.assertFalse((product == null), "Product Not Found!");
        Assert.assertTrue(homePageActions.addProductToCart(product, productStr).equals("Product Added To Cart"), "Failed To Add Product To Cart!");
        cartPageActions.goToCartPage();
        Assert.assertTrue(cartPageActions.verifyCartProducts(List.of(productStr)),"All Items Not Found In Cart!");
    }

    @When("I proceed for checkout")
    public void proceed_to_checkout() {
        cartPageActions.proceedToCheckout();
    }

    @Then("{string} message is displayed on the confirmation page")
    public void verify_confirmation_page(String message) {
        Assert.assertTrue(finalCheckoutPage.placeOrder("India").contains(message),"Failed To Place Order!");
    }
}
