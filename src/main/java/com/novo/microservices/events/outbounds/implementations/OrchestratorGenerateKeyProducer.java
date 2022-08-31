package com.novo.microservices.events.outbounds.implementations;

import com.novo.microservices.components.configurations.MessageConfiguration;
import com.novo.microservices.components.helpers.HsmMapper;
import com.novo.microservices.dtos.requests.HsmGenerateKeyRequest;
import com.novo.microservices.dtos.requests.HsmRequest;
import com.novo.microservices.events.outbounds.contracts.IOrchestratorGenerateKeyProducer;
import com.novo.microservices.tbs.utils.components.helpers.RoutingKeyHelper;
import com.novo.microservices.tbs.utils.dtos.TransactionMessage;
import com.novo.utils.messaging.components.aspects.BrokerDeclareExchange;
import com.novo.utils.messaging.components.aspects.BrokerPublisher;
import com.novo.utils.messaging.components.enums.EventType;
import com.novo.utils.messaging.components.helpers.EnvironmentUtils;
import com.novo.utils.messaging.dtos.Event;
import com.novo.utils.messaging.dtos.RoutingKey;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.novo.microservices.constants.ProcessConstants.BROKER_HSM_GENERATE_DERIVED_TRANSACTION_PRODUCER_ROUTING_KEY;
import static com.novo.microservices.tbs.utils.constants.TransactionsConstants.BROKER_DECLARE_EXCHANGE;
import static com.novo.microservices.tbs.utils.constants.TransactionsConstants.BROKER_DECLARE_EXCHANGE_TYPE;

@Component
@BrokerDeclareExchange(exchanges = BROKER_DECLARE_EXCHANGE, typeName = BROKER_DECLARE_EXCHANGE_TYPE)
@Log4j2
public class OrchestratorGenerateKeyProducer implements IOrchestratorGenerateKeyProducer {

    private final EnvironmentUtils environmentUtils;
    private final MessageConfiguration messageConfiguration;

    @Autowired
    public OrchestratorGenerateKeyProducer(EnvironmentUtils environmentUtils, MessageConfiguration messageConfiguration) {
        this.environmentUtils = environmentUtils;
        this.messageConfiguration = messageConfiguration;
    }

    @Override
    @BrokerPublisher(routingKey = BROKER_HSM_GENERATE_DERIVED_TRANSACTION_PRODUCER_ROUTING_KEY, exchange = BROKER_DECLARE_EXCHANGE)
    public Event<TransactionMessage<HsmRequest>> doOnProcessGenerateKey(TransactionMessage<HsmGenerateKeyRequest> transactionMessage) {

        final RoutingKey routingKey = RoutingKeyHelper.createRoutingKey(EventType.SERVICE, messageConfiguration.getHsmGenerateDerivedTransactionProducer());

        final Event<TransactionMessage<HsmRequest>> event = Event.<TransactionMessage<HsmRequest>>builder()
            .data(HsmMapper.mapperRequest(transactionMessage))
            .routingKey(routingKey)
            .build();

        log.info("Sending transaction to exchange [{}], routing key [{}]. transactionType [{}], messageId [{}], requestId [{}]",
            environmentUtils.getValue(BROKER_DECLARE_EXCHANGE),
            routingKey,
            transactionMessage.getTransactionType(),
            transactionMessage.getMessageId(),
            transactionMessage.getRequestId());

        return event;
    }
}
