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
