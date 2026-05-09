package com.cifertech.exceptionhandler.exceptions._4xx;

import com.cifertech.exceptionhandler.exceptions.ApiException;
import com.cifertech.exceptionhandler.exceptions.HttpCode;

import java.util.Map;

public class PreconditionFailed extends ApiException {

    public PreconditionFailed(Object details) {
        super(HttpCode.PRECONDITION_FAILED, details);
    }

    public PreconditionFailed(String message) {
        super(HttpCode.PRECONDITION_FAILED, Map.of("message", message));
    }

    public PreconditionFailed(){
        super(HttpCode.PRECONDITION_FAILED, null);
    }
}
