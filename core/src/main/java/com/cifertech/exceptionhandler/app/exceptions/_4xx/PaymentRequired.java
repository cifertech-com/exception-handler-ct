package com.cifertech.exceptionhandler.app.exceptions._4xx;

import com.cifertech.exceptionhandler.app.exceptions.ApiException;
import com.cifertech.exceptionhandler.app.exceptions.HttpCode;

import java.util.Map;

public class PaymentRequired extends ApiException {

    public PaymentRequired(Object details) {
        super(HttpCode.PAYMENT_REQUIRED, details);
    }

    public PaymentRequired(String message) {
        super(HttpCode.PAYMENT_REQUIRED, Map.of("message", message));
    }

    public PaymentRequired(){
        super(HttpCode.PAYMENT_REQUIRED, null);
    }
}
