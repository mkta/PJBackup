package com.brandpath.lbb.tests.impl.functional;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;

import java.util.ArrayList;
import java.util.List;

import com.brandpath.utils.Preparation;
import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.message.MessageType;
import org.apache.http.Consts;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.http.HttpStatus;
import org.testng.annotations.Test;

/**
 * Created by gregory on 13/02/17.
 */
public class IOfferingServiceRSTest extends Preparation {

	@Test
	@CitrusTest
	public void getOfferings() throws  Exception {

		variable("site","ae76e4f3-f397-11e6-8386-063dc089a783");

		//variable("site","dee5762e-89b2-4e1d-9464-58d2c81d217b");

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

		List<NameValuePair> nvps = new ArrayList<>();
		nvps.add(new BasicNameValuePair("page", "1"));
		nvps.add(new BasicNameValuePair("pageSize", "3"));
		nvps.add(new BasicNameValuePair("sort", "sortNameAsc"));
		nvps.add(new BasicNameValuePair("filterTYPES", "PRODUCT"));
		nvps.add(new BasicNameValuePair("filterHIERARCHY", "M_SHOES"));

		String params = URLEncodedUtils.format(nvps, Consts.UTF_8);

		http().client("lbbHttpClient")
				.send().get("/ws/offerings?"+params)
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
		        .validate("$.pagination.offeringsCount",greaterThanOrEqualTo(Long.valueOf("1")))
		;

	}


	@Test
	@CitrusTest
	public void getOfferingByFriendlyUrlAndThenCode() throws  Exception {

		variable("site", "ae76e4f3-f397-11e6-8386-063dc089a783");
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



		List<NameValuePair> nvps = new ArrayList<>();
		nvps.add(new BasicNameValuePair("page", "1"));
		nvps.add(new BasicNameValuePair("pageSize", "3"));
		nvps.add(new BasicNameValuePair("sort", "sortNameAsc"));
		nvps.add(new BasicNameValuePair("type", "DEVICE"));
		nvps.add(new BasicNameValuePair("filterHIERARCHY", "M_SHOES"));

		String params = URLEncodedUtils.format(nvps, Consts.UTF_8);

		http().client("lbbHttpClient")
				.send().get("/ws/offerings?"+params)
				.header("Host", "${lbbws.host}")
				.header("Origin", "${lbbws.origin}")
				.header("Referer", "${lbbws.referer}")
				.header("token", "${token}")
				.contentType("application/json")
				.accept("application/json")
		;


		//variable("code","dummy");

		http().client("lbbHttpClient").receive().response().messageType(MessageType.JSON)
				.status(HttpStatus.OK)
				.validate("$.code","SUCCESS")
				.validate("$.pagination.pages",greaterThanOrEqualTo(Long.valueOf("1")))
				.extractFromPayload("$.offeringList.[0].friendlyUrl", "friendlyUrl")
				.extractFromPayload("$.offeringList.[0].code", "code")
		;


		//verify friendly URL call
		http().client("lbbHttpClient")
				.send().get("/ws/offerings/short/${friendlyUrl}")
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
				.validate("$.offering.friendlyUrl","${friendlyUrl}")
		;



		//verify code URL call
		http().client("lbbHttpClient")
				.send().get("/ws/offerings/code/citrus:encodeBase64(${code})")
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
				.validate("$.offering.code", "${code}")
		;

	}
}
