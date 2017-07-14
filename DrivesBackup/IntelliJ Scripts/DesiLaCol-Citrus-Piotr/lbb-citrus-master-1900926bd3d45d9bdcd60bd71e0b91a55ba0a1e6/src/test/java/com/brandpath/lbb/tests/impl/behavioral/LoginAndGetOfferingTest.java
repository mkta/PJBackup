package com.brandpath.lbb.tests.impl.behavioral;

import com.brandpath.utils.Preparation;
import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.message.MessageType;
import org.springframework.http.HttpStatus;
import org.testng.annotations.Test;

public class LoginAndGetOfferingTest extends Preparation {


	@Test
	@CitrusTest
	public void loginAndGetSingleOffering() throws  Exception{

		variable("site","ae76e4f3-f397-11e6-8386-063dc089a783");

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

		variable("my.offering","2d7973aa-1779-4bc7-806d-5fae289be8b0");


		http().client("lbbHttpClient")
				.send().get("/ws/offerings/${my.offering}")
				.header("Host", "${lbbws.host}")
				.header("Origin", "${lbbws.origin}")
				.header("Referer", "${lbbws.referer}")
				.header("token", "${token}")
				.contentType("application/json")
				.accept("application/json")
		;

		http().client("lbbHttpClient").receive().response().messageType(MessageType.JSON)
				.status(HttpStatus.OK)
				.validate("$.code","SUCCESS")
		;

	}

	@Test
	@CitrusTest
	public void loginAndGetSingleOfferingInDifferentLang() throws  Exception{

		variable("site","ae76e4f3-f397-11e6-8386-063dc089a783");

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

		//
		http().client("lbbHttpClient")
				.send().put("/ws/session/updateProperties?paramId=lang&value=ro")
				.header("Host", "${lbbws.host}")
				.header("Origin", "${lbbws.origin}")
				.header("Referer", "${lbbws.referer}")
				.header("token", "${token}")
				.contentType("application/json")
				.accept("application/json");

		http().client("lbbHttpClient").receive().response()
				.status(HttpStatus.OK)
				.messageType(MessageType.JSON);

		variable("my.offering","55bc9b65-a3a4-4a7d-82e0-c6f814c8d91d");


		http().client("lbbHttpClient")
				.send().get("/ws/offerings/${my.offering}")
				.header("Host", "${lbbws.host}")
				.header("Origin", "${lbbws.origin}")
				.header("Referer", "${lbbws.referer}")
				.header("token", "${token}")
				.contentType("application/json")
				.accept("application/json")
		;

		http().client("lbbHttpClient").receive().response().messageType(MessageType.JSON)
				.status(HttpStatus.OK)
				.validate("$.code","SUCCESS")
		;

	}

}
