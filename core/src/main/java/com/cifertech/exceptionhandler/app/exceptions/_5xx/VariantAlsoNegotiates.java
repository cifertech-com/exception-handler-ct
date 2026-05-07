package com.cifertech.exceptionhandler.app.exceptions._5xx;

import com.cifertech.exceptionhandler.app.exceptions.ApiException;
import com.cifertech.exceptionhandler.app.exceptions.HttpCode;

import java.util.Map;

public class VariantAlsoNegotiates extends ApiException {

    public VariantAlsoNegotiates(Object details) {
        super(HttpCode.VARIANT_ALSO_NEGOTIATES, details);
    }

    public VariantAlsoNegotiates(String message) {
        super(HttpCode.VARIANT_ALSO_NEGOTIATES, Map.of("message", message));
    }

    public VariantAlsoNegotiates(){
        super(HttpCode.VARIANT_ALSO_NEGOTIATES, null);
    }
}
