package com.brandpath.lbb.tests.impl.behavioral;

import com.brandpath.utils.Preparation;
import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.message.MessageType;
import org.springframework.http.HttpStatus;
import org.testng.annotations.Test;

public class WhiteDevFlowTest extends Preparation {

	@Test(enabled = false)
	@CitrusTest
	public void whiteDevFlow1() throws  Exception{

	//1. List sites - no auth

		http().client("lbbHttpClient")
				.send().get("/ws/site/list")
				.header("Host", "${lbbws.host}")
				.header("Origin", "${lbbws.origin}")
				.header("Referer", "${lbbws.referer}")
				.contentType("application/json")
				.accept("application/json");

		http().client("lbbHttpClient").receive().response()
				.messageType(MessageType.JSON)
				.status(HttpStatus.OK)
				.extractFromPayload("$.[0]", "site"); //at least one site returned;

		echo("Site is: ${site}");


	//2. List lang list properties - no auth

		http().client("lbbHttpClient")
				.send().get("/ws/site/lang/list?sitePublicId=${site}")
				.header("Host", "${lbbws.host}")
				.header("Origin", "${lbbws.origin}")
				.header("Referer", "${lbbws.referer}")
				.contentType("application/json")
				.accept("application/json");

		http().client("lbbHttpClient").receive().response()
				.messageType(MessageType.JSON)
				.status(HttpStatus.OK)
				.validate("$.[0]","en")
		;

	//3. session create

		http().client("lbbHttpClient")
				.send().post("/ws/session")
				.payload("${site}")
				.header("Host", "${lbbws.host}")
				.header("Origin", "${lbbws.origin}")
				.header("Referer", "${lbbws.referer}")
				.header("sitePublicId", "${site}") //samsung site :) 5b7d3ff1-c560-4879-a4c7-4ec1962ee1c9
				.contentType("application/json")
				.accept("application/json");

		http().client("lbbHttpClient").receive().response().messageType(MessageType.JSON)
				.extractFromPayload("$.token", "token");

		echo("Token is: ${token}");

	//4. site properties  - no auth

		http().client("lbbHttpClient")
				.send().get("/ws/site/properties/${site}")
				.header("Host", "${lbbws.host}")
				.header("Origin", "${lbbws.origin}")
				.header("Referer", "${lbbws.referer}")
				.contentType("application/json")
				.accept("application/json");

		http().client("lbbHttpClient").receive().response()
				.messageType(MessageType.JSON)
				.status(HttpStatus.OK)
				.validate("$.PUBLICID","${site}")
		;

	//5. translation langId=en

		http().client("lbbHttpClient")
				.send().get("/ws/lang/translation?langId=en") //&groupId=Advanced Search
				.header("Host", "${lbbws.host}")
				.header("Origin", "${lbbws.origin}")
				.header("Referer", "${lbbws.referer}")
				//.header("sitePublicId", "${site}")
				.header("token", "${token}")
				.contentType("application/json")
				.accept("application/json");

		http().client("lbbHttpClient").receive().response()
				.messageType(MessageType.JSON)
				.status(HttpStatus.OK)
				.validate("$.['PIM Button'].PIM_PRODUCT_PUBLISHED","PUBLISH")
		;
	}
}
