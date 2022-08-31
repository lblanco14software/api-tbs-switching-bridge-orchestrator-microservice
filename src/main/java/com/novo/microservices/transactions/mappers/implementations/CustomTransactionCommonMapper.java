package com.novo.microservices.transactions.mappers.implementations;

import com.novo.microservices.dtos.custom.CustomTransactionInformation;
import com.novo.microservices.dtos.requests.OrchestrationTransactionRequest;
import com.novo.microservices.tbs.utils.dtos.StandardTransaction;
import com.novo.microservices.transactions.mappers.contracts.ICustomTransactionCommonMapper;
import com.novo.microservices.transactions.mappers.contracts.ICustomTransactionMapper;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import postilion.realtime.sdk.message.bitmap.StructuredData;

import static com.novo.microservices.transactions.constants.OrchestratorTransactionFieldsConstants.*;

/**
 * CustomTransactionCommonMapper
 * <p>
 * CustomTransactionCommonMapper class.
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
@Log4j2
@Component
public class CustomTransactionCommonMapper implements ICustomTransactionCommonMapper {

    private final ICustomTransactionMapper customTransactionMapper;

    public CustomTransactionCommonMapper(final ICustomTransactionMapper customTransactionMapper) {
        this.customTransactionMapper = customTransactionMapper;
        log.debug("CustomTransactionCommonMapper loaded successfully");
    }

    /***
     * metodo que construye la respuesta para el cliente una vez fue procesado por parte del switch de NovoPayment / method that builds the response for the client once it has been processed by the NovoPayment switch
     * @param currentOrchestrationTransactionRequest clase que contiene el request original enviado por parte del cliente / class containing the original request sent by the client
     * @param standardTransactionResponse clase que contiene el response procesado por NovoPayment  / class containing the response processed by NovoPayment
     * @return {@link CustomTransactionInformation} clase que se debe retornar al cliente con la información de la transacción / class that should be returned to the client with the transaction information
     */
    @Override
    public CustomTransactionInformation buildCustomTransactionInformation(final OrchestrationTransactionRequest currentOrchestrationTransactionRequest,
                                                                          final StandardTransaction standardTransactionResponse) {

        final CustomTransactionInformation customTransactionInformation = customTransactionMapper.mapperCustomTransaction(standardTransactionResponse);
        customTransactionInformation.setDe3(currentOrchestrationTransactionRequest.getTransaction().getDe3());
        customTransactionInformation.setMessageTypeIndicator(standardTransactionResponse.getMessageTypeIndicator());

        // se proceso el StructuredData que retorna  postilion y se extraen los campos B24_F60 y B24_F63
        if (StringUtils.isNoneEmpty(standardTransactionResponse.getDataElement_127_22())) {
            final StructuredData structuredData = new StructuredData();
            structuredData.fromMsgString(standardTransactionResponse.getDataElement_127_22());
            customTransactionInformation.setDe44(structuredData.get(ORCHESTRATOR_REQUEST_TRANSACTION_FIELD_NAME_DE_44));
            customTransactionInformation.setDe60(structuredData.get(ORCHESTRATOR_REQUEST_TRANSACTION_FIELD_NAME_DE_60));
            customTransactionInformation.setDe63(structuredData.get(ORCHESTRATOR_REQUEST_TRANSACTION_FIELD_NAME_DE_63));
        }

        log.debug("buildCustomTransactionInformation process, customTransactionInformation: {}", customTransactionInformation);
        return customTransactionInformation;
    }
}