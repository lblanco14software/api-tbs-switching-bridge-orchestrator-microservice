package com.novo.microservices.events.inbounds.implementations;

import com.novo.microservices.events.inbounds.contracts.IOrchestratorTransactionConsumer;
import com.novo.microservices.services.contracts.IOrchestratorTransactionEventService;
import com.novo.microservices.tbs.utils.dtos.StandardTransaction;
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

import static com.novo.microservices.constants.ProcessConstants.BROKER_ORCHESTRATOR_INFORMATION_TRANSACTION_RESPONSE_ROUTING_KEY;
import static com.novo.microservices.constants.ProcessConstants.BROKER_ORCHESTRATOR_TRANSACTION_RESPONSE_QUEUE;
import static com.novo.microservices.tbs.utils.constants.TransactionsConstants.BROKER_DECLARE_EXCHANGE;

/**
 * OrchestratorTransactionConsumer
 * <p>
 * OrchestratorTransactionConsumer class.
 * <p>
 * <p>
 * ESTE COMPONENTE FUE CONSTRUIDO SIGUIENDO LOS ESTANDARES DE DESARROLLO Y EL PROCEDIMIENTO
 * DE DESARROLLO DE APLICACIONES DE NOVOPAYMENT Y SE ENCUENTRA PROTEGIDO POR LAS LEYES DE
 * PROPIEDAD INTELECTUAL Y DERECHOS DE AUTOR.
 *
 * @author Novopayment Inc.
 * @author lblanco@novopayment.com
 * @since 4/11/2022
 */
@Log4j2
@Service
public class OrchestratorTransactionConsumer extends BaseMessageConsumer implements IOrchestratorTransactionConsumer {

    private final IOrchestratorTransactionEventService orchestratorTransactionEventService;
    private final AppSessionContext appSessionContext;
    private final EnvironmentUtils environmentUtils;

    public OrchestratorTransactionConsumer(final IOrchestratorTransactionEventService orchestratorTransactionEventService,
                                           final AppSessionContext appSessionContext,
                                           final EnvironmentUtils environmentUtils) {

        this.orchestratorTransactionEventService = orchestratorTransactionEventService;
        this.appSessionContext = appSessionContext;
        this.environmentUtils = environmentUtils;
        log.debug("OrchestratorTransactionConsumer loaded successfully");
    }

    @Override
    @BrokerConsumer(
        queues = {BROKER_ORCHESTRATOR_TRANSACTION_RESPONSE_QUEUE},
        bindings = {@BrokerDeclareBinding(exchange = BROKER_DECLARE_EXCHANGE, routingKey = BROKER_ORCHESTRATOR_INFORMATION_TRANSACTION_RESPONSE_ROUTING_KEY, queue = BROKER_ORCHESTRATOR_TRANSACTION_RESPONSE_QUEUE)},
        declareQueues = {@BrokerDeclareQueue(queues = {BROKER_ORCHESTRATOR_TRANSACTION_RESPONSE_QUEUE})}
    )
    public void processTransaction(Event<TransactionMessage<StandardTransaction>> eventTransactionMessage) {

        try {
            final TransactionMessage<StandardTransaction> transactionMessage = eventTransactionMessage.getData();
            appSessionContext.setApplicationSession(
                ApplicationSession.builder()
                    .userId(transactionMessage.getTenantId())
                    .requestId(transactionMessage.getRequestId())
                    .tenantId(transactionMessage.getTenantId())
                    .build()
            );
            this.processAppSessionContext(transactionMessage);
            log.info("receiving message from queue [{}]", environmentUtils.getValue(BROKER_ORCHESTRATOR_TRANSACTION_RESPONSE_QUEUE));
            log.debug("receiving message [{}] from queue [{}]", transactionMessage, environmentUtils.getValue(BROKER_ORCHESTRATOR_TRANSACTION_RESPONSE_QUEUE));
            orchestratorTransactionEventService.doOnProcessTransactionResponseByEvent(eventTransactionMessage)
                .doOnSuccess(success ->
                    log.debug("success doOnProcessTransactionResponseByEvent from eventTransactionMessage: {}", success)
                )
                .doOnError(throwable ->
                    log.error("exception error in process doOnProcessTransactionResponseByEvent, error: {}", throwable.getMessage())
                )
                .subscribe();
        } catch (Exception e) {
            log.error("error in processTransaction from OrchestratorTransactionConsumer from queue [{}]", environmentUtils.getValue(BROKER_ORCHESTRATOR_TRANSACTION_RESPONSE_QUEUE));
            log.error("error in processTransaction from OrchestratorTransactionConsumer, exception error: {}", e.getMessage());
        }
    }
}