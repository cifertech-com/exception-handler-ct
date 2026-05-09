package com.cifertech.exceptionhandler.exceptions._4xx;

import com.cifertech.exceptionhandler.exceptions.ApiException;
import com.cifertech.exceptionhandler.exceptions.HttpCode;

import java.util.Map;

public class PreconditionRequired extends ApiException {

    public PreconditionRequired(Object details) {
        super(HttpCode.PRECONDITION_REQUIRED, details);
    }

    public PreconditionRequired(String message) {
        super(HttpCode.PRECONDITION_REQUIRED, Map.of("message", message));
    }

    public PreconditionRequired(){
        super(HttpCode.PRECONDITION_REQUIRED, null);
    }
}
