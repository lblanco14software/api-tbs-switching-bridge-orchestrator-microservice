package com.novo.microservices.dtos;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * SagaTransactionalStateResultTest
 * <p>
 * SagaTransactionalStateResultTest class.
 * <p>
 * <p>
 * ESTE COMPONENTE FUE CONSTRUIDO SIGUIENDO LOS ESTANDARES DE DESARROLLO Y EL PROCEDIMIENTO
 * DE DESARROLLO DE APLICACIONES DE NOVOPAYMENT Y SE ENCUENTRA PROTEGIDO POR LAS LEYES DE
 * PROPIEDAD INTELECTUAL Y DERECHOS DE AUTOR.
 *
 * @author Novopayment Inc.
 * @author lblanco@novopayment.com
 * @since 4/20/2022
 */
@SpringBootTest
@AutoConfigureMockMvc
public class SagaTransactionalStateResultTest {

    @Test
    @Order(1)
    @DisplayName("isSuccessfullyResponse Test")
    void isSuccessfullyResponseTest() {

        // Given
        final SagaTransactionalStateResult sagaTransactionalStateResult = SagaTransactionalStateResult.builder().build();

        // When
        final boolean instance = sagaTransactionalStateResult.isSuccessfullyResponse();

        // Then
        Assertions.assertTrue(instance);
    }

    @Test
    @Order(2)
    @DisplayName("isErrorInProcess Test")
    void isErrorInProcessTest() {

        // Given
        List<String> warnings = List.of("error in process");
        final SagaTransactionalStateResult sagaTransactionalStateResult = SagaTransactionalStateResult.builder()
            .warnings(warnings)
            .build();

        // When
        final boolean instance = sagaTransactionalStateResult.isErrorInProcess();

        // Then
        Assertions.assertTrue(instance);
    }
}