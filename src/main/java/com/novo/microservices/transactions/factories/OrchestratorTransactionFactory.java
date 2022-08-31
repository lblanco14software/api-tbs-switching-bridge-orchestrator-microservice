package com.novo.microservices.transactions.factories;

import com.novo.microservices.components.exceptions.AbstractFactoryException;
import com.novo.microservices.transactions.contracts.IOrchestratorTransaction;
import com.novo.microservices.transactions.contracts.IOrchestratorTransactionFactory;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.stereotype.Component;

import static com.novo.microservices.constants.ProcessConstants.ABSTRACT_FACTORY_TRANSACTION_COMPONENT_CONFIGURATION;
import static com.novo.microservices.constants.ProcessConstants.ORCHESTRATOR_TRANSACTION_EXCEPTION;

/**
 * OrchestratorTransactionFactory
 * <p>
 * OrchestratorTransactionFactory class.
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
@Component(ABSTRACT_FACTORY_TRANSACTION_COMPONENT_CONFIGURATION)
public class OrchestratorTransactionFactory implements IOrchestratorTransactionFactory {

    private final BeanFactory beanFactory;

    public OrchestratorTransactionFactory(final BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    @Override
    public IOrchestratorTransaction factory(String transactionId) throws AbstractFactoryException {

        try {
            log.debug("transaction id: {}", transactionId);
            return beanFactory.getBean(transactionId, IOrchestratorTransaction.class);
        } catch (BeansException e) {
            log.error("error in process factory IOrchestratorTransaction, error: {}", e.getMessage());
            throw new AbstractFactoryException(ORCHESTRATOR_TRANSACTION_EXCEPTION);
        }
    }
}