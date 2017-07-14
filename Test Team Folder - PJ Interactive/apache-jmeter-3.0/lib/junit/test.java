package com.example.tests;

import com.thoughtworks.selenium.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.regex.Pattern;

public class WebPArt-law {
	private Selenium selenium;

	@Before
	public void setUp() throws Exception {
		selenium = new DefaultSelenium("localhost", 4444, "*chrome", "http://pimtest.brandpath.com:8080/");
		selenium.start();
	}

	@Test
	public void testWebPArt-law() throws Exception {
		String WebScriptStartTimelaw = selenium.getEval("new Date().getFullYear() + \"-\" + (new Date().getMonth() +1) + \"-\" + new Date().getDate().toString() + \"\"+ \"_\"+new Date().getHours()+\":\" + new Date().getMinutes() + \":\" + new Date().getSeconds()");
		String webScriptDate = selenium.getEval("new Date().getFullYear() + \"-\" + (new Date().getMonth() +1) + \"-\" + new Date().getDate().toString() + \"\"+ \"_\"+new Date().getHours()+\"\" + new Date().getMinutes() + \"\" + new Date().getSeconds()");
		String basketProductlaw = "############ScriptRun- " + webScriptDate;
		String spricelaw = "############ScriptRun- " + webScriptDate;
		String vouchermsglaw = "############ScriptRun- " + webScriptDate;
		String fpricelaw = "############ScriptRun- " + webScriptDate;
		String paypallpricelaw = "############ScriptRun- " + webScriptDate;
		String OrderNumberlaw = "############ScriptRun- " + webScriptDate;
		String ordercreatedatelaw = "############ScriptRun- " + webScriptDate;
		String vouchdislaw = "############ScriptRun- " + webScriptDate;
		selenium.deleteAllVisibleCookies();
		selenium.open("http://rodc-opt-tst-31.central.local/");
		// Clear Cookies
		Thread.sleep(10000);
		selenium.runScript("System.import('config').then(function(module){     var config = module.AppConfig,         container = document.body.aurelia.container,         AppConfig = container.get(config);         AppConfig.clearSession(); });");
		Thread.sleep(10000);
		// Go to Mobiles & Wearables
		selenium.click("xpath=html/body/header/nav/div[3]/div/div[3]/ul[1]/li[2]/div[1]/a");
		Thread.sleep(5000);
		selenium.click("id=sizeAll");
		// Open lastest created offering PDP
		Thread.sleep(5000);
		// Open Main
		selenium.click("link=Galaxy S6 Blue 128 GB");
		// selenium.label("skip");
		Thread.sleep(5000);
		// Add to basket
		selenium.click("xpath=(//button[@type='button'])[7]");
		Thread.sleep(10000);
		// Go to basket
		selenium.click("link=See basket");
		// Paypall
		Thread.sleep(3000);
		selenium.click("id=terms");
		selenium.click("id=credit");
		// AddVoucher
		selenium.type("id=voucher", "SV-9ZZE-NA23");
		selenium.click("xpath=html/body/router-view/router-view/section[1]/div/div/div[2]/div[1]/table/tbody[4]/tr/td[1]/form/button");
		Thread.sleep(7000);
		String vouchermsglaw = selenium.getText("xpath=html/body/router-view/router-view/section[1]/div/div/div[2]/div[1]/table/tbody[4]/tr/td[1]/div/p[2]");
		System.out.println(vouchermsglaw);
		boolean vouchdilaw = selenium.isElementPresent("xpath=html/body/router-view/router-view/section[1]/div/div/div[2]/div[1]/table/tbody[4]/tr/td[3]/div/span");
		System.out.println(vouchdilaw);
		// selenium.gotoIf("${vouchdilaw}==false", "novlaw");
		String vouchdislaw = selenium.getText("xpath=html/body/router-view/router-view/section[1]/div/div/div[2]/div[1]/table/tbody[4]/tr/td[3]/div/span");
		System.out.println(vouchdislaw);
		// StoreStuff
		// selenium.label("novlaw");
		String spricelaw = selenium.getText("xpath=html/body/router-view/router-view/section[1]/div/div/div[2]/div[1]/table/tbody[1]/tr/td[5]/div/span");
		System.out.println(spricelaw);
		String basketProductlaw = selenium.getText("xpath=html/body/router-view/router-view/section[1]/div/div/div[2]/div[1]/table/tbody[1]/tr/td[2]/span/a");
		System.out.println(basketProductlaw);
		String fpricelaw = selenium.getText("xpath=html/body/router-view/router-view/section[1]/div/div/div[2]/div[1]/table/tbody[5]/tr/td[2]/p/span");
		System.out.println(fpricelaw);
		// Go to chekcout
		selenium.click("xpath=html/body/router-view/router-view/section[1]/div/div/div[2]/div[2]/div/form/div[3]/button");
		Thread.sleep(3000);
		// Guest login
		selenium.click("link=CONTINUE AS A GUEST");
		Thread.sleep(3000);
		// Add billing details
		selenium.select("id=bill_title", "label=Mr");
		selenium.click("css=option[value=\"Mr\"]");
		selenium.type("id=bill_first_name", "Geani test first name");
		selenium.type("id=bill_second_name", "test last name script");
		selenium.type("id=bill_address_1", "test addes 1");
		selenium.type("id=bill_address_2", "test addes 2");
		selenium.type("id=bill_city", "test city");
		selenium.type("id=bill_state", "test county");
		selenium.type("id=bill_zipcode", "DH98 1BT");
		selenium.type("id=bill_email", "catalin.fleancu@pj-interactive.ro");
		selenium.type("id=bill_email_confirm", "catalin.fleancu@pj-interactive.ro");
		selenium.type("id=bill_phone", "0766831683683");
		// Proceed to WorldPay
		selenium.click("xpath=html/body/router-view/router-view/section/div/div/div[1]/form/div/div[3]/div[2]/button[1]");
		// WorldPay Credit Info
		Thread.sleep(15000);
		selenium.type("id=cardNumber", "4444333322221111");
		selenium.type("id=cardholderName", "Geani Script WorldPay");
		selenium.select("id=expiryMonth", "label=01");
		selenium.select("id=expiryYear", "label=2018");
		selenium.type("id=securityCode", "123");
		selenium.click("id=submitButton");
		Thread.sleep(20000);
		// ThankYouPage
		// Store Full order text
		String fullText = selenium.getText("xpath=//*[@id='complete-basket']/div/div/h1[2]");
		// Remove First Part
		String section1 = "1";
		String rFirstPart = selenium.getEval("'" + fullText + "'.split('#')['" + section1 + "']");
		// Remove Second Part And Store Order Number
		Thread.sleep(5000);
		String section2 = "0";
		String OrderNumberlaw = selenium.getEval("'" + rFirstPart + "'.split(' s')['" + section2 + "']");
		System.out.println(OrderNumberlaw);
		String ordercreatedatelaw = selenium.getEval("new Date().getFullYear() + \"-\" + (new Date().getMonth() +1) + \"-\" + new Date().getDate().toString() + \"\"+ \"_\"+new Date().getHours()+\"\" + new Date().getMinutes() + \"\" + new Date().getSeconds()");
		System.out.println(ordercreatedatelaw);
	}

	@After
	public void tearDown() throws Exception {
		selenium.stop();
	}
}
