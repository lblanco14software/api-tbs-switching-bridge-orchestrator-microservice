package com.novo.microservices.components.helpers;

import com.novo.microservices.mocks.StandardTransactionResponseFixture;
import com.novo.microservices.tbs.utils.components.enums.SagaEventType;
import com.novo.microservices.tbs.utils.dtos.StandardTransaction;
import com.novo.microservices.tbs.utils.dtos.TransactionMessage;
import com.novo.microservices.tbs.utils.dtos.requests.CreateSagaTransactionalStateRequest;
import com.novo.microservices.transactions.enums.TransactionSagaStatesFormats;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

/**
 * SagaTransactionalStateRequestHelperTest
 * <p>
 * SagaTransactionalStateRequestHelperTest class.
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
public class SagaTransactionalStateRequestHelperTest {

    @Test
    @Order(1)
    @DisplayName("Build Create Saga Transactional Request Test")
    void buildCreateSagaTransactionalStateRequestTest() {

        // Given
        final TransactionMessage<StandardTransaction> eventTransactionMessage = new TransactionMessage<>();
        final StandardTransaction standardTransaction = new StandardTransactionResponseFixture().getInstance();
        eventTransactionMessage.setData(standardTransaction);
        final String microservicesUuId = UUID.randomUUID().toString();
        final SagaEventType sagaEventType = SagaEventType.EVENT_TYPE_INBOUND;
        final String eventState = TransactionSagaStatesFormats.ORCHESTRATOR_TRANSACTION_SAGA_STATE_FORMAT_RECEIVED.toString();

        // When
        final CreateSagaTransactionalStateRequest instance = SagaTransactionalStateRequestHelper.buildCreateSagaTransactionalStateRequest(
            eventTransactionMessage, microservicesUuId, sagaEventType, eventState
        );

        // Then
        Assertions.assertNotNull(instance);
        Assertions.assertNotNull(instance.getEventInformation());
        Assertions.assertNotNull(instance.getRequestInformation());
    }
}