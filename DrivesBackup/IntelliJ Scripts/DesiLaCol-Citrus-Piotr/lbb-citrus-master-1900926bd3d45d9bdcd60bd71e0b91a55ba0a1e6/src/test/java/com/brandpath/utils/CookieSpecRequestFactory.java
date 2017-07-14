package com.brandpath.utils;

import org.apache.http.client.HttpClient;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.client.params.CookiePolicy;
import org.apache.http.impl.client.DefaultHttpClient;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;

//BROWSER_COMPATIBILITY
public class CookieSpecRequestFactory implements FactoryBean<HttpComponentsClientHttpRequestFactory>, InitializingBean {


	/** The target request factory */
	private HttpClient httpClient;

	@Override public HttpComponentsClientHttpRequestFactory getObject() throws Exception {

		httpClient.getParams().setParameter(ClientPNames.COOKIE_POLICY, CookiePolicy.BROWSER_COMPATIBILITY);
		return new HttpComponentsClientHttpRequestFactory(httpClient);
	}

	@Override public Class<?> getObjectType() {
		return HttpComponentsClientHttpRequestFactory.class;
	}

	@Override public boolean isSingleton() {
		return false;
	}

	@Override public void afterPropertiesSet() throws Exception {
		if (httpClient == null) {
			httpClient = new DefaultHttpClient();
		}

	}
}
