package com.cifertech.exceptionhandler;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

@Provider
public class ExceptionGlobalHandler implements ExceptionMapper<Throwable>{

    private static final Logger log = Logger.getLogger(ExceptionGlobalHandler.class.getName());

    @org.eclipse.microprofile.config.inject.ConfigProperty(name = "exception.handler.default-message", defaultValue = "Oops, something unexpected happens")
    String message;

    @Override
    public Response toResponse(Throwable throwable) {
        log.log(Level.SEVERE, throwable.getMessage(), throwable);
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(Map.of("message", message))
                .build();
    }

}
