package com.cifertech.exceptionhandler.app.exceptions._4xx;

import com.cifertech.exceptionhandler.app.exceptions.ApiException;
import com.cifertech.exceptionhandler.app.exceptions.HttpCode;

import java.util.Map;

public class RangeNotSatisfiable extends ApiException {

    public RangeNotSatisfiable(Object details) {
        super(HttpCode.RANGE_NOT_SATISFIABLE, details);
    }

    public RangeNotSatisfiable(String message) {
        super(HttpCode.RANGE_NOT_SATISFIABLE, Map.of("message", message));
    }

    public RangeNotSatisfiable(){
        super(HttpCode.RANGE_NOT_SATISFIABLE, null);
    }
}
