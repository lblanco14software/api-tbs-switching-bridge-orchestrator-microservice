package com.novo.microservices.transactions.mappers.contracts;

import com.novo.microservices.dtos.requests.OrchestrationTransactionRequest;
import com.novo.microservices.tbs.utils.dtos.StandardTransaction;
import org.mapstruct.*;

import static com.novo.microservices.tbs.utils.constants.StandardTransactionConstants.*;
import static com.novo.microservices.transactions.constants.OrchestratorTransactionFieldsConstants.*;

/**
 * IStandardTransactionMapper
 * <p>
 * IStandardTransactionMapper interface.
 * <p>
 * <p>
 * ESTE COMPONENTE FUE CONSTRUIDO SIGUIENDO LOS ESTANDARES DE DESARROLLO Y EL PROCEDIMIENTO
 * DE DESARROLLO DE APLICACIONES DE NOVOPAYMENT Y SE ENCUENTRA PROTEGIDO POR LAS LEYES DE
 * PROPIEDAD INTELECTUAL Y DERECHOS DE AUTOR.
 *
 * @author Novopayment Inc.
 * @author lblanco@novopayment.com
 * @since 4/2/2022
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, nullValueMappingStrategy = NullValueMappingStrategy.RETURN_NULL)
public interface IStandardTransactionMapper {

    @Condition
    default boolean isNotEmpty(String value) {
        return value != null && value.length() > 0;
    }

    @Mappings({
        @Mapping(target = STANDARD_FIELD_DATA_ELEMENT_127_2, source = ORCHESTRATOR_REQUEST_PAYMENT_HEADER_TRACKING_ID),
        @Mapping(target = STANDARD_FIELD_MESSAGE_TYPE_INDICATOR, source = ORCHESTRATOR_REQUEST_TRANSACTION_MESSAGE_TYPE_INDICATOR),
        @Mapping(target = STANDARD_FIELD_DATA_ELEMENT_100, source = ORCHESTRATOR_REQUEST_PAYMENT_HEADER_BANK_CODE),
        @Mapping(target = STANDARD_FIELD_DATA_ELEMENT_2, source = ORCHESTRATOR_REQUEST_TRANSACTION_DE_2),
        @Mapping(target = STANDARD_FIELD_DATA_ELEMENT_3, source = ORCHESTRATOR_REQUEST_TRANSACTION_DE_3),
        @Mapping(target = STANDARD_FIELD_DATA_ELEMENT_4, source = ORCHESTRATOR_REQUEST_TRANSACTION_DE_4),
        @Mapping(target = STANDARD_FIELD_DATA_ELEMENT_7, source = ORCHESTRATOR_REQUEST_TRANSACTION_DE_7),
        @Mapping(target = STANDARD_FIELD_DATA_ELEMENT_11, source = ORCHESTRATOR_REQUEST_TRANSACTION_DE_11),
        @Mapping(target = STANDARD_FIELD_DATA_ELEMENT_12, source = ORCHESTRATOR_REQUEST_TRANSACTION_DE_12),
        @Mapping(target = STANDARD_FIELD_DATA_ELEMENT_13, source = ORCHESTRATOR_REQUEST_TRANSACTION_DE_13),
        @Mapping(target = STANDARD_FIELD_DATA_ELEMENT_18, source = ORCHESTRATOR_REQUEST_TRANSACTION_DE_18),
        @Mapping(target = STANDARD_FIELD_DATA_ELEMENT_22, source = ORCHESTRATOR_REQUEST_TRANSACTION_DE_22),
        @Mapping(target = STANDARD_FIELD_DATA_ELEMENT_32, source = ORCHESTRATOR_REQUEST_TRANSACTION_DE_32),
        @Mapping(target = STANDARD_FIELD_DATA_ELEMENT_35, source = ORCHESTRATOR_REQUEST_TRANSACTION_DE_35),
        @Mapping(target = STANDARD_FIELD_DATA_ELEMENT_37, source = ORCHESTRATOR_REQUEST_TRANSACTION_DE_37),
        @Mapping(target = STANDARD_FIELD_DATA_ELEMENT_41, source = ORCHESTRATOR_REQUEST_TRANSACTION_DE_41),
        @Mapping(target = STANDARD_FIELD_DATA_ELEMENT_42, source = ORCHESTRATOR_REQUEST_TRANSACTION_DE_42),
        @Mapping(target = STANDARD_FIELD_DATA_ELEMENT_43, source = ORCHESTRATOR_REQUEST_TRANSACTION_DE_43),
        @Mapping(target = STANDARD_FIELD_DATA_ELEMENT_49, source = ORCHESTRATOR_REQUEST_TRANSACTION_DE_49),
        @Mapping(target = STANDARD_FIELD_DATA_ELEMENT_52, source = ORCHESTRATOR_REQUEST_TRANSACTION_DE_52),
    })
    StandardTransaction mapperStandardTransaction(OrchestrationTransactionRequest orchestrationTransactionRequest);

    @Mappings({
        @Mapping(target = STANDARD_FIELD_DATA_ELEMENT_90, source = ORCHESTRATOR_REQUEST_TRANSACTION_DE_90),
    })
    StandardTransaction mapperReverseTransaction(OrchestrationTransactionRequest orchestrationTransactionRequest, @MappingTarget StandardTransaction standardTransaction);
}