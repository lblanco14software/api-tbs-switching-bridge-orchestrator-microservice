package com.novo.microservices.services.base;

import com.novo.microservices.components.enums.ValidateResult;
import com.novo.microservices.components.helpers.SagaTransactionalStateRequestHelper;
import com.novo.microservices.components.validations.IOrchestrationSagaResponseValidation;
import com.novo.microservices.components.validations.ISagaTransactionResponseValidation;
import com.novo.microservices.dtos.OrchestratorValidationResult;
import com.novo.microservices.dtos.responses.OrchestrationSagaResponse;
import com.novo.microservices.services.contracts.IOrchestratorClassificationService;
import com.novo.microservices.services.contracts.IOrchestratorCrudService;
import com.novo.microservices.services.contracts.IOrchestratorStateService;
import com.novo.microservices.tbs.utils.components.enums.SagaEventType;
import com.novo.microservices.tbs.utils.components.exceptions.SagaProcessException;
import com.novo.microservices.tbs.utils.dtos.StandardTransaction;
import com.novo.microservices.tbs.utils.dtos.TransactionMessage;
import com.novo.microservices.tbs.utils.services.contracts.ISagaMongoDatabaseService;
import com.novo.microservices.tbs.utils.services.contracts.ISagaTransactionalBridgeService;
import com.novo.utils.messaging.dtos.Event;
import lombok.extern.log4j.Log4j2;
import reactor.core.publisher.Mono;

/**
 * OrchestratorBaseTransactionService
 * <p>
 * OrchestratorBaseTransactionService class.
 * <p>
 * <p>
 * ESTE COMPONENTE FUE CONSTRUIDO SIGUIENDO LOS ESTANDARES DE DESARROLLO Y EL PROCEDIMIENTO
 * DE DESARROLLO DE APLICACIONES DE NOVOPAYMENT Y SE ENCUENTRA PROTEGIDO POR LAS LEYES DE
 * PROPIEDAD INTELECTUAL Y DERECHOS DE AUTOR.
 *
 * @author Novopayment Inc.
 * @author lblanco@novopayment.com
 * @since 4/29/2022
 */
@Log4j2
public abstract class OrchestratorBaseTransactionService {

    private final IOrchestratorClassificationService orchestratorClassificationService;
    private final ISagaTransactionalBridgeService sagaTransactionalBridgeService;
    private final IOrchestratorStateService orchestratorStateService;
    private final ISagaMongoDatabaseService sagaMongoDatabaseService;
    private final IOrchestratorCrudService orchestratorCrudService;

    public OrchestratorBaseTransactionService(final IOrchestratorClassificationService orchestratorClassificationService,
                                              final ISagaTransactionalBridgeService sagaTransactionalBridgeService,
                                              final IOrchestratorStateService orchestratorStateService,
                                              final ISagaMongoDatabaseService sagaMongoDatabaseService,
                                              final IOrchestratorCrudService orchestratorCrudService) {

        this.orchestratorClassificationService = orchestratorClassificationService;
        this.sagaTransactionalBridgeService = sagaTransactionalBridgeService;
        this.orchestratorStateService = orchestratorStateService;
        this.sagaMongoDatabaseService = sagaMongoDatabaseService;
        this.orchestratorCrudService = orchestratorCrudService;
    }

    public Mono<OrchestrationSagaResponse> doOnValidateEventTransactionResponse(final Event<TransactionMessage<StandardTransaction>> eventTransactionMessage) {

        return ISagaTransactionResponseValidation.validateTransactionMessage()
            .apply(eventTransactionMessage.getData())
            .flatMap(orchestratorValidationResult -> {
                if (ValidateResult.SUCCESSFULLY_VALID.equals(orchestratorValidationResult.getValidateResult())) {
                    return Mono.just(OrchestrationSagaResponse.builder().transactionMessage(eventTransactionMessage.getData()).build());
                }
                return Mono.error(() -> new SagaProcessException("error in validation doOnValidateEventTransactionResponse"));
            })
            .doOnSuccess(success ->
                log.debug("success doOnValidateEventTransactionResponse from eventTransactionMessage: {}", success)
            )
            .doOnError(throwable ->
                log.error("exception error in process doOnValidateEventTransactionResponse, error: {}", throwable.getMessage())
            )
            .log();
    }

    public Mono<OrchestrationSagaResponse> doOnValidateSagaTransaction(final OrchestrationSagaResponse orchestrationSagaResponse) {

        return Mono.just(orchestrationSagaResponse)
            .flatMap(currentOrchestrationSagaResponse -> {
                sagaMongoDatabaseService.loadMongoDatabase(orchestrationSagaResponse.getTransactionMessage().getTenantId());
                return Mono.just(orchestrationSagaResponse);
            })
            // load the transaction saga control
            .flatMap(currentOrchestrationSagaResponse -> sagaTransactionalBridgeService.doOnFindSagaTransactionState(orchestrationSagaResponse.getTransactionMessage().getMessageId())
                .flatMap(sagaTransactionalStateResponse -> {
                    orchestrationSagaResponse.setCurrentSagaControl(sagaTransactionalStateResponse.getSagaControl());
                    return Mono.just(orchestrationSagaResponse);
                })
            )
            // load the transaction crud information
            .flatMap(currentOrchestrationSagaResponse -> orchestratorCrudService.getDomainEntityById(orchestrationSagaResponse.getTransactionMessage().getMessageId())
                .flatMap(orchestratorTransactionInformation -> {
                    currentOrchestrationSagaResponse.setOrchestratorTransactionInformation(orchestratorTransactionInformation);
                    return Mono.just(currentOrchestrationSagaResponse);
                })
            )
            // we find the transaction classification
            .flatMap(currentOrchestrationSagaResponse -> orchestratorClassificationService.doOnFindTransactionClassification(
                        orchestrationSagaResponse.getOrchestratorTransactionInformation().getServiceId(),
                        orchestrationSagaResponse.getOrchestratorTransactionInformation().getMessageTypeIndicator(),
                        orchestrationSagaResponse.getOrchestratorTransactionInformation().getProcessingCode()
                    )
                    .flatMap(orchestratorTransactionClassification -> {
                        currentOrchestrationSagaResponse.setOrchestratorTransactionClassification(orchestratorTransactionClassification);
                        return Mono.just(currentOrchestrationSagaResponse);
                    })
            )
            // we load the transaction states, and we assigned de future transaction state
            .flatMap(currentOrchestrationSagaResponse -> orchestratorStateService.doOnLoadOrchestratorTransactionStates(
                        currentOrchestrationSagaResponse.getOrchestratorTransactionClassification()
                    )
                    .flatMap(orchestratorTransactionStates -> {
                        currentOrchestrationSagaResponse.setOrchestratorTransactionStates(orchestratorTransactionStates);
                        return Mono.just(currentOrchestrationSagaResponse);
                    })
            )
            // we validate if the status of the transaction is as expected to perform the update
            .flatMap(currentOrchestrationSagaResponse -> {
                final OrchestratorValidationResult orchestratorValidationResult = IOrchestrationSagaResponseValidation.validateMainResponse()
                    .and(IOrchestrationSagaResponseValidation.validateSagaState())
                    .apply(currentOrchestrationSagaResponse);

                if (ValidateResult.SUCCESSFULLY_VALID.equals(orchestratorValidationResult.getValidateResult())) {
                    return Mono.just(currentOrchestrationSagaResponse);
                } else {
                    return sagaTransactionalBridgeService.doOnCreateSagaTransactionHistory(SagaTransactionalStateRequestHelper.buildCreateSagaTransactionalStateRequest(
                        currentOrchestrationSagaResponse.getTransactionMessage(), currentOrchestrationSagaResponse.getMicroservicesUuId(), SagaEventType.EVENT_TYPE_INBOUND, currentOrchestrationSagaResponse.getNextTransactionSagaState())
                    ).flatMap(createSagaTransactionalStateResponse -> Mono.error(() -> new SagaProcessException("error in validation doOnUpdateSagaTransaction")));
                }
            })
            .doOnSuccess(success ->
                log.debug("success doOnValidateSagaTransaction from orchestrationSagaResponse: {}", success)
            )
            .doOnError(throwable ->
                log.error("exception error in process doOnValidateSagaTransaction, error: {}", throwable.getMessage())
            )
            .log();
    }
}