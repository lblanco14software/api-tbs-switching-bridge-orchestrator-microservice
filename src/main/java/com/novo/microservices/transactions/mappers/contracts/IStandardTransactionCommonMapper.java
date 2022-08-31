package com.novo.microservices.transactions.mappers.contracts;

import com.novo.microservices.dtos.requests.OrchestrationTransactionRequest;
import com.novo.microservices.tbs.utils.dtos.StandardTransaction;

/**
 * IStandardTransactionCommonMapper
 * <p>
 * IStandardTransactionCommonMapper interface.
 * <p>
 * <p>
 * ESTE COMPONENTE FUE CONSTRUIDO SIGUIENDO LOS ESTANDARES DE DESARROLLO Y EL PROCEDIMIENTO
 * DE DESARROLLO DE APLICACIONES DE NOVOPAYMENT Y SE ENCUENTRA PROTEGIDO POR LAS LEYES DE
 * PROPIEDAD INTELECTUAL Y DERECHOS DE AUTOR.
 *
 * @author Novopayment Inc.
 * @author lblanco@novopayment.com
 * @since 4/4/2022
 */
public interface IStandardTransactionCommonMapper {

    StandardTransaction buildStandardTransaction(OrchestrationTransactionRequest currentOrchestrationTransactionRequest);
}