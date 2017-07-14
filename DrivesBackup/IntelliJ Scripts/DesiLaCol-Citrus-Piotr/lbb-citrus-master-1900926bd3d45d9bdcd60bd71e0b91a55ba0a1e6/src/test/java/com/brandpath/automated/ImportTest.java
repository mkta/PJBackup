package com.brandpath.automated;

import com.brandpath.utils.Preparation;
import com.brandpath.utils.ResourceReader;
import com.brandpath.utils.TokenHelper;
import com.consol.citrus.annotations.CitrusTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.testng.annotations.Test;

import org.apache.commons.lang.math.RandomUtils;

import javax.sql.DataSource;

/**
 * Created by Artur on 24.03.2017.
 */
@Test(enabled = true)
public class ImportTest extends TokenHelper {
    @Autowired
    @Qualifier("pimDS")
    private DataSource dataSource;


    @CitrusTest
    public void checkStatus() {

       pimToken();

        soap().client("pimImportClient")
                .send()
                .header("citrus_http_Authorization","Bearer ${soapToken}")
                .payload(       "<sei:checkStatus xmlns:sei=\"http://sei.service.pim.brandpath.com/\">" +
                        "         <loadStatus security=\"${soapToken}\" id=\"183\" client=\"DESILACOL\"/>" +
                        "      </sei:checkStatus>");

    }

    @CitrusTest
    public void createTwoUpdatesNowNoCompress_invalidXML_missingCurrencies() {

        pimToken();

        String xml = ResourceReader.resourceFileAsString("import/xml/createTwoUpdatesNowNoCompress_invalidXML_missingCurrencies.xml");

        soap().client("pimImportClient")
                .send()
                .header("citrus_http_Authorization","Bearer ${soapToken}")
                .payload(xml);


        soap().client("pimImportClient")
                .receive()
                .schemaValidation(false)
                .extractFromPayload("//return/status","checkStatus")
                .validate("//return/status","CONTENT_INVALID")
        ;

        echo("PIM Check Import status is: ${checkStatus}");
    }

    @CitrusTest
    public void createTwoUpdatesNowNoCompress_invalidXML_negativePrices() {

        String randPricePrefix = (RandomUtils.nextInt(90)  + 10) + ""; /* from 10 to 99 */
        variable("randPricePrefix", randPricePrefix);

        pimToken();


        String xml = ResourceReader.resourceFileAsString("import/xml/createTwoUpdatesNowNoCompress_invalidXML_negativePrices.xml");

        soap().client("pimImportClient")
                .send()
                .header("citrus_http_Authorization","Bearer ${soapToken}")
                .payload(xml);


        soap().client("pimImportClient")
                .receive()
                .schemaValidation(false)
                .extractFromPayload("//return/status","checkStatus")
                .extractFromPayload("//return/@id","loadId")
                .validate("//return/status","ACCEPTED")
        ;
        echo("PIM Check Import status is: ${checkStatus} for loadId: ${loadId}" );

        sleep(120.0);

        // check status via soap WS
        soap().client("pimImportClient")
                .send()
                .header("citrus_http_Authorization","Bearer ${soapToken}")
                .payload(       "<sei:checkStatus xmlns:sei=\"http://sei.service.pim.brandpath.com/\">" +
                        "         <loadStatus security=\"${soapToken}\" id=\"${loadId}\" client=\"DESILACOL\"/>" +
                        "      </sei:checkStatus>");


        soap().client("pimImportClient")
                .receive()
                .schemaValidation(false)
                .extractFromPayload("//return/status","checkStatus2")
                .validate("//return/status","CONTENT_INVALID");


        echo("PIM Check Final Import status is: ${checkStatus2}");
    }

    @CitrusTest
    public void createTwoUpdatesNowNoCompress_invalidXML_invalidOfferingCode() {

        pimToken();

        String xml = ResourceReader.resourceFileAsString("import/xml/createTwoUpdatesNowNoCompress_invalidXML_invalidOfferingCode.xml");

        soap().client("pimImportClient")
                .send()
                .header("citrus_http_Authorization","Bearer ${soapToken}")
                .payload(xml);


        soap().client("pimImportClient")
                .receive()
                .schemaValidation(false)
                .extractFromPayload("//return/status","checkStatus")
                .validate("//return/status","CONTENT_INVALID")
        ;

        echo("PIM Check Import status is: ${checkStatus}");
    }

    @CitrusTest
    public void createTwoUpdatesNowNoCompress_samePrices() {

        pimToken();

        String xml = ResourceReader.resourceFileAsString("import/xml/createTwoUpdatesNowNoCompress_samePrices.xml");

        soap().client("pimImportClient")
                .send()
                .header("citrus_http_Authorization","Bearer ${soapToken}")
                .payload(xml);


        soap().client("pimImportClient")
                .receive()
                .schemaValidation(false)
                .extractFromPayload("//return/status","checkStatus")
                .extractFromPayload("//return/@id","loadId")
                .validate("//return/status","ACCEPTED")
        ;

        echo("PIM Check Import status is: ${checkStatus} for loadId: ${loadId}" );

        sleep(120.0);

        // check status via soap WS
        soap().client("pimImportClient")
                .send()
                .header("citrus_http_Authorization","Bearer ${soapToken}")
                .payload(       "<sei:checkStatus xmlns:sei=\"http://sei.service.pim.brandpath.com/\">" +
                        "         <loadStatus security=\"${soapToken}\" id=\"${loadId}\" client=\"DESILACOL\"/>" +
                        "      </sei:checkStatus>");


        soap().client("pimImportClient")
                .receive()
                .schemaValidation(false)
                .extractFromPayload("//return/status","checkStatus2")
                .validate("//return/status","CONTENT_INVALID");


        echo("PIM Check Final Import status is: ${checkStatus2}");
    }

    @CitrusTest
    public void createTwoUpdatesNowNoCompress_addPriceToNewSite() {

        pimToken();

        String xml = ResourceReader.resourceFileAsString("import/xml/createTwoUpdatesNowNoCompress_addPriceToNewSite.xml");

        soap().client("pimImportClient")
                .send()
                .header("citrus_http_Authorization","Bearer ${soapToken}")
                .payload(xml);


        soap().client("pimImportClient")
                .receive()
                .schemaValidation(false)
                .extractFromPayload("//return/status","checkStatus")
                .extractFromPayload("//return/@id","loadId")
                .validate("//return/status","ACCEPTED")
        ;

        echo("PIM Check Import status is: ${checkStatus} for loadId: ${loadId}" );

        sleep(120.0);

        // check status via soap WS
        soap().client("pimImportClient")
                .send()
                .header("citrus_http_Authorization","Bearer ${soapToken}")
                .payload(       "<sei:checkStatus xmlns:sei=\"http://sei.service.pim.brandpath.com/\">" +
                        "         <loadStatus security=\"${soapToken}\" id=\"${loadId}\" client=\"DESILACOL\"/>" +
                        "      </sei:checkStatus>");


        soap().client("pimImportClient")
                .receive()
                .schemaValidation(false)
                .extractFromPayload("//return/status","checkStatus2")
                .validate("//return/status","COMPLETED");


        echo("PIM Check Final Import status is: ${checkStatus2}");
    }

    @CitrusTest
    public void createTwoUpdatesNowNoCompress_differentPriceList() {

        String randPricePrefix = (RandomUtils.nextInt(90)  + 10) + ""; /* from 10 to 99 */
        variable("randPricePrefix", randPricePrefix);

        pimToken();


        String xml = ResourceReader.resourceFileAsString("import/xml/createTwoUpdatesNowNoCompress_differentPriceList.xml");

        soap().client("pimImportClient")
                .send()
                .header("citrus_http_Authorization","Bearer ${soapToken}")
                .payload(xml);


        soap().client("pimImportClient")
                .receive()
                .schemaValidation(false)
                .extractFromPayload("//return/status","checkStatus")
                .extractFromPayload("//return/@id","loadId")
                .validate("//return/status","ACCEPTED")
        ;
        echo("PIM Check Import status is: ${checkStatus} for loadId: ${loadId}" );

        sleep(120.0);

        // check status via soap WS
        soap().client("pimImportClient")
                .send()
                .header("citrus_http_Authorization","Bearer ${soapToken}")
                .payload(       "<sei:checkStatus xmlns:sei=\"http://sei.service.pim.brandpath.com/\">" +
                        "         <loadStatus security=\"${soapToken}\" id=\"${loadId}\" client=\"DESILACOL\"/>" +
                        "      </sei:checkStatus>");


        soap().client("pimImportClient")
                .receive()
                .schemaValidation(false)
                .extractFromPayload("//return/status","checkStatus2")
                .validate("//return/status","COMPLETED");


        echo("PIM Check Final Import status is: ${checkStatus2}");
    }


    @CitrusTest
            public void checkDBOfferings(){
        query(dataSource)
                .statement("SELECT onStock DIV 1 as ONSTOCK, onOrder DIV 1 as ONORDER, stockAvailable DIV 1 as STOCKAVAILABLE"
                        + "  FROM stock_stock ;")
                .extract("ONSTOCK","onStockBefore")
                .extract("ONORDER","onOrderBefore")
                .extract("STOCKAVAILABLE","stockAvailableBefore")
        //find the stock available number above
        ;
    }

    @CitrusTest
    public void createProduct() {
            pimToken();

            String xml = ResourceReader.resourceFileAsString("import/createNewProduct.xml");

            soap().client("pimImportClient")
                    .send()
                    .header("citrus_http_Authorization", "Bearer ${soapToken}")
                    .payload(xml);


            soap().client("pimImportClient")
                    .receive()
                    .schemaValidation(false)
                    .extractFromPayload("//return/status", "checkStatus")
                    .validate("//return/status", "ACCEPTED")
            ;
        }
    }
