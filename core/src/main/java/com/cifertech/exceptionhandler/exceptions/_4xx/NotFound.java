package com.cifertech.exceptionhandler.exceptions._4xx;

import com.cifertech.exceptionhandler.exceptions.ApiException;
import com.cifertech.exceptionhandler.exceptions.HttpCode;

import java.util.Map;

public class NotFound extends ApiException {

    public NotFound(Object details) {
        super(HttpCode.NOT_FOUND, details);
    }

    public NotFound(String message) {
        super(HttpCode.NOT_FOUND, Map.of("message", message));
    }

    public NotFound(){
        super(HttpCode.NOT_FOUND, null);
    }
}
