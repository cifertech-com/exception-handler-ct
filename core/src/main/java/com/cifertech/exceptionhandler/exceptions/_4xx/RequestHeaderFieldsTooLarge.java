package com.cifertech.exceptionhandler.exceptions._4xx;

import com.cifertech.exceptionhandler.exceptions.ApiException;
import com.cifertech.exceptionhandler.exceptions.HttpCode;

import java.util.Map;

public class RequestHeaderFieldsTooLarge extends ApiException {

    public RequestHeaderFieldsTooLarge(Object details) {
        super(HttpCode.REQUEST_HEADER_FIELDS_TOO_LARGE, details);
    }

    public RequestHeaderFieldsTooLarge(String message) {
        super(HttpCode.REQUEST_HEADER_FIELDS_TOO_LARGE, Map.of("message", message));
    }

    public RequestHeaderFieldsTooLarge(){
        super(HttpCode.REQUEST_HEADER_FIELDS_TOO_LARGE, null);
    }
}
