package com.brandpath.utils;

import com.consol.citrus.message.MessageType;

/**
 * Created by bartoszt on 22.03.2017.
 */
public class TokenHelper extends  Preparation {

    /**
     * token is as variable soapToken
     */
    public void pimToken() {
        soap().client("pimTokenClient")
                .send()
                .header("citrus_http_userName","desilacolUser1")
                .header("citrus_http_password","MdQ2XY!jSD.mn3")
                .payload("<soap:getToken xmlns:soap=\"http://sei.service.pim.brandpath.com/\"/>");

        soap().client("pimTokenClient")
                .receive()
                .schemaValidation(false)
                .extractFromPayload("//return/token","soapToken")
                .timeout(5000);

    }

    /**
     * token is as variable token
     *
     */
    public void wsToken() {


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
    }
}
