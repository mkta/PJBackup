package Desi_FE_LOOP_Script;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static Desi_FE_LOOP_Script.intConf.site;
import static Desi_FE_LOOP_Script.intXpath.*;

/**
 * Created by catalinf on 04-Apr-17.
 * ////// Assertion classes \\\\\\\
 * Modify this if assertion problems
 */
class Assert extends _Desi_FE_LOOP_Test {


    static void aHP(WebDriver driver, String country) {

        //store url

        String oUrl = driver.getCurrentUrl();

        //store title

        String title = driver.getTitle();

        //verify url

        if (oUrl.equals(site + country)) {
            System.out.println(site + " Initiated");
        } else {
            System.out.println("Sie failed");
        }

        //Echo site name

        String navTitle = driver.findElement(By.xpath(xnavTitle)).getText();
        System.out.println(" Site name is : " + title);
        System.out.println("Expected Site name is : " + navTitle);

    }

    static void aPLP(WebDriver driver, String plpURL) {

        //verify PLP url
        String oplpUrl = driver.getCurrentUrl();

        if (oplpUrl.equals(plpURL)) {
            System.out.println("PLP Opened");
        } else {
            System.out.println("PLP failed");
        }

        String navListing = driver.findElement(By.xpath(xnavListing)).getText();
        System.out.println("Navigation crumb listing :" + navListing);
        String bcListing = driver.findElement(By.xpath(xbcListing)).getText();
        System.out.println("Bread crumb listing :" + bcListing);

        if (bcListing.equals(navListing)) {
            System.out.println("PLP Correct");
        } else {
            System.out.println("PLP Wrong");
        }

    }

    static void aPDP() {
    }
}
