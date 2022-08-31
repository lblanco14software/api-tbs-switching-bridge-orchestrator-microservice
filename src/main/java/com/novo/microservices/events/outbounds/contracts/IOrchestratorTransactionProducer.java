package com.novo.microservices.events.outbounds.contracts;

import com.novo.microservices.dtos.OrchestratorProducerResult;
import com.novo.microservices.tbs.utils.dtos.SagaControl;
import com.novo.microservices.tbs.utils.dtos.SagaOutBox;
import com.novo.microservices.transactions.contracts.IOrchestratorTransaction;
import reactor.core.publisher.Mono;

/**
 * IOrchestratorTransactionProducer
 * <p>
 * IOrchestratorTransactionProducer class.
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
public interface IOrchestratorTransactionProducer {

    Mono<OrchestratorProducerResult> doOnProcessTransaction(SagaOutBox sagaOutBox, SagaControl sagaControl, IOrchestratorTransaction orchestratorTransaction);
}