package com.brandpath.lbb.tests.impl.functional;

import com.brandpath.utils.Preparation;
import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.message.MessageType;
import org.springframework.http.HttpStatus;
import org.testng.annotations.Test;

public class GetProcessorPaymentMethodsRSTest extends Preparation {


	@Test(groups = {"basic","getProcessorPaymentMethods"})
	@CitrusTest
	public void getProcessorPaymentMethods() throws  Exception {

		// this (and the httpClient URL) needs to be changed if the test needs to be run for JP site
		variable("site","ae76e4f3-f397-11e6-8386-063dc089a783");//Desi La Col USA
        if (System.getProperty("target.env").equalsIgnoreCase("awststJP") ){
            variable("site","b5acb07c-f397-11e6-8386-063dc089a783");//Desi La Col Japan
        }

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
				.send().get("/ws/checkout/payment/methods")
				.header("Host", "${lbbws.host}")
				.header("token", "${token}")
				.header("Origin", "${lbbws.origin}")
				.header("Referer", "${lbbws.referer}")
				.contentType("application/json")
				.accept("application/json");

		http().client("lbbHttpClient").receive().response().messageType(MessageType.JSON)
				.status(HttpStatus.OK)
				.extractFromPayload("$.availablePaymentMethods", "availablePaymentMethods");

		echo("The available payment methods for the site ${site} are: ${availablePaymentMethods}");
	}
}
