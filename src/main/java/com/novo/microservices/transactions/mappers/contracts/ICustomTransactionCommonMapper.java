package com.novo.microservices.transactions.mappers.contracts;

import com.novo.microservices.dtos.custom.CustomTransactionInformation;
import com.novo.microservices.dtos.requests.OrchestrationTransactionRequest;
import com.novo.microservices.tbs.utils.dtos.StandardTransaction;

/**
 * ICustomTransactionCommonMapper
 * <p>
 * ICustomTransactionCommonMapper interface.
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
public interface ICustomTransactionCommonMapper {

    /***
     * metodo que construye la respuesta para el cliente una vez fue procesado por parte del switch de NovoPayment / method that builds the response for the client once it has been processed by the NovoPayment switch
     * @param currentOrchestrationTransactionRequest clase que contiene el request original enviado por parte del cliente / class containing the original request sent by the client
     * @param standardTransactionResponse clase que contiene el response procesado por NovoPayment  / class containing the response processed by NovoPayment
     * @return {@link CustomTransactionInformation} clase que se debe retornar al cliente con la información de la transacción / class that should be returned to the client with the transaction information
     */
    CustomTransactionInformation buildCustomTransactionInformation(OrchestrationTransactionRequest currentOrchestrationTransactionRequest, StandardTransaction standardTransactionResponse);
}