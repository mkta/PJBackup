package com.brandpath.lbb.tests.impl.functional;

import static com.brandpath.utils.RegularExpressionMatcher.matchesPattern;

import com.brandpath.utils.Preparation;
import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.message.MessageType;
import org.springframework.http.HttpStatus;
import org.testng.annotations.Test;

/**
 * Created by gregory on 28/02/17.
 */
public class TimeServiceRSTest extends Preparation {

	@Test
	@CitrusTest
	public void getTime() throws  Exception {

		http().client("lbbHttpClient")
				.send().get("/ws/get-time")
				.header("Host", "${lbbws.host}")
				.header("Origin", "${lbbws.origin}")
				.header("Referer", "${lbbws.referer}")
				.contentType("application/json")
				.accept("application/json");

		http().client("lbbHttpClient").receive().response()
				.messageType(MessageType.JSON)
				.status(HttpStatus.OK)
				.validate("$.date", matchesPattern("((?:2|1)\\d{3}(?:-|\\/)(?:(?:0[1-9])|(?:1[0-2]))(?:-|\\/)(?:(?:0[1-9])|(?:[1-2][0-9])|(?:3[0-1]))(?:T|\\s)(?:(?:[0-1][0-9])|(?:2[0-3])):(?:[0-5][0-9]):(?:[0-5][0-9]))"))

													//timestamp matcher: http://txt2re.com/
		;
	}
}
