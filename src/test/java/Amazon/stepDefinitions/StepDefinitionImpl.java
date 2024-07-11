package Amazon.stepDefinitions;

import java.io.IOException;
import java.util.List;

import org.openqa.selenium.WebElement;
import org.testng.Assert;

import Amazon.TestComponents.BaseTest;
import Amazon.pageobjects.CartPage;
import Amazon.pageobjects.CheckoutPage;
import Amazon.pageobjects.ConfirmationPage;
import Amazon.pageobjects.LandingPage;
import Amazon.pageobjects.ProductCatalogue;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;

public class StepDefinitionImpl extends BaseTest {

    public LandingPage landingPage;
    public ProductCatalogue productCatalogue;
    public ConfirmationPage confirmationPage;

    @Given("I landed on Ecommerce Page")
    public void I_landed_on_Ecommerce_Page() throws IOException {
        landingPage = launchApplication();
    }

    @Given("^Logged in with username (.+) and password (.+)$")
    public void logged_in_username_and_password(String username, String password) {
        productCatalogue = landingPage.loginApplication(username, password);
    }

    @When("^I add product (.+) to Cart$")
    public void i_add_product_to_cart(String productName) throws InterruptedException {
        List<WebElement> products = productCatalogue.getProductList();
        productCatalogue.addProductToCart(productName);
    }

    @When("^Checkout (.+) and submit the order$")
    public void checkout_submit_order(String productName) {
        CartPage cartPage = productCatalogue.goToCartPage();
        Boolean match = cartPage.VerifyProductDisplay(productName);
        Assert.assertTrue(match);
        CheckoutPage checkoutPage = cartPage.goToCheckout();
        checkoutPage.selectCountry("india");
        confirmationPage = checkoutPage.submitOrder();
    }

    @Then("{string} message is displayed on ConfirmationPage")
    public void message_displayed_confirmationPage(String message) {
        String confirmMessage = confirmationPage.getConfirmationMessage();
        Assert.assertTrue(confirmMessage.equalsIgnoreCase(message));
        driver.close();
    }

    @Then("^\"([^\"]*)\" message is displayed$")
    public void something_message_is_displayed(String strArg1) throws Throwable {
        Assert.assertEquals(strArg1, landingPage.getErrorMessage());
        driver.close();
    }
}
