package com.flipk.tests;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeTest;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;

public class FetchIphonesDetails {

	WebDriver driver = null;
	
  @Test
  public void f() throws AWTException, InterruptedException, IOException {
	  Robot r = new Robot();
	  //Close the login pop-up
	  r.keyPress(KeyEvent.VK_ESCAPE);
	  
	  //Enter search key and click search
	  driver.findElement(By.name("q")).sendKeys("iphone");
	  driver.findElement(By.xpath("//form[contains(@action,'search')]")).submit();
	  Thread.sleep(1000);
	  
	  //Select the To price range to 50000 (40000 is not available, so 50000 is selected)
	  Select priceToRange = new Select(driver.findElement(By.xpath("//section[2]/descendant::select[2]")));
	  priceToRange.selectByIndex(9);
	  
	  //Apply price Low to High filter
	  driver.findElement(By.xpath("//div[text()='Price -- Low to High']")).click();
	  List<WebElement> phoneList = null;
	  String phoneDecription = "", ratings = "";
	  File file = new File("iphonelist.csv");
	  FileWriter fw = new FileWriter(file);
	  int price = 0;
	  
	  //Traverse each page to find phones less than 40,000 and write to CSV file
	  while(driver.findElements(By.xpath("//span[text()='Next']")).size() > 0 ){
		  try{
			  phoneList = driver.findElements(By.xpath("//div[@class='_1YokD2 _3Mn1Gg'][2]//div[contains(text(),'Apple') or contains(text(),'APPLE')]"));

			  for(int i=0; i<phoneList.size(); i++){
				  phoneList.get(i).getText();
			  }
		  }
		 catch(StaleElementReferenceException e){
			 phoneList = driver.findElements(By.xpath("//div[@class='_1YokD2 _3Mn1Gg'][2]//div[contains(text(),'Apple') or contains(text(),'APPLE')]"));

			 fw.write("Device Details,Price,Ratings\n");
			 
			 for(int i=0; i<phoneList.size(); i++){
				 phoneDecription = phoneList.get(i).getText();
				 ratings = phoneList.get(i).findElement(By.xpath("../div[2]/span[2]/span/span")).getText().replace(" Ratings", "");
				 price = Integer.parseInt(phoneList.get(i).findElement(By.xpath("../following-sibling::div/div/div/div")).getText().replaceAll("[^a-zA-Z0-9]", ""));
				 
				 if(price <=40000){
					// phoneDecription = phoneDecription + ", " + price + ", " + ratings;
					// phoneDecription = "\""+phoneDecription+"\","; 
					 fw.write("\""+phoneDecription+"\",");
					 fw.write(price+",");
					 fw.write("\""+ratings+"\",");
					 fw.append("\n");
					 System.out.println(phoneDecription);
					 phoneDecription = ""; 
				 }
			  }
		 }
		  
		  if(driver.findElements(By.xpath("//span[text()='Next']")).size()>0){
			  try{
				  driver.findElement(By.xpath("//span[text()='Next']")).click();
			  }
			  catch(ElementClickInterceptedException ex){
				  WebDriverWait wait = new WebDriverWait(driver, 10);
				  wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[text()='Next']")));
				  if(driver.findElements(By.xpath("//span[text()='Next']")).size()>0)
					  driver.findElement(By.xpath("//span[text()='Next']")).click();
			  }
			  Thread.sleep(3000);
		  }
		
	  }
	  fw.flush();
	  fw.close();  
  }
  
  @BeforeTest
  public void beforeTest() throws InterruptedException {
	  System.setProperty("webdriver.chrome.driver", "C:\\Users\\nm045365\\Documents\\FlipkartAutomationProjectDependencies\\chromedriver.exe");
	  driver = new ChromeDriver();
	  driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	  driver.get("https://www.flipkart.com");
	  driver.manage().window().maximize();
  }

  @AfterTest
  public void afterTest() {
	 driver.close();
	 driver.quit();
  }

}
