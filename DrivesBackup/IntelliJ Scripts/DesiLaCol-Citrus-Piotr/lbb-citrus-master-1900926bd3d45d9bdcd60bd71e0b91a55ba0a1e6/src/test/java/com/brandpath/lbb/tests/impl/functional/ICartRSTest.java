package com.brandpath.lbb.tests.impl.functional;

import static org.hamcrest.Matchers.*;

import com.brandpath.utils.Preparation;
import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.message.MessageType;
import org.hamcrest.Matchers;
import org.springframework.http.HttpStatus;
import org.testng.annotations.Test;

/**
 * Created by gregory on 23/02/17.
 */
public class ICartRSTest extends Preparation {

	@Test
	@CitrusTest
	public void getCart() throws  Exception { //just a new empty cart

		variable("site","5b7d3ff1-c560-4879-a4c7-4ec1962ee1c9");

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
				.send().get("ws/cart/packages")
				.header("Host", "${lbbws.host}")
				.header("Origin", "${lbbws.origin}")
				.header("Referer", "${lbbws.referer}")
				.header("token", "${token}")
				.contentType("application/json")
				.accept("application/json")
		;

		http().client("lbbHttpClient").receive().response().messageType(MessageType.JSON)
				.status(HttpStatus.OK)
				.validate("$.cartType", "STANDARD")
				.validate("$.status", "INITIALISED")
		;

	}


	@Test
	@CitrusTest
	public void addToCart() throws  Exception {

		variable("site","ae76e4f3-f397-11e6-8386-063dc089a783");

		//make session
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


		//get the cart
		http().client("lbbHttpClient")
				.send().get("ws/cart/packages")
				.header("Host", "${lbbws.host}")
				.header("Origin", "${lbbws.origin}")
				.header("Referer", "${lbbws.referer}")
				.header("token", "${token}")
				.contentType("application/json")
				.accept("application/json")
		;

		http().client("lbbHttpClient").receive().response().messageType(MessageType.JSON)
				.status(HttpStatus.OK)
				.validate("$.cartType", "STANDARD")
				.validate("$.status", "INITIALISED")
				.validate("$.reference","@assertThat(not(isEmptyString())@")
				.extractFromPayload("$.reference","cartRef")
		;


		//add to cart

		variable("my.offering","2d7973aa-1779-4bc7-806d-5fae289be8b0");

		//make it available in stock
		send("site8Endpoint").payload("COD,${my.offering},1");
		sleep(2000);

		//add to cart the offering
		http().client("lbbHttpClient")
				.send().post("ws/cart/packages")
				.header("Host", "${lbbws.host}")
				.header("Origin", "${lbbws.origin}")
				.header("Referer", "${lbbws.referer}")
				.header("token", "${token}")
				.payload("{\"offeringId\":\"${my.offering}\",\"quantity\":1}")
				.contentType("application/json")
				.accept("application/json")
		;

		http().client("lbbHttpClient").receive().response().messageType(MessageType.JSON)
				.status(HttpStatus.OK)
				.validate("$.reference","${cartRef}")
				.validate("$.packages.[0].offeringPublicId","${my.offering}")

		;
	}



	@Test
	@CitrusTest
	public void emptyCart() throws  Exception {

		variable("site","ae76e4f3-f397-11e6-8386-063dc089a783");

		//make session
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


		//get the cart
		http().client("lbbHttpClient")
				.send().get("ws/cart/packages")
				.header("Host", "${lbbws.host}")
				.header("Origin", "${lbbws.origin}")
				.header("Referer", "${lbbws.referer}")
				.header("token", "${token}")
				.contentType("application/json")
				.accept("application/json")
		;

		http().client("lbbHttpClient").receive().response().messageType(MessageType.JSON)
				.status(HttpStatus.OK)
				.validate("$.cartType", "STANDARD")
				.validate("$.status", "INITIALISED")
				.validate("$.reference","@assertThat(not(isEmptyString())@")
				.extractFromPayload("$.reference","cartRef")
		;


		//add to cart

		variable("my.offering","2d7973aa-1779-4bc7-806d-5fae289be8b0");

		//make it available in stock
		send("site8Endpoint").payload("COD,${my.offering},1");
		sleep(2000);

		//add to cart the offering
		http().client("lbbHttpClient")
				.send().post("ws/cart/packages")
				.header("Host", "${lbbws.host}")
				.header("Origin", "${lbbws.origin}")
				.header("Referer", "${lbbws.referer}")
				.header("token", "${token}")
				.payload("{\"offeringId\":\"${my.offering}\",\"quantity\":1}")
				.contentType("application/json")
				.accept("application/json")
		;

		http().client("lbbHttpClient").receive().response().messageType(MessageType.JSON)
				.status(HttpStatus.OK)
				.validate("$.reference","${cartRef}")
				.validate("$.packages.[0].offeringPublicId","${my.offering}")

		;


		//empty cart
		http().client("lbbHttpClient")
				.send().delete("ws/cart/packages")
				.header("Host", "${lbbws.host}")
				.header("Origin", "${lbbws.origin}")
				.header("Referer", "${lbbws.referer}")
				.header("token", "${token}")
				.payload("{\"offeringId\":\"${my.offering}\",\"quantity\":1}")
				.contentType("application/json")
				.accept("application/json")
		;

		http().client("lbbHttpClient").receive().response().messageType(MessageType.JSON)
				.status(HttpStatus.OK)
				.validate("$.reference","${cartRef}")
				.validate("$.packages",empty())

		;
	}


	@Test
	@CitrusTest
	public void updateQuantity() throws  Exception {

		variable("site","ae76e4f3-f397-11e6-8386-063dc089a783");

		//make session
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


		//get the cart
		http().client("lbbHttpClient")
				.send().get("ws/cart/packages")
				.header("Host", "${lbbws.host}")
				.header("Origin", "${lbbws.origin}")
				.header("Referer", "${lbbws.referer}")
				.header("token", "${token}")
				.contentType("application/json")
				.accept("application/json")
		;

		http().client("lbbHttpClient").receive().response().messageType(MessageType.JSON)
				.status(HttpStatus.OK)
				.validate("$.cartType", "STANDARD")
				.validate("$.status", "INITIALISED")
				.validate("$.reference","@assertThat(not(isEmptyString())@")
				.extractFromPayload("$.reference","cartRef")
		;


		//add to cart

		variable("my.offering","2d7973aa-1779-4bc7-806d-5fae289be8b0");

		//make it available in stock
		send("site8Endpoint").payload("COD,${my.offering},1");
		sleep(2000);

		//add to cart the offering
		http().client("lbbHttpClient")
				.send().post("ws/cart/packages")
				.header("Host", "${lbbws.host}")
				.header("Origin", "${lbbws.origin}")
				.header("Referer", "${lbbws.referer}")
				.header("token", "${token}")
				.payload("{\"offeringId\":\"${my.offering}\",\"quantity\":1}")
				.contentType("application/json")
				.accept("application/json")
		;

		http().client("lbbHttpClient").receive().response().messageType(MessageType.JSON)
				.status(HttpStatus.OK)
				.validate("$.reference","${cartRef}")
				.validate("$.cartPublicId","@assertThat(not(isEmptyString())@")
				.validate("$.packages.[0].offeringPublicId","${my.offering}")
				.validate("$.packages.[0].packagePublicId","@assertThat(not(isEmptyString())@")
				.validate("$.packages.[0].reference","@assertThat(not(isEmptyString())@")
				.extractFromPayload("$.reference","cartRef")
				.extractFromPayload("$.cartPublicId","cartPublicId")
				.extractFromPayload("$.packages.[0].packagePublicId","packagePublicId")
				.extractFromPayload("$.packages.[0].reference","packageReference")
		;

		//update quantity
		variable("my.qty","3");

		http().client("lbbHttpClient")
				.send().put("ws/cart/packages/${packagePublicId}")
				.header("Host", "${lbbws.host}")
				.header("Origin", "${lbbws.origin}")
				.header("Referer", "${lbbws.referer}")
				.header("token", "${token}")
				.payload("{\n"
						+ "  \"quantity\": ${my.qty}\n"
						+ "}")
				.contentType("application/json")
				.accept("application/json")
		;

		http().client("lbbHttpClient").receive().response().messageType(MessageType.JSON)
				.status(HttpStatus.OK)
				.validate("$.reference","${cartRef}")
				.validate("$.packages.[0].quantity","${my.qty}")

		;

	}


	@Test
	@CitrusTest
	public void updateQuantityWithZeroShouldEmptyBasket() throws  Exception {

		variable("site","ae76e4f3-f397-11e6-8386-063dc089a783");

		//make session
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


		//get the cart
		http().client("lbbHttpClient")
				.send().get("ws/cart/packages")
				.header("Host", "${lbbws.host}")
				.header("Origin", "${lbbws.origin}")
				.header("Referer", "${lbbws.referer}")
				.header("token", "${token}")
				.contentType("application/json")
				.accept("application/json")
		;

		http().client("lbbHttpClient").receive().response().messageType(MessageType.JSON)
				.status(HttpStatus.OK)
				.validate("$.cartType", "STANDARD")
				.validate("$.status", "INITIALISED")
				.validate("$.reference","@assertThat(not(isEmptyString())@")
				.extractFromPayload("$.reference","cartRef")
		;


		//add to cart

		variable("my.offering","2d7973aa-1779-4bc7-806d-5fae289be8b0");

		//make it available in stock
		send("site8Endpoint").payload("COD,${my.offering},1");
		sleep(2000);

		//add to cart the offering
		http().client("lbbHttpClient")
				.send().post("ws/cart/packages")
				.header("Host", "${lbbws.host}")
				.header("Origin", "${lbbws.origin}")
				.header("Referer", "${lbbws.referer}")
				.header("token", "${token}")
				.payload("{\"offeringId\":\"${my.offering}\",\"quantity\":1}")
				.contentType("application/json")
				.accept("application/json")
		;

		http().client("lbbHttpClient").receive().response().messageType(MessageType.JSON)
				.status(HttpStatus.OK)
				.validate("$.reference","${cartRef}")
				.validate("$.cartPublicId","@assertThat(not(isEmptyString())@")
				.validate("$.packages.[0].offeringPublicId","${my.offering}")
				.validate("$.packages.[0].packagePublicId","@assertThat(not(isEmptyString())@")
				.validate("$.packages.[0].reference","@assertThat(not(isEmptyString())@")
				.extractFromPayload("$.reference","cartRef")
				.extractFromPayload("$.cartPublicId","cartPublicId")
				.extractFromPayload("$.packages.[0].packagePublicId","packagePublicId")
				.extractFromPayload("$.packages.[0].reference","packageReference")
		;

		//update quantity
		variable("my.qty","2");

		http().client("lbbHttpClient")
				.send().put("ws/cart/packages/${packagePublicId}")
				.header("Host", "${lbbws.host}")
				.header("Origin", "${lbbws.origin}")
				.header("Referer", "${lbbws.referer}")
				.header("token", "${token}")
				.payload("{\n"
						+ "  \"quantity\": ${my.qty}\n"
						+ "}")
				.contentType("application/json")
				.accept("application/json")
		;

		http().client("lbbHttpClient").receive().response().messageType(MessageType.JSON)
				.status(HttpStatus.OK)
				.validate("$.reference","${cartRef}")
				.validate("$.packages.[0].quantity","${my.qty}")

		;


		//then send quantity 0 which should remove that package from cart;
		//update quantity
		variable("my.qty2","0");

		http().client("lbbHttpClient")
				.send().put("ws/cart/packages/${packagePublicId}")
				.header("Host", "${lbbws.host}")
				.header("Origin", "${lbbws.origin}")
				.header("Referer", "${lbbws.referer}")
				.header("token", "${token}")
				.payload("{\n"
						+ "  \"quantity\": ${my.qty2}\n"
						+ "}")
				.contentType("application/json")
				.accept("application/json")
		;

		http().client("lbbHttpClient").receive().response().messageType(MessageType.JSON)
				.status(HttpStatus.OK)
				.validate("$.reference","${cartRef}")
				.validate("$.packages",is(empty()))

		;

	}
}
