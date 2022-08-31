package com.novo.microservices.components.exceptions;

import com.novo.microservices.components.enums.ResponseCode;
import com.novo.microservices.tbs.utils.components.exceptions.BusinessProcessException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * BusinessProcessExceptionTest
 * <p>
 * BusinessProcessExceptionTest class.
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
public class BusinessProcessExceptionTest {

    private static final String EXCEPTION_MESSAGE = "exception message test";
    private static final String CUSTOM_EXCEPTION_MESSAGE = "other exception message test";

    @Test
    @Order(1)
    @DisplayName("Business Process Exception Constructor Test")
    void testConstructor() {

        // Given
        final ResponseCode responseCodeExpected = ResponseCode.INTERNAL_SERVER_ERROR;

        // When
        final BusinessProcessException instance = assertThrows(BusinessProcessException.class, this::executeBusinessProcessException);

        // Then
        Assertions.assertNotNull(instance);
        Assertions.assertEquals(responseCodeExpected, instance.getResponseCode());
    }

    @Test
    @Order(2)
    @DisplayName("Business Process Exception Second Constructor Test")
    void testSecondConstructor() {

        // Given
        final ResponseCode responseCodeExpected = ResponseCode.INTERNAL_SERVER_ERROR;

        // When
        final BusinessProcessException instance = assertThrows(BusinessProcessException.class, this::executeSecondBusinessProcessException);

        // Then
        Assertions.assertNotNull(instance);
        Assertions.assertEquals(responseCodeExpected, instance.getResponseCode());
        Assertions.assertEquals(EXCEPTION_MESSAGE, instance.getMessage());
    }

    @Test
    @Order(3)
    @DisplayName("Business Process Exception Second Constructor Test")
    void testThirdConstructor() {

        // Given
        final ResponseCode responseCodeExpected = ResponseCode.INTERNAL_SERVER_ERROR;

        // When
        final BusinessProcessException instance = assertThrows(BusinessProcessException.class, this::executeThirdBusinessProcessException);

        // Then
        Assertions.assertNotNull(instance);
        Assertions.assertEquals(EXCEPTION_MESSAGE, instance.getMessage());
    }

    @Test
    @Order(4)
    @DisplayName("Business Process Exception Second Constructor Test")
    void testFourConstructor() {

        // Given
        final ResponseCode responseCodeExpected = ResponseCode.INTERNAL_SERVER_ERROR;

        // When
        final BusinessProcessException instance = assertThrows(BusinessProcessException.class, this::executeFourBusinessProcessException);

        // Then
        Assertions.assertNotNull(instance);
        Assertions.assertEquals(EXCEPTION_MESSAGE, instance.getMessage());
    }

    void executeBusinessProcessException() {
        throw new BusinessProcessException(ResponseCode.INTERNAL_SERVER_ERROR);
    }

    void executeSecondBusinessProcessException() {
        throw new BusinessProcessException(EXCEPTION_MESSAGE, ResponseCode.INTERNAL_SERVER_ERROR);
    }

    void executeThirdBusinessProcessException() {
        throw new BusinessProcessException(EXCEPTION_MESSAGE);
    }

    void executeFourBusinessProcessException() {

        try {
            throw new Exception(CUSTOM_EXCEPTION_MESSAGE);
        } catch (Exception e) {
            throw new BusinessProcessException(EXCEPTION_MESSAGE, ResponseCode.INTERNAL_SERVER_ERROR, e);
        }
    }
}