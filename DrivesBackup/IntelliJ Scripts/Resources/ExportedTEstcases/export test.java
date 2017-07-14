package com.example.tests;

import java.util.regex.Pattern;
import java.util.concurrent.TimeUnit;
import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

public class ExportTest {
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
  public void testExport() throws Exception {
    // ERROR: Caught exception [ERROR: Unsupported command [getEval |  | ]]
    // ERROR: Caught exception [ERROR: Unsupported command [getEval |  | ]]
    String basketProductBTFF = "############ScriptRun- " + webScriptDate;
    String spriceBTFF = "############ScriptRun- " + webScriptDate;
    String vouchermsgBTFF = "############ScriptRun- " + webScriptDate;
    String fpriceBTFF = "############ScriptRun- " + webScriptDate;
    String paypallpriceBTFF = "############ScriptRun- " + webScriptDate;
    String OrderNumberBTFF = "############ScriptRun- " + webScriptDate;
    String ordercreatedateBTFF = "############ScriptRun- " + webScriptDate;
    String vouchdisBTFF = "############ScriptRun- " + webScriptDate;
    String WebScriptRedirectToPayPallTimeBTFF = "############ScriptRun- " + webScriptDate;
    String WebScriptPayPallFinishTimeBTFF = "############ScriptRun- " + webScriptDate;
    String otonBTFF = "############ScriptRun- " + webScriptDate;
    String tsBTFF = "############ScriptRun- " + webScriptDate;
    // ERROR: Caught exception [ERROR: Unsupported command [deleteAllVisibleCookies |  | ]]
    driver.get("https://sabtst.brandpath.net/");
    // Clear Cookies
    // ERROR: Caught exception [ERROR: Unsupported command [runScript | System.import('config').then(function(module){     var config = module.AppConfig,         container = document.body.aurelia.container,         AppConfig = container.get(config);         AppConfig.clearSession(); }); | ]]
    // Go to TV
    driver.findElement(By.xpath("html/body/div[1]/header/nav/div[3]/div/div[3]/ul[1]/li[2]/div[1]/a")).click();
    driver.findElement(By.id("sizeAll")).click();
    // Open QuickView
    driver.findElement(By.linkText("Samsung Galaxy S6 Testing Test")).click();
    // Add to basket
    driver.findElement(By.xpath("html/body/div[1]/compose[1]/div/div/div[2]/div/div/div[2]/div[1]/div/div[2]/button")).click();
    // Go to basket
    driver.findElement(By.linkText("See basket")).click();
    // Paypall
    driver.findElement(By.id("transfer")).click();
    // AddVoucher
    boolean vouchaddBTFF = isElementPresent(By.id("voucher"));
    System.out.println(vouchaddBTFF);
    // ERROR: Caught exception [unknown command [gotoIf]]
    // ERROR: Caught exception [unknown command [label]]
    driver.findElement(By.id("voucher")).clear();
    driver.findElement(By.id("voucher")).sendKeys("geani-script");
    driver.findElement(By.xpath("html/body/div[1]/router-view/section/div/div/div[1]/div[2]/table/tbody[4]/tr[1]/td[1]/form/button")).click();
    // ERROR: Caught exception [unknown command [label]]
    String vouchermsgBTFF = driver.findElement(By.xpath("html/body/div[1]/router-view/section/div/div/div[1]/div[2]/table/tbody[4]/tr[1]/td[1]/div/p[2]/span")).getText();
    System.out.println(vouchermsgBTFF);
    boolean vouchdiBTFF = isElementPresent(By.xpath("html/body/div[1]/router-view/section/div/div/div[1]/div[2]/table/tbody[4]/tr[1]/td[3]/div/span"));
    System.out.println(vouchdiBTFF);
    // ERROR: Caught exception [unknown command [gotoIf]]
    String vouchdisBTFF = driver.findElement(By.xpath("html/body/div[1]/router-view/section/div/div/div[1]/div[2]/table/tbody[4]/tr[1]/td[3]/div/span")).getText();
    System.out.println(vouchdisBTFF);
    // StoreStuff
    // ERROR: Caught exception [unknown command [label]]
    String spriceBTFF = driver.findElement(By.xpath("html/body/div[1]/router-view/section/div/div/div[1]/div[2]/table/tbody[1]/tr/td[6]/div/span")).getText();
    System.out.println(spriceBTFF);
    String basketProductBTFF = driver.findElement(By.xpath("html/body/div[1]/router-view/section/div/div/div[1]/div[2]/table/tbody[1]/tr/td[2]/span/a")).getText();
    System.out.println(basketProductBTFF);
    String fpriceBTFF = driver.findElement(By.xpath("html/body/div[1]/router-view/section/div/div/div[1]/div[2]/table/tbody[5]/tr/td[2]/p/span")).getText();
    System.out.println(fpriceBTFF);
    // Go to chekcout
    driver.findElement(By.xpath("html/body/div[1]/router-view/section/div/div/div[1]/div[3]/div/form/div[2]/button")).click();
    // Add billing details
    new Select(driver.findElement(By.id("bill_title"))).selectByVisibleText("Mr");
    driver.findElement(By.cssSelector("option[value=\"Mr\"]")).click();
    driver.findElement(By.id("bill_first_name")).clear();
    driver.findElement(By.id("bill_first_name")).sendKeys("Geani test first name");
    driver.findElement(By.id("bill_second_name")).clear();
    driver.findElement(By.id("bill_second_name")).sendKeys("test last name script");
    driver.findElement(By.id("bill_address_1")).clear();
    driver.findElement(By.id("bill_address_1")).sendKeys("test addes 1");
    driver.findElement(By.id("bill_address_2")).clear();
    driver.findElement(By.id("bill_address_2")).sendKeys("test addes 2");
    driver.findElement(By.id("bill_city")).clear();
    driver.findElement(By.id("bill_city")).sendKeys("test city");
    driver.findElement(By.id("bill_state")).clear();
    driver.findElement(By.id("bill_state")).sendKeys("test county");
    driver.findElement(By.id("bill_zipcode")).clear();
    driver.findElement(By.id("bill_zipcode")).sendKeys("DH98 1BT");
    driver.findElement(By.id("bill_email")).clear();
    driver.findElement(By.id("bill_email")).sendKeys("catalin.fleancu@brandpath.com");
    driver.findElement(By.id("bill_email_confirm")).clear();
    driver.findElement(By.id("bill_email_confirm")).sendKeys("catalin.fleancu@brandpath.com");
    driver.findElement(By.id("bill_phone")).clear();
    driver.findElement(By.id("bill_phone")).sendKeys("0766831683683");
    driver.findElement(By.id("terms")).click();
    // Proceed to BankTransfer payment (checkout)
    // ERROR: Caught exception [ERROR: Unsupported command [getEval |  | ]]
    driver.findElement(By.xpath("html/body/div[1]/router-view/section/div/div/div[1]/form/div/div[3]/div[3]/button[1]")).click();
    // Confirm Payment
    // ERROR: Caught exception [ERROR: Unsupported command [getEval |  | ]]
    driver.findElement(By.xpath("html/body/div[1]/router-view/section/div/div/div[1]/form/div/div[3]/div[2]/button[1]")).click();
    // ThankYouPage
    // Store Full order text
    String fullText = driver.findElement(By.xpath("//*[@id='complete-basket']/div/div/h1[2]")).getText();
    // Remove First Part
    String section1 = "1";
    // ERROR: Caught exception [ERROR: Unsupported command [getEval |  | ]]
    // Remove Second Part And Store Order Number
    String section2 = "0";
    // ERROR: Caught exception [ERROR: Unsupported command [getEval |  | ]]
    System.out.println(OrderNumberBTFF);
    // ERROR: Caught exception [ERROR: Unsupported command [getEval |  | ]]
    System.out.println(ordercreatedateBTFF);
    // Othder tacking Page
    driver.findElement(By.xpath("html/body/div[1]/router-view/section/div/div/p[4]/a")).click();
    String otonBTFF = driver.findElement(By.xpath("html/body/div[1]/router-view/div[2]/h2/span")).getText();
    System.out.println(otonBTFF);
    try {
      assertEquals(OrderNumberBTFF, driver.findElement(By.xpath("html/body/div[1]/router-view/div[2]/h2/span")).getText());
    } catch (Error e) {
      verificationErrors.append(e.toString());
    }
    String tsBTFF = driver.findElement(By.xpath("html/body/div[1]/router-view/div[2]/table/tbody/tr/td[3]")).getText();
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
