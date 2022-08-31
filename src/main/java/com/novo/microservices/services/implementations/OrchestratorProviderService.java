package com.novo.microservices.services.implementations;

import com.novo.microservices.components.enums.ResponseCode;
import com.novo.microservices.components.enums.ValidateResult;
import com.novo.microservices.components.helpers.SagaTransactionalRequestHelper;
import com.novo.microservices.dtos.OrchestratorTransactionClassification;
import com.novo.microservices.dtos.OrchestratorValidationResult;
import com.novo.microservices.dtos.generics.GenericBusinessResponse;
import com.novo.microservices.dtos.requests.OrchestrationSagaRequest;
import com.novo.microservices.dtos.requests.OrchestrationTransactionRequest;
import com.novo.microservices.dtos.responses.OrchestrationTransactionResponse;
import com.novo.microservices.services.contracts.IOrchestratorProviderService;
import com.novo.microservices.tbs.utils.components.enums.SagaEventType;
import com.novo.microservices.tbs.utils.components.exceptions.BusinessProcessException;
import com.novo.microservices.tbs.utils.components.exceptions.SagaProcessException;
import com.novo.microservices.tbs.utils.dtos.StandardTransaction;
import com.novo.microservices.tbs.utils.dtos.responses.BusinessProcessResponse;
import com.novo.microservices.tbs.utils.services.contracts.ISagaTransactionalBridgeService;
import com.novo.microservices.transactions.contracts.IOrchestratorTransaction;
import com.novo.microservices.transactions.contracts.IOrchestratorTransactionFactory;
import com.novo.microservices.utils.common.context.AppSessionContext;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import postilion.realtime.sdk.util.XSoftwareFailure;
import reactor.core.publisher.Mono;

import java.util.Objects;
import java.util.UUID;

import static com.novo.microservices.constants.ProcessConstants.MICROSERVICES_UUID_CONFIGURATION;
import static org.springframework.beans.factory.config.BeanDefinition.SCOPE_PROTOTYPE;

/**
 * OrchestratorProviderService
 * <p>
 * OrchestratorProviderService class.
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
public class OrchestratorProviderService implements IOrchestratorProviderService {

    @Value(MICROSERVICES_UUID_CONFIGURATION)
    private String microservicesUuId;
    private final ISagaTransactionalBridgeService sagaTransactionalBridgeService;
    private final IOrchestratorTransactionFactory orchestratorTransactionFactory;
    private final AppSessionContext appSessionContext;
    private final String orchestratorProviderServiceId;

    public OrchestratorProviderService(final ISagaTransactionalBridgeService sagaTransactionalBridgeService,
                                       final IOrchestratorTransactionFactory orchestratorTransactionFactory,
                                       final AppSessionContext appSessionContext) {

        this.sagaTransactionalBridgeService = sagaTransactionalBridgeService;
        this.orchestratorTransactionFactory = orchestratorTransactionFactory;
        this.appSessionContext = appSessionContext;
        this.orchestratorProviderServiceId = UUID.randomUUID().toString();
        log.debug("orchestratorProviderServiceId: {}", orchestratorProviderServiceId);
        log.debug("OrchestratorProviderService loaded successfully");
    }

    @Override
    public Mono<IOrchestratorTransaction> doOnFactoryOrchestratorTransaction(final OrchestrationSagaRequest orchestrationSagaRequest) {

        log.debug("orchestratorTransactionControllerId {}", orchestratorProviderServiceId);
        return Mono.just(orchestratorTransactionFactory.factory(orchestrationSagaRequest.getOrchestratorTransactionClassification().getTransactionCode()))
            .flatMap(orchestratorTransaction -> {
                if (Objects.nonNull(orchestratorTransaction)) {

                    final StandardTransaction standardTransaction = orchestratorTransaction.buildStandardTransaction(orchestrationSagaRequest.getOrchestrationTransactionRequest());
                    if (Objects.isNull(standardTransaction)) {
                        return Mono.error(() -> new SagaProcessException("error in findTransactionClassification process"));
                    }

                    final OrchestratorValidationResult orchestratorValidationResult = orchestratorTransaction.validateStandardTransactionRequest(standardTransaction);
                    if (ValidateResult.NOT_VALID.equals(orchestratorValidationResult.getValidateResult())) {
                        return Mono.error(() -> new BusinessProcessException("error in findTransactionClassification process", ResponseCode.ERROR_PARAMS_REQUIRED));
                    }

                    return Mono.just(orchestratorTransaction);
                }
                return Mono.error(() -> new SagaProcessException("error in findTransactionClassification process"));
            })
            .onErrorResume(XSoftwareFailure.class, e -> Mono.error(() -> new BusinessProcessException("error in findTransactionClassification process", ResponseCode.ERROR_INVALID_PARAMETERS)))
            .doOnSuccess(success ->
                log.debug("success process doOnFactoryStandardTransaction by the current transaction is: {}", success)
            )
            .doOnError(throwable -> {
                // T00053991-256. Register state INFORMATION_ERROR in Saga
                doOnCreateTransactionSagaStateInformationError(orchestrationSagaRequest).subscribe();
                log.error("error in process doOnFactoryStandardTransaction there is no transaction matching the following transaction classification: {}, error: {}", orchestrationSagaRequest.getOrchestratorTransactionClassification(), throwable.getMessage());
                }
            );
    }

    @Override
    public Mono<StandardTransaction> doOnFactoryStandardTransaction(final OrchestratorTransactionClassification orchestratorTransactionClassification, final OrchestrationTransactionRequest orchestrationTransactionRequest) {

        log.debug("orchestratorTransactionControllerId {}", orchestratorProviderServiceId);
        return Mono.just(orchestratorTransactionFactory.factory(orchestratorTransactionClassification.getTransactionCode()))
            .flatMap(orchestratorTransaction -> {
                if (Objects.nonNull(orchestratorTransaction)) {
                    final StandardTransaction standardTransaction = orchestratorTransaction.buildStandardTransaction(orchestrationTransactionRequest);
                    if (Objects.nonNull(standardTransaction)) {
                        return Mono.just(standardTransaction);
                    }
                    return Mono.error(() -> new SagaProcessException("error in doOnFactoryStandardTransaction process"));
                }
                return Mono.error(() -> new SagaProcessException("error in doOnFactoryStandardTransaction process"));
            })
            .doOnSuccess(success ->
                log.debug("success process doOnFactoryStandardTransaction by the current transaction is: {}", success)
            )
            .doOnError(throwable ->
                log.error("error in process doOnFactoryStandardTransaction there is no transaction matching the following transaction classification: {}, error: {}", orchestratorTransactionClassification, throwable.getMessage())
            );
    }

    @Override
    public Mono<BusinessProcessResponse> doOnProcessTransactionResponse(final OrchestrationSagaRequest orchestrationSagaRequest) {

        return Mono.just(orchestrationSagaRequest)
            .flatMap(currentOrchestrationSagaRequest -> {

                final Boolean transactionResponseReceived = orchestrationSagaRequest.getTransactionResponseReceived();
                final String currentTransactionSagaState = orchestrationSagaRequest.getCurrentTransactionSagaState();
                final String transactionSagaStateCompleted = orchestrationSagaRequest.getOrchestratorTransactionStates().getTransactionSagaStateCompleted();

                if (Boolean.TRUE.equals(transactionResponseReceived) && currentTransactionSagaState.equals(transactionSagaStateCompleted)) {
                    return doOnProcessTransactionSagaStateCompleted(orchestrationSagaRequest);
                } else {
                    return doOnCreateTransactionSagaStateInternalError(orchestrationSagaRequest);
                }
            })
            .doOnSuccess(success -> {
                log.debug("orchestratorProviderServiceId {}", orchestratorProviderServiceId);
                log.debug("success process doOnProcessTransactionResponse by the current orchestrationSagaRequest is {}, the orchestrationTransactionResponse is: {}", orchestrationSagaRequest, success);
            })
            .doOnError(throwable ->
                log.error("error in process doOnProcessTransactionResponse, error: {}", throwable.getMessage())
            )
            .log();
    }

    private Mono<BusinessProcessResponse> doOnProcessTransactionSagaStateCompleted(final OrchestrationSagaRequest orchestrationSagaRequest) {

        return Mono.just(orchestrationSagaRequest)
            .flatMap(currentOrchestrationSagaRequest -> {
                final IOrchestratorTransaction orchestratorTransaction = currentOrchestrationSagaRequest.getOrchestratorTransaction();
                final OrchestrationTransactionRequest orchestrationTransactionRequest = orchestrationSagaRequest.getOrchestrationTransactionRequest();
                final StandardTransaction standardTransaction = orchestrationSagaRequest.getOrchestratorTransactionInformation().getStandardTransactionResponse();
                final OrchestrationTransactionResponse orchestrationTransactionResponse = orchestratorTransaction.buildSuccessfullyTransactionResponse(orchestrationTransactionRequest, standardTransaction);

                return Mono.just(BusinessProcessResponse.setEntitySuccessfullyResponse(new GenericBusinessResponse<>(orchestrationTransactionResponse)));
            })
            .doOnSuccess(success -> {
                log.debug("orchestratorProviderServiceId {}", orchestratorProviderServiceId);
                log.debug("success process doOnCreateTransactionSagaStateCompleted by the current orchestrationSagaRequest is {}, the orchestrationTransactionResponse is: {}", orchestrationSagaRequest, success);
            })
            .doOnError(throwable ->
                log.error("error in process doOnCreateTransactionSagaStateCompleted, error: {}", throwable.getMessage())
            )
            .log();
    }

    private Mono<BusinessProcessResponse> doOnCreateTransactionSagaStateInformationError(final OrchestrationSagaRequest orchestrationSagaRequest) {

        return sagaTransactionalBridgeService.doOnCreateSagaTransactionState(
                SagaTransactionalRequestHelper.buildCreateSagaTransactionalStateRequest(
                    appSessionContext,
                    microservicesUuId,
                    orchestrationSagaRequest.getSagaOutBox().getMessageId(),
                    orchestrationSagaRequest.getSagaOutBox().getCorrelationId(),
                    SagaEventType.EVENT_TYPE_INBOUND,
                    orchestrationSagaRequest.getOrchestratorTransactionStates().getTransactionSagaStateInformationError()
                )
            )
            .flatMap(createSagaTransactionalResponse -> {
                if (createSagaTransactionalResponse.isSuccessfullyResponse()) {
                    final IOrchestratorTransaction orchestratorTransaction = orchestrationSagaRequest.getOrchestratorTransaction();
                    final OrchestrationTransactionResponse orchestrationTransactionResponse = orchestratorTransaction.buildErrorTransactionResponse(orchestrationSagaRequest.getOrchestrationTransactionRequest());

                    return Mono.just(BusinessProcessResponse.setEntitySuccessfullyResponse(new GenericBusinessResponse<>(orchestrationTransactionResponse)));
                }
                log.error("error in process doOnCreateTransactionSagaStateInformationError, error in process save time transaction response");
                return Mono.just(BusinessProcessResponse.setBusinessProcessError(ResponseCode.ERROR_PARAMS_REQUIRED));
            })
            .doOnSuccess(success -> {
                log.debug("orchestratorProviderServiceId {}", orchestratorProviderServiceId);
                log.debug("success process doOnCreateTransactionSagaStateInformationError by the current orchestrationSagaRequest is {}, the orchestrationTransactionResponse is: {}", orchestrationSagaRequest, success);
            })
            .doOnError(throwable ->
                log.error("error in process doOnCreateTransactionSagaStateInformationError, error: {}", throwable.getMessage())
            )
            .log();
    }

    private Mono<BusinessProcessResponse> doOnCreateTransactionSagaStateInternalError(final OrchestrationSagaRequest orchestrationSagaRequest) {

        return sagaTransactionalBridgeService.doOnCreateSagaTransactionState(
                SagaTransactionalRequestHelper.buildCreateSagaTransactionalStateRequest(
                    appSessionContext,
                    microservicesUuId,
                    orchestrationSagaRequest.getSagaOutBox().getMessageId(),
                    orchestrationSagaRequest.getSagaOutBox().getCorrelationId(),
                    SagaEventType.EVENT_TYPE_OUTBOUND,
                    orchestrationSagaRequest.getOrchestratorTransactionStates().getTransactionSagaStateInternalError()
                )
            )
            .flatMap(createSagaTransactionalResponse -> {
                if (createSagaTransactionalResponse.isSuccessfullyResponse()) {
                    final IOrchestratorTransaction orchestratorTransaction = orchestrationSagaRequest.getOrchestratorTransaction();
                    final OrchestrationTransactionResponse orchestrationTransactionResponse = orchestratorTransaction.buildErrorTransactionResponse(orchestrationSagaRequest.getOrchestrationTransactionRequest());

                    return Mono.just(BusinessProcessResponse.setEntitySuccessfullyResponse(new GenericBusinessResponse<>(orchestrationTransactionResponse)));
                }
                log.error("error in process doOnCreateTransactionSagaStateInternalError, error in process save time transaction response");
                return Mono.just(BusinessProcessResponse.setBusinessProcessError(ResponseCode.INTERNAL_SERVER_ERROR));
            })
            .doOnSuccess(success -> {
                log.debug("orchestratorProviderServiceId {}", orchestratorProviderServiceId);
                log.debug("success process doOnCreateTransactionSagaStateInternalError by the current orchestrationSagaRequest is {}, the orchestrationTransactionResponse is: {}", orchestrationSagaRequest, success);
            })
            .doOnError(throwable ->
                log.error("error in process doOnCreateTransactionSagaStateInternalError, error: {}", throwable.getMessage())
            )
            .log();
    }
}