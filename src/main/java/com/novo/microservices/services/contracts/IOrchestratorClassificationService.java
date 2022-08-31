package com.novo.microservices.services.contracts;

import com.novo.microservices.dtos.OrchestratorTransactionClassification;
import com.novo.microservices.dtos.requests.OrchestrationTransactionRequest;
import reactor.core.publisher.Mono;

/**
 * IOrchestratorClassificationService
 * <p>
 * IOrchestratorClassificationService interface.
 * <p>
 * <p>
 * ESTE COMPONENTE FUE CONSTRUIDO SIGUIENDO LOS ESTANDARES DE DESARROLLO Y EL PROCEDIMIENTO
 * DE DESARROLLO DE APLICACIONES DE NOVOPAYMENT Y SE ENCUENTRA PROTEGIDO POR LAS LEYES DE
 * PROPIEDAD INTELECTUAL Y DERECHOS DE AUTOR.
 *
 * @author Novopayment Inc.
 * @author lblanco@novopayment.com
 * @since 4/25/2022
 */
public interface IOrchestratorClassificationService {

    Mono<OrchestratorTransactionClassification> doOnFindTransactionClassification(OrchestrationTransactionRequest orchestrationTransactionRequest);

    Mono<OrchestratorTransactionClassification> doOnFindTransactionClassification(final String serviceId, final String messageTypeIndicator, final String processingCode);

    Mono<OrchestratorTransactionClassification> doOnFindTransactionClassificationByCode(final String transactionCode);
}