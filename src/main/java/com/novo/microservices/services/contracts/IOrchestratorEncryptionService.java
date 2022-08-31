package com.novo.microservices.services.contracts;

import com.novo.microservices.dtos.responses.HsmGenerateKeyResponse;
import com.novo.microservices.tbs.utils.dtos.StandardTransaction;
import reactor.core.publisher.Mono;

import java.io.Serializable;

/**
 * IOrchestratorEncryptionService
 * <p>
 * IOrchestratorEncryptionService class.
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
public interface IOrchestratorEncryptionService {

    Mono<String> doOnEncryptTransaction(final StandardTransaction standardTransaction);

    Mono<StandardTransaction> doOnDecryptTransaction(final String standardTransactionEncrypted);

    String doOnEncryptData(final String clearData);

    <T extends Serializable> Mono<String> doOnEncryptGenericTransaction(final T transaction);

    Mono<HsmGenerateKeyResponse> doOnDecryptHsmGenerateKeyResponse(final String encryptTransaction);
}
