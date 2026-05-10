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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ExceptionGlobalHandlerTest {

    private ExceptionGlobalHandler handler;

    @BeforeEach
    void setUp() {
        handler = new ExceptionGlobalHandler();
    }

    @Test
    void testToResponseWithDefaultMessage() {
        // Arrange
        handler.message = "Oops, something unexpected happens";
        Throwable testException = new RuntimeException("This is a simulated unexpected error");

        // Act
        Response response = handler.toResponse(testException);

        // Assert
        assertNotNull(response);
        assertEquals(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), response.getStatus());
        
        assertTrue(response.getEntity() instanceof Map);
        Map<?, ?> entity = (Map<?, ?>) response.getEntity();
        assertEquals("Oops, something unexpected happens", entity.get("message"));
    }

    @Test
    void testToResponseWithCustomMessage() {
        // Arrange
        handler.message = "A custom unexpected error message here";
        Throwable testException = new NullPointerException("Simulated NPE");

        // Act
        Response response = handler.toResponse(testException);

        // Assert
        assertNotNull(response);
        assertEquals(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), response.getStatus());
        
        assertTrue(response.getEntity() instanceof Map);
        Map<?, ?> entity = (Map<?, ?>) response.getEntity();
        assertEquals("A custom unexpected error message here", entity.get("message"));
    }
}
