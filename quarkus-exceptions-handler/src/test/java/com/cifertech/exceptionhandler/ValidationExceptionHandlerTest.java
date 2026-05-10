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
import jakarta.validation.Path;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ValidationExceptionHandlerTest {

    private ValidationExceptionHandler handler;

    @BeforeEach
    void setUp() {
        handler = new ValidationExceptionHandler();
    }

    @Test
    void testToResponseWithViolations() {
        // Arrange
        ConstraintViolation<?> violation1 = mock(ConstraintViolation.class);
        Path path1 = mock(Path.class);
        when(path1.toString()).thenReturn("user.profile.email");
        when(violation1.getPropertyPath()).thenReturn(path1);
        when(violation1.getMessage()).thenReturn("must be a well-formed email address");

        ConstraintViolation<?> violation2 = mock(ConstraintViolation.class);
        Path path2 = mock(Path.class);
        when(path2.toString()).thenReturn("user.age");
        when(violation2.getPropertyPath()).thenReturn(path2);
        when(violation2.getMessage()).thenReturn("must be greater than or equal to 18");

        ConstraintViolationException exception = new ConstraintViolationException(
                "Validation failed",
                Set.of(violation1, violation2)
        );

        // Act
        Response response = handler.toResponse(exception);

        // Assert
        assertNotNull(response);
        assertEquals(400, response.getStatus());

        assertTrue(response.getEntity() instanceof Map);
        Map<?, ?> entity = (Map<?, ?>) response.getEntity();

        assertEquals(2, entity.size());
        assertEquals("must be a well-formed email address", entity.get("email"));
        assertEquals("must be greater than or equal to 18", entity.get("age"));
    }

    @Test
    void testToResponseWithEmptyPath() {
        // Arrange
        ConstraintViolation<?> violation1 = mock(ConstraintViolation.class);
        Path path1 = mock(Path.class);
        when(path1.toString()).thenReturn("");
        when(violation1.getPropertyPath()).thenReturn(path1);
        when(violation1.getMessage()).thenReturn("invalid value");

        ConstraintViolationException exception = new ConstraintViolationException(
                "Validation failed",
                Set.of(violation1)
        );

        // Act
        Response response = handler.toResponse(exception);

        // Assert
        assertNotNull(response);
        assertEquals(400, response.getStatus());

        assertTrue(response.getEntity() instanceof Map);
        Map<?, ?> entity = (Map<?, ?>) response.getEntity();

        assertEquals(1, entity.size());
        assertEquals("invalid value", entity.get(""));
    }
}
