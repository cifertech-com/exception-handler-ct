package com.cifertech.exceptionhandler.app;

import com.cifertech.exceptionhandler.app.exceptions.ApiException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

@Provider
public class ExceptionHandler implements ExceptionMapper<ApiException> {

    private static final Logger log = Logger.getLogger(ExceptionHandler.class.getName());
    private static final String MESSAGE = "Oops, something unexpected happens";

    @Override
    public Response toResponse(ApiException exception) {

        log.log(Level.SEVERE, exception.getMessage(), exception);
        return Response.status(exception.getHttpCode().getCode())
                .entity(getDetail(exception))
                .build();
    }

    private Object getDetail(ApiException exception){
        if (exception.getDetails() == null) {
            return Map.of("message", MESSAGE);
        }
        return exception.getDetails();
    }
}
