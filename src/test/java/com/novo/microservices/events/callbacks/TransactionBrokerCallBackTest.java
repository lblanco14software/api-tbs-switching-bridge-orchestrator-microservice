package com.novo.microservices.events.callbacks;

import com.novo.microservices.dtos.responses.OrchestrationSagaResponse;
import com.novo.microservices.mocks.EventFixture;
import com.novo.microservices.services.contracts.IOrchestratorTransactionErrorService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockserver.junit.jupiter.MockServerExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * TransactionBrokerCallBackTest
 * <p>
 * TransactionBrokerCallBackTest class.
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
@ExtendWith({SpringExtension.class, MockServerExtension.class})
class TransactionBrokerCallBackTest {

    @InjectMocks
    private OrchestratorBrokerCallBack callBack;

    @Mock
    private IOrchestratorTransactionErrorService service;

    @Test
    void processBrokerTransactionCallBack() {
        when(service.doOnProcessErrorInTransactionPublish(any())).thenReturn(Mono.just(OrchestrationSagaResponse.builder().build()));

        callBack.processBrokerTransactionCallBack(1, "kk", new EventFixture().getInstance());

        verify(service, times(1)).doOnProcessErrorInTransactionPublish(any());
    }
}
