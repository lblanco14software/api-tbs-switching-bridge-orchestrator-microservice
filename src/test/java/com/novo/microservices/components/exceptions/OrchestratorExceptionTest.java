package com.novo.microservices.components.exceptions;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * OrchestratorExceptionTest
 * <p>
 * OrchestratorExceptionTest class.
 * <p>
 * <p>
 * ESTE COMPONENTE FUE CONSTRUIDO SIGUIENDO LOS ESTANDARES DE DESARROLLO Y EL PROCEDIMIENTO
 * DE DESARROLLO DE APLICACIONES DE NOVOPAYMENT Y SE ENCUENTRA PROTEGIDO POR LAS LEYES DE
 * PROPIEDAD INTELECTUAL Y DERECHOS DE AUTOR.
 *
 * @author Novopayment Inc.
 * @author lblanco@novopayment.com
 * @since 4/19/2022
 */
@SpringBootTest
@AutoConfigureMockMvc
public class OrchestratorExceptionTest {

    private static final String EXCEPTION_MESSAGE = "exception message test";
    private static final String CUSTOM_EXCEPTION_MESSAGE = "other exception message test";

    @Test
    @Order(1)
    @DisplayName("Business Process Exception Constructor Test")
    void testConstructor() {

        // Given
        // When
        final OrchestratorException instance = assertThrows(OrchestratorException.class, this::executeBusinessProcessException);

        // Then
        Assertions.assertNotNull(instance);
        Assertions.assertEquals(EXCEPTION_MESSAGE, instance.getMessage());
    }

    void executeBusinessProcessException() {
        throw new OrchestratorException(EXCEPTION_MESSAGE);
    }
}