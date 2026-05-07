package com.cifertech.exceptionhandler.app.exceptions._4xx;

import com.cifertech.exceptionhandler.app.exceptions.ApiException;
import com.cifertech.exceptionhandler.app.exceptions.HttpCode;

import java.util.Map;

public class IAmATeapot extends ApiException {

    public IAmATeapot(Object details) {
        super(HttpCode.I_AM_A_TEAPOT, details);
    }

    public IAmATeapot(String message) {
        super(HttpCode.I_AM_A_TEAPOT, Map.of("message", message));
    }

    public IAmATeapot(){
        super(HttpCode.I_AM_A_TEAPOT, null);
    }
}
