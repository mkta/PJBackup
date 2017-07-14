package com.brandpath.lbb.tests.impl.functional;


import com.brandpath.utils.Preparation;
import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.message.MessageType;
import org.springframework.http.HttpStatus;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

@Test
public class ISessionRSTest extends Preparation {


	@DataProvider(name = "test1")
	public static Object[][] sitesInEu() {
		return new Object[][] {
				{1, "ae76e4f3-f397-11e6-8386-063dc089a783"}, //USA
				{2, "dee5762e-89b2-4e1d-9464-58d2c81d217b"}, //UK
				//{3, "b5acb07c-f397-11e6-8386-063dc089a783"}, //JP
		};
	}


	@CitrusTest
	@Test(groups = "basic", dataProvider = "test1")
	public void createSession(Integer number, String sitePublicId) throws  Exception {

		variable("site",sitePublicId); // sitePublicId

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


	}

	@CitrusTest
	public void updateSessionProperty() throws  Exception {

		variable("site","ae76e4f3-f397-11e6-8386-063dc089a783");

		http().client("lbbHttpClient")
				.send().post("/ws/session")
				.payload("${site}")
				.header("Host", "${lbbws.host}")
				.header("Origin", "${lbbws.origin}")
				.header("Referer", "${lbbws.referer}")
				.contentType("application/json")
				.accept("application/json");

		http().client("lbbHttpClient").receive().response().messageType(MessageType.JSON)
				.extractFromPayload("$.token", "token");

		echo("Token is: ${token}");


		http().client("lbbHttpClient")
				.send().put("/ws/session/updateProperties?paramId=test&value=123")
				.header("Host", "${lbbws.host}")
				.header("Origin", "${lbbws.origin}")
				.header("Referer", "${lbbws.referer}")
				.header("token", "${token}")
				.contentType("application/json")
				.accept("application/json");

		http().client("lbbHttpClient").receive().response()
				.status(HttpStatus.OK)
				.messageType(MessageType.JSON);


	}

	@CitrusTest
	public void getSessionProperty() throws  Exception {

		variable("site","ae76e4f3-f397-11e6-8386-063dc089a783");

		http().client("lbbHttpClient")
				.send().post("/ws/session")
				.payload("${site}")
				.header("Host", "${lbbws.host}")
				.header("Origin", "${lbbws.origin}")
				.header("Referer", "${lbbws.referer}")
				.contentType("application/json")
				.accept("application/json");

		http().client("lbbHttpClient").receive().response().messageType(MessageType.JSON)
				.extractFromPayload("$.token", "token");

		echo("Token is: ${token}");


		http().client("lbbHttpClient")
				.send().put("/ws/session/updateProperties?paramId=test&value=testing")
				.header("Host", "${lbbws.host}")
				.header("Origin", "${lbbws.origin}")
				.header("Referer", "${lbbws.referer}")
				.header("token", "${token}")
				.contentType("application/json")
				.accept("application/json");

		http().client("lbbHttpClient").receive().response()
				.status(HttpStatus.OK)
				.messageType(MessageType.JSON);

		http().client("lbbHttpClient")
				.send().get("/ws/session?paramId=test")
				.header("Host", "${lbbws.host}")
				.header("Origin", "${lbbws.origin}")
				.header("Referer", "${lbbws.referer}")
				.header("token", "${token}")
				.contentType("application/json")
				.accept("application/json");

		http().client("lbbHttpClient").receive().response()
				.status(HttpStatus.OK)
				.messageType(MessageType.JSON);


	}
}
