package com.cifertech.exceptionhandler.exceptions._5xx;

import com.cifertech.exceptionhandler.exceptions.ApiException;
import com.cifertech.exceptionhandler.exceptions.HttpCode;

import java.util.Map;

public class NotImplemented extends ApiException {

    public NotImplemented(Object details) {
        super(HttpCode.NOT_IMPLEMENTED, details);
    }

    public NotImplemented(String message) {
        super(HttpCode.NOT_IMPLEMENTED, Map.of("message", message));
    }

    public NotImplemented(){
        super(HttpCode.NOT_IMPLEMENTED, null);
    }
}
