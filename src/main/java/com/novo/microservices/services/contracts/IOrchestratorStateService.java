package com.novo.microservices.services.contracts;

import com.novo.microservices.dtos.OrchestratorTransactionClassification;
import com.novo.microservices.dtos.OrchestratorTransactionStates;
import reactor.core.publisher.Mono;

/**
 * IOrchestratorStateService
 * <p>
 * IOrchestratorStateService interface.
 * <p>
 * <p>
 * ESTE COMPONENTE FUE CONSTRUIDO SIGUIENDO LOS ESTANDARES DE DESARROLLO Y EL PROCEDIMIENTO
 * DE DESARROLLO DE APLICACIONES DE NOVOPAYMENT Y SE ENCUENTRA PROTEGIDO POR LAS LEYES DE
 * PROPIEDAD INTELECTUAL Y DERECHOS DE AUTOR.
 *
 * @author Novopayment Inc.
 * @author lblanco@novopayment.com
 * @since 4/26/2022
 */
public interface IOrchestratorStateService {

    Mono<OrchestratorTransactionStates> doOnLoadOrchestratorTransactionStates(OrchestratorTransactionClassification orchestratorTransactionClassification);
}