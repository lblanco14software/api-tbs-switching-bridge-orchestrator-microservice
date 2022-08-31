package com.novo.microservices.services.contracts;

import com.novo.microservices.dtos.OrchestratorTransactionInformation;
import com.novo.microservices.services.interfaces.IGetDomainEntityById;
import com.novo.microservices.services.interfaces.ISaveDomainEntity;
import com.novo.microservices.services.interfaces.IUpdateDomainEntity;
import reactor.core.publisher.Mono;

/**
 * IOrchestratorCrudService
 * <p>
 * IOrchestratorCrudService interface.
 * <p>
 * <p>
 * ESTE COMPONENTE FUE CONSTRUIDO SIGUIENDO LOS ESTANDARES DE DESARROLLO Y EL PROCEDIMIENTO
 * DE DESARROLLO DE APLICACIONES DE NOVOPAYMENT Y SE ENCUENTRA PROTEGIDO POR LAS LEYES DE
 * PROPIEDAD INTELECTUAL Y DERECHOS DE AUTOR.
 *
 * @author Novopayment Inc.
 * @author lblanco@novopayment.com
 * @since 4/11/2022
 */
public interface IOrchestratorCrudService
    extends
    ISaveDomainEntity<Mono<OrchestratorTransactionInformation>, OrchestratorTransactionInformation>,
    IUpdateDomainEntity<Mono<OrchestratorTransactionInformation>, OrchestratorTransactionInformation>,
    IGetDomainEntityById<Mono<OrchestratorTransactionInformation>, String> {

}