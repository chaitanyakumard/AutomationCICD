package Amazon.tests;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import Amazon.pageobjects.LandingPage;
import io.github.bonigarcia.wdm.WebDriverManager;

public class StandAloneTest {
    public static void main(String[] args) {
    	
    	String productName = "ZARA COAT 3";
        // Set up WebDriverManager to manage ChromeDriver binaries
        WebDriverManager.chromedriver().setup();

        // Initialize ChromeDriver
        WebDriver driver = new ChromeDriver();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.manage().window().maximize();

        // Open the URL
        driver.get("https://rahulshettyacademy.com/client");
        LandingPage landingPage = new LandingPage(driver);

        // Enter email
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("userEmail"))).sendKeys("chaitanyadhoddi23@gmail.com");

        // Enter password and click login
        driver.findElement(By.id("userPassword")).sendKeys("Chaitanya19@");
        driver.findElement(By.id("login")).click();

        // Wait for the products to be visible
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".mb-3")));

        // Find the specific product and add it to the cart
        WebElement prod = driver.findElement(By.xpath("//b[text()='" + productName + "']/ancestor::div[contains(@class, 'mb-3')]"));
        WebElement addToCartButton = prod.findElement(By.cssSelector(".card-body button:last-of-type"));
        addToCartButton.click();

        // Wait for the toast message to appear and disappear
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#toast-container")));
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(".ng-animating")));

        // Click on the cart icon
        driver.findElement(By.cssSelector("[routerlink*='cart']")).click();
        
        // Wait for the cart page to load
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".cartSection h3")));
        
        // Verify if the product is added to the cart
        List<Object> cartProductNames = driver.findElements(By.cssSelector(".cartSection h3"))
                .stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
        
        Boolean match = cartProductNames.stream()
                .anyMatch(cartProduct -> ((String) cartProduct).equalsIgnoreCase(productName));
        Assert.assertTrue(match, "Product '" + productName + "' is not found in the cart.");
        
        // Proceed to checkout
        driver.findElement(By.cssSelector(".totalRow button")).click();
        
        // Example of continuing checkout process, adjust as per your website's flow
        Actions actions = new Actions(driver);
        actions.sendKeys(driver.findElement(By.cssSelector("[placeholder='Select Country']")), "India").build().perform();
        
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".ta-results")));
        
        driver.findElement(By.xpath("(//button[contains(@class,'ta-item')])[2]")).click();
        driver.findElement(By.cssSelector(".action__submit")).click();
        
        // Validate confirmation message
        String confirmMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".hero-primary"))).getText();
        Assert.assertTrue(confirmMessage.equalsIgnoreCase("Thank you for the order."), "Confirmation message mismatch.");


       
    }
}
