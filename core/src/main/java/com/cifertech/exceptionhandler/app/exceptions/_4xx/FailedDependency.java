package com.cifertech.exceptionhandler.app.exceptions._4xx;

import com.cifertech.exceptionhandler.app.exceptions.ApiException;
import com.cifertech.exceptionhandler.app.exceptions.HttpCode;

import java.util.Map;

public class FailedDependency extends ApiException {

    public FailedDependency(Object details) {
        super(HttpCode.FAILED_DEPENDENCY, details);
    }

    public FailedDependency(String message) {
        super(HttpCode.FAILED_DEPENDENCY, Map.of("message", message));
    }

    public FailedDependency(){
        super(HttpCode.FAILED_DEPENDENCY, null);
    }
}
