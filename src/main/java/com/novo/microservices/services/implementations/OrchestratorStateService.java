package com.novo.microservices.services.implementations;

import com.novo.microservices.components.helpers.SagaTransactionalStateRequestHelper;
import com.novo.microservices.dtos.OrchestratorTransactionClassification;
import com.novo.microservices.dtos.OrchestratorTransactionStates;
import com.novo.microservices.services.contracts.IOrchestratorStateService;
import com.novo.microservices.transactions.enums.TransactionSagaStatesFormats;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.UUID;

import static org.springframework.beans.factory.config.BeanDefinition.SCOPE_PROTOTYPE;

/**
 * OrchestratorStateService
 * <p>
 * OrchestratorStateService class.
 * <p>
 * <p>
 * ESTE COMPONENTE FUE CONSTRUIDO SIGUIENDO LOS ESTANDARES DE DESARROLLO Y EL PROCEDIMIENTO
 * DE DESARROLLO DE APLICACIONES DE NOVOPAYMENT Y SE ENCUENTRA PROTEGIDO POR LAS LEYES DE
 * PROPIEDAD INTELECTUAL Y DERECHOS DE AUTOR.
 *
 * @author Novopayment Inc.
 * @author lblanco@novopayment.com
 * @since 4/25/2022
 */
@Log4j2
@Service
@Scope(SCOPE_PROTOTYPE)
public class OrchestratorStateService implements IOrchestratorStateService {

    private final String orchestratorStateServiceId;

    public OrchestratorStateService() {
        this.orchestratorStateServiceId = UUID.randomUUID().toString();
        log.debug("orchestratorStateServiceId: {}", orchestratorStateServiceId);
        log.debug("OrchestratorStateService loaded successfully");
    }

    @Override
    public Mono<OrchestratorTransactionStates> doOnLoadOrchestratorTransactionStates(final OrchestratorTransactionClassification orchestratorTransactionClassification) {

        return Mono.just(OrchestratorTransactionStates.builder()
                .transactionSagaStateReceived(SagaTransactionalStateRequestHelper.loadTransactionSagaState(orchestratorTransactionClassification, TransactionSagaStatesFormats.ORCHESTRATOR_TRANSACTION_SAGA_STATE_FORMAT_RECEIVED))
                .transactionSagaStatePending(SagaTransactionalStateRequestHelper.loadTransactionSagaState(orchestratorTransactionClassification, TransactionSagaStatesFormats.ORCHESTRATOR_TRANSACTION_SAGA_STATE_FORMAT_PENDING))
                .transactionSagaStateCompleted(SagaTransactionalStateRequestHelper.loadTransactionSagaState(orchestratorTransactionClassification, TransactionSagaStatesFormats.ORCHESTRATOR_TRANSACTION_SAGA_STATE_FORMAT_COMPLETED))
                .transactionSagaStateInformationError(SagaTransactionalStateRequestHelper.loadTransactionSagaState(orchestratorTransactionClassification, TransactionSagaStatesFormats.ORCHESTRATOR_TRANSACTION_SAGA_STATE_FORMAT_INFORMATION_ERROR))
                .transactionSagaStateInternalError(SagaTransactionalStateRequestHelper.loadTransactionSagaState(orchestratorTransactionClassification, TransactionSagaStatesFormats.ORCHESTRATOR_TRANSACTION_SAGA_STATE_FORMAT_INTERNAL_ERROR))
                .build()
            )
            .doOnSuccess(success -> {
                log.debug("orchestratorStateServiceId {}", orchestratorStateServiceId);
                log.debug("success process doOnLoadOrchestratorTransactionStates by the current OrchestratorTransactionStates is: {}", success);
            })
            .doOnError(throwable ->
                log.error("error in process doOnLoadOrchestratorTransactionStates, error: {}", throwable.getMessage())
            );
    }
}