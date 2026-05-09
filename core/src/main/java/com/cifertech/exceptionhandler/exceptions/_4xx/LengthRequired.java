package com.cifertech.exceptionhandler.exceptions._4xx;

import com.cifertech.exceptionhandler.exceptions.ApiException;
import com.cifertech.exceptionhandler.exceptions.HttpCode;

import java.util.Map;

public class LengthRequired extends ApiException {

    public LengthRequired(Object details) {
        super(HttpCode.LENGTH_REQUIRED, details);
    }

    public LengthRequired(String message) {
        super(HttpCode.LENGTH_REQUIRED, Map.of("message", message));
    }

    public LengthRequired(){
        super(HttpCode.LENGTH_REQUIRED, null);
    }
}
