package com.novo.microservices.components.helpers;

import com.novo.microservices.dtos.requests.OrchestrationTransactionRequest;
import com.novo.microservices.mocks.AppSessionContextFixture;
import com.novo.microservices.mocks.OrchestrationTransactionRequestFixture;
import com.novo.microservices.tbs.utils.components.enums.SagaEventType;
import com.novo.microservices.tbs.utils.dtos.requests.CreateSagaTransactionalRequest;
import com.novo.microservices.tbs.utils.dtos.requests.CreateSagaTransactionalStateRequest;
import com.novo.microservices.transactions.enums.TransactionSagaStatesFormats;
import com.novo.microservices.utils.common.context.AppSessionContext;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

import static com.novo.microservices.transactions.constants.OrchestratorTransactionSagaConstants.ORCHESTRATOR_CHECK_CUSTOMER_INFO_TRANSACTION_FORMAT;

/**
 * SagaTransactionalRequestHelperTest
 * <p>
 * SagaTransactionalRequestHelperTest class.
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
public class SagaTransactionalRequestHelperTest {

    @Test
    @Order(1)
    @DisplayName("Build Create Saga Transactional Request Test")
    void buildCreateSagaTransactionalRequestTest() {

        // Given
        final OrchestrationTransactionRequest orchestrationTransactionRequest = new OrchestrationTransactionRequestFixture().getInstance();
        final AppSessionContext appSessionContext = new AppSessionContextFixture().getInstance();
        final String microservicesUuId = UUID.randomUUID().toString();
        final SagaEventType sagaEventType = SagaEventType.EVENT_TYPE_INBOUND;
        final String eventState = TransactionSagaStatesFormats.ORCHESTRATOR_TRANSACTION_SAGA_STATE_FORMAT_RECEIVED.toString();

        // When
        final CreateSagaTransactionalRequest instance = SagaTransactionalRequestHelper.buildCreateSagaTransactionalRequest(
            orchestrationTransactionRequest, appSessionContext, microservicesUuId, ORCHESTRATOR_CHECK_CUSTOMER_INFO_TRANSACTION_FORMAT, sagaEventType, eventState
        );

        // Then
        Assertions.assertNotNull(instance);
        Assertions.assertNotNull(instance.getEventInformation());
        Assertions.assertNotNull(instance.getRequestInformation());
        Assertions.assertNotNull(instance.getTransactionInformation());
        Assertions.assertEquals(appSessionContext.getApplicationSession().getTenantId(), instance.getRequestInformation().getTenantId());
        Assertions.assertEquals(appSessionContext.getApplicationSession().getRequestId(), instance.getRequestInformation().getRequestId());
    }

    @Test
    @Order(2)
    @DisplayName("Build Create Saga Transactional State Request Test")
    void buildCreateSagaTransactionalStateRequestTest() {

        // Given
        final AppSessionContext appSessionContext = new AppSessionContextFixture().getInstance();
        final String microservicesUuId = UUID.randomUUID().toString();
        final String messageId = UUID.randomUUID().toString();
        final String correlationId = UUID.randomUUID().toString();
        final SagaEventType sagaEventType = SagaEventType.EVENT_TYPE_INBOUND;
        final String eventState = TransactionSagaStatesFormats.ORCHESTRATOR_TRANSACTION_SAGA_STATE_FORMAT_RECEIVED.toString();

        // When
        final CreateSagaTransactionalStateRequest instance = SagaTransactionalRequestHelper.buildCreateSagaTransactionalStateRequest(
            appSessionContext, microservicesUuId, messageId, correlationId, sagaEventType, eventState
        );

        // Then
        Assertions.assertNotNull(instance);
        Assertions.assertNotNull(instance.getEventInformation());
        Assertions.assertNotNull(instance.getRequestInformation());
        Assertions.assertEquals(appSessionContext.getApplicationSession().getTenantId(), instance.getRequestInformation().getTenantId());
        Assertions.assertEquals(appSessionContext.getApplicationSession().getRequestId(), instance.getRequestInformation().getRequestId());
    }
}