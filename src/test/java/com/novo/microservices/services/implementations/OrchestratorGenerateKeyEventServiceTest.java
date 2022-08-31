package com.novo.microservices.services.implementations;

import com.novo.microservices.components.helpers.ContextInformationService;
import com.novo.microservices.components.helpers.SagaTransactionalJsonHelper;
import com.novo.microservices.dtos.HsmGenerateKeyRequestBody;
import com.novo.microservices.dtos.OrchestratorTransactionClassification;
import com.novo.microservices.dtos.OrchestratorTransactionInformation;
import com.novo.microservices.dtos.OrchestratorTransactionStates;
import com.novo.microservices.dtos.base.BusinessResponse;
import com.novo.microservices.dtos.requests.HsmGenerateKeyRequest;
import com.novo.microservices.dtos.requests.HsmRequest;
import com.novo.microservices.dtos.responses.HsmGenerateKeyResponse;
import com.novo.microservices.events.outbounds.contracts.IOrchestratorGenerateKeyProducer;
import com.novo.microservices.services.contracts.*;
import com.novo.microservices.tbs.utils.components.enums.SagaState;
import com.novo.microservices.tbs.utils.components.validations.implementations.CommonTransactionValidation;
import com.novo.microservices.tbs.utils.dtos.SagaControl;
import com.novo.microservices.tbs.utils.dtos.SagaOutBox;
import com.novo.microservices.tbs.utils.dtos.TransactionMessage;
import com.novo.microservices.tbs.utils.dtos.responses.CreateSagaTransactionalResponse;
import com.novo.microservices.tbs.utils.dtos.responses.CreateSagaTransactionalStateResponse;
import com.novo.microservices.tbs.utils.dtos.responses.SagaTransactionalStateResponse;
import com.novo.microservices.tbs.utils.events.outbounds.implementations.TransactionProducer;
import com.novo.microservices.tbs.utils.services.contracts.ISagaMongoDatabaseService;
import com.novo.microservices.tbs.utils.services.contracts.ISagaTransactionalBridgeService;
import com.novo.microservices.utils.common.context.AppSessionContext;
import com.novo.microservices.utils.common.dtos.ApplicationSession;
import com.novo.utils.messaging.dtos.Event;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockserver.junit.jupiter.MockServerExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;

import static com.novo.microservices.transactions.constants.OrchestratorTransactionConstants.ORCHESTRATOR_HSM_GENERATE_KEY_TRANSACTION_CODE;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

/**
 * OrchestratorGenerateKeyEventServiceTest
 * <p>
 * OrchestratorGenerateKeyEventServiceTest class.
 * <p>
 * <p>
 * ESTE COMPONENTE FUE CONSTRUIDO SIGUIENDO LOS ESTANDARES DE DESARROLLO Y EL PROCEDIMIENTO
 * DE DESARROLLO DE APLICACIONES DE NOVOPAYMENT Y SE ENCUENTRA PROTEGIDO POR LAS LEYES DE
 * PROPIEDAD INTELECTUAL Y DERECHOS DE AUTOR.
 *
 * @author Novopayment Inc.
 * @author squijano@novopayment.com
 * @since 4/25/2022
 */
@SpringBootTest(classes = OrchestratorGenerateKeyEventService.class)
@ExtendWith({SpringExtension.class, MockServerExtension.class})
@ContextConfiguration(classes = {
    OrchestratorGenerateKeyEventService.class,
    TransactionProducer.class,
    BusinessResponse.class,
    ContextInformationService.class,
    CommonTransactionValidation.class
})
@TestPropertySource(properties = {"spring.config.location=classpath:application.yml"})
class OrchestratorGenerateKeyEventServiceTest {
    @Autowired
    private IOrchestratorGenerateKeyEventService service;

    @MockBean
    private IOrchestratorGenerateKeyProducer producer;

    @MockBean
    private IOrchestratorHsmConfigurationService hsmGenerateKeyConfigurationService;

    @MockBean
    private ISagaMongoDatabaseService sagaMongoDatabaseService;

    @MockBean
    private ISagaTransactionalBridgeService sagaTransactionalBridgeService;

    @MockBean
    private IOrchestratorClassificationService orchestratorClassificationService;

    @MockBean
    private IOrchestratorStateService orchestratorStateService;

    @MockBean
    private IOrchestratorCrudService orchestratorCrudService;

    @MockBean
    private IOrchestratorEncryptionService orchestratorEncryptionService;

    @MockBean
    private IOrchestratorParameterService orchestratorParameterService;

    @MockBean
    private SagaTransactionalJsonHelper sagaTransactionalJsonHelper;

    @MockBean
    private AppSessionContext appSessionContext;

    @BeforeEach
    public void setUp() {
        Event<TransactionMessage<HsmRequest>> eventRequest = Event.<TransactionMessage<HsmRequest>>builder()
            .data(TransactionMessage.<HsmRequest>builder()
                .data(HsmRequest.builder().build())
                .build())
            .build();

        Event<TransactionMessage<HsmGenerateKeyResponse>> eventResponse = Event.<TransactionMessage<HsmGenerateKeyResponse>>builder()
            .data(TransactionMessage.<HsmGenerateKeyResponse>builder()
                .data(HsmGenerateKeyResponse.builder().build())
                .build())
            .build();

        HsmGenerateKeyRequest entity = HsmGenerateKeyRequest.builder()
            .header("Example")
            .command("Example")
            .body(HsmGenerateKeyRequestBody.builder().build())
            .build();

        when(producer.doOnProcessGenerateKey(any())).thenReturn(eventRequest);
        when(sagaTransactionalJsonHelper.loadHsmGenerateKeyParameters()).thenReturn(List.of(entity));
        when(sagaMongoDatabaseService.loadMongoDatabase(any())).thenReturn(Mono.empty());
        when(sagaTransactionalBridgeService.doOnCreateSagaTransaction(any())).thenReturn(Mono.just(CreateSagaTransactionalResponse.builder()
            .successfullyResponse(true)
            .sagaControl(SagaControl.builder()
                .eventState("HSM_GENERATE_KEY_PEDING")
                .build())
            .sagaOutBox(SagaOutBox.builder().build())
            .build()));
        when(sagaTransactionalBridgeService.doOnCreateSagaTransactionState(any())).thenReturn(Mono.just(CreateSagaTransactionalStateResponse.builder()
            .successfullyResponse(true)
            .build()));
        when(sagaTransactionalBridgeService.doOnFindSagaTransactionState(any())).thenReturn(Mono.just(SagaTransactionalStateResponse.builder()
            .sagaControl(SagaControl.builder()
                .eventState("HSM_GENERATE_KEY_COMPLETED")
                .build())
            .originalSagaOutBox(SagaOutBox.builder().build())
            .build()));
        when(orchestratorClassificationService.doOnFindTransactionClassification(any())).thenReturn(Mono.just(OrchestratorTransactionClassification.builder().build()));
        when(orchestratorClassificationService.doOnFindTransactionClassificationByCode(any())).thenReturn(Mono.just(OrchestratorTransactionClassification.builder()
            .transactionCode(ORCHESTRATOR_HSM_GENERATE_KEY_TRANSACTION_CODE)
            .serviceId("00110011")
            .messageTypeIndicator("1111")
            .processingCode("000000")
            .transactionStateCode("HSM_GENERATE_KEY")
            .transactionReferenceCode("HsmGenerateKey")
            .build())
        );
        when(orchestratorStateService.doOnLoadOrchestratorTransactionStates(any())).thenReturn(Mono.just(OrchestratorTransactionStates.builder().build()));
        when(orchestratorCrudService.getDomainEntityById(any())).thenReturn(Mono.just(OrchestratorTransactionInformation.builder().build()));
        when(orchestratorCrudService.saveEntity(any())).thenReturn(Mono.just(OrchestratorTransactionInformation.builder().build()));
        when(orchestratorCrudService.updateEntity(any())).thenReturn(Mono.just(OrchestratorTransactionInformation.builder().build()));
        when(orchestratorEncryptionService.doOnEncryptGenericTransaction(any())).thenReturn(Mono.just("123"));
        when(orchestratorEncryptionService.doOnDecryptHsmGenerateKeyResponse(any())).thenReturn(Mono.just(eventResponse.getData().getData()));
        when(orchestratorParameterService.findTenantParameterSetting(any())).thenReturn("100");
        when(appSessionContext.getApplicationSession()).thenReturn(ApplicationSession.builder()
            .tenantId("mx-yastas")
            .requestId("12345")
            .build());
    }

    @Test
    void doUpdateProcessGenerateKey() {
        TransactionMessage<HsmGenerateKeyResponse> transactionMessage = TransactionMessage.<HsmGenerateKeyResponse>builder()
            .data(HsmGenerateKeyResponse.builder().build())
            .build();

        StepVerifier.create(service.doUpdateProcessGenerateKey(transactionMessage, SagaState.COMPLETED))
            .expectNext(HsmGenerateKeyResponse.builder().build())
            .verifyComplete();

    }
}