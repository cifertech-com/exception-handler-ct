package com.cifertech.exceptionhandler.app.exceptions._4xx;

import com.cifertech.exceptionhandler.app.exceptions.ApiException;
import com.cifertech.exceptionhandler.app.exceptions.HttpCode;

import java.util.Map;

public class PayloadTooLarge extends ApiException {

    public PayloadTooLarge(Object details) {
        super(HttpCode.PAYLOAD_TOO_LARGE, details);
    }

    public PayloadTooLarge(String message) {
        super(HttpCode.PAYLOAD_TOO_LARGE, Map.of("message", message));
    }

    public PayloadTooLarge(){
        super(HttpCode.PAYLOAD_TOO_LARGE, null);
    }
}
