package com.brandpath.lbb.tests.impl.functional;

import com.brandpath.utils.Preparation;
import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.message.MessageType;
import org.springframework.http.HttpStatus;
import org.testng.annotations.Test;

public class IOrderRSTest extends Preparation {

	@Test(enabled = false)
	@CitrusTest
	public void getOrderStatus() throws Exception {


		http().client("lbbHttpClient")
				.send().get("ws/orderstatus/3e40b595-ca6a-4058-bd95-b4404935ed67")
				.header("Host", "${lbbws.host}")
				.header("Origin", "${lbbws.origin}")
				.header("Referer", "${lbbws.referer}")
				//.header("token", "${token}")
				.contentType("application/json")
				.accept("application/json")
		;

		http().client("lbbHttpClient").receive().response().messageType(MessageType.JSON)
				.status(HttpStatus.OK)
				//.validate("$.cartType", "STANDARD")
				//.validate("$.status", "INITIALISED")
		;

	}

}