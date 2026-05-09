package com.cifertech.exceptionhandler.exceptions._5xx;

import com.cifertech.exceptionhandler.exceptions.ApiException;
import com.cifertech.exceptionhandler.exceptions.HttpCode;

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
