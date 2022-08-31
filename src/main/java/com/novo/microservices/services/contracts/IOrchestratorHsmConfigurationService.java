package com.novo.microservices.services.contracts;

import com.novo.microservices.dtos.requests.HsmGenerateKeyRequest;
import reactor.core.publisher.Mono;

/**
 * IOrchestratorHsmGenerateKeyConfigurationService
 * <p>
 * IOrchestratorHsmGenerateKeyConfigurationService interface.
 * <p>
 * <p>
 * ESTE COMPONENTE FUE CONSTRUIDO SIGUIENDO LOS ESTANDARES DE DESARROLLO Y EL PROCEDIMIENTO
 * DE DESARROLLO DE APLICACIONES DE NOVOPAYMENT Y SE ENCUENTRA PROTEGIDO POR LAS LEYES DE
 * PROPIEDAD INTELECTUAL Y DERECHOS DE AUTOR.
 *
 * @author Novopayment Inc.
 * @author jquiroga@novopayment.com
 * @since 5/18/2022
 */
public interface IOrchestratorHsmConfigurationService {
    Mono<HsmGenerateKeyRequest> doOnFindByHeaderAndCommand(String header, String command);
}