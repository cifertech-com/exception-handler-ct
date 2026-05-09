package com.cifertech.exceptionhandler;

import com.cifertech.exceptionhandler.exceptions.ApiException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

@Provider
public class ExceptionHandler implements ExceptionMapper<ApiException> {

    private static final Logger log = Logger.getLogger(ExceptionHandler.class.getName());

    @org.eclipse.microprofile.config.inject.ConfigProperty(name = "exception.handler.default-message", defaultValue = "Oops, something unexpected happens")
    String message;

    @Override
    public Response toResponse(ApiException exception) {

        log.log(Level.SEVERE, exception.getMessage(), exception);
        return Response.status(exception.getHttpCode().getCode())
                .entity(getDetail(exception))
                .build();
    }

    private Object getDetail(ApiException exception){
        if (exception.getDetails() == null) {
            return Map.of("message", message);
        }
        return exception.getDetails();
    }
}
