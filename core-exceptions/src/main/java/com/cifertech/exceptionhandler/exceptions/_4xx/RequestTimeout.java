package com.cifertech.exceptionhandler.exceptions._4xx;

import com.cifertech.exceptionhandler.exceptions.ApiException;
import com.cifertech.exceptionhandler.exceptions.HttpCode;

import java.util.Map;

public class RequestTimeout extends ApiException {

    public RequestTimeout(Object details) {
        super(HttpCode.REQUEST_TIMEOUT, details);
    }

    public RequestTimeout(String message) {
        super(HttpCode.REQUEST_TIMEOUT, Map.of("message", message));
    }

    public RequestTimeout(){
        super(HttpCode.REQUEST_TIMEOUT, null);
    }
}
