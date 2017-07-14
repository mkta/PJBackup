package com.brandpath.lbb.tests.impl.behavioral;

import com.brandpath.utils.Preparation;
import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.message.MessageType;
import org.springframework.http.HttpStatus;
import org.testng.annotations.Test;

/**
 * Created by gregory on 15/02/17.
 */
@Test(enabled = false)
public class RabbitMQTest extends Preparation {



	@CitrusTest
	public void changingAvailabilityFlag() throws  Exception{

		variable("site","5b7d3ff1-c560-4879-a4c7-4ec1962ee1c9");
		variable("my.offering","17d1ad67-3507-46dc-a28c-35747651b279");


		variable("availableStock","1");
		variable("outOfStock","3");

		variable("siteId","siteId-8"); //this is the queue


		variable("payload1","COD,${my.offering},${outOfStock}"); // force change to 3 /out-of-stock
		send("site8Endpoint").payload("${payload1}");


		//then verify change:

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


		sleep(3000);

		//verify again that it's now out of stock

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
				.validate("$.offering.availabilityFlag","${outOfStock}")
		;


		//go back to stock available:

		variable("payload2","COD,${my.offering},${availableStock}"); // force change to 1
		send("site8Endpoint").payload("${payload2}");


		sleep(3000);

		//verify again that it's now out of stock

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
				.validate("$.offering.availabilityFlag","${availableStock}")
		;


	}


	@Test
	@CitrusTest
	public void jmsTry() throws  Exception{

		send("site8Endpoint").payload("COD,17d1ad67-3507-46dc-a28c-35747651b279,1");


	}
}
