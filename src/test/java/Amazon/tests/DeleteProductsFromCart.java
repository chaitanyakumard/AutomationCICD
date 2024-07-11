package Amazon.tests;


import java.util.Arrays;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import Amazon.TestComponents.BaseTest;
import Amazon.pageobjects.CartPage;
import Amazon.pageobjects.ProductCatalogue;


public class DeleteProductsFromCart extends BaseTest 
{
	@Test
	public void deleteProducts() throws InterruptedException {
	ProductCatalogue productCatalogue = landingPage.loginApplication("chaitanyadhoddi23@gmail.com", "Chaitanya19@");
	
	List<String> productList = Arrays.asList("ZARA COAT 3", "ADIDAS ORIGINAL", "IPHONE 13 PRO");
    productList.forEach(product -> {    
			 try {
				productCatalogue.addProductToCart(product);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}		
    });
 // Go to cart
    productCatalogue.goToCartPage();
    Thread.sleep(4000);
    // Delete items from the cart
    CartPage cartPage = new CartPage(driver);
    cartPage.deleteAllItems();

    // Verify if the cart is empty
    Assert.assertTrue(cartPage.isCartEmpty(), "No Products in Your Cart !");
   
	}
	@AfterMethod
	public void tearDown()
	{
		// Close the browser
        driver.quit();
	}
}