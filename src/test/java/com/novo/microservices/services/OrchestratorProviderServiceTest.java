package com.novo.microservices.services;

import com.novo.microservices.MicroservicesBrokerTestConfiguration;
import com.novo.microservices.MicroservicesCommonTestConfiguration;
import com.novo.microservices.MicroservicesSagaTestConfiguration;
import com.novo.microservices.MicroservicesTestConfiguration;
import com.novo.microservices.components.helpers.ContextInformationService;
import com.novo.microservices.components.helpers.DocumentationMicroservice;
import com.novo.microservices.dtos.OrchestratorTransactionClassification;
import com.novo.microservices.dtos.base.BusinessResponse;
import com.novo.microservices.dtos.requests.OrchestrationSagaRequest;
import com.novo.microservices.dtos.requests.OrchestrationTransactionRequest;
import com.novo.microservices.mocks.OrchestrationTransactionRequestFixture;
import com.novo.microservices.services.contracts.IOrchestratorParameterService;
import com.novo.microservices.services.implementations.OrchestratorClassificationService;
import com.novo.microservices.services.implementations.OrchestratorProviderService;
import com.novo.microservices.tbs.utils.dtos.StandardTransaction;
import com.novo.microservices.transactions.contracts.IOrchestratorTransaction;
import com.novo.microservices.transactions.mappers.contracts.IStandardTransactionCommonDefaultValuesMapper;
import com.novo.microservices.utils.common.context.AppSessionContext;
import com.novo.microservices.utils.common.dtos.ApplicationSession;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;

import java.util.Objects;
import java.util.UUID;

import static com.novo.microservices.constants.ProcessConstants.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;

/**
 * OrchestratorProviderServiceTest
 * <p>
 * OrchestratorProviderServiceTest class.
 * <p>
 * <p>
 * ESTE COMPONENTE FUE CONSTRUIDO SIGUIENDO LOS ESTANDARES DE DESARROLLO Y EL PROCEDIMIENTO
 * DE DESARROLLO DE APLICACIONES DE NOVOPAYMENT Y SE ENCUENTRA PROTEGIDO POR LAS LEYES DE
 * PROPIEDAD INTELECTUAL Y DERECHOS DE AUTOR.
 *
 * @author Novopayment Inc.
 * @author lblanco@novopayment.com
 * @since 4/19/2022
 */
@SpringBootTest
@AutoConfigureMockMvc
@Import({
    MicroservicesBrokerTestConfiguration.class,
    MicroservicesCommonTestConfiguration.class,
    MicroservicesSagaTestConfiguration.class,
    MicroservicesTestConfiguration.class,
    DocumentationMicroservice.class,
    BusinessResponse.class,
    ContextInformationService.class,
    AppSessionContext.class
})
class OrchestratorProviderServiceTest {

    @Autowired
    OrchestratorProviderService orchestratorProviderService;
    @Autowired
    OrchestratorClassificationService orchestratorClassificationService;
    @Autowired
    AppSessionContext appSessionContext;
    @MockBean
    IStandardTransactionCommonDefaultValuesMapper standardTransactionCommonDefaultValuesMapper;
    @MockBean
    IOrchestratorParameterService orchestratorParameterService;

    @BeforeEach
    void setup() {
        when(orchestratorParameterService.findTenantParameterSetting(MICROSERVICES_ORCHESTRATOR_HSM_CONFIGURATIONS_PATH)).thenReturn(Objects.requireNonNull(getClass().getResource("/json/microservices-orchestrator-hsm-configurations.json")).getPath());
        when(orchestratorParameterService.findTenantParameterSetting(MICROSERVICES_ORCHESTRATOR_TRANSACTIONS_CODES_PATH)).thenReturn(Objects.requireNonNull(getClass().getResource("/json/microservices-orchestrator-transactions-codes.json")).getPath());
        when(orchestratorParameterService.findTenantParameterSetting(MICROSERVICES_ORCHESTRATOR_TRANSACTIONS_MAPPINGS_PATH)).thenReturn(Objects.requireNonNull(getClass().getResource("/json/microservices-orchestrator-transactions-mappings.json")).getPath());
        when(orchestratorParameterService.findTenantParameterSetting(MICROSERVICES_ORCHESTRATOR_TRANSACTIONS_STRUCTURE_DATA_MAPPINGS_PATH)).thenReturn(Objects.requireNonNull(getClass().getResource("/json/microservices-orchestrator-transactions-structure-data-mappings.json")).getPath());
    }

    @Test
    @DisplayName("Find Transaction Classification # 1 Test")
    void doOnFindTransactionClassificationTest() {

        // Given
        final OrchestrationTransactionRequest orchestrationTransactionRequest = new OrchestrationTransactionRequestFixture().getInstance();

        // When
        final OrchestratorTransactionClassification instance = orchestratorClassificationService.doOnFindTransactionClassification(orchestrationTransactionRequest).block();

        // Then
        Assertions.assertNotNull(instance);
    }

    @Test
    @DisplayName("Find Transaction Classification # 2 Test")
    void doOnFindTransactionClassificationSecondTest() {

        // Given
        final OrchestrationTransactionRequest orchestrationTransactionRequest = new OrchestrationTransactionRequestFixture().getInstance();
        final String serviceId = orchestrationTransactionRequest.getPaymentHeader().getServiceId();
        final String messageTypeIndicator = orchestrationTransactionRequest.getTransaction().getMessageTypeIndicator();
        final String processingCode = orchestrationTransactionRequest.getTransaction().getDe3();

        // When
        final OrchestratorTransactionClassification instance = orchestratorClassificationService.doOnFindTransactionClassification(serviceId, messageTypeIndicator, processingCode).block();

        // Then
        Assertions.assertNotNull(instance);
    }

    @Test
    @DisplayName("Factory Orchestrator Transaction Test")
    void doOnFactoryOrchestratorTransactionTest() {

        // Given
        final OrchestrationTransactionRequest orchestrationTransactionRequest = new OrchestrationTransactionRequestFixture().getInstance();
        final String serviceId = orchestrationTransactionRequest.getPaymentHeader().getServiceId();
        final String messageTypeIndicator = orchestrationTransactionRequest.getTransaction().getMessageTypeIndicator();
        final String processingCode = orchestrationTransactionRequest.getTransaction().getDe3();
        final OrchestratorTransactionClassification orchestratorTransactionClassification = orchestratorClassificationService.doOnFindTransactionClassification(serviceId, messageTypeIndicator, processingCode).block();
        appSessionContext.setApplicationSession(
            ApplicationSession.builder()
                .tenantId("mx-yastas")
                .requestId(UUID.randomUUID().toString())
                .build()
        );

        // When
        doAnswer(invocation -> {
            StandardTransaction standardTransaction = invocation.getArgument(4);
            standardTransaction.setDataElement_25("dataElement_25");
            standardTransaction.setDataElement_123("dataElement_123");

            return null;
        }).when(standardTransactionCommonDefaultValuesMapper).applyDefaultValuesToRequestTransaction(anyString(), anyString(), anyString(), anyString(), any(StandardTransaction.class));
        assert orchestratorTransactionClassification != null;
        var orchestrationSagaRequest = OrchestrationSagaRequest.builder()
            .orchestratorTransactionClassification(orchestratorTransactionClassification)
            .orchestrationTransactionRequest(orchestrationTransactionRequest)
            .build();
        final IOrchestratorTransaction instance = orchestratorProviderService.doOnFactoryOrchestratorTransaction(orchestrationSagaRequest).block();

        // Then
        Assertions.assertNotNull(instance);
    }

    @Test
    @DisplayName("Factory Standard Transaction Test")
    void doOnFactoryStandardTransactionTest() {

        // Given
        final OrchestrationTransactionRequest orchestrationTransactionRequest = new OrchestrationTransactionRequestFixture().getInstance();
        final String serviceId = orchestrationTransactionRequest.getPaymentHeader().getServiceId();
        final String messageTypeIndicator = orchestrationTransactionRequest.getTransaction().getMessageTypeIndicator();
        final String processingCode = orchestrationTransactionRequest.getTransaction().getDe3();
        final OrchestratorTransactionClassification orchestratorTransactionClassification = orchestratorClassificationService.doOnFindTransactionClassification(serviceId, messageTypeIndicator, processingCode).block();
        appSessionContext.setApplicationSession(
            ApplicationSession.builder()
                .tenantId("mx-yastas")
                .requestId(UUID.randomUUID().toString())
                .build()
        );

        // When
        assert orchestratorTransactionClassification != null;
        final StandardTransaction instance = orchestratorProviderService.doOnFactoryStandardTransaction(orchestratorTransactionClassification, orchestrationTransactionRequest).block();

        // Then
        Assertions.assertNotNull(instance);
    }
}