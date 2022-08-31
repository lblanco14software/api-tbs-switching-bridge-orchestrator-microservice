package com.novo.microservices.components.validations;

import com.novo.microservices.dtos.SagaTransactionalStateResult;
import com.novo.microservices.tbs.utils.dtos.SagaControl;
import com.novo.microservices.tbs.utils.dtos.responses.SagaTransactionalStateResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * SagaTransactionResponseValidationTest
 * <p>
 * SagaTransactionResponseValidationTest class.
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
public class SagaTransactionResponseValidationTest {

    @Test
    @Order(1)
    @DisplayName("Validate Saga Response Error # 1 Test")
    void validateResponseTest() {

        // Given
        final SagaTransactionalStateResponse sagaTransactionalStateResponse = SagaTransactionalStateResponse.builder().build();
        final SagaTransactionalStateResult instanceExpected = SagaTransactionalStateResult.builder().sagaStateChangeResult(SagaTransactionalStateResult.SagaStateChangeResult.NOT_SAGA_EXPECTED_STATE).build();

        // When
        final SagaTransactionalStateResult instance = ISagaTransactionalStateResponseValidation.validateResponse().apply(sagaTransactionalStateResponse);

        // Then
        Assertions.assertNotNull(instance);
        Assertions.assertEquals(instanceExpected.getSagaStateChangeResult(), instance.getSagaStateChangeResult());
    }

    @Test
    @Order(1)
    @DisplayName("Validate Saga Response Error # 2 Test")
    void validateChangeStateErrorTest() {

        // Given
        final String currentState = "pending";
        final SagaTransactionalStateResponse sagaTransactionalStateResponse = SagaTransactionalStateResponse.builder()
            .sagaControl(SagaControl.builder()
                .eventState("pending")
                .build())
            .build();
        final SagaTransactionalStateResult instanceExpected = SagaTransactionalStateResult.builder().sagaStateChangeResult(SagaTransactionalStateResult.SagaStateChangeResult.NOT_SAGA_EXPECTED_STATE).build();

        // When
        final SagaTransactionalStateResult instance = ISagaTransactionalStateResponseValidation.validateChangeState(currentState).apply(sagaTransactionalStateResponse);

        // Then
        Assertions.assertNotNull(instance);
        Assertions.assertEquals(instanceExpected.getSagaStateChangeResult(), instance.getSagaStateChangeResult());
    }

    @Test
    @Order(1)
    @DisplayName("Validate Saga Response Success # 1 Test")
    void validateChangeStateSuccessTest() {

        // Given
        final String currentState = "pending";
        final SagaTransactionalStateResponse sagaTransactionalStateResponse = SagaTransactionalStateResponse.builder()
            .sagaControl(SagaControl.builder()
                .eventState("completed")
                .build())
            .build();
        final SagaTransactionalStateResult instanceExpected = SagaTransactionalStateResult.builder().sagaStateChangeResult(SagaTransactionalStateResult.SagaStateChangeResult.SAGA_EXPECTED_STATE).build();

        // When
        final SagaTransactionalStateResult instance = ISagaTransactionalStateResponseValidation.validateChangeState(currentState).apply(sagaTransactionalStateResponse);

        // Then
        Assertions.assertNotNull(instance);
        Assertions.assertEquals(instanceExpected.getSagaStateChangeResult(), instance.getSagaStateChangeResult());
    }
}