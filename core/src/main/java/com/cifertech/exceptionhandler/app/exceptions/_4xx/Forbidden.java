package com.cifertech.exceptionhandler.app.exceptions._4xx;

import com.cifertech.exceptionhandler.app.exceptions.ApiException;
import com.cifertech.exceptionhandler.app.exceptions.HttpCode;

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
