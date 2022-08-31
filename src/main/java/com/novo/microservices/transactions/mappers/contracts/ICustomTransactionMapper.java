package com.novo.microservices.transactions.mappers.contracts;

import com.novo.microservices.dtos.custom.CustomTransactionInformation;
import com.novo.microservices.tbs.utils.dtos.StandardTransaction;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Mappings;

import static com.novo.microservices.tbs.utils.constants.StandardTransactionConstants.*;
import static com.novo.microservices.transactions.constants.OrchestratorTransactionFieldsConstants.*;

/**
 * ICustomTransactionMapper
 * <p>
 * ICustomTransactionMapper interface.
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
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ICustomTransactionMapper {

    @Mappings({
        @Mapping(target = ORCHESTRATOR_RESPONSE_TRANSACTION_DE2, source = STANDARD_FIELD_DATA_ELEMENT_2),
        @Mapping(target = ORCHESTRATOR_RESPONSE_TRANSACTION_DE4, source = STANDARD_FIELD_DATA_ELEMENT_4),
        @Mapping(target = ORCHESTRATOR_RESPONSE_TRANSACTION_DE7, source = STANDARD_FIELD_DATA_ELEMENT_7),
        @Mapping(target = ORCHESTRATOR_RESPONSE_TRANSACTION_DE11, source = STANDARD_FIELD_DATA_ELEMENT_11),
        @Mapping(target = ORCHESTRATOR_RESPONSE_TRANSACTION_DE12, source = STANDARD_FIELD_DATA_ELEMENT_12),
        @Mapping(target = ORCHESTRATOR_RESPONSE_TRANSACTION_DE13, source = STANDARD_FIELD_DATA_ELEMENT_13),
        @Mapping(target = ORCHESTRATOR_RESPONSE_TRANSACTION_DE18, source = STANDARD_FIELD_DATA_ELEMENT_18),
        @Mapping(target = ORCHESTRATOR_RESPONSE_TRANSACTION_DE22, source = STANDARD_FIELD_DATA_ELEMENT_22),
        @Mapping(target = ORCHESTRATOR_RESPONSE_TRANSACTION_DE32, source = STANDARD_FIELD_DATA_ELEMENT_32),
        @Mapping(target = ORCHESTRATOR_RESPONSE_TRANSACTION_DE35, source = STANDARD_FIELD_DATA_ELEMENT_35),
        @Mapping(target = ORCHESTRATOR_RESPONSE_TRANSACTION_DE37, source = STANDARD_FIELD_DATA_ELEMENT_37),
        @Mapping(target = ORCHESTRATOR_RESPONSE_TRANSACTION_DE38, source = STANDARD_FIELD_DATA_ELEMENT_38),
        @Mapping(target = ORCHESTRATOR_RESPONSE_TRANSACTION_DE39, source = STANDARD_FIELD_DATA_ELEMENT_39),
        @Mapping(target = ORCHESTRATOR_RESPONSE_TRANSACTION_DE41, source = STANDARD_FIELD_DATA_ELEMENT_41),
        @Mapping(target = ORCHESTRATOR_RESPONSE_TRANSACTION_DE42, source = STANDARD_FIELD_DATA_ELEMENT_42),
        @Mapping(target = ORCHESTRATOR_RESPONSE_TRANSACTION_DE43, source = STANDARD_FIELD_DATA_ELEMENT_43),
        @Mapping(target = ORCHESTRATOR_RESPONSE_TRANSACTION_DE49, source = STANDARD_FIELD_DATA_ELEMENT_49),
    })
    CustomTransactionInformation mapperCustomTransaction(StandardTransaction standardTransactionResponse);
}