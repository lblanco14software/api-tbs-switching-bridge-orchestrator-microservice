package com.novo.microservices.components.helpers;

import com.novo.microservices.dtos.OrchestratorTransactionClassification;
import com.novo.microservices.tbs.utils.components.enums.SagaEventType;
import com.novo.microservices.tbs.utils.components.helpers.SagaCommonHelper;
import com.novo.microservices.tbs.utils.dtos.EventInformation;
import com.novo.microservices.tbs.utils.dtos.RequestInformation;
import com.novo.microservices.tbs.utils.dtos.StandardTransaction;
import com.novo.microservices.tbs.utils.dtos.TransactionMessage;
import com.novo.microservices.tbs.utils.dtos.requests.CreateSagaTransactionalStateRequest;
import com.novo.microservices.transactions.enums.TransactionSagaStatesFormats;
import lombok.experimental.UtilityClass;
import reactor.core.publisher.Mono;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

/**
 * SagaTransactionalStateRequestHelper
 * <p>
 * SagaTransactionalStateRequestHelper class.
 * <p>
 * <p>
 * ESTE COMPONENTE FUE CONSTRUIDO SIGUIENDO LOS ESTANDARES DE DESARROLLO Y EL PROCEDIMIENTO
 * DE DESARROLLO DE APLICACIONES DE NOVOPAYMENT Y SE ENCUENTRA PROTEGIDO POR LAS LEYES DE
 * PROPIEDAD INTELECTUAL Y DERECHOS DE AUTOR.
 *
 * @author Novopayment Inc.
 * @author lblanco@novopayment.com
 * @since 4/1/2022
 */
@UtilityClass
public class SagaTransactionalStateRequestHelper {

    public static CreateSagaTransactionalStateRequest buildCreateSagaTransactionalStateRequest(TransactionMessage<StandardTransaction> eventTransactionMessage,
                                                                                               final String microservicesUuId,
                                                                                               final SagaEventType sagaEventType,
                                                                                               final String eventState) {

        final Date currentEventDate = new Date();
        final String eventDate = SagaCommonHelper.createEvenDate(currentEventDate);

        return CreateSagaTransactionalStateRequest.builder()
            .eventInformation(EventInformation.builder()
                .messageId(eventTransactionMessage.getMessageId())
                .eventId(UUID.randomUUID().toString())
                .correlationId(eventTransactionMessage.getCorrelationId())
                .eventSource(eventTransactionMessage.getEventSource())
                .eventSource(microservicesUuId)
                .eventState(eventState)
                .eventType(sagaEventType.toString())
                .eventStartTime(eventDate)
                .eventPivot(false)
                .eventCompensatory(false)
                .build())
            .requestInformation(RequestInformation.builder()
                .tenantId(eventTransactionMessage.getTenantId())
                .requestId(eventTransactionMessage.getRequestId())
                .build())
            .build();
    }

    public static <T extends Serializable> CreateSagaTransactionalStateRequest buildCreateSagaTransactionalStateRequestByGenericTransactionMessage(TransactionMessage<T> eventTransactionMessage,
                                                                                                                                                   final String microservicesUuId,
                                                                                                                                                   final SagaEventType sagaEventType,
                                                                                                                                                   final String eventState) {

        return CreateSagaTransactionalStateRequest.builder()
            .eventInformation(EventInformation.builder()
                .messageId(eventTransactionMessage.getMessageId())
                .eventId(UUID.randomUUID().toString())
                .correlationId(eventTransactionMessage.getCorrelationId())
                .eventSource(eventTransactionMessage.getEventSource())
                .eventSource(microservicesUuId)
                .eventState(eventState)
                .eventType(sagaEventType.toString())
                .eventStartTime(SagaCommonHelper.createEvenDate(new Date()))
                .eventPivot(false)
                .eventCompensatory(false)
                .build())
            .requestInformation(RequestInformation.builder()
                .tenantId(eventTransactionMessage.getTenantId())
                .requestId(eventTransactionMessage.getRequestId())
                .build())
            .build();
    }

    public static String loadTransactionSagaState(final OrchestratorTransactionClassification orchestratorTransactionClassification,
                                                  final TransactionSagaStatesFormats transactionSagaStatesFormats) {

        return String.format(transactionSagaStatesFormats.getTransactionSagaFormat(), orchestratorTransactionClassification.getTransactionStateCode());
    }

    public static Mono<String> doOnLoadTransactionSagaState(final TransactionSagaStatesFormats transactionSagaStatesFormats,
                                                            final OrchestratorTransactionClassification orchestratorTransactionClassification) {

        return Mono.just(String.format(transactionSagaStatesFormats.getTransactionSagaFormat(), orchestratorTransactionClassification.getTransactionStateCode()));
    }
}