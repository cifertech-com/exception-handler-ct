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

import com.cifertech.exceptionhandler.exceptions.ApiException;
import com.cifertech.exceptionhandler.exceptions.HttpCode;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

 class ExceptionHandlerTest {

    private ExceptionHandler handler;

    @BeforeEach
    void setUp() {
        handler = new ExceptionHandler();
    }

    @Test
    void testToResponseWithNullDetailsAndDefaultMessage() {
        // Arrange
        handler.message = "Oops, something unexpected happens";
        ApiException testException = new ApiException(HttpCode.BAD_REQUEST, null);

        // Act
        Response response = handler.toResponse(testException);

        // Assert
        assertNotNull(response);
        assertEquals(400, response.getStatus());
        
        assertTrue(response.getEntity() instanceof Map);
        Map<?, ?> entity = (Map<?, ?>) response.getEntity();
        assertEquals("Oops, something unexpected happens", entity.get("message"));
    }

    @Test
    void testToResponseWithNullDetailsAndCustomConfiguredMessage() {
        // Arrange
        handler.message = "A dynamically configured default message";
        ApiException testException = new ApiException(HttpCode.UNAUTHORIZED, null);

        // Act
        Response response = handler.toResponse(testException);

        // Assert
        assertNotNull(response);
        assertEquals(401, response.getStatus());
        
        assertTrue(response.getEntity() instanceof Map);
        Map<?, ?> entity = (Map<?, ?>) response.getEntity();
        assertEquals("A dynamically configured default message", entity.get("message"));
    }

    @Test
    void testToResponseWithCustomDetailsString() {
        // Arrange
        handler.message = "Oops, something unexpected happens";
        String customDetails = "El usuario con ID 10 no existe.";
        ApiException testException = new ApiException(HttpCode.NOT_FOUND, customDetails);

        // Act
        Response response = handler.toResponse(testException);

        // Assert
        assertNotNull(response);
        assertEquals(404, response.getStatus());
        assertEquals("El usuario con ID 10 no existe.", response.getEntity());
    }

    @Test
    void testToResponseWithCustomDetailsMap() {
        // Arrange
        handler.message = "Oops, something unexpected happens";
        Map<String, String> customDetails = Map.of("error", "Validation failed", "field", "email");
        ApiException testException = new ApiException(HttpCode.UNPROCESSABLE_ENTITY, customDetails);

        // Act
        Response response = handler.toResponse(testException);

        // Assert
        assertNotNull(response);
        assertEquals(422, response.getStatus());
        
        assertTrue(response.getEntity() instanceof Map);
        Map<?, ?> entity = (Map<?, ?>) response.getEntity();
        assertEquals("Validation failed", entity.get("error"));
        assertEquals("email", entity.get("field"));
    }
}
