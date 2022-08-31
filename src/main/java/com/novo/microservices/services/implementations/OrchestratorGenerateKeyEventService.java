package com.novo.microservices.services.implementations;

import com.novo.microservices.components.helpers.SagaTransactionalStateRequestHelper;
import com.novo.microservices.dtos.responses.HsmGenerateKeyResponse;
import com.novo.microservices.services.contracts.IOrchestratorCrudService;
import com.novo.microservices.services.contracts.IOrchestratorEncryptionService;
import com.novo.microservices.services.contracts.IOrchestratorGenerateKeyEventService;
import com.novo.microservices.tbs.utils.components.enums.SagaEventType;
import com.novo.microservices.tbs.utils.components.enums.SagaState;
import com.novo.microservices.tbs.utils.components.exceptions.SagaProcessException;
import com.novo.microservices.tbs.utils.dtos.TransactionMessage;
import com.novo.microservices.tbs.utils.dtos.requests.CreateSagaTransactionalStateRequest;
import com.novo.microservices.tbs.utils.dtos.responses.CreateSagaTransactionalStateResponse;
import com.novo.microservices.tbs.utils.services.contracts.ISagaMongoDatabaseService;
import com.novo.microservices.tbs.utils.services.contracts.ISagaTransactionalBridgeService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import static com.novo.microservices.constants.ProcessConstants.HSM_GENERATE_KEY_TRANSACTION_TYPE;
import static com.novo.microservices.constants.ProcessConstants.MICROSERVICES_UUID_CONFIGURATION;
import static org.springframework.beans.factory.config.BeanDefinition.SCOPE_PROTOTYPE;

/**
 * OrchestratorGenerateKeyEventService
 * <p>
 * OrchestratorGenerateKeyEventService class.
 * <p>
 * <p>
 * ESTE COMPONENTE FUE CONSTRUIDO SIGUIENDO LOS ESTANDARES DE DESARROLLO Y EL PROCEDIMIENTO
 * DE DESARROLLO DE APLICACIONES DE NOVOPAYMENT Y SE ENCUENTRA PROTEGIDO POR LAS LEYES DE
 * PROPIEDAD INTELECTUAL Y DERECHOS DE AUTOR.
 *
 * @author Novopayment Inc.
 * @author jquiroga@novopayment.com
 * @since 5/19/2022
 */
@Log4j2
@Service
@Scope(SCOPE_PROTOTYPE)
public class OrchestratorGenerateKeyEventService implements IOrchestratorGenerateKeyEventService {

    @Value(MICROSERVICES_UUID_CONFIGURATION)
    private String microservicesUuId;
    private final IOrchestratorEncryptionService orchestratorEncryptionService;
    private final ISagaTransactionalBridgeService sagaTransactionalBridgeService;
    private final ISagaMongoDatabaseService sagaMongoDatabaseService;
    private final IOrchestratorCrudService orchestratorCrudService;

    public OrchestratorGenerateKeyEventService(final ISagaTransactionalBridgeService sagaTransactionalBridgeService,
                                               final IOrchestratorEncryptionService orchestratorEncryptionService,
                                               final ISagaMongoDatabaseService sagaMongoDatabaseService,
                                               final IOrchestratorCrudService orchestratorCrudService) {

        this.sagaTransactionalBridgeService = sagaTransactionalBridgeService;
        this.orchestratorEncryptionService = orchestratorEncryptionService;
        this.sagaMongoDatabaseService = sagaMongoDatabaseService;
        this.orchestratorCrudService = orchestratorCrudService;
    }

    @Override
    public Mono<HsmGenerateKeyResponse> doUpdateProcessGenerateKey(TransactionMessage<HsmGenerateKeyResponse> transactionMessage, SagaState sagaState) {

        return Mono.just(transactionMessage)
            .flatMap(this::loadMongoDatabase)
            .flatMap(tMessage -> orchestratorEncryptionService.doOnEncryptGenericTransaction(tMessage.getData()))
            .flatMap(transactionEncrypted -> orchestratorCrudService.getDomainEntityById(transactionMessage.getMessageId())
                .flatMap(orchestratorTransactionInformation -> {
                    orchestratorTransactionInformation.setTransactionEncrypted(transactionEncrypted);
                    return Mono.just(orchestratorTransactionInformation);
                }))
            .flatMap(orchestratorCrudService::updateEntity)
            .flatMap(orchestratorTransactionInformation -> doOnBuildSagaStateRequestByTransactionMessage(transactionMessage, sagaState.getCode(HSM_GENERATE_KEY_TRANSACTION_TYPE)))
            .flatMap(sagaTransactionalBridgeService::doOnCreateSagaTransactionState)
            .flatMap(this::doOnHandleUpdateStateResponse)
            .flatMap(response -> Mono.just(transactionMessage.getData()))
            .doOnSuccess(success -> log.info("success doUpdateProcessGenerateKey from orchestrationHsmGenerateKeyRequest, response: {}", success))
            .doOnError(throwable -> log.error("exception error in process doUpdateProcessGenerateKey from orchestrationHsmGenerateKeyRequest, error: {}", throwable.getMessage()))
            .log();
    }

    private Mono<TransactionMessage<HsmGenerateKeyResponse>> loadMongoDatabase(final TransactionMessage<HsmGenerateKeyResponse> transactionMessage) {
        sagaMongoDatabaseService.loadMongoDatabase(transactionMessage.getTenantId()).subscribe();
        return Mono.just(transactionMessage)
            .doOnSuccess(success -> log.debug("success loadMongoDatabase, success: {}", success))
            .doOnError(throwable -> log.error("exception error in process loadMongoDatabase, error: {}", throwable.getMessage()))
            .log();
    }

    /*
     * Start Save Saga Transaction Functions
     * */

    private Mono<CreateSagaTransactionalStateRequest> doOnBuildSagaStateRequestByTransactionMessage(final TransactionMessage<HsmGenerateKeyResponse> transactionMessage, final String sagaState) {
        return Mono.just(SagaTransactionalStateRequestHelper.buildCreateSagaTransactionalStateRequestByGenericTransactionMessage(
                transactionMessage,
                microservicesUuId,
                SagaEventType.EVENT_TYPE_OUTBOUND,
                sagaState))
            .doOnSuccess(success -> log.debug("success buildSagaStateRequestByTransactionMessage from orchestrationHsmGenerateKeyRequest, standardTransaction: {}", success))
            .doOnError(throwable -> log.error("exception error in process buildSagaStateRequestByTransactionMessage, error: {}", throwable.getMessage()))
            .log();
    }

    private Mono<CreateSagaTransactionalStateResponse> doOnHandleUpdateStateResponse(final CreateSagaTransactionalStateResponse createSagaTransactionalStateResponse) {
        if (!createSagaTransactionalStateResponse.isSuccessfullyResponse()) return Mono.error(() -> new SagaProcessException("error in CreateSagaTransactionStateRequest from orchestrationHsmGenerateKeyRequest"));
        return Mono.just(createSagaTransactionalStateResponse);
    }
    /*
     * End Save Saga Transaction Functions
     * */
}