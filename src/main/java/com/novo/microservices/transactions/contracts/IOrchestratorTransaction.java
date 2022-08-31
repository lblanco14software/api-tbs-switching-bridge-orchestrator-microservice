package com.novo.microservices.transactions.contracts;

import com.novo.microservices.dtos.OrchestratorValidationResult;
import com.novo.microservices.dtos.requests.OrchestrationTransactionRequest;
import com.novo.microservices.dtos.responses.OrchestrationTransactionResponse;
import com.novo.microservices.tbs.utils.dtos.StandardTransaction;
import com.novo.microservices.tbs.utils.dtos.commons.CommonBrokerConfiguration;

/**
 * IOrchestratorTransaction
 * <p>
 * IOrchestratorTransaction interface.
 * <p>
 * <p>
 * ESTE COMPONENTE FUE CONSTRUIDO SIGUIENDO LOS ESTANDARES DE DESARROLLO Y EL PROCEDIMIENTO
 * DE DESARROLLO DE APLICACIONES DE NOVOPAYMENT Y SE ENCUENTRA PROTEGIDO POR LAS LEYES DE
 * PROPIEDAD INTELECTUAL Y DERECHOS DE AUTOR.
 *
 * @author Novopayment Inc.
 * @author lblanco@novopayment.com
 * @since 4/2/2022
 */
public interface IOrchestratorTransaction {

    StandardTransaction buildStandardTransaction(OrchestrationTransactionRequest currentOrchestrationTransactionRequest);

    OrchestrationTransactionResponse buildSuccessfullyTransactionResponse(OrchestrationTransactionRequest currentOrchestrationTransactionRequest, StandardTransaction standardTransactionResponse);

    OrchestrationTransactionResponse buildErrorTransactionResponse(OrchestrationTransactionRequest currentOrchestrationTransactionRequest);

    OrchestratorValidationResult validateStandardTransactionRequest(StandardTransaction standardTransactionRequest);

    StandardTransaction getCurrentStandardTransactionRequest();

    CommonBrokerConfiguration getBrokerConfigurationProducer();
}