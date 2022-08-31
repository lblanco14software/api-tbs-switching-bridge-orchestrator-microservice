package com.novo.microservices.services.base;

import com.novo.microservices.mocks.*;
import com.novo.microservices.services.contracts.IOrchestratorClassificationService;
import com.novo.microservices.services.contracts.IOrchestratorCrudService;
import com.novo.microservices.services.contracts.IOrchestratorStateService;
import com.novo.microservices.services.implementations.OrchestratorTransactionErrorService;
import com.novo.microservices.tbs.utils.components.exceptions.SagaProcessException;
import com.novo.microservices.tbs.utils.dtos.responses.CreateSagaTransactionalStateResponse;
import com.novo.microservices.tbs.utils.dtos.responses.SagaTransactionalStateResponse;
import com.novo.microservices.tbs.utils.services.contracts.ISagaMongoDatabaseService;
import com.novo.microservices.tbs.utils.services.contracts.ISagaTransactionalBridgeService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

/**
 * OrchestratorBaseTransactionServiceTest
 * <p>
 * OrchestratorBaseTransactionServiceTest class.
 * <p>
 * <p>
 * ESTE COMPONENTE FUE CONSTRUIDO SIGUIENDO LOS ESTANDARES DE DESARROLLO Y EL PROCEDIMIENTO
 * DE DESARROLLO DE APLICACIONES DE NOVOPAYMENT Y SE ENCUENTRA PROTEGIDO POR LAS LEYES DE
 * PROPIEDAD INTELECTUAL Y DERECHOS DE AUTOR.
 *
 * @author Novopayment Inc.
 * @author duhernandez@novopayment.com
 * @since 4/25/2022
 */
@ExtendWith(MockitoExtension.class)
class OrchestratorBaseTransactionServiceTest {

    @InjectMocks
    private OrchestratorTransactionErrorService baseTransactionService;
    @Mock
    private IOrchestratorClassificationService orchestratorClassificationService;
    @Mock
    private ISagaTransactionalBridgeService sagaTransactionalBridgeService;
    @Mock
    private IOrchestratorStateService orchestratorStateService;
    @Mock
    private ISagaMongoDatabaseService sagaMongoDatabaseService;
    @Mock
    private IOrchestratorCrudService orchestratorCrudService;

    @Test
    void doOnValidateEventTransactionResponse() {
        var event = new EventFixture().getInstance();
        event.getData().setCorrelationId("correlationId");
        StepVerifier.create(baseTransactionService.doOnValidateEventTransactionResponse(event))
            .assertNext(response -> {
                assertEquals(response.getTransactionMessage().getMessageId(), event.getData().getMessageId());
            })
            .verifyComplete();
    }

    @Test
    void doOnValidateEventTransactionResponseWithoutCorrelationId() {
        var event = new EventFixture().getInstance();
        StepVerifier.create(baseTransactionService.doOnValidateEventTransactionResponse(event))
            .expectError(SagaProcessException.class)
            .verify();
    }

    @Test
    void doOnValidateSagaTransaction() {
        var transactionInformation = new OrchestratorTransactionInformationFixture().getInstance();
        var sagaResponse = new OrchestrationSagaResponseFixture().getInstance();
        sagaResponse.getCurrentSagaControl().setEventState("transactionSagaStatePending");
        var sagaStateResponse = SagaTransactionalStateResponse.builder()
            .sagaControl(sagaResponse.getCurrentSagaControl())
            .build();
        var transactionClassification = new OrchestratorTransactionClassificationFixture().getInstance();

        var transactionState = new OrchestratorTransactionStatesFixture().getInstance();

        when(sagaTransactionalBridgeService.doOnFindSagaTransactionState(anyString())).thenReturn(Mono.just(sagaStateResponse));
        when(orchestratorCrudService.getDomainEntityById(anyString())).thenReturn(Mono.just(transactionInformation));

        when(orchestratorClassificationService.doOnFindTransactionClassification(
            transactionInformation.getServiceId(),
            transactionInformation.getMessageTypeIndicator(),
            transactionInformation.getProcessingCode()))
            .thenReturn(Mono.just(transactionClassification));

        when(orchestratorStateService.doOnLoadOrchestratorTransactionStates(transactionClassification))
            .thenReturn(Mono.just(transactionState));

        StepVerifier.create(baseTransactionService.doOnValidateSagaTransaction(sagaResponse))
            .assertNext(r -> {
                assertEquals(r.getTransactionMessage().getCorrelationId(), sagaResponse.getTransactionMessage().getCorrelationId());
                assertEquals(r.getTransactionMessage().getTenantId(), sagaResponse.getTransactionMessage().getTenantId());
            })
            .verifyComplete();
    }

    @Test
    void doOnValidateSagaTransactionWithInvalidState() {
        var transactionInformation = new OrchestratorTransactionInformationFixture().getInstance();
        var sagaResponse = new OrchestrationSagaResponseFixture().getInstance();
        sagaResponse.getCurrentSagaControl().setEventState("invalidState");
        var sagaStateResponse = SagaTransactionalStateResponse.builder()
            .sagaControl(sagaResponse.getCurrentSagaControl())
            .build();
        var sagaCreateRepose = CreateSagaTransactionalStateResponse.builder()
            .sagaControl(sagaResponse.getCurrentSagaControl())
            .build();
        var transactionClassification = new OrchestratorTransactionClassificationFixture().getInstance();

        var transactionState = new OrchestratorTransactionStatesFixture().getInstance();

        when(sagaTransactionalBridgeService.doOnFindSagaTransactionState(anyString())).thenReturn(Mono.just(sagaStateResponse));
        when(orchestratorCrudService.getDomainEntityById(anyString())).thenReturn(Mono.just(transactionInformation));

        when(orchestratorClassificationService.doOnFindTransactionClassification(
            transactionInformation.getServiceId(),
            transactionInformation.getMessageTypeIndicator(),
            transactionInformation.getProcessingCode()))
            .thenReturn(Mono.just(transactionClassification));

        when(orchestratorStateService.doOnLoadOrchestratorTransactionStates(transactionClassification))
            .thenReturn(Mono.just(transactionState));

        when(sagaTransactionalBridgeService.doOnCreateSagaTransactionHistory(any())).thenReturn(Mono.just(sagaCreateRepose));

        StepVerifier.create(baseTransactionService.doOnValidateSagaTransaction(sagaResponse))
            .expectError(SagaProcessException.class)
            .verify();
    }
}