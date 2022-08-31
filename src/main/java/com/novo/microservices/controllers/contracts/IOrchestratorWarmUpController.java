package com.novo.microservices.controllers.contracts;

import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;

/**
 * IOrchestratorWarmUpController
 * <p>
 * IOrchestratorWarmUpController interface.
 * <p>
 * <p>
 * ESTE COMPONENTE FUE CONSTRUIDO SIGUIENDO LOS ESTANDARES DE DESARROLLO Y EL PROCEDIMIENTO
 * DE DESARROLLO DE APLICACIONES DE NOVOPAYMENT Y SE ENCUENTRA PROTEGIDO POR LAS LEYES DE
 * PROPIEDAD INTELECTUAL Y DERECHOS DE AUTOR.
 *
 * @author Novopayment Inc.
 * @author lblanco@novopayment.com
 * @since 8/31/2022
 */
public interface IOrchestratorWarmUpController {

    ResponseEntity<Object> doOnWarmUpMicroservice();
}