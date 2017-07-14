package com.brandpath.utils;

import org.testng.annotations.BeforeClass;

/**
 * Created by gregory on 09/02/17.
 */
public class Preparation extends ModernDesigner {

	@BeforeClass
	public static void prepare(){
		System.setProperty("https.protocols","TLSv1.1,TLSv1.2,TLSv1,SSLv3");
	}
}
