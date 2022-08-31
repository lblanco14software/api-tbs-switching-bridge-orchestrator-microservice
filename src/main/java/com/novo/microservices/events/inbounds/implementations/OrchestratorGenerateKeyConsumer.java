package com.novo.microservices.events.inbounds.implementations;

import com.novo.microservices.components.helpers.HsmMapper;
import com.novo.microservices.dtos.responses.HsmGenerateKeyResponse;
import com.novo.microservices.dtos.responses.HsmResponse;
import com.novo.microservices.events.inbounds.contracts.IOrchestratorGenerateKeyConsumer;
import com.novo.microservices.services.contracts.IOrchestratorGenerateKeyEventService;
import com.novo.microservices.tbs.utils.components.enums.SagaState;
import com.novo.microservices.tbs.utils.dtos.TransactionMessage;
import com.novo.microservices.tbs.utils.events.BaseMessageConsumer;
import com.novo.microservices.utils.common.context.AppSessionContext;
import com.novo.microservices.utils.common.dtos.ApplicationSession;
import com.novo.utils.messaging.components.aspects.BrokerConsumer;
import com.novo.utils.messaging.components.aspects.BrokerDeclareBinding;
import com.novo.utils.messaging.components.aspects.BrokerDeclareQueue;
import com.novo.utils.messaging.components.helpers.EnvironmentUtils;
import com.novo.utils.messaging.dtos.Event;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import static com.novo.microservices.constants.ProcessConstants.BROKER_HSM_GENERATE_DERIVED_RESPONSE_CONSUMER_QUEUE;
import static com.novo.microservices.constants.ProcessConstants.BROKER_HSM_GENERATE_DERIVED_RESPONSE_CONSUMER_ROUTING_KEY;
import static com.novo.microservices.tbs.utils.constants.TransactionsConstants.BROKER_DECLARE_EXCHANGE;

/**
 * OrchestratorGenerateKeyConsumer
 * <p>
 * OrchestratorGenerateKeyConsumer class.
 * <p>
 * <p>
 * ESTE COMPONENTE FUE CONSTRUIDO SIGUIENDO LOS ESTANDARES DE DESARROLLO Y EL PROCEDIMIENTO
 * DE DESARROLLO DE APLICACIONES DE NOVOPAYMENT Y SE ENCUENTRA PROTEGIDO POR LAS LEYES DE
 * PROPIEDAD INTELECTUAL Y DERECHOS DE AUTOR.
 *
 * @author Novopayment Inc.
 * @author jquiroga@novopayment.com
 * @since 5/16/2022
 */
@Log4j2
@Service
public class OrchestratorGenerateKeyConsumer extends BaseMessageConsumer implements IOrchestratorGenerateKeyConsumer {

    private final IOrchestratorGenerateKeyEventService orchestratorGenerateKeyEventService;
    private final AppSessionContext appSessionContext;
    private final EnvironmentUtils environmentUtils;

    public OrchestratorGenerateKeyConsumer(final IOrchestratorGenerateKeyEventService orchestratorGenerateKeyEventService,
                                           final AppSessionContext appSessionContext,
                                           final EnvironmentUtils environmentUtils) {

        this.orchestratorGenerateKeyEventService = orchestratorGenerateKeyEventService;
        this.appSessionContext = appSessionContext;
        this.environmentUtils = environmentUtils;
    }

    @Override
    @BrokerConsumer(
        queues = {BROKER_HSM_GENERATE_DERIVED_RESPONSE_CONSUMER_QUEUE},
        bindings = {@BrokerDeclareBinding(exchange = BROKER_DECLARE_EXCHANGE, routingKey = BROKER_HSM_GENERATE_DERIVED_RESPONSE_CONSUMER_ROUTING_KEY, queue = BROKER_HSM_GENERATE_DERIVED_RESPONSE_CONSUMER_QUEUE)},
        declareQueues = {@BrokerDeclareQueue(queues = {BROKER_HSM_GENERATE_DERIVED_RESPONSE_CONSUMER_QUEUE})}
    )
    public void processGenerateKey(Event<TransactionMessage<HsmResponse>> event) {

        final TransactionMessage<HsmGenerateKeyResponse> transactionMessage = HsmMapper.mapperResponse(event.getData());
        log.info("receiving message from queue [{}]", environmentUtils.getValue(BROKER_HSM_GENERATE_DERIVED_RESPONSE_CONSUMER_QUEUE));
        log.debug("receiving message [{}] from queue [{}]", transactionMessage, environmentUtils.getValue(BROKER_HSM_GENERATE_DERIVED_RESPONSE_CONSUMER_QUEUE));

        appSessionContext.setApplicationSession(
            ApplicationSession.builder()
                .userId(transactionMessage.getTenantId())
                .requestId(transactionMessage.getRequestId())
                .tenantId(transactionMessage.getTenantId())
                .build()
        );
        this.processAppSessionContext(transactionMessage);
        orchestratorGenerateKeyEventService.doUpdateProcessGenerateKey(transactionMessage, SagaState.COMPLETED)
            .doOnSuccess(result -> log.info("Message processed successfully. transactionType [{}], transactionState [{}], messageId [{}], requestId [{}]",
                transactionMessage.getTransactionType(),
                transactionMessage.getEventState(),
                transactionMessage.getMessageId(),
                transactionMessage.getRequestId()))
            .doOnError(error -> log.error("Error processing message [{}], error [{}]",
                transactionMessage,
                error.getMessage()))
            .subscribe();
    }
}