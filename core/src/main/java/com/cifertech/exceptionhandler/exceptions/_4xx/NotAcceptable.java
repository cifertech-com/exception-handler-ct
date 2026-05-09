package com.cifertech.exceptionhandler.exceptions._4xx;

import com.cifertech.exceptionhandler.exceptions.ApiException;
import com.cifertech.exceptionhandler.exceptions.HttpCode;

import java.util.Map;

public class NotAcceptable extends ApiException {

    public NotAcceptable(Object details) {
        super(HttpCode.NOT_ACCEPTABLE, details);
    }

    public NotAcceptable(String message) {
        super(HttpCode.NOT_ACCEPTABLE, Map.of("message", message));
    }

    public NotAcceptable(){
        super(HttpCode.NOT_ACCEPTABLE, null);
    }
}
