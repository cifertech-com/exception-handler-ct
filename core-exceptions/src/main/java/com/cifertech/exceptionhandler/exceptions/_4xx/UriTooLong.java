package com.cifertech.exceptionhandler.exceptions._4xx;

import com.cifertech.exceptionhandler.exceptions.ApiException;
import com.cifertech.exceptionhandler.exceptions.HttpCode;

import java.util.Map;

public class UriTooLong extends ApiException {

    public UriTooLong(Object details) {
        super(HttpCode.URI_TOO_LONG, details);
    }

    public UriTooLong(String message) {
        super(HttpCode.URI_TOO_LONG, Map.of("message", message));
    }

    public UriTooLong(){
        super(HttpCode.URI_TOO_LONG, null);
    }
}
