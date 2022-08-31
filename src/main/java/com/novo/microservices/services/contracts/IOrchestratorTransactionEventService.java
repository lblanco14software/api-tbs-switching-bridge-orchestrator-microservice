package com.novo.microservices.services.contracts;

import com.novo.microservices.dtos.responses.OrchestrationSagaResponse;
import com.novo.microservices.tbs.utils.dtos.StandardTransaction;
import com.novo.microservices.tbs.utils.dtos.TransactionMessage;
import com.novo.utils.messaging.dtos.Event;
import reactor.core.publisher.Mono;

/**
 * IOrchestratorTransactionEventService
 * <p>
 * IOrchestratorTransactionEventService interface.
 * <p>
 * <p>
 * ESTE COMPONENTE FUE CONSTRUIDO SIGUIENDO LOS ESTANDARES DE DESARROLLO Y EL PROCEDIMIENTO
 * DE DESARROLLO DE APLICACIONES DE NOVOPAYMENT Y SE ENCUENTRA PROTEGIDO POR LAS LEYES DE
 * PROPIEDAD INTELECTUAL Y DERECHOS DE AUTOR.
 *
 * @author Novopayment Inc.
 * @author lblanco@novopayment.com
 * @since 4/27/2022
 */
public interface IOrchestratorTransactionEventService {

    Mono<OrchestrationSagaResponse> doOnProcessTransactionResponseByEvent(Event<TransactionMessage<StandardTransaction>> eventTransactionMessage);
}