package com.cifertech.exceptionhandler.app.exceptions._5xx;

import com.cifertech.exceptionhandler.app.exceptions.ApiException;
import com.cifertech.exceptionhandler.app.exceptions.HttpCode;

import java.util.Map;

public class ServiceUnavailable extends ApiException {

    public ServiceUnavailable(Object details) {
        super(HttpCode.SERVICE_UNAVAILABLE, details);
    }

    public ServiceUnavailable(String message) {
        super(HttpCode.SERVICE_UNAVAILABLE, Map.of("message", message));
    }

    public ServiceUnavailable(){
        super(HttpCode.SERVICE_UNAVAILABLE, null);
    }
}
