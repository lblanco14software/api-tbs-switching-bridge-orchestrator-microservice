package com.novo.microservices.services;

import com.novo.microservices.MicroservicesBrokerTestConfiguration;
import com.novo.microservices.MicroservicesCommonTestConfiguration;
import com.novo.microservices.MicroservicesSagaTestConfiguration;
import com.novo.microservices.MicroservicesTestConfiguration;
import com.novo.microservices.components.helpers.ContextInformationService;
import com.novo.microservices.components.helpers.DocumentationMicroservice;
import com.novo.microservices.dtos.OrchestratorTransactionClassification;
import com.novo.microservices.dtos.OrchestratorTransactionStates;
import com.novo.microservices.dtos.base.BusinessResponse;
import com.novo.microservices.dtos.requests.OrchestrationTransactionRequest;
import com.novo.microservices.mocks.OrchestrationTransactionRequestFixture;
import com.novo.microservices.services.contracts.IOrchestratorParameterService;
import com.novo.microservices.services.implementations.OrchestratorClassificationService;
import com.novo.microservices.services.implementations.OrchestratorStateService;
import com.novo.microservices.utils.common.context.AppSessionContext;
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

import static com.novo.microservices.constants.ProcessConstants.*;
import static org.mockito.Mockito.when;

/**
 * OrchestratorStateServiceTest
 * <p>
 * OrchestratorStateServiceTest class.
 * <p>
 * <p>
 * ESTE COMPONENTE FUE CONSTRUIDO SIGUIENDO LOS ESTANDARES DE DESARROLLO Y EL PROCEDIMIENTO
 * DE DESARROLLO DE APLICACIONES DE NOVOPAYMENT Y SE ENCUENTRA PROTEGIDO POR LAS LEYES DE
 * PROPIEDAD INTELECTUAL Y DERECHOS DE AUTOR.
 *
 * @author Novopayment Inc.
 * @author lblanco@novopayment.com
 * @since 4/26/2022
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
class OrchestratorStateServiceTest {

    @Autowired
    OrchestratorClassificationService orchestratorClassificationService;
    @Autowired
    OrchestratorStateService orchestratorStateService;

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
    @DisplayName("Load Orchestrator TransactionStates Test")
    void doOnLoadOrchestratorTransactionStatesTest() {

        // Given
        final OrchestrationTransactionRequest orchestrationTransactionRequest = new OrchestrationTransactionRequestFixture().getInstance();
        final String serviceId = orchestrationTransactionRequest.getPaymentHeader().getServiceId();
        final String messageTypeIndicator = orchestrationTransactionRequest.getTransaction().getMessageTypeIndicator();
        final String processingCode = orchestrationTransactionRequest.getTransaction().getDe3();
        final OrchestratorTransactionClassification orchestratorTransactionClassification = orchestratorClassificationService.doOnFindTransactionClassification(serviceId, messageTypeIndicator, processingCode).block();

        // When
        assert orchestratorTransactionClassification != null;
        final OrchestratorTransactionStates instance = orchestratorStateService.doOnLoadOrchestratorTransactionStates(orchestratorTransactionClassification).block();

        // Then
        Assertions.assertNotNull(instance);
    }
}