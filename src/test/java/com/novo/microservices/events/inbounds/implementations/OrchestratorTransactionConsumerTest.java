package com.novo.microservices.events.inbounds.implementations;

import com.novo.microservices.mocks.EventFixture;
import com.novo.microservices.mocks.OrchestrationSagaResponseFixture;
import com.novo.microservices.services.contracts.IOrchestratorTransactionEventService;
import com.novo.microservices.utils.common.context.AppSessionContext;
import com.novo.utils.messaging.components.helpers.EnvironmentUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;

import static com.novo.microservices.constants.ProcessConstants.BROKER_ORCHESTRATOR_TRANSACTION_RESPONSE_QUEUE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

/**
 * OrchestratorTransactionConsumerTest
 * <p>
 * OrchestratorTransactionConsumerTest class.
 * <p>
 * <p>
 * ESTE COMPONENTE FUE CONSTRUIDO SIGUIENDO LOS ESTANDARES DE DESARROLLO Y EL PROCEDIMIENTO
 * DE DESARROLLO DE APLICACIONES DE NOVOPAYMENT Y SE ENCUENTRA PROTEGIDO POR LAS LEYES DE
 * PROPIEDAD INTELECTUAL Y DERECHOS DE AUTOR.
 *
 * @author Novopayment Inc.
 * @author duhernandez@novopayment.com
 * @since 5/19/22
 */
@ExtendWith({MockitoExtension.class})
class OrchestratorTransactionConsumerTest {

    @InjectMocks
    private OrchestratorTransactionConsumer consumer;

    @Mock
    private IOrchestratorTransactionEventService service;

    @Mock
    private EnvironmentUtils environmentUtils;

    @Spy
    private AppSessionContext appSessionContext;

    @Test
    void processTransaction() {
        var event = new EventFixture().getInstance();
        when(service.doOnProcessTransactionResponseByEvent(event)).thenReturn(Mono.just(new OrchestrationSagaResponseFixture().getInstance()));
        consumer.processTransaction(event);
        assertEquals(appSessionContext.getApplicationSession().getTenantId(), event.getData().getTenantId());
        assertEquals(appSessionContext.getApplicationSession().getRequestId(), event.getData().getRequestId());
        assertEquals(appSessionContext.getApplicationSession().getUserId(), event.getData().getTenantId());
        verify(service, times(1)).doOnProcessTransactionResponseByEvent(event);
    }

    @Test
    void processBrokerTransactionCallBackWithError() {
        var event = new EventFixture().getInstance();
        when(service.doOnProcessTransactionResponseByEvent(event)).thenThrow(new RuntimeException());
        consumer.processTransaction(event);
        assertEquals(appSessionContext.getApplicationSession().getTenantId(), event.getData().getTenantId());
        assertEquals(appSessionContext.getApplicationSession().getRequestId(), event.getData().getRequestId());
        assertEquals(appSessionContext.getApplicationSession().getUserId(), event.getData().getTenantId());
        verify(service, times(1)).doOnProcessTransactionResponseByEvent(event);
        verify(environmentUtils, times(3)).getValue(BROKER_ORCHESTRATOR_TRANSACTION_RESPONSE_QUEUE);
    }
}