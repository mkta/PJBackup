package com.brandpath.lbb.tests.esom;


import com.brandpath.utils.Preparation;
import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.dsl.builder.SoapClientActionBuilder;
import com.consol.citrus.dsl.builder.SoapClientRequestActionBuilder;
import com.consol.citrus.dsl.builder.SoapClientResponseActionBuilder;
import org.testng.annotations.Test;

public class EmailWsTest extends Preparation {


	private void variables() {
		/*
		*
		* SET CORRECT EMAIL BEFORE TESTING !
		*
		 */
		variable("my.email", "bartosz.tomaszewski@brandpath.com");
		variable("esom.userName", "esom-ws");
		variable("esom.password", "esom-ws32.AjgfL12");
		variable("esom.templateName", "Testing template");

	}

	/* ------------------ */


	private SoapClientRequestActionBuilder esomClientSend(SoapClientActionBuilder client) {

		SoapClientRequestActionBuilder scrab = client.send()
				.header("citrus_http_userName","${esom.userName}")
				.header("citrus_http_password","${esom.password}");
		return scrab;
	}

	private SoapClientResponseActionBuilder esomClientReceive(SoapClientActionBuilder client) {
		return client.receive().schemaValidation(false);
	}

	/* ------------------ */

	private SoapClientActionBuilder getEsomTokenClient() {
		return soap().client("esomTokenClient");
	}

	private SoapClientActionBuilder getEsomCommsClient() {
		return soap().client("esomCommsClient");
	}

	/* ------------------ */


	private SoapClientRequestActionBuilder esomTokenClientSend() {
		return esomClientSend(getEsomTokenClient());
	}

	private SoapClientResponseActionBuilder esomTokenClientReceive() {
		return esomClientReceive(getEsomTokenClient());
	}

	private SoapClientRequestActionBuilder esomCommsClientSend() {
		return esomClientSend(getEsomCommsClient()).header("citrus_http_Authorization", "Bearer ${soapToken}");
	}

	private SoapClientResponseActionBuilder esomCommsClientReceive() {
		return esomClientReceive(getEsomCommsClient());
	}

	/* ------------------ */

	private void esomTokenVariable () {
		esomTokenClientSend().payload(
				"<getToken xmlns=\"http://sei.ws.esom.brandpath.com/\">\n" + "</getToken>");

		esomTokenClientReceive().extractFromPayload("//return/token","soapToken").timeout(5000);

		echo("Soap Token is: ${soapToken}");

	}


	private void sendCommsRequest() {

		esomCommsClientSend().payload(
				"<sei:sendCommsRequest xmlns:sei=\"http://sei.ws.esom.brandpath.com/\">\n"
						+ "	<siteTag>${esom.siteTag}</siteTag>\n"
						+ "	<templateName>${esom.templateName}</templateName>\n"
						+ " <languageId>${lang}</languageId>\n"
						+ " <receipient>${my.email}</receipient>\n"
						+ " <params>\n"
						+ "   <entry>\n"
						+ "     <key>{orderReference}</key>\n"
						+ "     <value>TEST-TEST</value>\n"
						+ "   </entry>\n"
						+ "  </params>\n"
						+ "</sei:sendCommsRequest>\n"
		);
		validateCommsResponse();

	}

	private void validateCommsResponse() {
		esomCommsClientReceive().validate("//result", "true");
	}



	@Test
	@CitrusTest
	public void testToken() {
		/* set variables */
		variables();
		/* ask for token*/
		esomTokenVariable();

	}


	@Test
	@CitrusTest
	public void testEmailSendLangEnSiteUk() {
		/* set variables */
		variables();
		/* ask for token*/
		esomTokenVariable();

		variable("lang", "en");
		variable("esom.siteTag", "DLC_UK_ONLINE");

		/* ws call */
		sendCommsRequest();
	}

	@Test
	@CitrusTest
	public void testEmailSendLangEnSiteUs() {
		/* set variables */
		variables();
		/* ask for token*/
		esomTokenVariable();

		variable("lang", "en");
		variable("esom.siteTag", "DLC_US_ONLINE");

		/* ws call */
		sendCommsRequest();
	}

	@Test
	@CitrusTest
	public void testEmailSendLangJpSiteJp() {
		/* set variables */
		variables();
		/* ask for token*/
		esomTokenVariable();

		variable("lang", "jp");
		variable("esom.siteTag", "DLC_JP_ONLINE");

		/* ws call */
		sendCommsRequest();
	}



}
