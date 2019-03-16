/**
package com.iw.ucafoster;
//como se arranca selenium
//java -jar -Dwebdriver.gecko.driver=geckodriver.exe selenium-server-standalone-3.4.0.jar -port 4445
import java.util.regex.Pattern;
import java.util.concurrent.TimeUnit;
import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.openqa.selenium.*;

import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.*;
//import org.openqa.selenium.chrome.ChromeDriver;

import org.openqa.selenium.support.ui.Select;

public class ProyectoApplicationTests {
  private WebDriver driver;
  private String baseUrl;
  private boolean acceptNextAlert = true;
  private StringBuffer verificationErrors = new StringBuffer();

  @Before
  public void setUp() throws Exception {
    //driver = new FirefoxDriver();
    driver = new HtmlUnitDriver();
    //driver = new ChromeDriver();
    baseUrl = "http://localhost:8080/";
    driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
  }

  @Test
  public void testProyectoApplicationTests() throws Exception {
    driver.get(baseUrl + "/#!ingredientes");
    driver.findElement(By.xpath("//div[@id='ROOT-2521314']/div/div[2]/div/div/div/div/div[8]/div")).click();
    driver.findElement(By.xpath("//div[@id='ROOT-2521314']/div/div[2]/div/div[2]/div/div[2]/div/div[2]/div/div/div/div[3]/div")).click();
    driver.findElement(By.xpath("//div[5]/div/div/div/input")).clear();
    driver.findElement(By.xpath("//div[5]/div/div/div/input")).sendKeys("jamon");
    driver.findElement(By.xpath("//div[3]/div/input")).clear();
    driver.findElement(By.xpath("//div[3]/div/input")).sendKeys("salado");
  }

  @After
  public void tearDown() throws Exception {
    driver.quit();
    String verificationErrorString = verificationErrors.toString();
    if (!"".equals(verificationErrorString)) {
      fail(verificationErrorString);
    }
  }

  private boolean isElementPresent(By by) {
    try {
      driver.findElement(by);
      return true;
    } catch (NoSuchElementException e) {
      return false;
    }
  }

  private boolean isAlertPresent() {
    try {
      driver.switchTo().alert();
      return true;
    } catch (NoAlertPresentException e) {
      return false;
    }
  }

  private String closeAlertAndGetItsText() {
    try {
      Alert alert = driver.switchTo().alert();
      String alertText = alert.getText();
      if (acceptNextAlert) {
        alert.accept();
      } else {
        alert.dismiss();
      }
      return alertText;
    } finally {
      acceptNextAlert = true;
    }
  }
}
**/
