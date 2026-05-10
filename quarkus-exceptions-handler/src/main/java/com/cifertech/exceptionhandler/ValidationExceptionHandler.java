/*
 * Copyright © 2026 CiferTech (yacson.ramirez@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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