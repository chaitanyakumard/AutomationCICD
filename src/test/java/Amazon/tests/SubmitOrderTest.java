package Amazon.tests;


import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;

import org.testng.annotations.DataProvider;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import Amazon.TestComponents.BaseTest;
import Amazon.pageobjects.CartPage;
import Amazon.pageobjects.CheckoutPage;
import Amazon.pageobjects.ConfirmationPage;
import Amazon.pageobjects.OrderPage;
import Amazon.pageobjects.ProductCatalogue;
import io.github.bonigarcia.wdm.WebDriverManager;
import Amazon.pageobjects.LandingPage;


public class SubmitOrderTest extends BaseTest {
	
	String productName = "ZARA COAT 3";

	@Test(dataProvider = "getData", groups = { "Purchase" })
	public void submitOrder(HashMap<String, String> input) throws IOException, InterruptedException {
	
		
		ProductCatalogue productCatalogue = landingPage.loginApplication(input.get("email"), input.get("password"));
		List<WebElement> products = productCatalogue.getProductList();
		productCatalogue.addProductToCart(input.get("product"));

		CartPage cartPage = productCatalogue.goToCartPage();

		Boolean match = cartPage.VerifyProductDisplay(input.get("product"));

		Assert.assertTrue(match);
		CheckoutPage checkoutPage = cartPage.goToCheckout();
		checkoutPage.selectCountry("india");
		ConfirmationPage confirmationPage = checkoutPage.submitOrder();
		String confirmMessage = confirmationPage.getConfirmationMessage();
		Assert.assertTrue(confirmMessage.equalsIgnoreCase("THANKYOU FOR THE ORDER."));

	}

	@Test(dependsOnMethods = { "submitOrder" })
	public void OrderHistoryTest() {
		// "ZARA COAT 3";
		ProductCatalogue productCatalogue = landingPage.loginApplication("chaitanyadhoddi23@gmail.com", "Chaitanya19@");
		OrderPage ordersPage = productCatalogue.goToOrderPage();
		Assert.assertTrue(ordersPage.VerifyOrderDisplay(productName));
 
	}

	


	 @DataProvider
	  public Object[][] getData() throws IOException
	  {

		 List<HashMap<String, String>> data = getJsonDataToMap(System.getProperty("user.dir")+"\\src\\test\\java\\Amazon\\data\\PurchaseOrder.json");
		 return new Object[][] { { data.get(0) }, { data.get(1) } };
	    
	  }
	 
//	 Extent Reports -
//	@DataProvider
//	public Object[][] getData() throws IOException {
//		List<HashMap<String, String>> data = getJsonDataToMap(
//				System.getProperty("user.dir") + "//src//test//java//rahulshettyacademy//data//PurchaseOrder.json");
//		return new Object[][] { { data.get(0) }, { data.get(1) } };
//	}
	
//	 HashMap<String,String> map = new HashMap<String,String>();
//		map.put("email", "chaitanyadhoddi23@gmail.com");
//		map.put("password", "Chaitanya19@");
//		map.put("product", "ZARA COAT 3");
//		
//		HashMap<String,String> map1 = new HashMap<String,String>();
//		map1.put("email", "shetty@gmail.com");
//		map1.put("password", "Iamking@000");
//		map1.put("product", "ADIDAS ORIGINAL");
//	 getJsonDataToMap();
	

}