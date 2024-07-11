package Amazon.tests;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

public class TestDeleteMultiple {

	public WebDriver driver;

	@Test
	public void deleteProductFromcart() throws InterruptedException {
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--remote-allow-origins=*");
		// WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver(options);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.manage().window().maximize();
		driver.get("https://rahulshettyacademy.com/client");
		// LandingPage landingPage = new LandingPage(driver);
		driver.findElement(By.id("userEmail")).sendKeys("chaitanyadhoddi23@gmail.com");
		driver.findElement(By.id("userPassword")).sendKeys("Chaitanya19@");
		driver.findElement(By.id("login")).click();
		// Wait until products are visible
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".mb-3")));

		// Select products to add to cart
		String[] productList = { "ZARA COAT 3", "IPHONE 13 PRO", "ADIDAS ORIGINAL" };
		List<String> givenItems = Arrays.asList(productList);
		List<WebElement> products = driver.findElements(By.cssSelector(".mb-3"));
		products.stream().filter(s -> givenItems.contains(s.findElement(By.cssSelector("b")).getText()))
				.limit(givenItems.size()).forEach(s -> {
					s.findElement(By.cssSelector(".card-body button:last-of-type")).click();
					wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("toast-container")));
					wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(".ng-animating")));
				});
		// Go to cart
		driver.findElement(By.cssSelector("button[routerLink*='/cart']")).click();
		// wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@class='cartWrap
		// ng-star-inserted']")));
		Thread.sleep(4000);
		List<WebElement> deleteButtons = driver.findElements(By.cssSelector("button[class='btn btn-danger']"));
		while (!deleteButtons.isEmpty()) {
			for (WebElement deleteButton : deleteButtons) {
				try {
					deleteButton.click();
					wait.until(ExpectedConditions.stalenessOf(deleteButton)); // Wait until the button is no longer
																				// present
				} catch (StaleElementReferenceException e) {
					// Handle stale element reference exception
					// Re-locate the elements or handle the situation accordingly
					System.out.println("Stale element reference: " + e.getMessage());
				}
			}
			// Refresh the deleteButtons list after deleting products
			deleteButtons = driver.findElements(By.cssSelector("button[class='btn btn-danger']"));
		}

		Thread.sleep(4000);
		// Verify if the cart is empty
		WebElement emptyCartMessage = driver.findElement(By.cssSelector(".ng-star-inserted >h1"));
		System.out.println("Cart:" + emptyCartMessage.getText());
		Assert.assertEquals(emptyCartMessage.getText(), "No Products in Your Cart !");

	}

	@AfterMethod
	public void tearDown() {
		// Close the browser
		driver.quit();
	}

}
