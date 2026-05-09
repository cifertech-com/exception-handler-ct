package com.cifertech.exceptionhandler.exceptions._4xx;

import com.cifertech.exceptionhandler.exceptions.ApiException;
import com.cifertech.exceptionhandler.exceptions.HttpCode;

import java.util.Map;

public class UnavailableForLegalReasons extends ApiException {

    public UnavailableForLegalReasons(Object details) {
        super(HttpCode.UNAVAILABLE_FOR_LEGAL_REASONS, details);
    }

    public UnavailableForLegalReasons(String message) {
        super(HttpCode.UNAVAILABLE_FOR_LEGAL_REASONS, Map.of("message", message));
    }

    public UnavailableForLegalReasons(){
        super(HttpCode.UNAVAILABLE_FOR_LEGAL_REASONS, null);
    }
}
