package com.brandpath.utils;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

/**
 * Created by gregory on 03/10/16.
 */
public class TotpTest {

	@Test
	public void checkTotp() throws Exception{

		final String secretKey = "e51182cc-75ea-4160-bb81-8ce334b902c1";
		final String totp = new TimeBasedOTP().generate(secretKey);

		System.out.println("totp:"+totp);

		boolean validationRes = (new TimeBasedOTP()).validate(totp,secretKey.getBytes());

		assertTrue(validationRes);

	}


}
