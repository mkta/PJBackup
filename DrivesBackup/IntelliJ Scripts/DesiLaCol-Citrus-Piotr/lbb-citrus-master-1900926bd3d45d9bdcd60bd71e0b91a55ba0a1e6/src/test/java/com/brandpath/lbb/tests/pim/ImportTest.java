package com.brandpath.lbb.tests.pim;

import com.brandpath.utils.ResourceReader;
import com.brandpath.utils.TokenHelper;
import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.message.MessageType;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.springframework.http.HttpStatus;
import org.testng.annotations.Test;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;

@Test(enabled = true)
public class ImportTest extends TokenHelper {

	//@CitrusTest
	public void checkStatus() {

		pimToken();
		
		// first create a new row
		soap().client("pimImportClient")
		.send()
		.header("citrus_http_Authorization","Bearer ${soapToken}")
		.payload(       "<sei:createLoad xmlns:sei=\"http://sei.service.pim.brandpath.com/\">" + 
				"         <upload client=\"DESILACOL\" security=\"${soapToken}\" compressed=\"ZIP\">" + 
				"         <![CDATA[eJyVkUFrg0AQhc8J5D9M51omrto0NKwBo7YJlSia0KNY3YSFoKLbQv591yRt09JLYVnYmfke897yQ52XkBdK1pWDYeT6WbxdhKt0GfgIZa5E1qm8VQ5azJwSs8m0N+b9zJrOGBsznCPcjoa8aWUhsoPsFMjSQT/0su1zFocIndQSV7VoHa7WAc5hNBzow2+IgNe7nWhltYeiLoWD3pKWAaVbeqBITwLRHE6DX6v1pZ7+Be5FXknqmrwQ8FrXqqN3k1pR4oX3E/dxA+sI4mTlBelFZXBeH9Sx0RqpGwanNkLx1raiKo4OPi1ihEroGO5sa8wYgnFy8BN9cdP/Q0kS/w1NJpMriBufXvvnaKgv4MZ37toIN/rP1Hn1/Q+kuYCi]]>" +
				"         </upload>" + 
				"      </sei:createLoad>");


		soap().client("pimImportClient")
				.receive()
				.schemaValidation(false)
				.extractFromPayload("//return/status","checkStatus")
				.extractFromPayload("//return/@id","loadId")
				.validate("//return/status","ACCEPTED")
		;
		echo("PIM Check Import status is: ${checkStatus} / loadId: ${loadId}");
		
		// check status via soap WS
		soap().client("pimImportClient")
		.send()
		.header("citrus_http_Authorization","Bearer ${soapToken}")
		.payload(       "<sei:checkStatus xmlns:sei=\"http://sei.service.pim.brandpath.com/\">" + 
				"         <loadStatus security=\"${soapToken}\" id=\"${loadId}\" client=\"DESILACOL\"/>" +
				"      </sei:checkStatus>");


		soap().client("pimImportClient")
				.receive()
				.schemaValidation(false)
				.extractFromPayload("//return/status","checkStatus")
				.validate("//return/status","ACCEPTED")
		;
		
		echo("PIM Check Import status is: ${checkStatus}");
	}

	//@CitrusTest
	public void createLoad() {

		pimToken();
		
		soap().client("pimImportClient")
		.send()
		.header("citrus_http_Authorization","Bearer ${soapToken}")
		.payload(       "<sei:createLoad xmlns:sei=\"http://sei.service.pim.brandpath.com/\">" + 
				"         <upload client=\"DESILACOL\" security=\"${soapToken}\" compressed=\"ZIP\"  ><![CDATA[eJyVkUFrg0AQhc8J5D9M51omrto0NKwBo7YJlSia0KNY3YSFoKLbQv591yRt09JLYVnYmfke897yQ52XkBdK1pWDYeT6WbxdhKt0GfgIZa5E1qm8VQ5azJwSs8m0N+b9zJrOGBsznCPcjoa8aWUhsoPsFMjSQT/0su1zFocIndQSV7VoHa7WAc5hNBzow2+IgNe7nWhltYeiLoWD3pKWAaVbeqBITwLRHE6DX6v1pZ7+Be5FXknqmrwQ8FrXqqN3k1pR4oX3E/dxA+sI4mTlBelFZXBeH9Sx0RqpGwanNkLx1raiKo4OPi1ihEroGO5sa8wYgnFy8BN9cdP/Q0kS/w1NJpMriBufXvvnaKgv4MZ37toIN/rP1Hn1/Q+kuYCi]]></upload>" +
				"      </sei:createLoad>");


		soap().client("pimImportClient")
				.receive()
				.schemaValidation(false)
				.extractFromPayload("//return/status","checkStatus")
				.extractFromPayload("//return/@id", "loadId")
				.validate("//return/status","ACCEPTED")
		;
		echo("PIM Check Import status is: ${checkStatus} + loadId is: ${loadId}");
	}
	
	@CitrusTest
	public void createLoadWithoutPacking() {

		pimToken();
		
		soap().client("pimImportClient")
		.send()
		.header("citrus_http_Authorization","Bearer ${soapToken}")
		.payload(       "<sei:createLoad xmlns:sei=\"http://sei.service.pim.brandpath.com/\">" +
				"         <upload client=\"DESILACOL\" security=\"${soapToken}\" compressed=\"NO\">" +
				"         <![CDATA[" +
				"         	<load action=\"LOAD_PUBLISHED\" date_start=\"2017-03-15T15:00:00.0\">\" +" +
				"				<price_list id=\"DLC_UK_PL\" site_id=\"DLC_UK_ONLINE\"> " +
				"					<offering_prices code=\"CLAUDIA-1\"> "+
				"						<price type=\"SALEPRICE\" currency=\"GBP\" net=\"100.00\" /> " +
				"						<price type=\"WAS\" currency=\"GBP\" net=\"200.00\" /> " +
				"						<price type=\"RRP\" currency=\"GBP\" net=\"300.00\" /> " +
				"					</offering_prices> " +
				" 				</price_list>" +
				"			</load>" +
				" 			]]>" +
				"         </upload>" +
				"      </sei:createLoad>");


		soap().client("pimImportClient")
				.receive()
				.schemaValidation(false)
				.extractFromPayload("//return/status","checkStatus")
				.validate("//return/status","ACCEPTED")
		;

		echo("PIM Check Import status is: ${checkStatus}");
	}

	//@CitrusTest
	public void createTwoUpdatesNowNoCompress() {

		pimToken();

		String xml = ResourceReader.resourceFileAsString("import/createTwoUpdatesNowNoCompress.xml");

		soap().client("pimImportClient")
				.send()
				.header("citrus_http_Authorization","Bearer ${soapToken}")
				.payload(xml);


		soap().client("pimImportClient")
				.receive()
				.schemaValidation(false)
				.extractFromPayload("//return/status","checkStatus")
				.validate("//return/status","ACCEPTED")
		;

		echo("PIM Check Import status is: ${checkStatus}");
	}


	//@CitrusTest
	public void createTwoUpdatesIn2MinNoCompress() {

		ZonedDateTime startDate = ZonedDateTime.now(ZoneOffset.UTC).plus(2, ChronoUnit.MINUTES);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS");

		String startDateS = formatter.format(startDate);

		String randPricePrefix = (RandomUtils.nextInt(90)  + 10) + ""; /* from 10 to 99 */

		System.out.println("Start date set to: " + startDateS);
		System.out.println("Rand price prefix set to: " + randPricePrefix);

		variable("dateStart", startDate);
		variable("randPricePrefix", randPricePrefix);

		pimToken();

		String xml = ResourceReader.resourceFileAsString("import/createTwoUpdatesIn2MinNoCompress.xml");

		soap().client("pimImportClient")
				.send()
				.header("citrus_http_Authorization","Bearer ${soapToken}")
				.payload(xml);


		soap().client("pimImportClient")
				.receive()
				.schemaValidation(false)
				.extractFromPayload("//return/status","checkStatus")
				.extractFromPayload("//return/@id","loadId")
				.validate("//return/status","ACCEPTED")
		;

		echo("PIM Check Import status is: ${checkStatus}");

		sleep(180.0);

		// check status via soap WS
		soap().client("pimImportClient")
				.send()
				.header("citrus_http_Authorization","Bearer ${soapToken}")
				.payload(       "<sei:checkStatus xmlns:sei=\"http://sei.service.pim.brandpath.com/\">" +
						"         <loadStatus security=\"${soapToken}\" id=\"${loadId}\" client=\"DESILACOL\"/>" +
						"      </sei:checkStatus>");


		soap().client("pimImportClient")
				.receive()
				.schemaValidation(false)
				.extractFromPayload("//return/status","checkStatus2")
				.validate("//return/status","COMPLETED");

		echo("PIM Check Import status is: ${checkStatus2}");
		;
	}

	private double codeToPrice(String code) {
		double ret = 0;
		for (byte b : code.getBytes()) {
			ret += b;
		}
		return ret;
	}

	@Test
	@CitrusTest
	public void complexTest() throws IOException, TemplateException {

		variable("site","dee5762e-89b2-4e1d-9464-58d2c81d217b");

		wsToken();

		List<String> codes = getPublishedOfferingCodes();

		Map<String, Map<String, String>> codeToPriceMap = new HashMap<>();

		NumberFormat nf = NumberFormat.getNumberInstance(Locale.US);
		DecimalFormat df = (DecimalFormat)nf;
		df.applyPattern("#.00");

		for (String code : codes) {
			double price = codeToPrice(code);
			double rrp = price + 1;
			double was = price + 2;

			Map<String, String> prices = new HashMap<>();
			prices.put("SALEPRICE", df. format(price) );
			prices.put("RRP", df.format(rrp));
			prices.put("WAS", df.format(was));

			codeToPriceMap.put(code, prices);
		}

		String transformedTemplate = getXMLFromMultiTemplate(codeToPriceMap);

		executeTemplateAndWaitForResult(transformedTemplate);


		for (String code: codes) {

			echo("\n--------------------\nOFFERING CODE: "+code+"\n--------------------\n");

			//verify code URL call
			http().client("lbbHttpClient")
					.send().get("/ws/offerings/code/citrus:encodeBase64("+code+")")
					.header("Host", "${lbbws.host}")
					.header("Origin", "${lbbws.origin}")
					.header("Referer", "${lbbws.referer}")
					.header("token", "${token}")
					.contentType("application/json")
					.accept("application/json")
			;

			double priceExpected = codeToPrice(code);

			http().client("lbbHttpClient").receive().response().messageType(MessageType.JSON)
					.status(HttpStatus.OK)
					.validate("$.code", "SUCCESS")
					.validate("$.offering.code", code)
					.validate("$.offering.prices.currenciesPrices.GBP.SALEPRICE.priceTax.totalPrice", priceExpected);
			;

		}

		for (String code : codes) {
			double price = RandomUtils.nextInt(2000);
			double rrp = RandomUtils.nextInt(2000);
			double was = RandomUtils.nextInt(2000);

			Map<String, String> prices = new HashMap<>();
			prices.put("SALEPRICE", df. format(price) );
			prices.put("RRP", df.format(rrp));
			prices.put("WAS", df.format(was));

			codeToPriceMap.put(code, prices);

		}

		String transformedTemplateRandPrices = getXMLFromMultiTemplate(codeToPriceMap);

		executeTemplateAndWaitForResult(transformedTemplateRandPrices);

		echo("\n--------------------\nOFFERING CODE: " + codes.get(0) + "\n--------------------\n");

		//verify code URL call
		http().client("lbbHttpClient")
				.send().get("/ws/offerings/code/citrus:encodeBase64("+codes.get(0)+")")
				.header("Host", "${lbbws.host}")
				.header("Origin", "${lbbws.origin}")
				.header("Referer", "${lbbws.referer}")
				.header("token", "${token}")
				.contentType("application/json")
				.accept("application/json")
		;

		double priceNotExpected = codeToPrice(codes.get(0));

		http().client("lbbHttpClient").receive().response().messageType(MessageType.JSON)
				.status(HttpStatus.OK)
				.validate("$.code", "SUCCESS")
				.validate("$.offering.code", codes.get(0))
				.validate("$.offering.prices.currenciesPrices.GBP.SALEPRICE.priceTax.totalPrice", is(not(priceNotExpected)));
		;

	}

	private String getXMLFromMultiTemplate(Map<String, Map<String, String>> codeToPriceMap) throws IOException, TemplateException {
		String template = ResourceReader.resourceFileAsString("import/createALotOfUpdatesFTLNoCompressChangePrice.ftl");

		Map<String, Object> params = new HashMap<>();
		params.put("ctp", codeToPriceMap);

		/* process template */
		Configuration cfg = new Configuration(Configuration.VERSION_2_3_0);

		Template t = new Template("xxx-" + System.currentTimeMillis(), new StringReader(template), cfg);

		Writer out = new StringWriter();
		t.process(params, out);

		return out.toString();
	}

	@CitrusTest
	public void createManyNoCompress() throws IOException, TemplateException {

		/*
		Offering list is result of query:

		select distinct code from offering_price p
			join offering_versionview vv on (vv.offeringId = p.offeringId)
			where vv.statusId = 500 and p.currency = 'GBP' and vv.clientId = 101 and p.priceListId = 2 order by 1;

		  */
		String template = ResourceReader.resourceFileAsString("import/createALotOfUpdatesFTLNoCompress.ftl");

		List<String> codes = getPublishedOfferingCodes();


		Map<String, Object> params = new java.util.HashMap<>();
		params.put("codes", codes);

		/* process template */
		Configuration cfg = new Configuration(Configuration.VERSION_2_3_0);

		Template t = new Template("xxx-" + System.currentTimeMillis(), new StringReader(template), cfg);

		Writer out = new StringWriter();
		t.process(params, out);

		String transformedTemplate = out.toString();

		executeTemplateAndWaitForResult(transformedTemplate);
	}


	@CitrusTest
	public void createProductPublished() {

		pimToken();

		String rand = RandomStringUtils.random(5);
		variable("rand", rand);

		String xml = ResourceReader.resourceFileAsString("import/createNewProduct.xml");

		soap().client("pimImportClient")
				.send()
				.header("citrus_http_Authorization","Bearer ${soapToken}")
				.payload(xml);


		soap().client("pimImportClient")
				.receive()
				.schemaValidation(false)
				.extractFromPayload("//return/status","checkStatus")
				.validate("//return/status","ACCEPTED")
		;

		echo("PIM Check Import status is: ${checkStatus}");
	}

	@CitrusTest
	public void createProductDraft() {

		pimToken();

		String rand = RandomStringUtils.random(5);
		variable("rand", rand);

		String xml = ResourceReader.resourceFileAsString("import/createNewProductDraft.xml");

		soap().client("pimImportClient")
				.send()
				.header("citrus_http_Authorization","Bearer ${soapToken}")
				.payload(xml);


		soap().client("pimImportClient")
				.receive()
				.schemaValidation(false)
				.extractFromPayload("//return/status","checkStatus")
				.validate("//return/status","ACCEPTED")
		;

		echo("PIM Check Import status is: ${checkStatus}");
	}

	@CitrusTest
	public void createProductValidate() {

		pimToken();

		String rand = "VDTE";
		variable("rand", rand);

		String xml = ResourceReader.resourceFileAsString("import/createNewProductValidate.xml");

		soap().client("pimImportClient")
				.send()
				.header("citrus_http_Authorization","Bearer ${soapToken}")
				.payload(xml);


		soap().client("pimImportClient")
				.receive()
				.schemaValidation(false)
				.extractFromPayload("//return/status","checkStatus")
				.validate("//return/status","ACCEPTED")
		;

		echo("PIM Check Import status is: ${checkStatus}");
	}

	@CitrusTest
	public void create10products() throws IOException, TemplateException {

		String template = ResourceReader.resourceFileAsString("import/createNewProduct.ftl");

		List<String> codes = new ArrayList<>();

		for (int i = 0; i < 10; i++) {
			String code = "TEST_" + RandomStringUtils.randomAlphanumeric(6).toUpperCase();
			codes.add(code);
		}

		/* process template */
		Configuration cfg = new Configuration(Configuration.VERSION_2_3_0);

		Template t = new Template("xxx-" + System.currentTimeMillis(), new StringReader(template), cfg);

		Writer out = new StringWriter();
		HashMap<String, Object> data = new HashMap<>();
		data.put("codes", codes);
		t.process(data, out);

		String xml = out.toString();


		pimToken();

		soap().client("pimImportClient")
				.send()
				.header("citrus_http_Authorization","Bearer ${soapToken}")
				.payload(xml);


		soap().client("pimImportClient")
				.receive()
				.schemaValidation(false)
				.extractFromPayload("//return/status","checkStatus")
				.validate("//return/status","ACCEPTED")
		;

		echo("PIM Check Import status is: ${checkStatus}");
	}




	private void executeTemplateAndWaitForResult(String transformedTemplate) {
		pimToken();

		soap().client("pimImportClient")
				.send()
				.header("citrus_http_Authorization","Bearer ${soapToken}")
				.payload(transformedTemplate);


		soap().client("pimImportClient")
				.receive()
				.schemaValidation(false)
				.extractFromPayload("//return/status","checkStatus")
				.extractFromPayload("//return/@id","loadId")
				.validate("//return/status","ACCEPTED")
		;

		echo("PIM Check Import status is: ${checkStatus}");

		sleep(180.0);

		// check status via soap WS
		soap().client("pimImportClient")
				.send()
				.header("citrus_http_Authorization","Bearer ${soapToken}")
				.payload(       "<sei:checkStatus xmlns:sei=\"http://sei.service.pim.brandpath.com/\">" +
						"         <loadStatus security=\"${soapToken}\" id=\"${loadId}\" client=\"DESILACOL\"/>" +
						"      </sei:checkStatus>");


		soap().client("pimImportClient")
				.receive()
				.schemaValidation(false)
				.extractFromPayload("//return/status","checkStatus2")
				.validate("//return/status","COMPLETED");

		echo("PIM Check Import status is: ${checkStatus2}");
		;
	}


	private List<String> getPublishedOfferingCodes() {
		String offeringsFile = ResourceReader.resourceFileAsString("import/offeringListPublished.txt");

		String [] offerings = offeringsFile.split("\n");
		List<String> codes = new ArrayList<>();
		for (String s: offerings) {
			String code = s.trim();
			if (s.length() > 3) {
				codes.add(code);
			}
		}
		return codes;
	}


}
