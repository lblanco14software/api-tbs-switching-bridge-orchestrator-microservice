package com.novo.microservices.services.contracts;

import com.novo.microservices.dtos.OrchestratorTransactionClassification;
import com.novo.microservices.dtos.requests.OrchestrationSagaRequest;
import com.novo.microservices.dtos.requests.OrchestrationTransactionRequest;
import com.novo.microservices.tbs.utils.dtos.StandardTransaction;
import com.novo.microservices.tbs.utils.dtos.responses.BusinessProcessResponse;
import com.novo.microservices.transactions.contracts.IOrchestratorTransaction;
import reactor.core.publisher.Mono;

/**
 * IOrchestratorProviderService
 * <p>
 * IOrchestratorProviderService class.
 * <p>
 * <p>
 * ESTE COMPONENTE FUE CONSTRUIDO SIGUIENDO LOS ESTANDARES DE DESARROLLO Y EL PROCEDIMIENTO
 * DE DESARROLLO DE APLICACIONES DE NOVOPAYMENT Y SE ENCUENTRA PROTEGIDO POR LAS LEYES DE
 * PROPIEDAD INTELECTUAL Y DERECHOS DE AUTOR.
 *
 * @author Novopayment Inc.
 * @author lblanco@novopayment.com
 * @since 4/7/2022
 */
public interface IOrchestratorProviderService {

    Mono<IOrchestratorTransaction> doOnFactoryOrchestratorTransaction(final OrchestrationSagaRequest orchestrationSagaRequest);

    Mono<StandardTransaction> doOnFactoryStandardTransaction(OrchestratorTransactionClassification orchestratorTransactionClassification, OrchestrationTransactionRequest orchestrationTransactionRequest);

    Mono<BusinessProcessResponse> doOnProcessTransactionResponse(final OrchestrationSagaRequest orchestrationSagaRequest);
}
