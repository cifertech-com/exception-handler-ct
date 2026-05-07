package com.cifertech.exceptionhandler.app.exceptions._5xx;

import com.cifertech.exceptionhandler.app.exceptions.ApiException;
import com.cifertech.exceptionhandler.app.exceptions.HttpCode;

import java.util.Map;

public class NetworkAuthenticationRequired extends ApiException {

    public NetworkAuthenticationRequired(Object details) {
        super(HttpCode.NETWORK_AUTHENTICATION_REQUIRED, details);
    }

    public NetworkAuthenticationRequired(String message) {
        super(HttpCode.NETWORK_AUTHENTICATION_REQUIRED, Map.of("message", message));
    }

    public NetworkAuthenticationRequired(){
        super(HttpCode.NETWORK_AUTHENTICATION_REQUIRED, null);
    }
}
