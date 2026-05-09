package com.cifertech.exceptionhandler.exceptions._4xx;

import com.cifertech.exceptionhandler.exceptions.ApiException;
import com.cifertech.exceptionhandler.exceptions.HttpCode;

import java.util.Map;

public class MisdirectedRequest extends ApiException {

    public MisdirectedRequest(Object details) {
        super(HttpCode.MISDIRECTED_REQUEST, details);
    }

    public MisdirectedRequest(String message) {
        super(HttpCode.MISDIRECTED_REQUEST, Map.of("message", message));
    }

    public MisdirectedRequest(){
        super(HttpCode.MISDIRECTED_REQUEST, null);
    }
}
