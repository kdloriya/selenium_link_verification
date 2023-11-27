package KishanPractice.LinksStatus;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import io.github.bonigarcia.wdm.WebDriverManager;

public class LinkStatus 
{
		static WebDriver driver;
		static int failedLinkCount = 0;
		
	   public static void main (String [] args) throws InterruptedException, IOException {
		   
		   WebDriverManager.chromedriver().setup();
		   ChromeOptions option = new ChromeOptions();
	   	   option.addArguments("incognito");
	   	   driver = new ChromeDriver(option);
	   	   linksVerification(driver);
	   	   driver.quit();
	   	   
	   }
	   
		   public static void linksVerification (WebDriver driver) throws InterruptedException, IOException {
			 //finding total number of Links in page and segregation them based on the location in page.
			   
			   driver.get("https://rahulshettyacademy.com/AutomationPractice/");
			   Thread.sleep(3000);
			   List <WebElement> allLinks = driver.findElements(By.tagName("a"));
			   int pageTotalLinks = allLinks.size();
			   	   
			   WebElement footerDriver = driver.findElement(By.id("gf-BIG"));
			   List <WebElement> footerLinks = footerDriver.findElements(By.tagName("a"));
			   int footerTotalLinks = footerLinks.size();
			   
			   WebElement footerColumnDriver = footerDriver.findElement(By.xpath("//a[text()='Discount Coupons']//ancestor::ul"));
			   List <WebElement> footerColumnLinks = footerColumnDriver.findElements(By.tagName("a"));
			   int footerColumnTotalLinks = footerColumnLinks.size();
			   
			   System.out.println("Total Links in the Web Page: " + pageTotalLinks);
			   System.out.println("Total Links in the Web Page Footer: " + footerTotalLinks);
			   System.out.println("Total Links in the Web Page under Discount Coupon: " + footerColumnTotalLinks);
			   
			   for (WebElement link : footerLinks) {
				   String url = link.getAttribute("href");
				   HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
				   connection.setRequestMethod("HEAD");
				   connection.connect();
				   int respCode = connection.getResponseCode();
				   if (respCode > 400) {
					   failedLinkCount++;
					   System.out.println(url + " " + respCode);
					   System.out.println(failedLinkCount);
					}
				   
				   
			}   
	 }
}
