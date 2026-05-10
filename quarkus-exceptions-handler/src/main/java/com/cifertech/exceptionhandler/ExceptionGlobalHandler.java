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
