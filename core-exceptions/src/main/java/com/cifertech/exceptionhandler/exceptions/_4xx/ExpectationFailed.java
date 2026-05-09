package com.cifertech.exceptionhandler.exceptions._4xx;

import com.cifertech.exceptionhandler.exceptions.ApiException;
import com.cifertech.exceptionhandler.exceptions.HttpCode;

import java.util.Map;

public class ExpectationFailed extends ApiException {

    public ExpectationFailed(Object details) {
        super(HttpCode.EXPECTATION_FAILED, details);
    }

    public ExpectationFailed(String message) {
        super(HttpCode.EXPECTATION_FAILED, Map.of("message", message));
    }

    public ExpectationFailed(){
        super(HttpCode.EXPECTATION_FAILED, null);
    }
}
