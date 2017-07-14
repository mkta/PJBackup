package com.brandpath.lbb.tests.impl.functional;

import com.brandpath.utils.Preparation;
import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.message.MessageType;
import org.springframework.http.HttpStatus;
import org.testng.annotations.Test;

public class ISiteRSTest extends Preparation{

	@Test
	@CitrusTest
	public void sitesList() throws  Exception {

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
				.validate("$.[0].properties.DEFAULT_LANGUAGE","en");


	}

	@Test
	@CitrusTest
	public void getLangsList() throws  Exception {

		variable("site","ae76e4f3-f397-11e6-8386-063dc089a783");

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

	}

	@Test
	@CitrusTest
	public void sitePropertiesPerSite() throws  Exception {

		variable("site","ae76e4f3-f397-11e6-8386-063dc089a783");

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

	}

	@Test
	@CitrusTest
	public void siteProperties() throws  Exception {

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
				.send().get("/ws/site/properties")
				.header("Host", "${lbbws.host}")
				.header("Origin", "${lbbws.origin}")
				.header("Referer", "${lbbws.referer}")
				.header("token", "${token}")
				.contentType("application/json")
				.accept("application/json");

		http().client("lbbHttpClient").receive().response()
				.messageType(MessageType.JSON)
				.status(HttpStatus.OK)
				.validate("$.PUBLICID","${site}")
		;

	}


	@Test(expectedExceptions = com.consol.citrus.exceptions.TestCaseFailedException.class)
	@CitrusTest //should normally fail
	public void sitePropertiesWithoutVisibleItems() throws  Exception {

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

		http().client("lbbHttpClient")
				.send().get("/ws/site/properties")
				.header("Host", "${lbbws.host}")
				.header("Origin", "${lbbws.origin}")
				.header("Referer", "${lbbws.referer}")
				.header("sitePublicId", "${site}")
				.header("token", "${token}")
				.contentType("application/json")
				.accept("application/json");

		http().client("lbbHttpClient").receive().response()
				.messageType(MessageType.JSON)
				.status(HttpStatus.OK)
				.validate("$.PUBLICID","${site}")
				.validate("$.PSM_PASS","bla bla") //this will throw exception but we expect that too
				.validate("$.ESOM_PASS","bla bla") //this as well
		;

	}
}
