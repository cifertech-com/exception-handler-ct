package com.cifertech.exceptionhandler.app.exceptions._5xx;

import com.cifertech.exceptionhandler.app.exceptions.ApiException;
import com.cifertech.exceptionhandler.app.exceptions.HttpCode;

import java.util.Map;

public class InternalServerError extends ApiException {

    public InternalServerError(Object details) {
        super(HttpCode.INTERNAL_SERVER_ERROR, details);
    }

    public InternalServerError(String message) {
        super(HttpCode.INTERNAL_SERVER_ERROR, Map.of("message", message));
    }

    public InternalServerError(){
        super(HttpCode.INTERNAL_SERVER_ERROR, null);
    }
}
