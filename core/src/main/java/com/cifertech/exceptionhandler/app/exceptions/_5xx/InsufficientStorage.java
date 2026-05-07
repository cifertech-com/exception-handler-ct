package com.cifertech.exceptionhandler.app.exceptions._5xx;

import com.cifertech.exceptionhandler.app.exceptions.ApiException;
import com.cifertech.exceptionhandler.app.exceptions.HttpCode;

import java.util.Map;

public class InsufficientStorage extends ApiException {

    public InsufficientStorage(Object details) {
        super(HttpCode.INSUFFICIENT_STORAGE, details);
    }

    public InsufficientStorage(String message) {
        super(HttpCode.INSUFFICIENT_STORAGE, Map.of("message", message));
    }

    public InsufficientStorage(){
        super(HttpCode.INSUFFICIENT_STORAGE, null);
    }
}
