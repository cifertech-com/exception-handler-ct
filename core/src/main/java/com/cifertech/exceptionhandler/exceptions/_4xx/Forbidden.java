package com.cifertech.exceptionhandler.exceptions._4xx;

import com.cifertech.exceptionhandler.exceptions.ApiException;
import com.cifertech.exceptionhandler.exceptions.HttpCode;

import java.util.Map;

public class Forbidden extends ApiException {

    public Forbidden(Object details) {
        super(HttpCode.FORBIDDEN, details);
    }

    public Forbidden(String message) {
        super(HttpCode.FORBIDDEN, Map.of("message", message));
    }

    public Forbidden(){
        super(HttpCode.FORBIDDEN, null);
    }
}
