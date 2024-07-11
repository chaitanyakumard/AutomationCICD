package Amazon.tests;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class RSALogin {

	public static void main(String[] args) throws InterruptedException {
		WebDriver driver = new ChromeDriver();
		driver.get("https://rahulshettyacademy.com/");
		driver.manage().window().maximize();
		Thread.sleep(10000);
		// Login button
		driver.findElement(By.xpath("//a[@class='theme-btn register-btn']")).click();
		
		

	}

}