package com.novo.microservices.services.contracts;

import com.novo.microservices.dtos.responses.HsmGenerateKeyResponse;
import com.novo.microservices.tbs.utils.components.enums.SagaState;
import com.novo.microservices.tbs.utils.dtos.TransactionMessage;
import reactor.core.publisher.Mono;

/**
 * IOrchestratorGenerateKeyEventService
 * <p>
 * IOrchestratorGenerateKeyEventService interface.
 * <p>
 * <p>
 * ESTE COMPONENTE FUE CONSTRUIDO SIGUIENDO LOS ESTANDARES DE DESARROLLO Y EL PROCEDIMIENTO
 * DE DESARROLLO DE APLICACIONES DE NOVOPAYMENT Y SE ENCUENTRA PROTEGIDO POR LAS LEYES DE
 * PROPIEDAD INTELECTUAL Y DERECHOS DE AUTOR.
 *
 * @author Novopayment Inc.
 * @author jquiroga@novopayment.com
 * @since 5/19/2022
 */
public interface IOrchestratorGenerateKeyEventService {

    Mono<HsmGenerateKeyResponse> doUpdateProcessGenerateKey(TransactionMessage<HsmGenerateKeyResponse> transactionMessage, final SagaState sagaState);
}