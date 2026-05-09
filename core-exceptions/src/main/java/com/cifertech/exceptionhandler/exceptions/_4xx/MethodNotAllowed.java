package com.cifertech.exceptionhandler.exceptions._4xx;

import com.cifertech.exceptionhandler.exceptions.ApiException;
import com.cifertech.exceptionhandler.exceptions.HttpCode;

import java.util.Map;

public class MethodNotAllowed extends ApiException {

    public MethodNotAllowed(Object details) {
        super(HttpCode.METHOD_NOT_ALLOWED, details);
    }

    public MethodNotAllowed(String message) {
        super(HttpCode.METHOD_NOT_ALLOWED, Map.of("message", message));
    }

    public MethodNotAllowed(){
        super(HttpCode.METHOD_NOT_ALLOWED, null);
    }
}
