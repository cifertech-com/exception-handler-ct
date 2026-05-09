package com.cifertech.exceptionhandler.exceptions._5xx;

import com.cifertech.exceptionhandler.exceptions.ApiException;
import com.cifertech.exceptionhandler.exceptions.HttpCode;

import java.util.Map;

public class NotExtended extends ApiException {

    public NotExtended(Object details) {
        super(HttpCode.NOT_EXTENDED, details);
    }

    public NotExtended(String message) {
        super(HttpCode.NOT_EXTENDED, Map.of("message", message));
    }

    public NotExtended(){
        super(HttpCode.NOT_EXTENDED, null);
    }
}
