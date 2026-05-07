package com.cifertech.exceptionhandler.app.exceptions._4xx;

import com.cifertech.exceptionhandler.app.exceptions.ApiException;
import com.cifertech.exceptionhandler.app.exceptions.HttpCode;

import java.util.Map;

public class BadRequest extends ApiException {

    public BadRequest(Object details) {
        super(HttpCode.BAD_REQUEST, details);
    }

    public BadRequest(String message) {
        super(HttpCode.BAD_REQUEST, Map.of("message", message));
    }

    public BadRequest(){
        super(HttpCode.BAD_REQUEST, null);
    }
}
