package com.novo.microservices.events.inbounds;

import com.novo.microservices.dtos.responses.HsmResponse;
import com.novo.microservices.events.inbounds.implementations.OrchestratorGenerateKeyConsumer;
import com.novo.microservices.services.contracts.IOrchestratorGenerateKeyEventService;
import com.novo.microservices.tbs.utils.components.enums.SagaState;
import com.novo.microservices.tbs.utils.dtos.TransactionMessage;
import com.novo.microservices.utils.common.context.AppSessionContext;
import com.novo.utils.messaging.components.helpers.EnvironmentUtils;
import com.novo.utils.messaging.dtos.Event;
import org.apache.logging.log4j.ThreadContext;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * OrchestratorGenerateKeyConsumerTest
 * <p>
 * OrchestratorGenerateKeyConsumerTest class.
 * <p>
 * <p>
 * ESTE COMPONENTE FUE CONSTRUIDO SIGUIENDO LOS ESTANDARES DE DESARROLLO Y EL PROCEDIMIENTO
 * DE DESARROLLO DE APLICACIONES DE NOVOPAYMENT Y SE ENCUENTRA PROTEGIDO POR LAS LEYES DE
 * PROPIEDAD INTELECTUAL Y DERECHOS DE AUTOR.
 *
 * @author Novopayment Inc.
 * @author jquiroga@novopayment.com
 * @since 5/19/22
 */
@ExtendWith({MockitoExtension.class})
class OrchestratorGenerateKeyConsumerTest {
    @InjectMocks
    private OrchestratorGenerateKeyConsumer consumer;

    @Mock
    private EnvironmentUtils environmentUtils;

    @Mock
    private AppSessionContext appSessionContext;

    @Mock
    private IOrchestratorGenerateKeyEventService service;

    @Test
    void processGenerateKey() {
        Event<TransactionMessage<HsmResponse>> event = Event.<TransactionMessage<HsmResponse>>builder()
            .data(TransactionMessage.<HsmResponse>builder()
                .transactionType("transactionType")
                .eventState("eventState")
                .messageId("messageId")
                .tenantId("Tenant-ID")
                .requestId("Request-ID")
                .data(HsmResponse.builder()
                    .body(new HashMap<>())
                    .build())
                .build())
            .build();

        when(environmentUtils.getValue(any())).thenReturn("Queue");
        doReturn(Mono.empty()).when(service).doUpdateProcessGenerateKey(any(), any());

        consumer.processGenerateKey(event);

        verify(service, times(1)).doUpdateProcessGenerateKey(any(), eq(SagaState.COMPLETED));

        assertEquals(ThreadContext.get("userId"), event.getData().getTenantId());
        assertEquals(ThreadContext.get("requestId"), event.getData().getRequestId());
        assertEquals(ThreadContext.get("tenantId"), event.getData().getTenantId());
    }
}
