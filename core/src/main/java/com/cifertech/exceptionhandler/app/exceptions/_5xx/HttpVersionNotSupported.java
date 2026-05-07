package com.cifertech.exceptionhandler.app.exceptions._5xx;

import com.cifertech.exceptionhandler.app.exceptions.ApiException;
import com.cifertech.exceptionhandler.app.exceptions.HttpCode;

import java.util.Map;

public class HttpVersionNotSupported extends ApiException {

    public HttpVersionNotSupported(Object details) {
        super(HttpCode.HTTP_VERSION_NOT_SUPPORTED, details);
    }

    public HttpVersionNotSupported(String message) {
        super(HttpCode.HTTP_VERSION_NOT_SUPPORTED, Map.of("message", message));
    }

    public HttpVersionNotSupported(){
        super(HttpCode.HTTP_VERSION_NOT_SUPPORTED, null);
    }
}
