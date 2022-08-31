package com.novo.microservices.components.validations;

import com.novo.microservices.dtos.SagaTransactionalStateResult;
import com.novo.microservices.tbs.utils.dtos.SagaControl;
import com.novo.microservices.tbs.utils.dtos.responses.SagaTransactionalStateResponse;
import com.novo.microservices.transactions.enums.TransactionSagaStatesFormats;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * SagaTransactionalStateResponseValidationTest
 * <p>
 * SagaTransactionalStateResponseValidationTest class.
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
public class SagaTransactionalStateResponseValidationTest {

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
    @Order(2)
    @DisplayName("Validate Saga Response Error # 2 Test")
    void validateChangeStateTest() {

        // Given
        final String sagaCurrentState = TransactionSagaStatesFormats.ORCHESTRATOR_TRANSACTION_SAGA_STATE_FORMAT_RECEIVED.toString();
        final SagaTransactionalStateResponse sagaTransactionalStateResponse = SagaTransactionalStateResponse.builder()
            .sagaControl(SagaControl.builder()
                .eventState(sagaCurrentState)
                .build())
            .build();
        final SagaTransactionalStateResult instanceExpected = SagaTransactionalStateResult.builder().sagaStateChangeResult(SagaTransactionalStateResult.SagaStateChangeResult.NOT_SAGA_EXPECTED_STATE).build();

        // When
        final SagaTransactionalStateResult instance = ISagaTransactionalStateResponseValidation.validateChangeState(sagaCurrentState).apply(sagaTransactionalStateResponse);

        // Then
        Assertions.assertNotNull(instance);
        Assertions.assertEquals(instanceExpected.getSagaStateChangeResult(), instance.getSagaStateChangeResult());
    }

    @Test
    @Order(3)
    @DisplayName("Validate Saga Response Error # 3 Test")
    void validateChangeStateAllErrorsTest() {

        // Given
        final String sagaCurrentState = TransactionSagaStatesFormats.ORCHESTRATOR_TRANSACTION_SAGA_STATE_FORMAT_RECEIVED.toString();
        final SagaTransactionalStateResponse sagaTransactionalStateResponse = SagaTransactionalStateResponse.builder()
            .sagaControl(SagaControl.builder()
                .eventState(sagaCurrentState)
                .build())
            .build();
        final SagaTransactionalStateResult instanceExpected = SagaTransactionalStateResult.builder().sagaStateChangeResult(SagaTransactionalStateResult.SagaStateChangeResult.NOT_SAGA_EXPECTED_STATE).build();

        // When
        final SagaTransactionalStateResult instance = ISagaTransactionalStateResponseValidation.validateChangeState(sagaCurrentState)
            .and(ISagaTransactionalStateResponseValidation.validateResponse())
            .apply(sagaTransactionalStateResponse);

        // Then
        Assertions.assertNotNull(instance);
        Assertions.assertEquals(instanceExpected.getSagaStateChangeResult(), instance.getSagaStateChangeResult());
    }
}