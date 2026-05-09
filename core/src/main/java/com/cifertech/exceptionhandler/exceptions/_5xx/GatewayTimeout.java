package com.cifertech.exceptionhandler.exceptions._5xx;

import com.cifertech.exceptionhandler.exceptions.ApiException;
import com.cifertech.exceptionhandler.exceptions.HttpCode;

import java.util.Map;

public class GatewayTimeout extends ApiException {

    public GatewayTimeout(Object details) {
        super(HttpCode.GATEWAY_TIMEOUT, details);
    }

    public GatewayTimeout(String message) {
        super(HttpCode.GATEWAY_TIMEOUT, Map.of("message", message));
    }

    public GatewayTimeout(){
        super(HttpCode.GATEWAY_TIMEOUT, null);
    }
}
