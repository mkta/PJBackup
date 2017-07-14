package com.brandpath.utils;

import static org.springframework.test.util.AssertionErrors.assertTrue;

import com.consol.citrus.context.TestContext;
import com.consol.citrus.exceptions.ValidationException;
import com.consol.citrus.message.Message;
import com.consol.citrus.validation.DefaultMessageValidator;
import com.consol.citrus.validation.context.ValidationContext;

/**
 * Created by gregory on 26/10/16.
 */
public class AliveValidator extends DefaultMessageValidator {

	@Override public void validateMessagePayload(Message receivedMessage, Message controlMessage,
			ValidationContext validationContext, TestContext context) throws ValidationException {

		String msg = receivedMessage.getPayload(String.class);
		String assertInfo1 = "OK,DPL_";

		assertTrue("Does not start with DPL_: "+msg,msg.startsWith(assertInfo1));

	}
}
