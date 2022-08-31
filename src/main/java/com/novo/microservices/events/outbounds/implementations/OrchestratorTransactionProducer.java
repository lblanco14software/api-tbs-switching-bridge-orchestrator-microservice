package com.novo.microservices.events.outbounds.implementations;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.Gson;
import com.novo.microservices.dtos.OrchestratorProducerResult;
import com.novo.microservices.events.outbounds.contracts.IOrchestratorTransactionProducer;
import com.novo.microservices.tbs.utils.components.helpers.RoutingKeyHelper;
import com.novo.microservices.tbs.utils.dtos.SagaControl;
import com.novo.microservices.tbs.utils.dtos.SagaOutBox;
import com.novo.microservices.tbs.utils.dtos.StandardTransaction;
import com.novo.microservices.tbs.utils.dtos.TransactionMessage;
import com.novo.microservices.transactions.contracts.IOrchestratorTransaction;
import com.novo.utils.messaging.components.enums.EventType;
import com.novo.utils.messaging.dtos.Event;
import com.novo.utils.messaging.dtos.RoutingKey;
import com.novo.utils.messaging.services.contrats.IPublisherService;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

import static com.novo.microservices.tbs.utils.constants.TransactionsConstants.BROKER_DECLARE_EXCHANGE;

/**
 * OrchestratorTransactionProducer
 * <p>
 * OrchestratorTransactionProducer class.
 * <p>
 * <p>
 * ESTE COMPONENTE FUE CONSTRUIDO SIGUIENDO LOS ESTANDARES DE DESARROLLO Y EL PROCEDIMIENTO
 * DE DESARROLLO DE APLICACIONES DE NOVOPAYMENT Y SE ENCUENTRA PROTEGIDO POR LAS LEYES DE
 * PROPIEDAD INTELECTUAL Y DERECHOS DE AUTOR.
 *
 * @author Novopayment Inc.
 * @author lblanco@novopayment.com
 * @since 4/8/2022
 */
@Log4j2
@Service
public class OrchestratorTransactionProducer implements IOrchestratorTransactionProducer {

    private final IPublisherService publisherService;

    public OrchestratorTransactionProducer(IPublisherService publisherService) {
        this.publisherService = publisherService;
    }

    @Override
    public Mono<OrchestratorProducerResult> doOnProcessTransaction(SagaOutBox sagaOutBox, SagaControl sagaControl, IOrchestratorTransaction orchestratorTransaction) {

        final List<String> errors = new ArrayList<>();

        return Mono.just(OrchestratorProducerResult.builder().build())
            .flatMap(orchestratorProducerResult -> {

                final TransactionMessage<StandardTransaction> transactionMessage = new TransactionMessage<>();
                transactionMessage.setMessageId(sagaControl.getMessageId());
                transactionMessage.setCorrelationId(sagaControl.getCorrelationId());
                transactionMessage.setTenantId(sagaOutBox.getTenantId());
                transactionMessage.setRequestId(sagaOutBox.getRequestId());
                transactionMessage.setEventSource(sagaControl.getEventSource());
                transactionMessage.setEventState(sagaControl.getEventState());
                transactionMessage.setData(orchestratorTransaction.getCurrentStandardTransactionRequest());

                final RoutingKey routingKey = RoutingKeyHelper.createRoutingKey(EventType.SERVICE, orchestratorTransaction.getBrokerConfigurationProducer());
                final Event<TransactionMessage<StandardTransaction>> eventTransactionMessage = new Event<>();
                eventTransactionMessage.setData(transactionMessage);
                eventTransactionMessage.setRoutingKey(routingKey);

                final Gson gson = new Gson();
                log.debug("eventTransactionMessage {}", gson.toJson(transactionMessage));
                final MessageProperties messageProperties = new MessageProperties();
                eventTransactionMessage.setMessageProperties(messageProperties);
                eventTransactionMessage.setRoutingKey(routingKey);
                orchestratorProducerResult.setEventTransactionMessage(eventTransactionMessage);

                try {
                    publisherService.publish(BROKER_DECLARE_EXCHANGE, eventTransactionMessage);
                } catch (JsonProcessingException e) {
                    return Mono.error(new RuntimeException(e.getMessage(), e));
                }

                if (!errors.isEmpty()) {
                    orchestratorProducerResult.setErrors(errors);
                }

                return Mono.just(orchestratorProducerResult);
            });
    }
}