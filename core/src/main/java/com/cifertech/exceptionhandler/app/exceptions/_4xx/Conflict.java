package com.cifertech.exceptionhandler.app.exceptions._4xx;

import com.cifertech.exceptionhandler.app.exceptions.ApiException;
import com.cifertech.exceptionhandler.app.exceptions.HttpCode;

import java.util.Map;

public class Conflict extends ApiException {

    public Conflict(Object details) {
        super(HttpCode.CONFLICT, details);
    }

    public Conflict(String message) {
        super(HttpCode.CONFLICT, Map.of("message", message));
    }

    public Conflict(){
        super(HttpCode.CONFLICT, null);
    }
}
