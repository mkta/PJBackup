package com.brandpath.lbb.tests.impl.functional;

import com.brandpath.utils.Preparation;
import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.message.MessageType;
import org.springframework.http.HttpStatus;
import org.testng.annotations.Test;

public class ILangRSTest extends Preparation{
	
	@Test
	@CitrusTest
	public void getTranslationsForSessionDefault() throws  Exception { //gave 204 before

		variable("site","ae76e4f3-f397-11e6-8386-063dc089a783"); // Desi La Col USA

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
				.send().get("/ws/lang/translation")
				.header("Host", "${lbbws.host}")
				.header("Origin", "${lbbws.origin}")
				.header("Referer", "${lbbws.referer}")
				.header("token", "${token}")
				.contentType("application/json")
				.accept("application/json");

		http().client("lbbHttpClient").receive().response()
				.messageType(MessageType.JSON)
				.status(HttpStatus.OK)
				.validate("$.WEB.HOMEPAGE.MAIN_TITLE","Main title!")
				.validate("$.WEB.SHOES","Shoes")
				.validate("$.WEB.HOMEPAGE.NEW.TITLE","New | Rose Collection")
		;
		echo("Token is: ${token}");
	}

	@Test(enabled = true)
	@CitrusTest
	public void getTranslationsForRO() throws  Exception {

		variable("site","ae76e4f3-f397-11e6-8386-063dc089a783"); // Desi La Col USA

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
				.send().get("/ws/lang/translation?langId=ro")
				.header("Host", "${lbbws.host}")
				.header("Origin", "${lbbws.origin}")
				.header("Referer", "${lbbws.referer}")
				.header("token", "${token}")
				.contentType("application/json")
				.accept("application/json");

		http().client("lbbHttpClient").receive().response()
				.messageType(MessageType.JSON)
				.status(HttpStatus.OK)
				//.validate("$.WEB.HOMEPAGE.MAIN_TITLE","GÅÃ³wny tytuÅ")
				.validate("$.WEB.HOMEPAGE.MAIN_TITLE","Titlul principal")
		;
	}
}
