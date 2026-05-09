package com.cifertech.exceptionhandler.exceptions._4xx;

import com.cifertech.exceptionhandler.exceptions.ApiException;
import com.cifertech.exceptionhandler.exceptions.HttpCode;

import java.util.Map;

public class BadGateway extends ApiException {

    public BadGateway(Object details) {
        super(HttpCode.BAD_GATEWAY, details);
    }

    public BadGateway(String message) {
        super(HttpCode.BAD_GATEWAY, Map.of("message", message));
    }

    public BadGateway(){
        super(HttpCode.BAD_GATEWAY, null);
    }
}
