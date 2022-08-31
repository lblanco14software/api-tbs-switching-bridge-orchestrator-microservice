package com.novo.microservices.events.callbacks;

import com.novo.microservices.mocks.EventFixture;
import com.novo.microservices.mocks.OrchestrationSagaResponseFixture;
import com.novo.microservices.services.contracts.IOrchestratorTransactionErrorService;
import com.novo.utils.messaging.components.helpers.EnvironmentUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;

import static com.novo.microservices.constants.ProcessConstants.BROKER_ORCHESTRATOR_TRANSACTION_RESPONSE_QUEUE;
import static org.mockito.Mockito.*;

/**
 * OrchestratorBrokerCallBackTest
 * <p>
 * OrchestratorBrokerCallBackTest class.
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
class OrchestratorBrokerCallBackTest {

    @InjectMocks
    private OrchestratorBrokerCallBack callBack;

    @Mock
    private IOrchestratorTransactionErrorService service;

    @Mock
    private EnvironmentUtils environmentUtils;

    @Test
    void processBrokerTransactionCallBack() {
        var event = new EventFixture().getInstance();
        when(service.doOnProcessErrorInTransactionPublish(event)).thenReturn(Mono.just(new OrchestrationSagaResponseFixture().getInstance()));
        callBack.processBrokerTransactionCallBack(1, "msg", event);
        verify(service, times(1)).doOnProcessErrorInTransactionPublish(event);
    }

    @Test
    void processBrokerTransactionCallBackWithError() {
        var event = new EventFixture().getInstance();
        when(service.doOnProcessErrorInTransactionPublish(event)).thenThrow(new RuntimeException());
        callBack.processBrokerTransactionCallBack(1, "msg", event);
        verify(service, times(1)).doOnProcessErrorInTransactionPublish(event);
        verify(environmentUtils, times(1)).getValue(BROKER_ORCHESTRATOR_TRANSACTION_RESPONSE_QUEUE);
    }
}