package com.brandpath.utils;

import com.consol.citrus.dsl.testng.TestNGCitrusTestDesigner;


public class ModernDesigner extends TestNGCitrusTestDesigner {


	public ModernDesigner(){

		super();

		//if target env is not selected set default one
		String env = System.getProperty("target.env");
		if(env == null  || env.isEmpty()){
			System.setProperty("target.env","awststEU");
		}

	}
}
