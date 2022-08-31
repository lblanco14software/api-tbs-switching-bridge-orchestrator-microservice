package com.novo.microservices.services.implementations;

import com.novo.microservices.components.enums.ResponseCode;
import com.novo.microservices.components.enums.ValidateResult;
import com.novo.microservices.components.helpers.ContextCurrentTimeHelper;
import com.novo.microservices.components.helpers.SagaTransactionHelper;
import com.novo.microservices.components.helpers.SagaTransactionalRequestHelper;
import com.novo.microservices.components.validations.IOrchestrationHsmGenerateKeyRequestValidation;
import com.novo.microservices.components.validations.ISagaTransactionalStateResponseValidation;
import com.novo.microservices.dtos.OrchestratorTransactionInformation;
import com.novo.microservices.dtos.SagaTransactionalStateResult;
import com.novo.microservices.dtos.custom.CustomTransactionInformation;
import com.novo.microservices.dtos.custom.PaymentHeaderInformation;
import com.novo.microservices.dtos.generics.GenericBusinessResponse;
import com.novo.microservices.dtos.requests.*;
import com.novo.microservices.dtos.responses.HsmGenerateKeyResponse;
import com.novo.microservices.dtos.responses.OrchestrationGenerateKeyResponse;
import com.novo.microservices.events.outbounds.contracts.IOrchestratorGenerateKeyProducer;
import com.novo.microservices.repositories.entities.OrchestratorHsmReportEntity;
import com.novo.microservices.services.contracts.*;
import com.novo.microservices.tbs.utils.components.enums.HsmResponseCodes;
import com.novo.microservices.tbs.utils.components.enums.SagaEventType;
import com.novo.microservices.tbs.utils.components.exceptions.BusinessProcessException;
import com.novo.microservices.tbs.utils.components.exceptions.SagaProcessException;
import com.novo.microservices.tbs.utils.dtos.TransactionMessage;
import com.novo.microservices.tbs.utils.dtos.requests.CreateSagaTransactionalRequest;
import com.novo.microservices.tbs.utils.dtos.requests.CreateSagaTransactionalStateRequest;
import com.novo.microservices.tbs.utils.dtos.responses.BusinessProcessResponse;
import com.novo.microservices.tbs.utils.dtos.responses.CreateSagaTransactionalResponse;
import com.novo.microservices.tbs.utils.dtos.responses.CreateSagaTransactionalStateResponse;
import com.novo.microservices.tbs.utils.services.contracts.ISagaMongoDatabaseService;
import com.novo.microservices.tbs.utils.services.contracts.ISagaTransactionalBridgeService;
import com.novo.microservices.utils.common.context.AppSessionContext;
import com.novo.utils.messaging.dtos.Event;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.Objects;
import java.util.Optional;

import static com.novo.microservices.constants.HsmConstants.HSM_RESPONSE_MESSAGE_INVALID;
import static com.novo.microservices.constants.ProcessConstants.*;
import static com.novo.microservices.transactions.constants.OrchestratorTransactionConstants.ORCHESTRATOR_HSM_GENERATE_KEY_TRANSACTION_CODE;
import static org.springframework.beans.factory.config.BeanDefinition.SCOPE_PROTOTYPE;

/**
 * OrchestratorGenerateKeyService
 * <p>
 * OrchestratorGenerateKeyService class.
 * <p>
 * <p>
 * ESTE COMPONENTE FUE CONSTRUIDO SIGUIENDO LOS ESTANDARES DE DESARROLLO Y EL PROCEDIMIENTO
 * DE DESARROLLO DE APLICACIONES DE NOVOPAYMENT Y SE ENCUENTRA PROTEGIDO POR LAS LEYES DE
 * PROPIEDAD INTELECTUAL Y DERECHOS DE AUTOR.
 *
 * @author Novopayment Inc.
 * @author jquiroga@novopayment.com
 * @since 5/12/2022
 */
@Log4j2
@Service
@Scope(SCOPE_PROTOTYPE)
public class OrchestratorGenerateKeyService implements IOrchestratorGenerateKeyService {

    @Value(MICROSERVICES_UUID_CONFIGURATION)
    private String microservicesUuId;

    private final IOrchestratorHsmConfigurationService hsmGenerateKeyConfigurationService;
    private final IOrchestratorClassificationService orchestratorClassificationService;
    private final IOrchestratorGenerateKeyProducer orchestratorGenerateKeyProducer;
    private final ISagaTransactionalBridgeService sagaTransactionalBridgeService;
    private final IOrchestratorEncryptionService orchestratorEncryptionService;
    private final IOrchestratorParameterService orchestratorParameterService;
    private final ISagaMongoDatabaseService sagaMongoDatabaseService;
    private final IOrchestratorStateService orchestratorStateService;
    private final IOrchestratorCrudService orchestratorCrudService;
    private final IHsmReportService hsmReportService;
    private final AppSessionContext appSessionContext;

    @Autowired
    public OrchestratorGenerateKeyService(final IOrchestratorHsmConfigurationService hsmGenerateKeyConfigurationService,
                                          final IOrchestratorClassificationService orchestratorClassificationService,
                                          final IOrchestratorGenerateKeyProducer orchestratorGenerateKeyProducer,
                                          final ISagaTransactionalBridgeService sagaTransactionalBridgeService,
                                          final IOrchestratorEncryptionService orchestratorEncryptionService,
                                          final IOrchestratorParameterService orchestratorParameterService,
                                          final ISagaMongoDatabaseService sagaMongoDatabaseService,
                                          final IOrchestratorStateService orchestratorStateService,
                                          final IOrchestratorCrudService orchestratorCrudService,
                                          final IHsmReportService hsmReportService,
                                          final AppSessionContext appSessionContext) {

        this.hsmGenerateKeyConfigurationService = hsmGenerateKeyConfigurationService;
        this.orchestratorClassificationService = orchestratorClassificationService;
        this.orchestratorGenerateKeyProducer = orchestratorGenerateKeyProducer;
        this.sagaTransactionalBridgeService = sagaTransactionalBridgeService;
        this.orchestratorEncryptionService = orchestratorEncryptionService;
        this.orchestratorParameterService = orchestratorParameterService;
        this.sagaMongoDatabaseService = sagaMongoDatabaseService;
        this.orchestratorStateService = orchestratorStateService;
        this.orchestratorCrudService = orchestratorCrudService;
        this.hsmReportService = hsmReportService;
        this.appSessionContext = appSessionContext;
    }

    @Override
    public Mono<BusinessProcessResponse> doOnProcessGenerateKeyByRest(final OrchestrationGenerateKeyRequest orchestrationGenerateKeyRequest) {
        TransactionMessage<HsmGenerateKeyRequest> transactionMessageRequest = TransactionMessage.<HsmGenerateKeyRequest>builder().build();
        OrchestratorHsmReportEntity entity = new OrchestratorHsmReportEntity();
        return doOnSaveReportEntity(orchestrationGenerateKeyRequest, entity)
            .flatMap(this::doOnValidateFields)
            .flatMap(this::doOnMapperGenerateKeyRequestToSagaRequest)
            .flatMap(this::doOnSaveSagaTransaction)
            .flatMap(this::doOnSaveOrchestratorCrudTransaction)
            .flatMap(this::doOnUpdateSagaTransaction)
            .flatMap(sagaRequest -> doOnSendGenerateHsmRequest(sagaRequest, transactionMessageRequest))
            .flatMap(this::doOnWaitTransactionResponse)
            .flatMap(response -> doOnGenerateResponse(response, orchestrationGenerateKeyRequest, transactionMessageRequest, entity))
            .doOnSuccess(success -> log.info("success doOnProcessGenerateKeyByRest from orchestrationHsmGenerateKeyRequest, response: {}", success))
            .doOnError(throwable -> log.error("exception error in process doOnProcessGenerateKeyByRest from orchestrationHsmGenerateKeyRequest, error: {}", throwable.getMessage()))
            .log();
    }

    private Mono<OrchestrationGenerateKeyRequest> doOnSaveReportEntity(OrchestrationGenerateKeyRequest orchestrationGenerateKeyRequest, OrchestratorHsmReportEntity entity) {
        entity.setTrackingId(orchestrationGenerateKeyRequest.getTrackingId());
        entity.setTransactionDateRequest(getNow());
        return hsmReportService.save(entity)
            .flatMap(reportEntity -> {
                entity.setId(reportEntity.getId());
                return Mono.just(orchestrationGenerateKeyRequest);
            })
            .doOnSuccess(success -> log.debug("success doOnSaveReportEntity from orchestrationHsmGenerateKeyRequest, response: {}", success))
            .doOnError(throwable -> log.error("exception error in process doOnSaveReportEntity from orchestrationHsmGenerateKeyRequest, error: {}", throwable.getMessage()))
            .log();
    }

    private Mono<OrchestrationGenerateKeyResponse> doOnUpdateReportEntity(OrchestrationGenerateKeyResponse response, OrchestratorHsmReportEntity entity) {
        return hsmReportService.update(entity.getId(), response.getData().getResponseCode(), getNow())
            .flatMap(reportEntity -> {
                entity.setResponseCode(reportEntity.getResponseCode());
                entity.setTransactionDateResponse(reportEntity.getTransactionDateResponse());
                return Mono.just(response);
            })
            .doOnSuccess(success -> log.debug("success doOnUpdateReportEntity from orchestrationHsmGenerateKeyRequest, response: {}", success))
            .doOnError(throwable -> log.error("exception error in process doOnUpdateReportEntity from orchestrationHsmGenerateKeyRequest, error: {}", throwable.getMessage()))
            .log();
    }

    private Mono<OrchestrationGenerateKeyRequest> doOnValidateFields(final OrchestrationGenerateKeyRequest orchestrationGenerateKeyRequest) {
        return Mono.just(IOrchestrationHsmGenerateKeyRequestValidation.validateRequest().apply(orchestrationGenerateKeyRequest))
            .flatMap(orchestratorValidationResult -> {
                if (ValidateResult.NOT_VALID.equals(orchestratorValidationResult.getValidateResult()))
                    return Mono.error(() -> new BusinessProcessException("error in validation orchestrationTransactionRequest", ResponseCode.ERROR_PARAMS_REQUIRED));
                return Mono.just(orchestrationGenerateKeyRequest);
            })
            .doOnSuccess(success -> log.debug("success doOnValidateRequest from orchestrationTransactionRequest, response: {}", success))
            .doOnError(throwable -> log.error("exception error in process doOnValidateRequest, error: {}", throwable.getMessage()))
            .log();
    }

    private Mono<OrchestrationSagaRequest> doOnMapperGenerateKeyRequestToSagaRequest(final OrchestrationGenerateKeyRequest request) {

        return orchestratorClassificationService.doOnFindTransactionClassificationByCode(ORCHESTRATOR_HSM_GENERATE_KEY_TRANSACTION_CODE)
            .flatMap(orchestratorTransactionClassification -> {

                if (Objects.isNull(orchestratorTransactionClassification)) {
                    return Mono.error(() -> new SagaProcessException("error in doOnMapperGenerateKeyRequestToSagaRequest the classification transaction by hsm generate is empty"));
                }

                return Mono.just(OrchestrationSagaRequest.builder()
                    .orchestrationTransactionRequest(OrchestrationTransactionRequest.builder()
                        .paymentHeader(PaymentHeaderInformation.builder()
                            .trackingId(request.getTrackingId())
                            .serviceId(orchestratorTransactionClassification.getServiceId())
                            .build())
                        .transaction(CustomTransactionInformation.builder()
                            .messageTypeIndicator(orchestratorTransactionClassification.getMessageTypeIndicator())
                            .de3(orchestratorTransactionClassification.getProcessingCode())
                            .build())
                        .build())
                    .transactionResponseReceived(false)
                    .build());
            })
            .doOnSuccess(success -> log.info("success mapperGenerateKeyRequestToSagaRequest, response: {}", success))
            .doOnError(throwable -> log.error("exception error in process mapperGenerateKeyRequestToSagaRequest, error: {}", throwable.getMessage()))
            .log();
    }

    private Mono<OrchestrationSagaRequest> doOnSaveSagaTransaction(final OrchestrationSagaRequest orchestrationSagaRequest) {
        return Mono.just(orchestrationSagaRequest)
            .flatMap(this::loadMongoDatabase)
            .flatMap(this::setClassification)
            .flatMap(this::doOnSetStates)
            .flatMap(this::doOnBuildSagaRequest)
            .flatMap(sagaTransactionalBridgeService::doOnCreateSagaTransaction)
            .flatMap(createSagaTransactionalResponse -> this.doOnHandleResponse(createSagaTransactionalResponse, orchestrationSagaRequest))
            .doOnSuccess(success -> log.info("success doOnSaveSagaTransaction from orchestrationHsmGenerateKeyRequest, response: {}", success))
            .doOnError(throwable -> log.error("exception error in process doOnSaveSagaTransaction from orchestrationHsmGenerateKeyRequest, error: {}", throwable.getMessage()))
            .log();
    }

    private Mono<OrchestrationSagaRequest> doOnUpdateSagaTransaction(final OrchestrationSagaRequest orchestrationSagaRequest) {
        return Mono.just(orchestrationSagaRequest)
            .flatMap(this::loadMongoDatabase)
            .flatMap(mVoid -> doOnBuildSagaStateRequest(orchestrationSagaRequest, orchestrationSagaRequest.getOrchestratorTransactionStates().getTransactionSagaStatePending()))
            .flatMap(sagaTransactionalBridgeService::doOnCreateSagaTransactionState)
            .flatMap(response -> this.doOnHandleStateResponse(response, orchestrationSagaRequest))
            .doOnSuccess(success -> log.info("success doOnSaveSagaTransaction from orchestrationHsmGenerateKeyRequest, response: {}", success))
            .doOnError(throwable -> log.error("exception error in process doOnSaveSagaTransaction from orchestrationHsmGenerateKeyRequest, error: {}", throwable.getMessage()))
            .log();
    }

    private Mono<OrchestrationSagaRequest> doOnSaveOrchestratorCrudTransaction(final OrchestrationSagaRequest orchestrationSagaRequest) {
        log.info("doOnSaveOrchestratorCrudTransaction {}", orchestrationSagaRequest);
        return Mono.just(orchestrationSagaRequest)
            .flatMap(this::loadMongoDatabase)
            .flatMap(this::doOnBuildSagaOrchestratorRequest)
            .flatMap(orchestratorCrudService::saveEntity)
            .flatMap(orchestratorTransactionInformation -> Mono.just(orchestrationSagaRequest))
            .doOnSuccess(success -> log.info("success doOnSaveOrchestratorCrudTransaction from orchestrationHsmGenerateKeyRequest, response: {}", success))
            .doOnError(throwable -> log.error("exception error in process doOnSaveOrchestratorCrudTransaction from orchestrationHsmGenerateKeyRequest, error: {}", throwable.getMessage()))
            .log();
    }

    private Mono<OrchestrationSagaRequest> doOnSendGenerateHsmRequest(final OrchestrationSagaRequest orchestrationSagaRequest,
                                                                      final TransactionMessage<HsmGenerateKeyRequest> transactionMessageRequest) {

        return Mono.just(orchestrationSagaRequest)
            .flatMap(this::doOnGenerateHsmRequest)
            .flatMap(transactionMessage -> {
                transactionMessageRequest.setData(transactionMessage.getData());
                return Mono.just(transactionMessage);
            })
            .flatMap(this::doOnSendHsmRequest)
            .flatMap(event -> Mono.just(orchestrationSagaRequest))
            .doOnSuccess(success -> log.info("success doSendGenereateHsmRequest from orchestrationHsmGenerateKeyRequest, standardTransaction: {}", success))
            .doOnError(throwable -> log.error("exception error in process doSendGenereateHsmRequest, error: {}", throwable.getMessage()))
            .log();
    }

    private Mono<OrchestrationSagaRequest> doOnWaitTransactionResponse(final OrchestrationSagaRequest orchestrationSagaRequest) {

        return Mono.just(orchestrationSagaRequest)
            .flatMap(currentOrchestrationSagaRequest -> {
                final int orchestratorTransactionTimeOut = SagaTransactionHelper.getOrchestratorTransactionTimeOut(orchestratorParameterService.findTenantParameterSetting(MICROSERVICES_ORCHESTRATOR_HSM_TIME_MILLISECONDS_CONFIG));
                final int orchestratorTransactionDelay = SagaTransactionHelper.getOrchestratorTransactionDelay(orchestratorParameterService.findTenantParameterSetting(MICROSERVICES_ORCHESTRATOR_HSM_DELAY_MILLISECONDS_CONFIG));
                final long responseWaitingStarTime = ContextCurrentTimeHelper.getStarTime();

                while (ContextCurrentTimeHelper.getTotalTime(responseWaitingStarTime) < orchestratorTransactionTimeOut && Boolean.FALSE.equals(currentOrchestrationSagaRequest.getTransactionResponseReceived())) {
                    sagaTransactionalBridgeService.doOnFindSagaTransactionState(currentOrchestrationSagaRequest.getSagaOutBox().getMessageId())
                        .delayElement(Duration.ofMillis(orchestratorTransactionDelay))
                        .flatMap(currentSagaTransactionalStateResponse -> {
                            final SagaTransactionalStateResult sagaTransactionalStateResult = ISagaTransactionalStateResponseValidation.validateResponse()
                                .and(ISagaTransactionalStateResponseValidation.validateChangeState(orchestrationSagaRequest.getCurrentSagaControl().getEventState()))
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
                return Mono.just(currentOrchestrationSagaRequest);
            })
            .doOnSuccess(success -> log.info("success doOnProcessGenerateKey from orchestrationHsmGenerateKeyRequest, standardTransaction: {}", success))
            .doOnError(throwable -> log.error("exception error in process doOnProcessGenerateKey from orchestrationHsmGenerateKeyRequest, error: {}", throwable.getMessage()))
            .log();
    }

    private Mono<BusinessProcessResponse> doOnGenerateResponse(final OrchestrationSagaRequest orchestrationSagaRequest,
                                                               final OrchestrationGenerateKeyRequest orchestrationGenerateKeyRequest,
                                                               final TransactionMessage<HsmGenerateKeyRequest> transactionMessageRequest,
                                                               final OrchestratorHsmReportEntity entity) {
        return Mono.just(orchestrationSagaRequest)
            .flatMap(currentOrchestrationSagaRequest -> orchestratorCrudService.getDomainEntityById(orchestrationSagaRequest.getSagaOutBox().getMessageId())
                .flatMap(orchestratorTransactionInformation -> {
                    currentOrchestrationSagaRequest.setOrchestratorTransactionInformation(orchestratorTransactionInformation);
                    return Mono.just(currentOrchestrationSagaRequest);
                })
            )
            .flatMap(currentOrchestrationSagaRequest -> {
                if (Boolean.TRUE.equals(currentOrchestrationSagaRequest.getTransactionResponseReceived()) && currentOrchestrationSagaRequest.getCurrentTransactionSagaState().equals(currentOrchestrationSagaRequest.getOrchestratorTransactionStates().getTransactionSagaStateCompleted()))
                    return orchestratorEncryptionService.doOnDecryptHsmGenerateKeyResponse(orchestrationSagaRequest.getOrchestratorTransactionInformation().getTransactionEncrypted());

                return Mono.error(() -> new SagaProcessException("error in doOnDecryptHsmGenerateKeyResponse from orchestrationHsmGenerateKeyRequest"));
            })
            .flatMap(response -> doOnMapperGenerateKeyResponse(response, orchestrationGenerateKeyRequest, transactionMessageRequest))
            .flatMap(response -> doOnUpdateReportEntity(response, entity))
            .flatMap(response -> Mono.just(BusinessProcessResponse.setEntitySuccessfullyResponse(new GenericBusinessResponse<>(response.getData()))))
            .doOnSuccess(success -> log.debug("success doOnGenerateResponse from orchestrationHsmGenerateKeyRequest, standardTransaction: {}", success))
            .doOnError(throwable -> log.error("exception error in process doOnGenerateResponse, error: {}", throwable.getMessage()))
            .log();
    }

    private Mono<OrchestrationGenerateKeyResponse> doOnMapperGenerateKeyResponse(final HsmGenerateKeyResponse hsmGenerateKeyResponse,
                                                                                 final OrchestrationGenerateKeyRequest orchestrationGenerateKeyRequest,
                                                                                 final TransactionMessage<HsmGenerateKeyRequest> transactionMessageRequest) {
        return Mono.just(OrchestrationGenerateKeyResponse.builder()
                .data(OrchestrationGenerateKeyResponse.GenerateKeyData.builder()
                    .trackingId(orchestrationGenerateKeyRequest.getTrackingId())
                    .key(getKey(hsmGenerateKeyResponse.getBody().getKeyUnderZmk()))
                    .kcv(hsmGenerateKeyResponse.getBody().getCheckValue())
                    .ksi(getKsi(transactionMessageRequest.getData().getBody().getKsn()))
                    .responseCode(hsmGenerateKeyResponse.getResponseCode())
                    .responseMessage(getResponseMessage(hsmGenerateKeyResponse.getResponseCode()))
                    .build())
                .build())
            .doOnSuccess(success -> log.debug("success doOnMapperHsmGenerateKeyResponseToOrchestrationGenerateKeyResponse"))
            .doOnError(throwable -> log.error("exception error in process doOnMapperHsmGenerateKeyResponseToOrchestrationGenerateKeyResponse, error: {}", throwable.getMessage()))
            .log();
    }

    private String getKey(String key) {
        if (key.length() == 32 && key.charAt(0) != 'X' && key.charAt(0) != 'U') return key;
        return key.substring(1);
    }

    private String getKsi(String ksn) {
        return ksn.substring(0, 10);
    }

    private String getResponseMessage(String responseCode) {
        Optional<HsmResponseCodes> hsmResponse = HsmResponseCodes.getResponseByCode(responseCode);
        if (hsmResponse.isEmpty()) return HSM_RESPONSE_MESSAGE_INVALID;

        return hsmResponse.get().getDescription();
    }

    private Mono<OrchestrationSagaRequest> loadMongoDatabase(final OrchestrationSagaRequest orchestrationSagaRequest) {
        sagaMongoDatabaseService.loadMongoDatabase(appSessionContext.getApplicationSession().getTenantId()).subscribe();
        return Mono.just(orchestrationSagaRequest);
    }

    /*
     * Start Save Saga Transaction Functions
     * */
    private Mono<OrchestrationSagaRequest> setClassification(final OrchestrationSagaRequest orchestrationSagaRequest) {
        return orchestratorClassificationService
            .doOnFindTransactionClassification(orchestrationSagaRequest.getOrchestrationTransactionRequest())
            .flatMap(orchestratorTransactionClassification -> {
                orchestrationSagaRequest.setOrchestratorTransactionClassification(orchestratorTransactionClassification);
                return Mono.just(orchestrationSagaRequest);
            })
            .doOnSuccess(success -> log.debug("success setClassification from orchestrationHsmGenerateKeyRequest, standardTransaction: {}", success))
            .doOnError(throwable -> log.error("exception error in process setClassification, error: {}", throwable.getMessage()))
            .log();
    }

    private Mono<OrchestrationSagaRequest> doOnSetStates(final OrchestrationSagaRequest orchestrationSagaRequest) {
        return orchestratorStateService
            .doOnLoadOrchestratorTransactionStates(orchestrationSagaRequest.getOrchestratorTransactionClassification())
            .flatMap(orchestratorTransactionStates -> {
                orchestrationSagaRequest.setOrchestratorTransactionStates(orchestratorTransactionStates);
                return Mono.just(orchestrationSagaRequest);
            })
            .doOnSuccess(success -> log.debug("success setStates from orchestrationHsmGenerateKeyRequest, standardTransaction: {}", success))
            .doOnError(throwable -> log.error("exception error in process setStates, error: {}", throwable.getMessage()))
            .log();
    }

    private Mono<CreateSagaTransactionalRequest> doOnBuildSagaRequest(final OrchestrationSagaRequest orchestrationSagaRequest) {
        return Mono.just(SagaTransactionalRequestHelper.buildCreateSagaTransactionalRequest(
                orchestrationSagaRequest.getOrchestrationTransactionRequest(),
                appSessionContext,
                microservicesUuId,
                orchestrationSagaRequest.getOrchestratorTransactionClassification().getTransactionStateCode(),
                SagaEventType.EVENT_TYPE_INBOUND,
                orchestrationSagaRequest.getOrchestratorTransactionStates().getTransactionSagaStateReceived()))
            .doOnSuccess(success -> log.debug("success buildSagaRequest from orchestrationHsmGenerateKeyRequest, standardTransaction: {}", success))
            .doOnError(throwable -> log.error("exception error in process buildSagaRequest, error: {}", throwable.getMessage()))
            .log();
    }

    private Mono<CreateSagaTransactionalStateRequest> doOnBuildSagaStateRequest(final OrchestrationSagaRequest orchestrationSagaRequest, final String sagaState) {
        return Mono.just(SagaTransactionalRequestHelper.buildCreateSagaTransactionalStateRequest(
                appSessionContext,
                microservicesUuId,
                orchestrationSagaRequest.getSagaOutBox().getMessageId(),
                orchestrationSagaRequest.getSagaOutBox().getCorrelationId(),
                SagaEventType.EVENT_TYPE_OUTBOUND,
                sagaState))
            .doOnSuccess(success -> log.debug("success doOnBuildSagaStateRequest from orchestrationHsmGenerateKeyRequest, standardTransaction: {}", success))
            .doOnError(throwable -> log.error("exception error in process doOnBuildSagaStateRequest, error: {}", throwable.getMessage()))
            .log();
    }

    private Mono<OrchestrationSagaRequest> doOnHandleResponse(final CreateSagaTransactionalResponse createSagaTransactionalResponse, final OrchestrationSagaRequest orchestrationSagaRequest) {

        if (!createSagaTransactionalResponse.isSuccessfullyResponse()) return Mono.error(() -> new SagaProcessException("error in CreateSagaTransactionRequest from orchestrationHsmGenerateKeyRequest"));

        orchestrationSagaRequest.setSagaOutBox(createSagaTransactionalResponse.getSagaOutBox());
        orchestrationSagaRequest.setCurrentSagaControl(createSagaTransactionalResponse.getSagaControl());
        return Mono.just(orchestrationSagaRequest);
    }

    private Mono<OrchestrationSagaRequest> doOnHandleStateResponse(final CreateSagaTransactionalStateResponse createSagaTransactionalStateResponse, final OrchestrationSagaRequest orchestrationSagaRequest) {

        if (!createSagaTransactionalStateResponse.isSuccessfullyResponse()) return Mono.error(() -> new SagaProcessException("error in CreateSagaTransactionStateRequest from orchestrationHsmGenerateKeyRequest"));

        orchestrationSagaRequest.setCurrentSagaControl(createSagaTransactionalStateResponse.getSagaControl());
        return Mono.just(orchestrationSagaRequest);
    }
    /*
     * End Save Saga Transaction Functions
     * */

    /*
     * Start Save Saga Orchestrator Transaction Functions
     * */
    private Mono<OrchestratorTransactionInformation> doOnBuildSagaOrchestratorRequest(final OrchestrationSagaRequest orchestrationSagaRequest) {

        return Mono.just(OrchestratorTransactionInformation.builder()
                .messageId(orchestrationSagaRequest.getSagaOutBox().getMessageId())
                .serviceId(orchestrationSagaRequest.getOrchestratorTransactionClassification().getServiceId())
                .messageTypeIndicator(orchestrationSagaRequest.getOrchestratorTransactionClassification().getMessageTypeIndicator())
                .processingCode(orchestrationSagaRequest.getOrchestratorTransactionClassification().getProcessingCode())
                .transactionEncrypted(MICROSERVICES_ORCHESTRATOR_TRANSACTION_ENCRYPTION_EMPTY)
                .build())
            .doOnSuccess(success -> log.debug("success doOnBuildSagaOrchestratorRequest from orchestrationHsmGenerateKeyRequest, standardTransaction: {}", success))
            .doOnError(throwable -> log.error("exception error in process doOnBuildSagaOrchestratorRequest, error: {}", throwable.getMessage()))
            .log();
    }
    /*
     * End Save Saga Orchestrator Transaction Functions
     * */

    /*
     * Start HSM Generate Key Functions
     * */
    private Mono<HsmGenerateKeyRequest> addKsn(final HsmGenerateKeyRequest configuration) {
        configuration.getBody().setKsn(makeKsn());
        return Mono.just(configuration);
    }

    private String makeKsn() {
        return SagaTransactionHelper.generateIdByKey(10) + "00000";
    }

    private Mono<TransactionMessage<HsmGenerateKeyRequest>> doOnBuildTransactionMessage(final HsmGenerateKeyRequest hsmRequest, final OrchestrationSagaRequest sagaRequest) {
        return Mono.just(TransactionMessage.<HsmGenerateKeyRequest>builder()
                .messageId(sagaRequest.getSagaOutBox().getMessageId())
                .correlationId(sagaRequest.getSagaOutBox().getCorrelationId())
                .tenantId(sagaRequest.getSagaOutBox().getTenantId())
                .requestId(sagaRequest.getSagaOutBox().getRequestId())
                .eventSource(sagaRequest.getSagaOutBox().getEventSource())
                .eventState(sagaRequest.getCurrentTransactionSagaState())
                .transactionType(sagaRequest.getOrchestratorTransactionClassification().getMessageTypeIndicator())
                .data(hsmRequest)
                .build())
            .doOnSuccess(success -> log.debug("success doOnBuildTransactionMessage from orchestrationHsmGenerateKeyRequest, standardTransaction: {}", success))
            .doOnError(throwable -> log.error("exception error in process doOnBuildTransactionMessage, error: {}", throwable.getMessage()))
            .log();
    }
    /*
     * End HSM Generate Key Functions
     * */

    /*
     * Start Send HSM Generate Key Transaction
     * */
    private Mono<TransactionMessage<HsmGenerateKeyRequest>> doOnGenerateHsmRequest(final OrchestrationSagaRequest orchestrationSagaRequest) {
        return hsmGenerateKeyConfigurationService
            .doOnFindByHeaderAndCommand(HSM_GENERATE_KEY_HEADER_CONFIG, HSM_GENERATE_KEY_COMMAND_CONFIG)
            .flatMap(this::addKsn)
            .flatMap(hsmRequest -> doOnBuildTransactionMessage(hsmRequest, orchestrationSagaRequest))
            .doOnSuccess(success -> log.debug("success generateHsmRequest from orchestrationHsmGenerateKeyRequest, standardTransaction: {}", success))
            .doOnError(throwable -> log.error("exception error in process generateHsmRequest, error: {}", throwable.getMessage()))
            .log();
    }

    private Mono<Event<TransactionMessage<HsmRequest>>> doOnSendHsmRequest(TransactionMessage<HsmGenerateKeyRequest> transactionMessage) {
        return Mono.just(orchestratorGenerateKeyProducer.doOnProcessGenerateKey(transactionMessage))
            .doOnSuccess(success -> log.debug("success doOnSendHsmRequest from orchestrationHsmGenerateKeyRequest, standardTransaction: {}", success))
            .doOnError(throwable -> log.error("exception error in process doOnSendHsmRequest, error: {}", throwable.getMessage()))
            .log();
    }
    /*
     * End Send HSM Generate Key Transaction
     * */

    private Date getNow() {
        return Date.from(Instant.now());
    }
}