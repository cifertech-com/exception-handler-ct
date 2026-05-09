package com.cifertech.exceptionhandler.exceptions._5xx;

import com.cifertech.exceptionhandler.exceptions.ApiException;
import com.cifertech.exceptionhandler.exceptions.HttpCode;

import java.util.Map;

public class LoopDetected extends ApiException {

    public LoopDetected(Object details) {
        super(HttpCode.LOOP_DETECTED, details);
    }

    public LoopDetected(String message) {
        super(HttpCode.LOOP_DETECTED, Map.of("message", message));
    }

    public LoopDetected(){
        super(HttpCode.LOOP_DETECTED, null);
    }
}
