package com.cifertech.exceptionhandler;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.util.stream.Collectors.toMap;

@Provider
public class ValidationExceptionHandler implements ExceptionMapper<ConstraintViolationException> {

    private static final Logger log = Logger.getLogger(ValidationExceptionHandler.class.getName());

    @Override
    public Response toResponse(ConstraintViolationException exception) {

        log.log(Level.SEVERE, exception.getMessage(), exception);
        Map<String, String> details = exception.getConstraintViolations().stream()
                .collect(toMap(
                        ValidationExceptionHandler::getFieldName,
                        ConstraintViolation::getMessage
                ));

        return Response.status(Response.Status.BAD_REQUEST)
                .entity(details)
                .build();
    }

    private static String getFieldName(ConstraintViolation<?> violation) {
        String[] path = violation.getPropertyPath().toString().split("\\.");
        return path.length > 0 ? path[path.length - 1] : "unknown";
    }

}