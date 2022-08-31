package com.novo.microservices.services.implementations;

import com.novo.microservices.components.helpers.SagaTransactionalJsonHelper;
import com.novo.microservices.dtos.OrchestratorTransactionClassification;
import com.novo.microservices.dtos.OrchestratorTransactionInformation;
import com.novo.microservices.dtos.OrchestratorTransactionStates;
import com.novo.microservices.dtos.responses.OrchestrationSagaResponse;
import com.novo.microservices.mocks.EventFixture;
import com.novo.microservices.repositories.contracts.IOrchestratorTransactionRepository;
import com.novo.microservices.repositories.entities.OrchestratorTransactionEntity;
import com.novo.microservices.services.contracts.IOrchestratorCrudService;
import com.novo.microservices.services.contracts.IOrchestratorEncryptionService;
import com.novo.microservices.services.contracts.IOrchestratorParameterService;
import com.novo.microservices.tbs.utils.dtos.SagaControl;
import com.novo.microservices.tbs.utils.dtos.responses.CreateSagaTransactionalStateResponse;
import com.novo.microservices.tbs.utils.dtos.responses.SagaTransactionalStateResponse;
import com.novo.microservices.tbs.utils.services.contracts.ISagaMongoDatabaseService;
import com.novo.microservices.tbs.utils.services.contracts.ISagaTransactionalBridgeService;
import com.novo.microservices.tbs.utils.services.implementations.SagaOutBoxService;
import com.novo.microservices.tbs.utils.services.implementations.SagaTransactionalBridgeService;
import com.novo.utils.security.abstractions.AlgorithmFactory;
import com.novo.utils.security.services.implementations.EncryptionServices;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockserver.junit.jupiter.MockServerExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.mongodb.repository.support.SimpleMongoRepository;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Objects;
import java.util.Optional;

import static com.novo.microservices.constants.ProcessConstants.*;
import static org.mockito.Mockito.when;

/**
 * OrchestratorTransactionErrorServiceTest
 * <p>
 * OrchestratorTransactionErrorServiceTest class.
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
@Log4j2
@SpringBootTest(classes = OrchestratorTransactionErrorService.class)
@ExtendWith({SpringExtension.class, MockServerExtension.class})
@ContextConfiguration(classes = {
    OrchestratorTransactionErrorService.class,
    OrchestratorClassificationService.class,
    OrchestratorCacheService.class,
    OrchestrationSagaResponse.class,
    SagaTransactionalJsonHelper.class,
    SagaTransactionalBridgeService.class,
    SagaOutBoxService.class,
    SimpleMongoRepository.class,
    OrchestratorEncryptionService.class,
    EncryptionServices.class,
    AlgorithmFactory.class,
    OrchestratorStateService.class,
    OrchestratorCrudService.class
})
@TestPropertySource(properties = {"spring.config.location=classpath:application.yml"})
class OrchestratorTransactionErrorServiceTest {

    @Autowired
    private OrchestratorTransactionErrorService service;

    @MockBean
    private ISagaMongoDatabaseService sagaMongoDatabaseService;

    @MockBean
    private ISagaTransactionalBridgeService sagaTransactionalBridgeService;

    @MockBean
    private IOrchestratorTransactionRepository orchestratorTransactionRepository;

    @MockBean
    private IOrchestratorCrudService orchestratorCrudService;

    @MockBean
    private IOrchestratorEncryptionService orchestratorEncryptionService;

    @MockBean
    IOrchestratorParameterService orchestratorParameterService;

    @BeforeEach
    void setUp(){
        when(orchestratorParameterService.findTenantParameterSetting(MICROSERVICES_ORCHESTRATOR_HSM_CONFIGURATIONS_PATH)).thenReturn(Objects.requireNonNull(getClass().getResource("/json/microservices-orchestrator-hsm-configurations.json")).getPath());
        when(orchestratorParameterService.findTenantParameterSetting(MICROSERVICES_ORCHESTRATOR_TRANSACTIONS_CODES_PATH)).thenReturn(Objects.requireNonNull(getClass().getResource("/json/microservices-orchestrator-transactions-codes.json")).getPath());
        when(orchestratorParameterService.findTenantParameterSetting(MICROSERVICES_ORCHESTRATOR_TRANSACTIONS_MAPPINGS_PATH)).thenReturn(Objects.requireNonNull(getClass().getResource("/json/microservices-orchestrator-transactions-mappings.json")).getPath());
        when(orchestratorParameterService.findTenantParameterSetting(MICROSERVICES_ORCHESTRATOR_TRANSACTIONS_STRUCTURE_DATA_MAPPINGS_PATH)).thenReturn(Objects.requireNonNull(getClass().getResource("/json/microservices-orchestrator-transactions-structure-data-mappings.json")).getPath());

        String tenantId = "mx-yastas";

        Mockito.when(sagaMongoDatabaseService.loadMongoDatabase(tenantId))
            .thenReturn(Mono.empty());
        Mockito.when(sagaTransactionalBridgeService.doOnCreateSagaTransactionState(Mockito.any()))
            .thenReturn(Mono.just(CreateSagaTransactionalStateResponse.builder()
                .successfullyResponse(true)
                .build()));
        Mockito.when(sagaTransactionalBridgeService.doOnFindSagaTransactionState(Mockito.any()))
            .thenReturn(Mono.just(SagaTransactionalStateResponse.builder()
                .sagaControl(SagaControl.builder()
                    .eventState("CUSTOMER_INFO_TRANSACTION_PENDING")
                    .build())
                .build()));
        OrchestratorTransactionEntity orchestratorTransaction = new OrchestratorTransactionEntity();

        Mockito.when(orchestratorTransactionRepository.save(Mockito.any()))
            .thenReturn(orchestratorTransaction);
        Mockito.when(orchestratorTransactionRepository.findById(Mockito.any()))
            .thenReturn(Optional.of(orchestratorTransaction));
        Mockito.when(orchestratorCrudService.getDomainEntityById(Mockito.any()))
            .thenReturn(Mono.just(OrchestratorTransactionInformation.builder()
                .messageId("Message-Id")
                .serviceId("00660035")
                .processingCode("380000")
                .messageTypeIndicator("0200")
                .build()));
        Mockito.when(orchestratorEncryptionService.doOnEncryptTransaction(Mockito.any()))
            .thenReturn(Mono.just("EncryptedResponse"));
        Mockito.when(orchestratorCrudService.updateEntity(Mockito.any()))
            .thenReturn(Mono.just(OrchestratorTransactionInformation.builder().build()));

    }

    @Test
    void doOnProcessErrorInTransactionPublish() {
        EventFixture event = EventFixture.builder().build();

        event.getInstance().getData().setCorrelationId("1");

        StepVerifier.create(service.doOnProcessErrorInTransactionPublish(event.getInstance()))
            .expectNext(OrchestrationSagaResponse.builder()
                .orchestratorTransactionClassification(OrchestratorTransactionClassification.builder()
                    .transactionCode("orchestrator.customer.info.transaction")
                    .serviceId("00660035")
                    .messageTypeIndicator("0200")
                    .processingCode("380000")
                    .transactionStateCode("CUSTOMER_INFO_TRANSACTION")
                    .transactionReferenceCode("ConsultaDatosCliente")
                    .build())
                .orchestratorTransactionInformation(OrchestratorTransactionInformation.builder()
                    .messageId("Message-Id")
                    .serviceId("00660035")
                    .messageTypeIndicator("0200")
                    .processingCode("380000")
                    .build())
                .orchestratorTransactionStates(OrchestratorTransactionStates.builder()
                    .transactionSagaStateReceived("CUSTOMER_INFO_TRANSACTION_RECEIVED")
                    .transactionSagaStatePending("CUSTOMER_INFO_TRANSACTION_PENDING")
                    .transactionSagaStateCompleted("CUSTOMER_INFO_TRANSACTION_COMPLETED")
                    .transactionSagaStateInformationError("CUSTOMER_INFO_TRANSACTION_INFORMATION_ERROR")
                    .transactionSagaStateInternalError("CUSTOMER_INFO_TRANSACTION_INTERNAL_ERROR")
                    .build())
                .transactionMessage(event.getInstance().getData())
                .currentSagaControl(SagaControl.builder()
                    .eventState("CUSTOMER_INFO_TRANSACTION_PENDING")
                    .build())
                .build())
            .verifyComplete();
    }
}