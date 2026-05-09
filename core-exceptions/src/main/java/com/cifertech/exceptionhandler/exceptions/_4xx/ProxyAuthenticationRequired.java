package com.cifertech.exceptionhandler.exceptions._4xx;

import com.cifertech.exceptionhandler.exceptions.ApiException;
import com.cifertech.exceptionhandler.exceptions.HttpCode;

import java.util.Map;

public class ProxyAuthenticationRequired extends ApiException {

    public ProxyAuthenticationRequired(Object details) {
        super(HttpCode.PROXY_AUTHENTICATION_REQUIRED, details);
    }

    public ProxyAuthenticationRequired(String message) {
        super(HttpCode.PROXY_AUTHENTICATION_REQUIRED, Map.of("message", message));
    }

    public ProxyAuthenticationRequired(){
        super(HttpCode.PROXY_AUTHENTICATION_REQUIRED, null);
    }
}
