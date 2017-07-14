package com.brandpath.lbb.tests.pim;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.sql.DataSource;

import com.brandpath.utils.Preparation;
import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.message.MessageType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.testng.annotations.Test;

/**
 * Created by gregory on 01/09/16.
 */
@Test(enabled = false)
public class SomWsTest extends Preparation {

	@Autowired
	@Qualifier("pimDS")
	private DataSource dataSource;

	@CitrusTest
	public void stockReservationAndConfirmCalculations() {

		variable("my.productcode", "SM-G930FZDABTU");

		//verify now it's in DB for sure :)
		query(dataSource)
				.statement("SELECT onStock DIV 1 as ONSTOCK, onOrder DIV 1 as ONORDER, stockAvailable DIV 1 as STOCKAVAILABLE"
						+ "  FROM stock_stock where productCode = '${my.productcode}';")
				.extract("ONSTOCK","onStockBefore")
				.extract("ONORDER","onOrderBefore")
				.extract("STOCKAVAILABLE","stockAvailableBefore")


		//find the stock available number above
		;

		DateFormat df = new SimpleDateFormat("ddMMyyyyHHmmss");
		String orderReference = "CLC_"+df.format(new Date());

		variable("my.quantity","1");

		soap().client("somTokenClient")
				.send()
				.header("citrus_http_userName","som-ws")
				.header("citrus_http_password","som12345")
				.payload("<getToken xmlns=\"http://sei.ws.som.brandpath.com/\">\n" + "</getToken>");
		//


		soap().client("somTokenClient")
				.receive()
				.schemaValidation(false)
				.extractFromPayload("//return/token","soapToken")
				.timeout(5000);

		echo("Soap Token is: ${soapToken}");


		soap().client("somStockClient")
				.send()
				.header("citrus_http_Authorization","Bearer ${soapToken}")
				.payload(       "<sei:reserveStock xmlns:sei=\"http://sei.ws.som.brandpath.com/\">\n"
						+ "         <sessionId>dummySession</sessionId>\n"
						+ "         <orderReference>"+orderReference+"</orderReference>\n"
						+ "         <accountId>1</accountId>\n"
						+ "         <reservationTypeTag>aTest</reservationTypeTag>\n" + "         <offerings>\n"
						+ "            <offering offeringCode=\"offering1\" packageId=\"10\" quantity=\"1\">\n"
						+ "               <product productCode=\"${my.productcode}\" quantity=\"${my.quantity}\"/>\n"
						+ "            </offering>\n"
						+ "        </offerings>\n"
						+ "      </sei:reserveStock>");


		soap().client("somStockClient")
				.receive()
				.schemaValidation(false)
				.validate("//reserveStockResult","true")
		;

		//reserve one unit, onStock=nochange, onOrder=+1, stockAvailable = -1


		query(dataSource)
				.statement("SELECT onStock DIV 1 as ONSTOCK, onOrder DIV 1 as ONORDER, stockAvailable DIV 1 as STOCKAVAILABLE"
						+ "  FROM stock_stock where productCode = '${my.productcode}';")
				.extract("ONSTOCK","onStockAfter")
				.extract("ONORDER","onOrderAfter")
				.extract("STOCKAVAILABLE","stockAvailableAfter")
				.validate("ONSTOCK","${onStockBefore}")
				.validate("STOCKAVAILABLE","citrus:round(citrus:sum('${stockAvailableBefore}', '-${my.quantity}'))") //negative value there
				.validate("ONORDER","citrus:round(citrus:sum('${onOrderBefore}', '${my.quantity}'))")
		;
		//so it has been verified that onStock (hard stock) did not change
		//and stock available decreased by 1
		//and onStock (so reserved well, increased by 1 (var my.quantity)


		//now we confirm the reservation

		//confirm
		soap().client("somStockClient")
				.send()
				.header("citrus_http_Authorization","Bearer ${soapToken}")
				.payload(       "<sei:confirmStock xmlns:sei=\"http://sei.ws.som.brandpath.com/\">\n"
						+ "         <orderReference>"+orderReference+"</orderReference>\n"
						+ "      </sei:confirmStock>");


		soap().client("somStockClient")
				.receive()
				.schemaValidation(false)
				.validate("//confirmStockResult","true")
		;


		//verify everything go back as before reservation:
		query(dataSource)
				.statement("SELECT onStock DIV 1 as ONSTOCK, onOrder DIV 1 as ONORDER, stockAvailable DIV 1 as STOCKAVAILABLE"
						+ "  FROM stock_stock where productCode = '${my.productcode}';")
				.validate("ONSTOCK","citrus:round(citrus:sum('${onStockAfter}', '-${my.quantity}'))")
				.validate("STOCKAVAILABLE","${stockAvailableAfter}")
				.validate("ONORDER","${onOrderBefore}")
		;

	}


	/**
	 * The idea of this test it to first find out the current stock in the SOM DB
	 * then do reservation, check if stockAvalable decreased, and onStockincreased;
	 * then cancel it
	 * then look again into db to see whether the number come back to previous state as before reservation
	 */
	@CitrusTest
	public void stockReservationAndUnreserverCalculations() {

		variable("my.productcode", "SM-G930FZDABTU");

		//verify now it's in DB for sure :)
		query(dataSource)
				.statement("SELECT onStock DIV 1 as ONSTOCK, onOrder DIV 1 as ONORDER, stockAvailable DIV 1 as STOCKAVAILABLE"
						+ "  FROM stock_stock where productCode = '${my.productcode}';")
				.extract("ONSTOCK","onStockBefore")
				.extract("ONORDER","onOrderBefore")
				.extract("STOCKAVAILABLE","stockAvailableBefore")


		//find the stock available number above
		;


		DateFormat df = new SimpleDateFormat("ddMMyyyyHHmmss");
		String orderReference = "CLU_"+df.format(new Date());

		variable("my.quantity","1");

		soap().client("somTokenClient")
				.send()
				.header("citrus_http_userName","som-ws")
				.header("citrus_http_password","som12345")
				.payload("<getToken xmlns=\"http://sei.ws.som.brandpath.com/\">\n" + "</getToken>");
		//


		soap().client("somTokenClient")
				.receive()
				.schemaValidation(false)
				.extractFromPayload("//return/token","soapToken")
				.timeout(5000);

		echo("Soap Token is: ${soapToken}");


		soap().client("somStockClient")
				.send()
				.header("citrus_http_Authorization","Bearer ${soapToken}")
				.payload(       "<sei:reserveStock xmlns:sei=\"http://sei.ws.som.brandpath.com/\">\n"
						+ "         <sessionId>dummySession</sessionId>\n"
						+ "         <orderReference>"+orderReference+"</orderReference>\n"
						+ "         <accountId>1</accountId>\n"
						+ "         <reservationTypeTag>aTest</reservationTypeTag>\n" + "         <offerings>\n"
						+ "            <offering offeringCode=\"offering1\" packageId=\"10\" quantity=\"1\">\n"
						+ "               <product productCode=\"${my.productcode}\" quantity=\"${my.quantity}\"/>\n"
						+ "            </offering>\n"
						+ "        </offerings>\n"
						+ "      </sei:reserveStock>");


		soap().client("somStockClient")
				.receive()
				.schemaValidation(false)
				.validate("//reserveStockResult","true")
		;

		//reserve one unit, onStock=nochange, onOrder=+1, stockAvailable = -1


		query(dataSource)
				.statement("SELECT onStock DIV 1 as ONSTOCK, onOrder DIV 1 as ONORDER, stockAvailable DIV 1 as STOCKAVAILABLE"
						+ "  FROM stock_stock where productCode = '${my.productcode}';")
				.extract("ONSTOCK","onStockAfter")
				.extract("ONORDER","onOrderAfter")
				.extract("STOCKAVAILABLE","stockAvailableAfter")
				.validate("ONSTOCK","${onStockBefore}")
				.validate("STOCKAVAILABLE","citrus:round(citrus:sum('${stockAvailableBefore}', '-${my.quantity}'))") //negative value there
				.validate("ONORDER","citrus:round(citrus:sum('${onOrderBefore}', '${my.quantity}'))")
		;
		//so it has been verified that onStock (hard stock) did not change
		//and stock available decreased by 1
		//and onStock (so reserved well, increased by 1 (var my.quantity)


		//now we cancel the reservation so to numbers should go back as at the beginning of test;

		//unreserve
		soap().client("somStockClient")
				.send()
				.header("citrus_http_Authorization","Bearer ${soapToken}")
				.payload(       "<sei:unreserveStock xmlns:sei=\"http://sei.ws.som.brandpath.com/\">\n"
						+ "         <orderReference>"+orderReference+"</orderReference>\n"
						+ "      </sei:unreserveStock>");


		soap().client("somStockClient")
				.receive()
				.schemaValidation(false)
				.validate("//unreserveStockResult","true")
		;


		//verify everything go back as before reservation:
		query(dataSource)
				.statement("SELECT onStock DIV 1 as ONSTOCK, onOrder DIV 1 as ONORDER, stockAvailable DIV 1 as STOCKAVAILABLE"
						+ "  FROM stock_stock where productCode = '${my.productcode}';")
				.validate("ONSTOCK","${onStockBefore}")
				.validate("STOCKAVAILABLE","${stockAvailableBefore}") //negative value there
				.validate("ONORDER","${onOrderBefore}")
		;	}

	@CitrusTest
	public void doStockReservationAndUnReserve() {
		DateFormat df = new SimpleDateFormat("ddMMyyyyHHmmss");
		String orderReference = "ORD_"+df.format(new Date());

		soap().client("somTokenClient")
				.send()
				.header("citrus_http_userName","som-ws")
				.header("citrus_http_password","som12345")
				.payload("<getToken xmlns=\"http://sei.ws.som.brandpath.com/\">\n" + "</getToken>");
		//


		soap().client("somTokenClient")
				.receive()
				.schemaValidation(false)
				.extractFromPayload("//return/token","soapToken")
				.timeout(5000);

		echo("Soap Token is: ${soapToken}");


		soap().client("somStockClient")
				.send()
				.header("citrus_http_Authorization","Bearer ${soapToken}")
				.payload(       "<sei:reserveStock xmlns:sei=\"http://sei.ws.som.brandpath.com/\">\n"
						+ "         <sessionId>dummySession</sessionId>\n"
						+ "         <orderReference>"+orderReference+"</orderReference>\n"
						+ "         <accountId>1</accountId>\n"
						+ "         <reservationTypeTag>aTest</reservationTypeTag>\n" + "         <offerings>\n"
						+ "            <offering offeringCode=\"offering1\" packageId=\"10\" quantity=\"1\">\n"
						+ "               <product productCode=\"SM-G930FZDABTU\" quantity=\"2\"/>\n"
						+ "            </offering>\n"
//						+ "            <offering offeringCode=\"offering2\" packageId=\"11\" quantity=\"1\">\n"
//						+ "               <product productCode=\"SM-A300FZSUBTU\" quantity=\"1\"/>\n"
//						+ "            </offering>\n" + "
						+  "</offerings>\n"
						+ "      </sei:reserveStock>");


		soap().client("somStockClient")
				.receive()
				.schemaValidation(false)
				.validate("//reserveStockResult","true")
		//		.validate("//message","@assertThat(not(isEmptyString())@")
		;


		//unreserve
		soap().client("somStockClient")
				.send()
				.header("citrus_http_Authorization","Bearer ${soapToken}")
				.payload(       "<sei:unreserveStock xmlns:sei=\"http://sei.ws.som.brandpath.com/\">\n"
						+ "         <orderReference>"+orderReference+"</orderReference>\n"
						+ "      </sei:unreserveStock>");


		soap().client("somStockClient")
				.receive()
				.schemaValidation(false)
				.validate("//unreserveStockResult","true")
		;

	}

	@CitrusTest
	public void doStockReservationAndConfirm() {
		DateFormat df = new SimpleDateFormat("ddMMyyyyHHmmss");
		String orderReference = "ORD_"+df.format(new Date());

		soap().client("somTokenClient")
				.send()
				.header("citrus_http_userName","som-ws")
				.header("citrus_http_password","som12345")
				.payload("<getToken xmlns=\"http://sei.ws.som.brandpath.com/\">\n" + "</getToken>");
		//


		soap().client("somTokenClient")
				.receive()
				.schemaValidation(false)
				.extractFromPayload("//return/token","soapToken")
				.timeout(5000);

		echo("Soap Token is: ${soapToken}");


		soap().client("somStockClient")
				.send()
				.header("citrus_http_Authorization","Bearer ${soapToken}")
				.payload(       "<sei:reserveStock xmlns:sei=\"http://sei.ws.som.brandpath.com/\">\n"
						+ "         <sessionId>dummySession</sessionId>\n"
						+ "         <orderReference>"+orderReference+"</orderReference>\n"
						+ "         <accountId>1</accountId>\n"
						+ "         <reservationTypeTag>aTest</reservationTypeTag>\n"
							+ "<offerings>\n"
						+ "    <offering offeringCode=\"4988f345-e4f9-4c78-bcfb-6327c9b48d31\" packageId=\"8141\" quantity=\"1\">\n"
						+ "        <product productCode=\"SM-G930FZDABTU\" quantity=\"10\"/>\n" + "    </offering>\n"
//						+ "    <offering offeringCode=\"af128050-1434-41a5-8e9b-6e8b197b3606\" packageId=\"8142\" quantity=\"1\">\n"
//						+ "        <product productCode=\"DELUK660220\" quantity=\"1\"/>\n" + "    </offering>\n"
						+ "</offerings>"
						+ "      </sei:reserveStock>");


		soap().client("somStockClient")
				.receive()
				.schemaValidation(false)
				.validate("//reserveStockResult","true")
		//		.validate("//message","@assertThat(not(isEmptyString())@")
		;


		//confirm
		soap().client("somStockClient")
				.send()
				.header("citrus_http_Authorization","Bearer ${soapToken}")
				.payload(       "<sei:confirmStock xmlns:sei=\"http://sei.ws.som.brandpath.com/\">\n"
						+ "         <orderReference>"+orderReference+"</orderReference>\n"
						+ "      </sei:confirmStock>");


		soap().client("somStockClient")
				.receive()
				.schemaValidation(false)
				.validate("//confirmStockResult","true")
		;

	}


	@CitrusTest
	public void startPimManualSync() {
		soap().client("somStockClient")
				.send()
				.payload(       "<sei:syncPim xmlns:sei=\"http://sei.ws.som.brandpath.com/\"/>\n");


		soap().client("somStockClient")
				.receive()
				.schemaValidation(false)
		;
	}

	@CitrusTest
	public void getFullList() {
		soap().client("somStockClient")
				.send()
				.payload(       "<sei:fullList xmlns:sei=\"http://sei.ws.som.brandpath.com/\"/>\n");


		soap().client("somStockClient")
				.receive()
				.schemaValidation(false)
		;
	}

	@CitrusTest
	public void addCode() {
		soap().client("somStockClient")
				.send()
				.payload(       "<sei:addCode xmlns:sei=\"http://sei.ws.som.brandpath.com/\">\n"
						+ "<code>SM-G928FZKABTU</code>"
						+ "      </sei:addCode>");


		soap().client("somStockClient")
				.receive()
				.schemaValidation(false)
		;
	}

	@CitrusTest
	public void removeCode() {
		soap().client("somStockClient")
				.send()
				.payload(       "<sei:removeCode xmlns:sei=\"http://sei.ws.som.brandpath.com/\">\n"
						+ "<code>SM-G928FZKABTU</code>"
						+ "      </sei:removeCode>");


		soap().client("somStockClient")
				.receive()
				.schemaValidation(false)
		;
	}

	@CitrusTest
	public void crossTestWithSabreAndSom() {

		//as in com.brandpath.commons.client.som.AvailabilityFlag
		variable("PRE_ORDER_STOCK","0");
		variable("AVAILABLE_STOCK","1");
		variable("OUT_OF_STOCK","3");

		http().client("sabreHttpClient")
				.send().post("/ws/site/sessiontoken")
				.payload("${default.test.sitepublicId}")
				.header("Host", "${sabrews.host}")
				.header("Origin", "${sabrews.origin}")
				.header("Referer", "${sabrews.referer}")
				.header("sitePublicId", "${default.test.sitepublicId}")
				.contentType("application/json")
				.accept("application/json")
		;

		http().client("sabreHttpClient").receive().response().messageType(MessageType.JSON)
				.extractFromPayload("$.token","token");


		variable("my.offering", "4988f345-e4f9-4c78-bcfb-6327c9b48d31"); //which is EO-RG920BWEGWW, Samsung LEVEL Link Bluetooth Adaptor (White)

		http().client("sabreHttpClient")
				.send().get("ws/offering/${my.offering}")
				.payload("${default.test.sitepublicId}")
				.header("Host", "${sabrews.host}")
				.header("Origin", "${sabrews.origin}")
				.header("Referer", "${sabrews.referer}")
				.header("sitePublicId", "${default.test.sitepublicId}")
				.header("token", "${token}")
				.contentType("application/json")
				.accept("application/json");

		http().client("sabreHttpClient").receive().response().messageType(MessageType.JSON)
				.status(HttpStatus.OK)
				.validate("$.code","SUCCESS")
				.extractFromPayload("$.offering.products[0].code","productCode")
				.extractFromPayload("$.offering.publicId","offeringPublicId")
				.validate("$.offering.availabilityFlag","${AVAILABLE_STOCK}") //ON STOCK
		;

		echo("Product code is: ${productCode} with offering public id: ${offeringPublicId}");


		//establish the stock baseline
		query(dataSource)
				.statement("SELECT onStock DIV 1 as ONSTOCK, onOrder DIV 1 as ONORDER, stockAvailable DIV 1 as STOCKAVAILABLE"
						+ "  FROM stock_stock where productCode = '${productCode}';")
				.extract("ONSTOCK","onStock")
				.extract("ONORDER","onOrder")
				.extract("STOCKAVAILABLE","stockAvailable")
		;


		//now go and do reservation based on this product
		soap().client("somTokenClient")
				.send()
				.header("citrus_http_userName","som-ws")
				.header("citrus_http_password","som12345")
				.payload("<getToken xmlns=\"http://sei.ws.som.brandpath.com/\">\n" + "</getToken>");
		//


		soap().client("somTokenClient")
				.receive()
				.schemaValidation(false)
				.extractFromPayload("//return/token","soapToken")
				.timeout(5000);

		echo("Soap Token is: ${soapToken}");


		soap().client("somStockClient")
				.send()
				.header("citrus_http_Authorization","Bearer ${soapToken}")
				.payload(       "<sei:reserveStock xmlns:sei=\"http://sei.ws.som.brandpath.com/\">\n"
						+ "         <sessionId>dummySession</sessionId>\n"
						+ "         <orderReference>SUKLYB-GDFF-NVQ2</orderReference>\n"
						+ "         <accountId>1</accountId>\n"
						+ "         <reservationTypeTag>aTest</reservationTypeTag>\n" + "         <offerings>\n"
						+ "            <offering offeringCode=\"abc\" packageId=\"11\" quantity=\"1\">\n"
						+ "               <product productCode=\"${productCode}\" quantity=\"${onStock}\"/>\n" //reserve whole stock
						+ "            </offering>\n" + "         </offerings>\n"
						+ "      </sei:reserveStock>");


		soap().client("somStockClient")
				.receive()
				.schemaValidation(false)
				.validate("//reserveStockResult","true")
		;

		//its reserved so now verify the cache
		sleep(5000);

		http().client("sabreHttpClient")
				.send().get("ws/offering/${my.offering}")
				.payload("${default.test.sitepublicId}")
				.header("Host", "${sabrews.host}")
				.header("Origin", "${sabrews.origin}")
				.header("Referer", "${sabrews.referer}")
				.header("sitePublicId", "${default.test.sitepublicId}")
				.header("token", "${token}")
				.contentType("application/json")
				.accept("application/json");

		http().client("sabreHttpClient").receive().response().messageType(MessageType.JSON)
				.status(HttpStatus.OK)
				.validate("$.code","SUCCESS")
				.validate("$.offering.availabilityFlag","${OUT_OF_STOCK}") //NO in STOCK
		;


		//and now to UnReserve
		//unreserve
		soap().client("somStockClient")
				.send()
				.header("citrus_http_Authorization","Bearer ${soapToken}")
				.payload(       "<sei:unreserveStock xmlns:sei=\"http://sei.ws.som.brandpath.com/\">\n"
						+ "         <orderReference>SUKLYB-GDFF-NVQ2</orderReference>\n"
						+ "      </sei:unreserveStock>");


		soap().client("somStockClient")
				.receive()
				.schemaValidation(false)
				.validate("//unreserveStockResult","true")
		;

		//should now change the avaiability flag from 1 to 0 again.
		sleep(5000);

		http().client("sabreHttpClient")
				.send().get("ws/offering/${my.offering}")
				.payload("${default.test.sitepublicId}")
				.header("Host", "${sabrews.host}")
				.header("Origin", "${sabrews.origin}")
				.header("Referer", "${sabrews.referer}")
				.header("sitePublicId", "${default.test.sitepublicId}")
				.header("token", "${token}")
				.contentType("application/json")
				.accept("application/json");

		http().client("sabreHttpClient").receive().response().messageType(MessageType.JSON)
				.status(HttpStatus.OK)
				.validate("$.code","SUCCESS")
				.validate("$.offering.availabilityFlag","${AVAILABLE_STOCK}") //back to STOCK
		;
	}
}
