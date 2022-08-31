package com.novo.microservices.transactions.mappers.contracts;

import com.novo.microservices.dtos.requests.OrchestrationTransactionRequest;
import postilion.realtime.sdk.message.bitmap.StructuredData;

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
 * @author duhernandez@novopayment.com
 * @since 4/4/2022
 */
public interface IStandardTransactionStructureDataMapper {
    StructuredData buildCommonStructuredData(OrchestrationTransactionRequest currentOrchestrationTransactionRequest);
}