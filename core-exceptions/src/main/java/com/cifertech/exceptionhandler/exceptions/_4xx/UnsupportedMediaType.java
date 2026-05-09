package com.cifertech.exceptionhandler.exceptions._4xx;

import com.cifertech.exceptionhandler.exceptions.ApiException;
import com.cifertech.exceptionhandler.exceptions.HttpCode;

import java.util.Map;

public class UnsupportedMediaType extends ApiException {

    public UnsupportedMediaType(Object details) {
        super(HttpCode.UNSUPPORTED_MEDIA_TYPE, details);
    }

    public UnsupportedMediaType(String message) {
        super(HttpCode.UNSUPPORTED_MEDIA_TYPE, Map.of("message", message));
    }

    public UnsupportedMediaType(){
        super(HttpCode.UNSUPPORTED_MEDIA_TYPE, null);
    }
}
