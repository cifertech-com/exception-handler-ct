package com.cifertech.exceptionhandler.app.exceptions._4xx;

import com.cifertech.exceptionhandler.app.exceptions.ApiException;
import com.cifertech.exceptionhandler.app.exceptions.HttpCode;

import java.util.Map;

public class UnprocessableEntity extends ApiException {

    public UnprocessableEntity(Object details) {
        super(HttpCode.UNPROCESSABLE_ENTITY, details);
    }

    public UnprocessableEntity(String message) {
        super(HttpCode.UNPROCESSABLE_ENTITY, Map.of("message", message));
    }

    public UnprocessableEntity(){
        super(HttpCode.UNPROCESSABLE_ENTITY, null);
    }
}
