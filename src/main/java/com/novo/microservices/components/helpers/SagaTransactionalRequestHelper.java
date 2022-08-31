package com.novo.microservices.components.helpers;

import com.novo.microservices.dtos.requests.OrchestrationTransactionRequest;
import com.novo.microservices.tbs.utils.components.enums.SagaEventType;
import com.novo.microservices.tbs.utils.components.helpers.SagaCommonHelper;
import com.novo.microservices.tbs.utils.dtos.EventInformation;
import com.novo.microservices.tbs.utils.dtos.RequestInformation;
import com.novo.microservices.tbs.utils.dtos.TransactionInformation;
import com.novo.microservices.tbs.utils.dtos.requests.CreateSagaTransactionalRequest;
import com.novo.microservices.tbs.utils.dtos.requests.CreateSagaTransactionalStateRequest;
import com.novo.microservices.utils.common.context.AppSessionContext;
import lombok.experimental.UtilityClass;

import java.util.Date;
import java.util.UUID;

/**
 * SagaTransactionalRequestHelper
 * <p>
 * SagaTransactionalRequestHelper class.
 * <p>
 * <p>
 * ESTE COMPONENTE FUE CONSTRUIDO SIGUIENDO LOS ESTANDARES DE DESARROLLO Y EL PROCEDIMIENTO
 * DE DESARROLLO DE APLICACIONES DE NOVOPAYMENT Y SE ENCUENTRA PROTEGIDO POR LAS LEYES DE
 * PROPIEDAD INTELECTUAL Y DERECHOS DE AUTOR.
 *
 * @author Novopayment Inc.
 * @author lblanco@novopayment.com
 * @since 3/31/2022
 */
@UtilityClass
public class SagaTransactionalRequestHelper {

    public static CreateSagaTransactionalRequest buildCreateSagaTransactionalRequest(
        final OrchestrationTransactionRequest orchestrationTransactionRequest,
        final AppSessionContext appSessionContext,
        final String microservicesUuId,
        final String eventTransactionType,
        final SagaEventType sagaEventType,
        final String eventState
    ) {

        final Date currentEventDate = new Date();
        final String currentTimeStamp = SagaCommonHelper.createTimestamp(currentEventDate);
        final String eventDate = SagaCommonHelper.createEvenDate(currentEventDate);
        final String messageId = SagaCommonHelper.createMessageId(orchestrationTransactionRequest.getPaymentHeader().getTrackingId(), currentTimeStamp);
        final String correlationId = SagaCommonHelper.createCorrelationId(messageId, appSessionContext.getApplicationSession().getRequestId());

        return CreateSagaTransactionalRequest.builder()
            .eventInformation(EventInformation.builder()
                .messageId(messageId)
                .eventId(UUID.randomUUID().toString())
                .correlationId(correlationId)
                .eventSource(microservicesUuId)
                .eventState(eventState)
                .eventType(sagaEventType.toString())
                .eventStartTime(eventDate)
                .eventPivot(false)
                .eventCompensatory(false)
                .build())
            .transactionInformation(TransactionInformation.builder()
                .transactionDate(eventDate)
                .transactionProcessDate(eventDate)
                .transactionType(eventTransactionType)
                .build())
            .requestInformation(RequestInformation.builder()
                .tenantId(appSessionContext.getApplicationSession().getTenantId())
                .requestId(appSessionContext.getApplicationSession().getRequestId())
                .build())
            .build();
    }

    public static CreateSagaTransactionalStateRequest buildCreateSagaTransactionalStateRequest(
        final AppSessionContext appSessionContext,
        final String microservicesUuId,
        final String messageId,
        final String correlationId,
        final SagaEventType sagaEventType,
        final String eventState
    ) {

        final Date currentEventDate = new Date();
        final String eventDate = SagaCommonHelper.createEvenDate(currentEventDate);

        return CreateSagaTransactionalStateRequest.builder()
            .eventInformation(EventInformation.builder()
                .messageId(messageId)
                .eventId(UUID.randomUUID().toString())
                .correlationId(correlationId)
                .eventSource(microservicesUuId)
                .eventState(eventState)
                .eventType(sagaEventType.toString())
                .eventStartTime(eventDate)
                .eventPivot(false)
                .eventCompensatory(false)
                .build())
            .requestInformation(RequestInformation.builder()
                .tenantId(appSessionContext.getApplicationSession().getTenantId())
                .requestId(appSessionContext.getApplicationSession().getRequestId())
                .build())
            .build();
    }
}