package com.novo.microservices.transactions.bases;

import com.novo.microservices.MicroservicesBrokerTestConfiguration;
import com.novo.microservices.MicroservicesCommonTestConfiguration;
import com.novo.microservices.MicroservicesSagaTestConfiguration;
import com.novo.microservices.MicroservicesTestConfiguration;
import com.novo.microservices.components.configurations.MessageConfiguration;
import com.novo.microservices.components.enums.ValidateResult;
import com.novo.microservices.components.helpers.ContextInformationService;
import com.novo.microservices.components.helpers.DocumentationMicroservice;
import com.novo.microservices.dtos.OrchestratorValidationResult;
import com.novo.microservices.dtos.base.BusinessResponse;
import com.novo.microservices.dtos.requests.OrchestrationTransactionRequest;
import com.novo.microservices.dtos.responses.OrchestrationTransactionResponse;
import com.novo.microservices.mocks.*;
import com.novo.microservices.services.contracts.IOrchestratorParameterService;
import com.novo.microservices.tbs.utils.dtos.StandardTransaction;
import com.novo.microservices.transactions.implementations.CashDepositTransaction;
import com.novo.microservices.transactions.mappers.implementations.StandardTransactionCommonMapper;
import com.novo.microservices.utils.common.context.AppSessionContext;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.annotation.Import;
import postilion.realtime.sdk.message.bitmap.StructuredData;

import java.util.Objects;

import static com.novo.microservices.constants.ProcessConstants.*;
import static org.mockito.Mockito.when;

/**
 * OrchestratorBaseTransactionTest
 * <p>
 * OrchestratorBaseTransactionTest class.
 * <p>
 * <p>
 * ESTE COMPONENTE FUE CONSTRUIDO SIGUIENDO LOS ESTANDARES DE DESARROLLO Y EL PROCEDIMIENTO
 * DE DESARROLLO DE APLICACIONES DE NOVOPAYMENT Y SE ENCUENTRA PROTEGIDO POR LAS LEYES DE
 * PROPIEDAD INTELECTUAL Y DERECHOS DE AUTOR.
 *
 * @author Novopayment Inc.
 * @author lblanco@novopayment.com
 * @since 4/10/2022
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
    AppSessionContext.class,
    StandardTransactionCommonMapper.class,
    MessageConfiguration.class,
    OrchestratorBaseTransaction.class,
})
//@TestPropertySource(properties = {"spring.config.location=classpath:application-test.yml"})
class OrchestratorBaseTransactionTest {

    @Autowired
    private MessageConfiguration messageConfiguration;
    @SpyBean
    private CashDepositTransaction orchestratorBaseTransaction;
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
    @Order(2)
    @DisplayName("CurrentStandard Transaction Test")
    void setCurrentStandardTransactionTest() {

        // Given
        final StandardTransaction standardTransactionExpected = new StandardTransactionRequestFixture().getInstance();
        when(orchestratorBaseTransaction.getCurrentStandardTransactionRequest()).thenReturn(standardTransactionExpected);

        // When
        final StandardTransaction standardTransaction = orchestratorBaseTransaction.getCurrentStandardTransactionRequest();

        // Then
        Assertions.assertNotNull(standardTransaction);
        Assertions.assertEquals(standardTransactionExpected.getDataElement_2(), standardTransaction.getDataElement_2());
        Assertions.assertEquals(standardTransactionExpected.getDataElement_3(), standardTransaction.getDataElement_3());
        Assertions.assertEquals(standardTransactionExpected.getDataElement_4(), standardTransaction.getDataElement_4());
        Assertions.assertEquals(standardTransactionExpected.getDataElement_7(), standardTransaction.getDataElement_7());
        Assertions.assertEquals(standardTransactionExpected.getDataElement_11(), standardTransaction.getDataElement_11());
        Assertions.assertEquals(standardTransactionExpected.getDataElement_12(), standardTransaction.getDataElement_12());
        Assertions.assertEquals(standardTransactionExpected.getDataElement_13(), standardTransaction.getDataElement_13());
        Assertions.assertEquals(standardTransactionExpected.getDataElement_18(), standardTransaction.getDataElement_18());
        Assertions.assertEquals(standardTransactionExpected.getDataElement_22(), standardTransaction.getDataElement_22());
        Assertions.assertEquals(standardTransactionExpected.getDataElement_4(), standardTransaction.getDataElement_4());
    }

    @Test
    @Order(3)
    @DisplayName("Message Configuration Test")
    void getMessageConfigurationTest() {

        // When
        final var producerConfig = orchestratorBaseTransaction.getBrokerConfigurationProducer();

        // Then
        Assertions.assertNotNull(messageConfiguration);
        Assertions.assertEquals(producerConfig.getCommand(), messageConfiguration.getCashDepositTransactionProducer().getCommand());
        Assertions.assertEquals(producerConfig.getCommand(), messageConfiguration.getCustomerInformationTransactionProducer().getCommand());
        Assertions.assertEquals(producerConfig.getCommand(), messageConfiguration.getReverseCashDepositTransactionProducer().getCommand());
        Assertions.assertEquals(producerConfig.getCommand(), messageConfiguration.getCommissionQueryTransactionProducer().getCommand());
    }

    @Test
    @Order(4)
    @DisplayName("Build Successfully Transaction Response Error Case Test")
    void buildSuccessfullyTransactionResponseErrorTest() {

        // Given
        final OrchestrationTransactionRequest orchestrationTransactionRequest = new OrchestrationTransactionRequestFixture().getInstance();
        final StandardTransaction standardTransactionResponse = new StandardTransactionResponseFixture().getInstance();
        final OrchestrationTransactionResponse orchestrationTransactionResponseExpected = new OrchestrationTransactionSuccessfullyResponse().getInstance();

        // When
        final OrchestrationTransactionResponse orchestrationTransactionResponse = orchestratorBaseTransaction.buildSuccessfullyTransactionResponse(orchestrationTransactionRequest, standardTransactionResponse);

        // Then
        Assertions.assertNotNull(orchestrationTransactionResponse);
        Assertions.assertEquals(orchestrationTransactionResponseExpected.getPaymentResponse().getResponseStatus(), orchestrationTransactionResponse.getPaymentResponse().getResponseStatus());
        Assertions.assertEquals(orchestrationTransactionResponseExpected.getPaymentResponse().getResponseDescription(), orchestrationTransactionResponse.getPaymentResponse().getResponseDescription());
        Assertions.assertEquals(orchestrationTransactionResponseExpected.getPaymentResponse().getTrackingId(), orchestrationTransactionResponse.getPaymentResponse().getTrackingId());
        Assertions.assertEquals(orchestrationTransactionResponseExpected.getPaymentResponse().getServiceId(), orchestrationTransactionResponse.getPaymentResponse().getServiceId());
    }

    @Test
    @Order(5)
    @DisplayName("Build Successfully Transaction Response Test")
    void buildSuccessfullyTransactionResponseTest() {

        // Given
        final OrchestrationTransactionRequest orchestrationTransactionRequest = new OrchestrationTransactionRequestFixture().getInstance();
        final StandardTransaction standardTransactionResponse = new StandardTransactionResponseFixture().getInstance();
        final OrchestrationTransactionResponse orchestrationTransactionResponseExpected = new OrchestrationTransactionSuccessfullyResponse().getInstance();
        standardTransactionResponse.setDataElement_39("00");

        // When
        final OrchestrationTransactionResponse orchestrationTransactionResponse = orchestratorBaseTransaction.buildSuccessfullyTransactionResponse(orchestrationTransactionRequest, standardTransactionResponse);

        // Then
        Assertions.assertNotNull(orchestrationTransactionResponse);
        Assertions.assertEquals(orchestrationTransactionResponseExpected.getPaymentResponse().getResponseStatus(), orchestrationTransactionResponse.getPaymentResponse().getResponseStatus());
        Assertions.assertEquals(orchestrationTransactionResponseExpected.getPaymentResponse().getResponseDescription(), orchestrationTransactionResponse.getPaymentResponse().getResponseDescription());
        Assertions.assertEquals(orchestrationTransactionResponseExpected.getPaymentResponse().getTrackingId(), orchestrationTransactionResponse.getPaymentResponse().getTrackingId());
        Assertions.assertEquals(orchestrationTransactionResponseExpected.getPaymentResponse().getServiceId(), orchestrationTransactionResponse.getPaymentResponse().getServiceId());
    }

    @Test
    @Order(6)
    @DisplayName("Build Error Transaction Response Test")
    void buildErrorTransactionResponseTest() {

        // Given
        final OrchestrationTransactionRequest orchestrationTransactionRequest = new OrchestrationTransactionRequestFixture().getInstance();
        final OrchestrationTransactionResponse orchestrationTransactionResponseExpected = new OrchestrationTransactionErrorResponse().getInstance();

        // When
        final OrchestrationTransactionResponse orchestrationTransactionResponse = orchestratorBaseTransaction.buildErrorTransactionResponse(orchestrationTransactionRequest);

        // Then
        Assertions.assertNotNull(orchestrationTransactionResponse);
        Assertions.assertEquals(orchestrationTransactionResponseExpected.getPaymentResponse().getResponseStatus(), orchestrationTransactionResponse.getPaymentResponse().getResponseStatus());
        Assertions.assertEquals(orchestrationTransactionResponseExpected.getPaymentResponse().getResponseDescription(), orchestrationTransactionResponse.getPaymentResponse().getResponseDescription());
        Assertions.assertEquals(orchestrationTransactionResponseExpected.getPaymentResponse().getTrackingId(), orchestrationTransactionResponse.getPaymentResponse().getTrackingId());
        Assertions.assertEquals(orchestrationTransactionResponseExpected.getPaymentResponse().getServiceId(), orchestrationTransactionResponse.getPaymentResponse().getServiceId());
    }

    @Test
    @Order(7)
    @DisplayName("Build Common Structured Data")
    void buildCommonStructuredDataTest() {

        // Given
        final OrchestrationTransactionRequest orchestrationTransactionRequest = new OrchestrationTransactionRequestFixture().getInstance();


        // When
        final StructuredData structuredData = orchestratorBaseTransaction.buildCommonStructuredData(orchestrationTransactionRequest);

        // Then
        Assertions.assertNotNull(structuredData);
    }

    @Test
    @Order(8)
    @DisplayName("Validate Standard Transaction Request Test Case NOT_VALID")
    void validateStandardTransactionRequestTest() {

        // Given
        final OrchestrationTransactionRequest orchestrationTransactionRequest = new OrchestrationTransactionRequestFixture().getInstance();
        final StandardTransaction standardTransaction = orchestratorBaseTransaction.buildStandardTransaction(orchestrationTransactionRequest);
        standardTransaction.setMessageTypeIndicator(null);
        final ValidateResult validateResultExpected = ValidateResult.NOT_VALID;

        // When
        final OrchestratorValidationResult orchestratorValidationResult = orchestratorBaseTransaction.validateStandardTransactionRequest(standardTransaction);

        // Then
        Assertions.assertNotNull(orchestratorValidationResult);
        Assertions.assertEquals(validateResultExpected, orchestratorValidationResult.getValidateResult());
    }
}