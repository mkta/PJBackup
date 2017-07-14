package com.brandpath.lbb.tests.impl.functional;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.Matchers.emptyArray;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.brandpath.utils.Preparation;
import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.message.MessageType;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.http.HttpStatus;
import org.testng.annotations.Test;

/**
 * Created by gregory on 21/02/17.
 */
public class IFiltersServiceRSTest extends Preparation {


	@Test
	@CitrusTest
	public void createFilterAndGetItOnlyById() throws  Exception {
		variable("site","ae76e4f3-f397-11e6-8386-063dc089a783");
		variable("my.filterId", UUID.randomUUID().toString());

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

		List<NameValuePair> nvps = new ArrayList<>();
		nvps.add(new BasicNameValuePair("filterId", "${my.filterId}"));
		nvps.add(new BasicNameValuePair("filterTYPES", "PRODUCT"));
		nvps.add(new BasicNameValuePair("filterFIELDS", "products.propertyTypes.tag"));
		nvps.add(new BasicNameValuePair("filterTYPE_NAMES", "DESCRIPTION"));
		nvps.add(new BasicNameValuePair("filterTAG_NAMES", "TEASER"));
		nvps.add(new BasicNameValuePair("filterHIERARCHY", "M_SHOES"));

		http().client("lbbHttpClient")
				.send().get("ws/filters?" + queryFromParamsPair(nvps))
				.header("Host", "${lbbws.host}")
				.header("Origin", "${lbbws.origin}")
				.header("Referer", "${lbbws.referer}")
				.header("token", "${token}")
				.contentType("application/json")
				.accept("application/json")
		;

		http().client("lbbHttpClient").receive().response().messageType(MessageType.JSON)
				.status(HttpStatus.OK)
				.validate("$.code", "SUCCESS")
				.validate("$.result.filterId","${my.filterId}")
				.validate("$.result.filtersList",is(not(emptyArray())))
				.extractFromPayload("$.result", "result")
		;


		//get it by Id

		http().client("lbbHttpClient")
				.send().get("ws/filters?filterId=${my.filterId}")
				.header("Host", "${lbbws.host}")
				.header("Origin", "${lbbws.origin}")
				.header("Referer", "${lbbws.referer}")
				.header("token", "${token}")
				.contentType("application/json")
				.accept("application/json")
		;

		http().client("lbbHttpClient").receive().response().messageType(MessageType.JSON)
				.status(HttpStatus.OK)
				.validate("$.code", "SUCCESS")
				.validate("$.result.filterId","${my.filterId}")
				.validate("$.result.filtersList",is(not(emptyArray())))
				.extractFromPayload("$.result", "result")
		;
	}



	@Test
	@CitrusTest
	public void getFilterAlwaysNewBigScenario() throws  Exception {

		variable("site","ae76e4f3-f397-11e6-8386-063dc089a783");
		variable("my.filterId", UUID.randomUUID().toString());

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

		List<NameValuePair> nvps = new ArrayList<>();
		nvps.add(new BasicNameValuePair("filterId", "${my.filterId}"));
		nvps.add(new BasicNameValuePair("filterTYPES", "PRODUCT"));
		nvps.add(new BasicNameValuePair("filterFIELDS", "products.propertyTypes.tag"));
		nvps.add(new BasicNameValuePair("filterTYPE_NAMES", "DESCRIPTION"));
		nvps.add(new BasicNameValuePair("filterTAG_NAMES", "TEASER"));
		nvps.add(new BasicNameValuePair("filterHIERARCHY", "M_SHOES"));

		http().client("lbbHttpClient")
				.send().get("ws/filters?" + queryFromParamsPair(nvps))
				.header("Host", "${lbbws.host}")
				.header("Origin", "${lbbws.origin}")
				.header("Referer", "${lbbws.referer}")
				.header("token", "${token}")
				.contentType("application/json")
				.accept("application/json")
		;

		http().client("lbbHttpClient").receive().response().messageType(MessageType.JSON)
				.status(HttpStatus.OK)
				.validate("$.code", "SUCCESS")
				.validate("$.result.filterId","${my.filterId}")
				.validate("$.result.filtersList",is(not(emptyArray())))
				.extractFromPayload("$.result", "result")
		;



		List<NameValuePair> nvps2 = new ArrayList<>();
		nvps2.add(new BasicNameValuePair("filterTYPES", "PRODUCT"));
		nvps2.add(new BasicNameValuePair("filterHIERARCHY", "M_SHOES"));


		//available/ids
		http().client("lbbHttpClient")
				.send().get("ws/filters/available/ids?" + queryFromParamsPair(nvps2))
				.header("Host", "${lbbws.host}")
				.header("Origin", "${lbbws.origin}")
				.header("Referer", "${lbbws.referer}")
				.header("token", "${token}")
				.contentType("application/json")
				.accept("application/json")
		;

		http().client("lbbHttpClient").receive().response().messageType(MessageType.JSON)
				.status(HttpStatus.OK)
				.validate("$.code", "SUCCESS")
				.validate("$.result","@contains(${my.filterId})@") //filter has to be found in available filter ids.
		;



		//getAllFilters:
		http().client("lbbHttpClient")
				.send().get("ws/filters/available?" + queryFromParamsPair(nvps2))
				.header("Host", "${lbbws.host}")
				.header("Origin", "${lbbws.origin}")
				.header("Referer", "${lbbws.referer}")
				.header("token", "${token}")
				.contentType("application/json")
				.accept("application/json")
		;

		http().client("lbbHttpClient").receive().response().messageType(MessageType.JSON)
				.status(HttpStatus.OK)
				.validate("$.code", "SUCCESS")
				.validate("$.result","@contains(${my.filterId})@") //filter has to be found in available filter ids.
		;

	}


	@Test
	@CitrusTest
	public void getFilterMainProductColour() throws  Exception {

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

		List<NameValuePair> nvps = new ArrayList<>();
		nvps.add(new BasicNameValuePair("filterNAME", "mainProductColour"));
		nvps.add(new BasicNameValuePair("filterHIERARCHY", "M_SHOES"));

		http().client("lbbHttpClient")
				.send().get("ws/filters/extended?" + queryFromParamsPair(nvps))
				.header("Host", "${lbbws.host}")
				.header("Origin", "${lbbws.origin}")
				.header("Referer", "${lbbws.referer}")
				.header("token", "${token}")
				.contentType("application/json")
				.accept("application/json")
		;

		http().client("lbbHttpClient").receive().response().messageType(MessageType.JSON)
				.status(HttpStatus.OK)
				.validate("$.code", "SUCCESS")
				.validate("$.result.tag", "MAINPRODUCTCOLOUR")
				.validate("$.result.name", "mainProductColour")
				.validate("$.result.filterType", "OTHER")
				.validate("$.result.values.size()", greaterThan(2))
				.validate("$.result.values", hasItem("White"))
		;

	}


	@Test
	@CitrusTest
	public void getFilterPriceIndexDbl() throws  Exception {

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

		List<NameValuePair> nvps = new ArrayList<>();
		nvps.add(new BasicNameValuePair("filterNAME", "priceIndexDbl"));
		nvps.add(new BasicNameValuePair("filterHIERARCHY", "M_SHOES"));

		http().client("lbbHttpClient")
				.send().get("ws/filters/extended?" + queryFromParamsPair(nvps))
				.header("Host", "${lbbws.host}")
				.header("Origin", "${lbbws.origin}")
				.header("Referer", "${lbbws.referer}")
				.header("token", "${token}")
				.contentType("application/json")
				.accept("application/json")
		;

		http().client("lbbHttpClient").receive().response().messageType(MessageType.JSON)
				.status(HttpStatus.OK)
				.validate("$.code", "SUCCESS")
				.validate("$.result.tag", "PRICEINDEXDBL")
				.validate("$.result.name", "priceIndexDbl")
				.validate("$.result.filterType", "RANGE")
				.validate("$.result.values.size()", greaterThan(5))
		;

	}

	private String queryFromParamsPair(List<NameValuePair> nvps) {
		String q = "";
		for (NameValuePair p : nvps) {
			q += p.getName() + "=" + p.getValue();
			q += "&";
		}
		q = q.substring(0, q.length() - 1);
		return q;
	}
}
