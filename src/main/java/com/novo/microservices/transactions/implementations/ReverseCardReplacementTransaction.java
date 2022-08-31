package com.novo.microservices.transactions.implementations;

import com.novo.microservices.components.configurations.MessageConfiguration;
import com.novo.microservices.tbs.utils.dtos.commons.CommonBrokerConfiguration;
import com.novo.microservices.transactions.bases.OrchestratorBaseTransaction;
import com.novo.microservices.transactions.mappers.contracts.ICustomTransactionCommonMapper;
import com.novo.microservices.transactions.mappers.contracts.IStandardTransactionCommonDefaultValuesMapper;
import com.novo.microservices.transactions.mappers.contracts.IStandardTransactionCommonMapper;
import com.novo.microservices.transactions.mappers.contracts.IStandardTransactionStructureDataMapper;
import com.novo.microservices.transactions.validations.contracts.IStandardTransactionValidation;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import static com.novo.microservices.transactions.constants.OrchestratorTransactionConstants.ORCHESTRATOR_REVERSE_CARD_REPLACEMENT_TRANSACTION_CODE;
import static org.springframework.beans.factory.config.BeanDefinition.SCOPE_PROTOTYPE;

/**
 * ReverseCardReplacementTransaction
 * <p>
 * ReverseCardReplacementTransaction class.
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
@Component(ORCHESTRATOR_REVERSE_CARD_REPLACEMENT_TRANSACTION_CODE)
@Scope(SCOPE_PROTOTYPE)
public class ReverseCardReplacementTransaction extends OrchestratorBaseTransaction {

    public ReverseCardReplacementTransaction(final IStandardTransactionCommonMapper standardTransactionCommonMapper,
                                             final IStandardTransactionCommonDefaultValuesMapper standardTransactionDefaultValuesMapper,
                                             final IStandardTransactionStructureDataMapper standardTransactionStructureDataMapper,
                                             final ICustomTransactionCommonMapper customTransactionCommonMapper,
                                             final IStandardTransactionValidation standardTransactionValidation,
                                             final MessageConfiguration messageConfiguration) {
        super(standardTransactionCommonMapper,
            standardTransactionDefaultValuesMapper,
            standardTransactionStructureDataMapper,
            customTransactionCommonMapper,
            standardTransactionValidation,
            messageConfiguration,
            ORCHESTRATOR_REVERSE_CARD_REPLACEMENT_TRANSACTION_CODE);
    }

    @Override
    protected boolean isReverseTransaction() {
        return true;
    }

    @Override
    public CommonBrokerConfiguration getBrokerConfigurationProducer() {
        return messageConfiguration.getReverseCardReplacementTransactionProducer();
    }
}