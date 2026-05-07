package com.cifertech.exceptionhandler.app.exceptions._4xx;

import com.cifertech.exceptionhandler.app.exceptions.ApiException;
import com.cifertech.exceptionhandler.app.exceptions.HttpCode;

import java.util.Map;

public class Locked extends ApiException {

    public Locked(Object details) {
        super(HttpCode.LOCKED, details);
    }

    public Locked(String message) {
        super(HttpCode.LOCKED, Map.of("message", message));
    }

    public Locked(){
        super(HttpCode.LOCKED, null);
    }
}
