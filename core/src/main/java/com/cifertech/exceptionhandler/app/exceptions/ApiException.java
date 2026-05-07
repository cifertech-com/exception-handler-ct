package com.cifertech.exceptionhandler.app.exceptions;

public class ApiException extends RuntimeException {
    private final transient HttpCode httpCode;
    private final transient Object detail;


    public ApiException(HttpCode httpCode, Object details) {
        this.httpCode = httpCode;
        this.detail = details;
    }

    public HttpCode getHttpCode() {
        return httpCode;
    }


    public Object getDetails() {
        return detail;
    }
}
