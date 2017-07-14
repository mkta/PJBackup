package com.example.tests;

import java.util.regex.Pattern;
import java.util.concurrent.TimeUnit;
import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

public class 1 {
  private WebDriver driver;
  private String baseUrl;
  private boolean acceptNextAlert = true;
  private StringBuffer verificationErrors = new StringBuffer();

  @Before
  public void setUp() throws Exception {
    driver = new FirefoxDriver();
    baseUrl = "http://pimtest.brandpath.com:8080/";
    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
  }

  @Test
  public void test1() throws Exception {
    String casuta = "1";
    // ERROR: Caught exception [unknown command [label]]
    Number casute = driver.findElements(By.xpath("html/body/div[3]/div/div/div/div/div/div[2]/div[3]/div[2]/div[2]/div[*]")).size();
    String a = driver.findElement(By.xpath("html/body/div[3]/div/div/div/div/div/div[2]/div[3]/div[2]/div[2]/div[2]")).getAttribute("class");
    System.out.println(a);
    // ERROR: Caught exception [unknown command [gotoIf]]
    // ERROR: Caught exception [unknown command [label]]
    // ERROR: Caught exception [ERROR: Unsupported command [getEval |  | ]]
    // ERROR: Caught exception [unknown command [gotoIf]]
    // ERROR: Caught exception [unknown command [goto]]
    // ERROR: Caught exception [unknown command [label]]
    System.out.println(casuta);
    System.out.println(casute);
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
