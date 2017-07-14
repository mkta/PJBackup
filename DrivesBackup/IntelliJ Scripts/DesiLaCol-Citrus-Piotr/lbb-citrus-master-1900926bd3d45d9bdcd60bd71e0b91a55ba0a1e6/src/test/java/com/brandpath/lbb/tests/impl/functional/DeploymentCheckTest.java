package com.brandpath.lbb.tests.impl.functional;

import com.brandpath.utils.AliveValidator;
import com.brandpath.utils.Preparation;
import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.message.MessageType;
import org.springframework.http.HttpStatus;
import org.testng.annotations.Test;

/**
 * Created by gregory on 28/02/17.
 */
public class DeploymentCheckTest extends Preparation {

	@Test
	@CitrusTest
	public void deploymentCheck() throws  Exception {

		http().client("lbbHttpClient")
				.send().get("/ws/v/aGlkZGVu")
				.header("Host", "${lbbws.host}")
				.header("Origin", "${lbbws.origin}")
				.header("Referer", "${lbbws.referer}")
				.contentType("application/json")
				.accept("application/json");

		http().client("lbbHttpClient").receive().response()
				.messageType(MessageType.JSON)
				.status(HttpStatus.OK)
				.validator(new AliveValidator())

		;
	}
}
