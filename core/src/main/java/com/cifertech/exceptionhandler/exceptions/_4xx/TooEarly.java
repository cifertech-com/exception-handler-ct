package com.cifertech.exceptionhandler.exceptions._4xx;

import com.cifertech.exceptionhandler.exceptions.ApiException;
import com.cifertech.exceptionhandler.exceptions.HttpCode;

import java.util.Map;

public class TooEarly extends ApiException {

    public TooEarly(Object details) {
        super(HttpCode.TOO_EARLY, details);
    }

    public TooEarly(String message) {
        super(HttpCode.TOO_EARLY, Map.of("message", message));
    }

    public TooEarly(){
        super(HttpCode.TOO_EARLY, null);
    }
}
