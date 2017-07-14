package com.brandpath.lbb.tests.impl.functional;

import org.springframework.http.HttpStatus;

import com.brandpath.utils.Preparation;
import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.message.MessageType;
import org.testng.annotations.Test;

public class CheckoutRSTest extends Preparation {


	@Test
	@CitrusTest
	public void initPayment() throws  Exception { //aka almost Customer Order flow; banktransfer

		variable("site","ae76e4f3-f397-11e6-8386-063dc089a783");
		//variable("utr","");

		http().client("lbbHttpClient")
				.send().post("/ws/session")
				.payload("${site}")
				.header("Host", "${lbbws.host}")
				.header("Origin", "${lbbws.origin}")
				.header("Referer", "${lbbws.referer}")
				.contentType("application/json")
				.accept("application/json");

		http().client("lbbHttpClient").receive().response().messageType(MessageType.JSON)
				.status(HttpStatus.CREATED)
				.extractFromPayload("$.token", "token");

		echo("Token is: ${token}");

		//make stock
		variable("my.offering","2d7973aa-1779-4bc7-806d-5fae289be8b0");
		
		//now let create an CART and Consumer (we dont have addresses stil !)
		http().client("lbbHttpClient")
				.send().post("/ws/cart/packages")
				.payload("{"
						+ "\"offeringId\":\"${my.offering}\","
						+ "\"quantity\":1"
						+ "}")
				.header("Host", "${lbbws.host}")
				.header("token", "${token}")
				.header("Origin", "${lbbws.origin}")
				.header("Referer", "${lbbws.referer}")
				.contentType("application/json")
				.accept("application/json");
		
		http().client("lbbHttpClient").receive().response().messageType(MessageType.JSON)
				.status(HttpStatus.OK)
				.validate("$.cartType", "STANDARD")
				.validate("$.status", "INITIALISED")
				.validate("$.reference","@assertThat(not(isEmptyString())@")
		;
		
		http().client("lbbHttpClient")
				.send().post("/ws/checkout/payment")
				.payload("{\n" + "  \"consumer\" : {\n" + "    \"typeTag\" : \"NEW\",\n" + "    \"statusId\" : 0,\n"
						+ "    \"properties\" : {\n" + "      \"MIDDLENAME\" : \"Balthazar\",\n"
						+ "      \"PHONENUMBER\" : \"0048876543212\",\n" + "      \"LASTNAME\" : \"Doe\",\n"
						+ "      \"FIRSTNAME\" : \"John\",\n" + "      \"TITLE\" : \"King\",\n"
						+ "      \"EMAIL\" : \"bartosz.tomaszewski@brandpath.com\"\n" + "    }\n" + "  },\n"
						+ "  \"addresses\" : {\n" + "    \"BILLING\" : {\n"
						+ "      \"addressLine1\" : \"Studio 103, The Business Centre\",\n"
						+ "      \"addressLine2\" : \"61 Wellfield Road\",\n" + "      \"city\" : \"Roath\",\n"
						+ "      \"postcode\" : \"CF24 3DG\",\n" + "      \"region\" : \"Cardiff\",\n"
						+ "      \"territoryCode\" : \"GBR\"\n" + "    },\n" + "    \"DELIVERY\" : {\n"
						+ "      \"addressLine1\" : \"Studio 103, The Business Centre\",\n"
						+ "      \"addressLine2\" : \"61 Wellfield Road\",\n" + "      \"city\" : \"Roath\",\n"
						+ "      \"postcode\" : \"CF24 3DG\",\n" + "      \"region\" : \"Cardiff\",\n"
						+ "      \"territoryCode\" : \"GBR\"\n" + "    }\n" + "  },\n" + "  \"agreements\" : {\n"
						+ "    \"AGREE_MARKETING_TERMS\" : false,\n" + "    \"AGREE_MARKETING_EMAIL\" : true\n"
						+ "  },\n" + "  \"paymentProcessor\" : \"BANKTRANSFER\"\n" + "}\n")
				.header("Host", "${lbbws.host}")
				.header("token", "${token}")
				.header("Origin", "${lbbws.origin}")
				.header("Referer", "${lbbws.referer}")
				.contentType("application/json")
				.accept("application/json");

		http().client("lbbHttpClient").receive().response().messageType(MessageType.JSON)
				.status(HttpStatus.OK)
				.extractFromPayload("$.utr", "utr");

		echo("utr is: ${utr}");
		
		// complete payment
		http().client("lbbHttpClient")
			.send().put("/ws/checkout/payment")
			.payload("${utr}")// utr as a orderReference
			.header("Host", "${lbbws.host}")
			.header("token", "${token}")
			.header("Origin", "${lbbws.origin}")
			.header("Referer", "${lbbws.referer}")
			.contentType("application/json")
			.accept("application/json");

		http().client("lbbHttpClient").receive().response().messageType(MessageType.JSON)
				.status(HttpStatus.OK)
				.extractFromPayload("$.code", "code");
		
		echo("code is: ${code}");



		//get the cart
		http().client("lbbHttpClient")
				.send().get("ws/cart/packages")
				.header("Host", "${lbbws.host}")
				.header("Origin", "${lbbws.origin}")
				.header("Referer", "${lbbws.referer}")
				.header("token", "${token}")
				.contentType("application/json")
				.accept("application/json")
		;

		http().client("lbbHttpClient").receive().response().messageType(MessageType.JSON)
				.status(HttpStatus.OK)
				.validate("$.cartType", "STANDARD")
				.validate("$.status", "COMPLETED")
				.validate("$.snapshotReference","@assertThat(not(isEmptyString())@")
				.extractFromPayload("$.snapshotReference","snapshotReference")
		;


		sleep(11000); //wait 11 seconds for WS->OMS order creation;

		http().client("lbbHttpClient")
				.send().get("ws/orderstatus/${snapshotReference}")
				.header("Host", "${lbbws.host}")
				.header("Origin", "${lbbws.origin}")
				.header("Referer", "${lbbws.referer}")
				//.header("token", "${token}")
				.contentType("application/json")
				.accept("application/json")
		;

		http().client("lbbHttpClient").receive().response().messageType(MessageType.JSON)
				.status(HttpStatus.OK)
				.validate("$.code", "200")
				.validate("$.encryptedOrderRef", "${snapshotReference}")
		;

	}
}
