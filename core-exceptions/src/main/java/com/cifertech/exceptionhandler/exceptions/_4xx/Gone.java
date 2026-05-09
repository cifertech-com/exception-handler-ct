package com.cifertech.exceptionhandler.exceptions._4xx;

import com.cifertech.exceptionhandler.exceptions.ApiException;
import com.cifertech.exceptionhandler.exceptions.HttpCode;

import java.util.Map;

public class Gone extends ApiException {

    public Gone(Object details) {
        super(HttpCode.GONE, details);
    }

    public Gone(String message) {
        super(HttpCode.GONE, Map.of("message", message));
    }

    public Gone(){
        super(HttpCode.GONE, null);
    }
}
