package Amazon.tests;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.wdm.WebDriverManager;

public class MultipleProductsToCart {

	    public static void main(String[] args) {
	    	ChromeOptions options = new ChromeOptions();
			options.addArguments("--remote-allow-origins=*");
			WebDriverManager.chromedriver().setup();
			WebDriver driver = new ChromeDriver(options);
	        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
	        driver.get("https://rahulshettyacademy.com/client");
	        driver.findElement(By.id("userEmail")).sendKeys("vigupta.kws@gmail.com");
	        driver.findElement(By.id("userPassword")).sendKeys("P@ssw0rd@94");

	        driver.findElement(By.id("login")).click();
	        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
	        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".mb-3")));

	        List<String> targetProducts = Arrays.asList("ZARA COAT 3", "ADIDAS ORIGINAL", "IPHONE 13 PRO");

	        List<WebElement> products = driver.findElements(By.cssSelector(".mb-3"));

	        // Loop through the target products and add them to the cart
	        targetProducts.forEach(targetProduct -> {
	            WebElement product = products.stream()
	                    .filter(p -> p.findElement(By.cssSelector("b")).getText().equals(targetProduct))
	                    .findFirst().orElse(null);

	            if (product != null) {
	                product.findElement(By.cssSelector(".card-body button:last-of-type")).click();

	                wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.cssSelector("div[class*='ng-animating']"))));
	                wait.until(ExpectedConditions.invisibilityOf(driver.findElement(By.cssSelector("div[class*='ngx-spinner-overlay']"))));
	            }
	        });

	        driver.findElement(By.xpath("//button[@routerlink='/dashboard/cart']")).click();
	    }
}
