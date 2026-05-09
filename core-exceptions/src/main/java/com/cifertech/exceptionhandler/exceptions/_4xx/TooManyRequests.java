package com.cifertech.exceptionhandler.exceptions._4xx;

import com.cifertech.exceptionhandler.exceptions.ApiException;
import com.cifertech.exceptionhandler.exceptions.HttpCode;

import java.util.Map;

public class TooManyRequests extends ApiException {

    public TooManyRequests(Object details) {
        super(HttpCode.TOO_MANY_REQUESTS, details);
    }

    public TooManyRequests(String message) {
        super(HttpCode.TOO_MANY_REQUESTS, Map.of("message", message));
    }

    public TooManyRequests(){
        super(HttpCode.TOO_MANY_REQUESTS, null);
    }
}
