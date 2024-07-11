package Amazon.tests;



import org.testng.annotations.Test;



import Amazon.TestComponents.BaseTest;
import Amazon.TestComponents.Retry;
import Amazon.pageobjects.CartPage;
import Amazon.pageobjects.CheckoutPage;
import Amazon.pageobjects.ProductCatalogue;
import Amazon.pageobjects.ConfirmationPage;

import org.testng.AssertJUnit;
import java.io.IOException;
import java.util.List;

import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;


public class ErrorValidationsTest extends BaseTest {

	@Test(groups= {"ErrorHandling"},retryAnalyzer=Retry.class)
	public void LoginErrorValidation() throws IOException, InterruptedException 
	{	
		landingPage.loginApplication("chaitanyadhoddi@gmail.com", "Chaitanya@");
		Assert.assertEquals("Incorrect email or password.", landingPage.getErrorMessage());

	}
	
	
	
	@Test
	public void ProductErrorValidation() throws IOException, InterruptedException
	{
		String productName = "ZARA COAT 3";
		ProductCatalogue productCatalogue = landingPage.loginApplication("chaitanyadhoddi23@gmail.com", "Chaitanya19@");
		List<WebElement> products = productCatalogue.getProductList();
		productCatalogue.addProductToCart(productName);
		CartPage cartPage = productCatalogue.goToCartPage();
		Boolean match = cartPage.VerifyProductDisplay("ZARA COAT 33");
		Assert.assertFalse(match);
	}

}