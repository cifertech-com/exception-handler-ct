package com.cifertech.exceptionhandler.exceptions._4xx;

import com.cifertech.exceptionhandler.exceptions.ApiException;
import com.cifertech.exceptionhandler.exceptions.HttpCode;

import java.util.Map;

public class Unauthorized extends ApiException {

    public Unauthorized(Object details) {
        super(HttpCode.UNAUTHORIZED, details);
    }

    public Unauthorized(String message) {
        super(HttpCode.UNAUTHORIZED, Map.of("message", message));
    }

    public Unauthorized(){
        super(HttpCode.UNAUTHORIZED, null);
    }
}
