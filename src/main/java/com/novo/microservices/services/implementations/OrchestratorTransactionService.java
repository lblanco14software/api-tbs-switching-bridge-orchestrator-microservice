package com.novo.microservices.services.implementations;

import com.google.gson.Gson;
import com.novo.microservices.components.enums.ResponseCode;
import com.novo.microservices.components.enums.ValidateResult;
import com.novo.microservices.components.helpers.ContextCurrentTimeHelper;
import com.novo.microservices.components.helpers.SagaTransactionHelper;
import com.novo.microservices.components.helpers.SagaTransactionalRequestHelper;
import com.novo.microservices.components.validations.IOrchestrationTransactionRequestValidation;
import com.novo.microservices.components.validations.ISagaTransactionalStateResponseValidation;
import com.novo.microservices.dtos.OrchestratorTransactionInformation;
import com.novo.microservices.dtos.SagaTransactionalStateResult;
import com.novo.microservices.dtos.requests.OrchestrationSagaRequest;
import com.novo.microservices.dtos.requests.OrchestrationTransactionRequest;
import com.novo.microservices.events.outbounds.contracts.IOrchestratorTransactionProducer;
import com.novo.microservices.services.contracts.*;
import com.novo.microservices.tbs.utils.components.enums.SagaEventType;
import com.novo.microservices.tbs.utils.components.exceptions.BusinessProcessException;
import com.novo.microservices.tbs.utils.components.exceptions.SagaProcessException;
import com.novo.microservices.tbs.utils.dtos.responses.BusinessProcessResponse;
import com.novo.microservices.tbs.utils.services.contracts.ISagaMongoDatabaseService;
import com.novo.microservices.tbs.utils.services.contracts.ISagaTransactionalBridgeService;
import com.novo.microservices.utils.common.context.AppSessionContext;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.UUID;

import static com.novo.microservices.constants.ProcessConstants.*;
import static org.springframework.beans.factory.config.BeanDefinition.SCOPE_PROTOTYPE;

/**
 * OrchestratorTransactionService
 * <p>
 * OrchestratorTransactionService class.
 * <p>
 * <p>
 * ESTE COMPONENTE FUE CONSTRUIDO SIGUIENDO LOS ESTANDARES DE DESARROLLO Y EL PROCEDIMIENTO
 * DE DESARROLLO DE APLICACIONES DE NOVOPAYMENT Y SE ENCUENTRA PROTEGIDO POR LAS LEYES DE
 * PROPIEDAD INTELECTUAL Y DERECHOS DE AUTOR.
 *
 * @author Novopayment Inc.
 * @author lblanco@novopayment.com
 * @since 4/7/2022
 */
@Log4j2
@Service
@Scope(SCOPE_PROTOTYPE)
public class OrchestratorTransactionService implements IOrchestratorTransactionService {

    @Value(MICROSERVICES_UUID_CONFIGURATION)
    private String microservicesUuId;
    private final IOrchestratorClassificationService orchestratorClassificationService;
    private final IOrchestratorTransactionProducer orchestratorTransactionProducer;
    private final ISagaTransactionalBridgeService sagaTransactionalBridgeService;
    private final IOrchestratorEncryptionService orchestratorEncryptionService;
    private final IOrchestratorParameterService orchestratorParameterService;
    private final IOrchestratorProviderService orchestratorProviderService;
    private final IOrchestratorStateService orchestratorStateService;
    private final ISagaMongoDatabaseService sagaMongoDatabaseService;
    private final IOrchestratorCrudService orchestratorCrudService;

    private final IOrchestratorTransactionReportCrudService orchestratorTransactionReportCrudService;
    private final AppSessionContext appSessionContext;
    private final String orchestratorTransactionServiceId;

    public OrchestratorTransactionService(final IOrchestratorClassificationService orchestratorClassificationService,
                                          final IOrchestratorTransactionProducer orchestratorTransactionProducer,
                                          final ISagaTransactionalBridgeService sagaTransactionalBridgeService,
                                          final IOrchestratorEncryptionService orchestratorEncryptionService,
                                          final IOrchestratorParameterService orchestratorParameterService,
                                          final IOrchestratorProviderService orchestratorProviderService,
                                          final IOrchestratorStateService orchestratorStateService,
                                          final ISagaMongoDatabaseService sagaMongoDatabaseService,
                                          final IOrchestratorCrudService orchestratorCrudService,
                                          final IOrchestratorTransactionReportCrudService orchestratorTransactionReportCrudService,
                                          final AppSessionContext appSessionContext) {

        this.orchestratorClassificationService = orchestratorClassificationService;
        this.orchestratorTransactionProducer = orchestratorTransactionProducer;
        this.sagaTransactionalBridgeService = sagaTransactionalBridgeService;
        this.orchestratorEncryptionService = orchestratorEncryptionService;
        this.orchestratorParameterService = orchestratorParameterService;
        this.orchestratorProviderService = orchestratorProviderService;
        this.orchestratorStateService = orchestratorStateService;
        this.sagaMongoDatabaseService = sagaMongoDatabaseService;
        this.orchestratorCrudService = orchestratorCrudService;
        this.orchestratorTransactionReportCrudService = orchestratorTransactionReportCrudService;
        this.appSessionContext = appSessionContext;
        this.orchestratorTransactionServiceId = UUID.randomUUID().toString();
        log.debug("orchestratorTransactionServiceId {}", orchestratorTransactionServiceId);
        log.debug("OrchestratorTransactionService loaded successfully");
    }

    @Override
    public Mono<BusinessProcessResponse> doOnProcessTransactionByRest(final OrchestrationTransactionRequest orchestrationTransactionRequest) {

        log.debug("orchestratorTransactionControllerId {}", orchestratorTransactionServiceId);
        return Mono.just(OrchestrationSagaRequest.builder().orchestrationTransactionRequest(orchestrationTransactionRequest).build())
            .flatMap(this::doOnValidateRequest)
            .flatMap(this::doOnSaveSagaTransaction)
            .flatMap(this::doOnSaveOrchestratorCrudTransaction)
            .flatMap(this::doOnConvertStandardTransaction)
            .flatMap(this::doOnPublishEventTransaction)
            .flatMap(this::doOnWaitTransactionResponse)
            .flatMap(this::doOnProcessTransactionResponse)
            .doOnSuccess(success -> {
                log.debug("success process processTransactionByRest");
                log.debug("success process processTransactionByRest, response: {}", success.toString());
            })
            .doOnError(throwable ->
                log.error("exception error in process processTransactionByRest, error: {}", throwable.getMessage())
            );
    }

    private Mono<OrchestrationSagaRequest> doOnValidateRequest(final OrchestrationSagaRequest orchestrationSagaRequest) {

        return Mono.just(IOrchestrationTransactionRequestValidation.validateMainRequest()
                .and(IOrchestrationTransactionRequestValidation.validatePaymentHeader())
                .and(IOrchestrationTransactionRequestValidation.validateTransactionRequest())
                .apply(orchestrationSagaRequest.getOrchestrationTransactionRequest()))
            .flatMap(orchestratorValidationResult -> {
                if (ValidateResult.NOT_VALID.equals(orchestratorValidationResult.getValidateResult())) {
                    // save transaction request and return error
                    return doOnSaveTransactionRequestWithValidationErrors(orchestrationSagaRequest)
                        .then(Mono.error(() -> new BusinessProcessException("error in validation orchestrationTransactionRequest", ResponseCode.ERROR_PARAMS_REQUIRED)));
                }
                return Mono.just(orchestrationSagaRequest);
            })
            .doOnSuccess(success ->
                log.debug("success doOnValidateRequest from orchestrationTransactionRequest, response: {}", success)
            )
            .doOnError(throwable ->
                log.error("exception error in process doOnValidateRequest, error: {}", throwable.getMessage())
            )
            .log();
    }

    private Mono<OrchestrationSagaRequest> doOnSaveTransactionRequestWithValidationErrors(final OrchestrationSagaRequest orchestrationSagaRequest) {
        return Mono.just(orchestrationSagaRequest.getOrchestrationTransactionRequest())
            .flatMap(orchestratorTransactionReportCrudService::saveEntity)
            .then(Mono.just(orchestrationSagaRequest));
    }

    private Mono<OrchestrationSagaRequest> doOnSaveSagaTransaction(final OrchestrationSagaRequest orchestrationSagaRequest) {

        return Mono.just(orchestrationSagaRequest)
            .flatMap(currentOrchestrationSagaRequest -> {
                sagaMongoDatabaseService.loadMongoDatabase(appSessionContext.getApplicationSession().getTenantId());
                return Mono.just(currentOrchestrationSagaRequest);
            })
            .flatMap(currentOrchestrationSagaRequest -> orchestratorClassificationService.doOnFindTransactionClassification(currentOrchestrationSagaRequest.getOrchestrationTransactionRequest())
                .flatMap(orchestratorTransactionClassification -> {
                    currentOrchestrationSagaRequest.setOrchestratorTransactionClassification(orchestratorTransactionClassification);
                    return Mono.just(currentOrchestrationSagaRequest);
                })
            )
            .flatMap(currentOrchestrationSagaRequest -> orchestratorStateService.doOnLoadOrchestratorTransactionStates(
                        currentOrchestrationSagaRequest.getOrchestratorTransactionClassification()
                    )
                    .flatMap(orchestratorTransactionStates -> {
                        currentOrchestrationSagaRequest.setOrchestratorTransactionStates(orchestratorTransactionStates);
                        return Mono.just(currentOrchestrationSagaRequest);
                    })
            )
            .flatMap(currentOrchestrationSagaRequest ->
                sagaTransactionalBridgeService.doOnCreateSagaTransaction(SagaTransactionalRequestHelper.buildCreateSagaTransactionalRequest(
                            currentOrchestrationSagaRequest.getOrchestrationTransactionRequest(),
                            appSessionContext,
                            microservicesUuId,
                            currentOrchestrationSagaRequest.getOrchestratorTransactionClassification().getTransactionStateCode(),
                            SagaEventType.EVENT_TYPE_INBOUND,
                            currentOrchestrationSagaRequest.getOrchestratorTransactionStates().getTransactionSagaStateReceived()
                        )
                    )
                    .flatMap(createSagaTransactionalResponse -> {
                        if (createSagaTransactionalResponse.isSuccessfullyResponse()) {
                            orchestrationSagaRequest.setSagaOutBox(createSagaTransactionalResponse.getSagaOutBox());
                            orchestrationSagaRequest.setCurrentSagaControl(createSagaTransactionalResponse.getSagaControl());
                            return Mono.just(currentOrchestrationSagaRequest);
                        }
                        return Mono.error(() -> new SagaProcessException("error in validation CreateSagaTransactionRequest"));
                    })
            )
            .doOnSuccess(success ->
                log.debug("success doOnSaveSagaTransaction from orchestrationTransactionRequest, response: {}", success)
            )
            .doOnError(throwable ->
                log.error("exception error in process doOnSaveSagaTransaction, error: {}", throwable.getMessage())
            )
            .log();
    }

    private Mono<OrchestrationSagaRequest> doOnSaveOrchestratorCrudTransaction(final OrchestrationSagaRequest orchestrationSagaRequest) {

        return Mono.just(orchestrationSagaRequest)
            .flatMap(currentOrchestrationSagaRequest -> {
                sagaMongoDatabaseService.loadMongoDatabase(appSessionContext.getApplicationSession().getTenantId());
                return Mono.just(currentOrchestrationSagaRequest);
            })
            .flatMap(currentOrchestrationSagaRequest -> orchestratorCrudService.saveEntity(
                    OrchestratorTransactionInformation.builder()
                        .messageId(currentOrchestrationSagaRequest.getSagaOutBox().getMessageId())
                        .serviceId(currentOrchestrationSagaRequest.getOrchestratorTransactionClassification().getServiceId())
                        .messageTypeIndicator(currentOrchestrationSagaRequest.getOrchestratorTransactionClassification().getMessageTypeIndicator())
                        .processingCode(currentOrchestrationSagaRequest.getOrchestratorTransactionClassification().getProcessingCode())
                        .transactionEncrypted(MICROSERVICES_ORCHESTRATOR_TRANSACTION_ENCRYPTION_EMPTY)
                        .build()
                )
                .flatMap(orchestratorTransactionInformation -> Mono.just(currentOrchestrationSagaRequest)))
            .doOnSuccess(success ->
                log.debug("success doOnSaveOrchestratorCrudTransaction from orchestrationTransactionRequest, response: {}", success)
            )
            .doOnError(throwable ->
                log.error("exception error in process doOnSaveOrchestratorCrudTransaction, error: {}", throwable.getMessage())
            )
            .log();
    }

    private Mono<OrchestrationSagaRequest> doOnConvertStandardTransaction(final OrchestrationSagaRequest orchestrationSagaRequest) {

        return orchestratorProviderService.doOnFactoryOrchestratorTransaction(orchestrationSagaRequest)
            .flatMap(orchestratorTransaction -> {
                orchestrationSagaRequest.setOrchestratorTransaction(orchestratorTransaction);
                return Mono.just(orchestrationSagaRequest);
            })
            .doOnSuccess(success ->
                log.debug("success doOnConvertStandardTransaction from orchestrationTransactionRequest: {}", success.getOrchestratorTransaction().getCurrentStandardTransactionRequest().toString())
            )
            .doOnError(throwable ->
                log.error("exception error in process doOnConvertStandardTransaction, error: {}", throwable.getMessage())
            )
            .log();
    }

    private Mono<OrchestrationSagaRequest> doOnPublishEventTransaction(final OrchestrationSagaRequest orchestrationSagaRequest) {

        return Mono.just(orchestrationSagaRequest)
            .flatMap(currentOrchestrationSagaRequest -> sagaTransactionalBridgeService.doOnCreateSagaTransactionState(SagaTransactionalRequestHelper.buildCreateSagaTransactionalStateRequest(
                        appSessionContext,
                        microservicesUuId,
                        currentOrchestrationSagaRequest.getSagaOutBox().getMessageId(),
                        currentOrchestrationSagaRequest.getSagaOutBox().getCorrelationId(),
                        SagaEventType.EVENT_TYPE_OUTBOUND,
                        currentOrchestrationSagaRequest.getOrchestratorTransactionStates().getTransactionSagaStatePending()
                    ))
                    .flatMap(createSagaTransactionalResponse -> {
                        if (createSagaTransactionalResponse.isSuccessfullyResponse()) {
                            orchestrationSagaRequest.setCurrentSagaControl(createSagaTransactionalResponse.getSagaControl());
                            return Mono.just(currentOrchestrationSagaRequest);
                        }
                        return Mono.error(() -> new SagaProcessException("error in validation CreateSagaTransactionRequest"));
                    })
            )
            .flatMap(currentOrchestrationSagaRequest -> orchestratorTransactionProducer.doOnProcessTransaction(
                    currentOrchestrationSagaRequest.getSagaOutBox(),
                    currentOrchestrationSagaRequest.getCurrentSagaControl(),
                    currentOrchestrationSagaRequest.getOrchestratorTransaction()
                ).flatMap(orchestratorProducerResult -> {
                    if (Boolean.TRUE.equals(orchestratorProducerResult.isSuccessfullyResponse())) {
                        currentOrchestrationSagaRequest.setTransactionResponseReceived(false);
                        currentOrchestrationSagaRequest.setCurrentTransactionSagaState(currentOrchestrationSagaRequest.getOrchestratorTransactionStates().getTransactionSagaStatePending());
                        return Mono.just(currentOrchestrationSagaRequest);
                    }
                    return Mono.error(() -> new SagaProcessException("error in validation doOnPublishEventTransaction"));
                })
            )
            .doOnSuccess(success ->
                log.debug("success doOnPublishEventTransaction from orchestrationSagaRequest: {}", success)
            )
            .doOnError(throwable ->
                log.error("exception error in process doOnPublishEventTransaction, error: {}", throwable.getMessage())
            )
            .log();
    }

    private Mono<OrchestrationSagaRequest> doOnWaitTransactionResponse(final OrchestrationSagaRequest orchestrationSagaRequest) {

        return Mono.just(orchestrationSagaRequest)
            .flatMap(currentOrchestrationSagaRequest -> {

                final int orchestratorTransactionTimeOut = SagaTransactionHelper.getOrchestratorTransactionTimeOut(orchestratorParameterService.findTenantParameterSetting(MICROSERVICES_ORCHESTRATOR_TRANSACTION_TIME_MILLISECONDS_CONFIG));
                final int orchestratorTransactionDelay = SagaTransactionHelper.getOrchestratorTransactionDelay(orchestratorParameterService.findTenantParameterSetting(MICROSERVICES_ORCHESTRATOR_TRANSACTION_DELAY_MILLISECONDS_CONFIG));
                final long responseWaitingStarTime = ContextCurrentTimeHelper.getStarTime();

                while (ContextCurrentTimeHelper.getTotalTime(responseWaitingStarTime) <= orchestratorTransactionTimeOut && Boolean.FALSE.equals(currentOrchestrationSagaRequest.getTransactionResponseReceived())) {

                    sagaTransactionalBridgeService.doOnFindSagaTransactionState(currentOrchestrationSagaRequest.getSagaOutBox().getMessageId())
                        .delayElement(Duration.ofMillis(orchestratorTransactionDelay))
                        .flatMap(currentSagaTransactionalStateResponse -> {
                            final SagaTransactionalStateResult sagaTransactionalStateResult = ISagaTransactionalStateResponseValidation.validateResponse()
                                .and(ISagaTransactionalStateResponseValidation.validateChangeState(orchestrationSagaRequest.getCurrentTransactionSagaState()))
                                .apply(currentSagaTransactionalStateResponse);

                            if (SagaTransactionalStateResult.SagaStateChangeResult.SAGA_EXPECTED_STATE.equals(sagaTransactionalStateResult.getSagaStateChangeResult())) {
                                // set true to transaction flag
                                currentOrchestrationSagaRequest.setTransactionResponseReceived(true);
                                // update the saga control entity with the last state of transaction
                                currentOrchestrationSagaRequest.setCurrentSagaControl(currentSagaTransactionalStateResponse.getSagaControl());
                                currentOrchestrationSagaRequest.setCurrentTransactionSagaState(currentSagaTransactionalStateResponse.getSagaControl().getEventState());
                            }
                            return Mono.just(currentSagaTransactionalStateResponse);
                        })
                        .subscribe();
                }

                log.info("Transaction response received: {}, sagaControl: {}",
                    currentOrchestrationSagaRequest.getTransactionResponseReceived(),
                    new Gson().toJson(currentOrchestrationSagaRequest.getCurrentSagaControl()));

                return Mono.just(currentOrchestrationSagaRequest);
            })
            .doOnSuccess(success ->
                log.debug("success doOnPublishEventTransaction from orchestrationSagaRequest, standardTransaction: {}", success)
            )
            .doOnError(throwable ->
                log.error("exception error in process doOnPublishEventTransaction, error: {}", throwable.getMessage())
            )
            .log();
    }

    private Mono<BusinessProcessResponse> doOnProcessTransactionResponse(final OrchestrationSagaRequest orchestrationSagaRequest) {

        return Mono.just(orchestrationSagaRequest)
            // load OrchestratorTransactionInformation contain the current information about response
            .flatMap(currentOrchestrationSagaRequest -> orchestratorCrudService.getDomainEntityById(orchestrationSagaRequest.getSagaOutBox().getMessageId())
                .flatMap(orchestratorTransactionInformation -> {
                    // set OrchestratorTransactionInformation to OrchestrationSagaRequest
                    currentOrchestrationSagaRequest.setOrchestratorTransactionInformation(orchestratorTransactionInformation);
                    return Mono.just(currentOrchestrationSagaRequest);
                })
            )
            .flatMap(currentOrchestrationSagaRequest -> {
                // validate if the transaction response was received
                if (Boolean.TRUE.equals(currentOrchestrationSagaRequest.getTransactionResponseReceived()) && currentOrchestrationSagaRequest.getCurrentTransactionSagaState().equals(currentOrchestrationSagaRequest.getOrchestratorTransactionStates().getTransactionSagaStateCompleted())) {
                    return orchestratorEncryptionService.doOnDecryptTransaction(orchestrationSagaRequest.getOrchestratorTransactionInformation().getTransactionEncrypted())
                        .flatMap(standardTransactionResponse -> {
                            // set StandardTransaction received to OrchestrationSagaRequest
                            currentOrchestrationSagaRequest.getOrchestratorTransactionInformation().setStandardTransactionResponse(standardTransactionResponse);
                            return Mono.just(currentOrchestrationSagaRequest);
                        });
                } else {
                    return Mono.just(currentOrchestrationSagaRequest);
                }
            })
            .flatMap(orchestratorProviderService::doOnProcessTransactionResponse)
            .doOnSuccess(success ->
                log.debug("success doOnProcessTransactionResponse from orchestrationSagaRequest, OrchestrationTransactionResponse: {}", success)
            )
            .doOnError(throwable ->
                log.error("exception error in process doOnProcessTransactionResponse")
            )
            .log();
    }
}