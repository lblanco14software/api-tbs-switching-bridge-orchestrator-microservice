package com.novo.microservices.events.outbounds.contracts;

import com.novo.microservices.dtos.requests.HsmGenerateKeyRequest;
import com.novo.microservices.dtos.requests.HsmRequest;
import com.novo.microservices.tbs.utils.dtos.TransactionMessage;
import com.novo.utils.messaging.dtos.Event;

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
public interface IOrchestratorGenerateKeyProducer {

    Event<TransactionMessage<HsmRequest>> doOnProcessGenerateKey(TransactionMessage<HsmGenerateKeyRequest> transactionMessage);
}