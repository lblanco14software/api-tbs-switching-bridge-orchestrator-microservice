package com.novo.microservices.components.configurations;

import com.novo.microservices.tbs.utils.components.enums.SagaState;
import com.novo.microservices.tbs.utils.dtos.StandardTransaction;
import com.novo.microservices.tbs.utils.dtos.TransactionMessage;
import com.novo.microservices.tbs.utils.dtos.responses.BusinessProcessResponse;
import com.novo.microservices.tbs.utils.services.contracts.ITransactionService;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

/**
 * OrchestratorConfiguration
 * <p>
 * OrchestratorConfiguration class.
 * <p>
 * <p>
 * ESTE COMPONENTE FUE CONSTRUIDO SIGUIENDO LOS ESTANDARES DE DESARROLLO Y EL PROCEDIMIENTO
 * DE DESARROLLO DE APLICACIONES DE NOVOPAYMENT Y SE ENCUENTRA PROTEGIDO POR LAS LEYES DE
 * PROPIEDAD INTELECTUAL Y DERECHOS DE AUTOR.
 *
 * @author Novopayment Inc.
 * @author lblanco@novopayment.com
 * @since 4/28/2022
 */
@Configuration
public class OrchestratorConfiguration {

    @Service(BeanTransaction.BEAN_NAME)
    @Log4j2
    public static class BeanTransaction implements ITransactionService {

        public static final String BEAN_NAME = "BeanTransaction";

        @Override
        public Mono<BusinessProcessResponse> processTransaction(Mono<TransactionMessage<StandardTransaction>> transactionMessage) {
            return null;
        }

        @Override
        public Mono<TransactionMessage<StandardTransaction>> updateTransactionState(TransactionMessage<StandardTransaction> transactionMessage, SagaState sagaState) {
            return null;
        }
    }
}