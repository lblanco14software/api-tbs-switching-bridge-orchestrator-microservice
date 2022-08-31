package com.novo.microservices.services.implementations;

import com.novo.microservices.components.helpers.SagaTransactionalStateRequestHelper;
import com.novo.microservices.dtos.responses.OrchestrationSagaResponse;
import com.novo.microservices.services.base.OrchestratorBaseTransactionService;
import com.novo.microservices.services.contracts.*;
import com.novo.microservices.tbs.utils.components.enums.SagaEventType;
import com.novo.microservices.tbs.utils.components.exceptions.SagaProcessException;
import com.novo.microservices.tbs.utils.dtos.StandardTransaction;
import com.novo.microservices.tbs.utils.dtos.TransactionMessage;
import com.novo.microservices.tbs.utils.services.contracts.ISagaMongoDatabaseService;
import com.novo.microservices.tbs.utils.services.contracts.ISagaTransactionalBridgeService;
import com.novo.utils.messaging.dtos.Event;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.UUID;

import static com.novo.microservices.constants.ProcessConstants.MICROSERVICES_UUID_CONFIGURATION;
import static org.springframework.beans.factory.config.BeanDefinition.SCOPE_PROTOTYPE;

/**
 * OrchestratorTransactionEventService
 * <p>
 * OrchestratorTransactionEventService class.
 * <p>
 * <p>
 * ESTE COMPONENTE FUE CONSTRUIDO SIGUIENDO LOS ESTANDARES DE DESARROLLO Y EL PROCEDIMIENTO
 * DE DESARROLLO DE APLICACIONES DE NOVOPAYMENT Y SE ENCUENTRA PROTEGIDO POR LAS LEYES DE
 * PROPIEDAD INTELECTUAL Y DERECHOS DE AUTOR.
 *
 * @author Novopayment Inc.
 * @author lblanco@novopayment.com
 * @since 4/27/2022
 */
@Log4j2
@Service
@Scope(SCOPE_PROTOTYPE)
public class OrchestratorTransactionEventService extends OrchestratorBaseTransactionService implements IOrchestratorTransactionEventService {

    @Value(MICROSERVICES_UUID_CONFIGURATION)
    private String microservicesUuId;
    private final ISagaTransactionalBridgeService sagaTransactionalBridgeService;
    private final IOrchestratorEncryptionService orchestratorEncryptionService;
    private final ISagaMongoDatabaseService sagaMongoDatabaseService;
    private final IOrchestratorCrudService orchestratorCrudService;
    private final String orchestratorTransactionEventServiceId;

    public OrchestratorTransactionEventService(final IOrchestratorClassificationService orchestratorClassificationService,
                                               final ISagaTransactionalBridgeService sagaTransactionalBridgeService,
                                               final IOrchestratorEncryptionService orchestratorEncryptionService,
                                               final IOrchestratorStateService orchestratorStateService,
                                               final ISagaMongoDatabaseService sagaMongoDatabaseService,
                                               final IOrchestratorCrudService orchestratorCrudService) {

        super(orchestratorClassificationService, sagaTransactionalBridgeService, orchestratorStateService, sagaMongoDatabaseService, orchestratorCrudService);
        this.sagaTransactionalBridgeService = sagaTransactionalBridgeService;
        this.orchestratorEncryptionService = orchestratorEncryptionService;
        this.sagaMongoDatabaseService = sagaMongoDatabaseService;
        this.orchestratorCrudService = orchestratorCrudService;
        this.orchestratorTransactionEventServiceId = UUID.randomUUID().toString();
        log.debug("orchestratorTransactionEventServiceId {}", orchestratorTransactionEventServiceId);
        log.debug("OrchestratorTransactionEventService loaded successfully");
    }

    @Override
    public Mono<OrchestrationSagaResponse> doOnProcessTransactionResponseByEvent(Event<TransactionMessage<StandardTransaction>> eventTransactionMessage) {

        return Mono.just(eventTransactionMessage)
            .flatMap(this::doOnValidateEventTransactionResponse)
            .flatMap(this::doOnValidateSagaTransaction)
            .flatMap(this::doOnUpdateSagaTransaction)
            .doOnSuccess(success -> {
                log.debug("orchestratorTransactionEventServiceId: {}", orchestratorTransactionEventServiceId);
                log.debug("success process doOnProcessTransactionResponseByEvent, response: {}", success.toString());
            })
            .doOnError(throwable ->
                log.error("exception error in process doOnProcessTransactionResponseByEvent, error: {}", throwable.getMessage())
            )
            .log();
    }

    private Mono<OrchestrationSagaResponse> doOnUpdateSagaTransaction(final OrchestrationSagaResponse orchestrationSagaResponse) {

        return Mono.just(orchestrationSagaResponse)
            .flatMap(currentOrchestrationSagaResponse -> {
                sagaMongoDatabaseService.loadMongoDatabase(currentOrchestrationSagaResponse.getTransactionMessage().getTenantId());
                return Mono.just(currentOrchestrationSagaResponse);
            })
            .flatMap(currentOrchestrationSagaResponse -> orchestratorEncryptionService.doOnEncryptTransaction(currentOrchestrationSagaResponse.getTransactionMessage().getData())
                .flatMap(standardTransactionEncrypted -> {
                    currentOrchestrationSagaResponse.getOrchestratorTransactionInformation().setTransactionEncrypted(standardTransactionEncrypted);
                    return orchestratorCrudService.updateEntity(currentOrchestrationSagaResponse.getOrchestratorTransactionInformation())
                        .flatMap(orchestratorTransactionInformation -> Mono.just(currentOrchestrationSagaResponse));
                })
            )
            .flatMap(currentOrchestrationSagaResponse -> sagaTransactionalBridgeService.doOnCreateSagaTransactionState(SagaTransactionalStateRequestHelper.buildCreateSagaTransactionalStateRequest(
                        currentOrchestrationSagaResponse.getTransactionMessage(), microservicesUuId, SagaEventType.EVENT_TYPE_INBOUND, currentOrchestrationSagaResponse.getOrchestratorTransactionStates().getTransactionSagaStateCompleted())
                    )
                    .flatMap(createSagaTransactionalStateResponse -> {
                        if (createSagaTransactionalStateResponse.isSuccessfullyResponse()) {
                            return Mono.just(currentOrchestrationSagaResponse);
                        }
                        return Mono.error(() -> new SagaProcessException("error in validation doOnUpdateSagaTransaction"));
                    })
            )
            .doOnSuccess(success ->
                log.debug("success doOnUpdateSagaTransaction from orchestrationSagaResponse: {}", success)
            )
            .doOnError(throwable ->
                log.error("exception error in process doOnUpdateSagaTransaction, error: {}", throwable.getMessage())
            )
            .log();
    }
}