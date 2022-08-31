package com.novo.microservices.events.inbounds.contracts;

import com.novo.microservices.dtos.responses.HsmResponse;
import com.novo.microservices.tbs.utils.dtos.TransactionMessage;
import com.novo.utils.messaging.dtos.Event;

/**
 * IOrchestratorTransactionConsumer
 * <p>
 * IOrchestratorTransactionConsumer class.
 * <p>
 * <p>
 * ESTE COMPONENTE FUE CONSTRUIDO SIGUIENDO LOS ESTANDARES DE DESARROLLO Y EL PROCEDIMIENTO
 * DE DESARROLLO DE APLICACIONES DE NOVOPAYMENT Y SE ENCUENTRA PROTEGIDO POR LAS LEYES DE
 * PROPIEDAD INTELECTUAL Y DERECHOS DE AUTOR.
 *
 * @author Novopayment Inc.
 * @author jquiroga@novopayment.com
 * @since 4/11/2022
 */
public interface IOrchestratorGenerateKeyConsumer {

    void processGenerateKey(final Event<TransactionMessage<HsmResponse>> event);
}