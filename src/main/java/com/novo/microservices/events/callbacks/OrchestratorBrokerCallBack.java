package com.novo.microservices.events.callbacks;

import com.novo.microservices.services.contracts.IOrchestratorTransactionErrorService;
import com.novo.microservices.tbs.utils.dtos.StandardTransaction;
import com.novo.microservices.tbs.utils.dtos.TransactionMessage;
import com.novo.microservices.tbs.utils.events.BaseMessageConsumer;
import com.novo.utils.messaging.components.aspects.ReturnCallBack;
import com.novo.utils.messaging.components.helpers.EnvironmentUtils;
import com.novo.utils.messaging.dtos.Event;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import static com.novo.microservices.constants.ProcessConstants.BROKER_ORCHESTRATOR_TRANSACTION_RESPONSE_QUEUE;

/**
 * TransactionBrokerCallBack
 * <p>
 * TransactionBrokerCallBack class.
 * <p>
 * <p>
 * ESTE COMPONENTE FUE CONSTRUIDO SIGUIENDO LOS ESTANDARES DE DESARROLLO Y EL PROCEDIMIENTO
 * DE DESARROLLO DE APLICACIONES DE NOVOPAYMENT Y SE ENCUENTRA PROTEGIDO POR LAS LEYES DE
 * PROPIEDAD INTELECTUAL Y DERECHOS DE AUTOR.
 *
 * @author Novopayment Inc.
 * @author lblanco@novopayment.com
 * @since 3/18/2022
 */
@Log4j2
@Service
public class OrchestratorBrokerCallBack extends BaseMessageConsumer {

    private final IOrchestratorTransactionErrorService orchestratorTransactionErrorService;
    private final EnvironmentUtils environmentUtils;

    public OrchestratorBrokerCallBack(final IOrchestratorTransactionErrorService orchestratorTransactionErrorService,
                                      final EnvironmentUtils environmentUtils) {

        this.orchestratorTransactionErrorService = orchestratorTransactionErrorService;
        this.environmentUtils = environmentUtils;
        log.debug("TransactionBrokerCallBack loaded successfully");
    }

    @ReturnCallBack
    public void processBrokerTransactionCallBack(int code, String message, Event<TransactionMessage<StandardTransaction>> eventTransactionMessage) {

        try {
            final TransactionMessage<StandardTransaction> transactionMessage = eventTransactionMessage.getData();
            this.processAppSessionContext(transactionMessage);
            log.error("receiving call back error message from a publisher");
            log.error("code: {}", code);
            log.error("message: {}", message);
            orchestratorTransactionErrorService.doOnProcessErrorInTransactionPublish(eventTransactionMessage).subscribe();
        } catch (Exception e) {
            log.error("error in processTransaction from processBrokerTransactionCallBack from queue [{}]", environmentUtils.getValue(BROKER_ORCHESTRATOR_TRANSACTION_RESPONSE_QUEUE));
            log.error("error in processTransaction from processBrokerTransactionCallBack, exception error: {}", e.getMessage());
        }
    }
}