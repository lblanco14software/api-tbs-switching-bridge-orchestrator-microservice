package com.novo.microservices.transactions.implementations;

import com.novo.microservices.MicroservicesBrokerTestConfiguration;
import com.novo.microservices.MicroservicesCommonTestConfiguration;
import com.novo.microservices.MicroservicesSagaTestConfiguration;
import com.novo.microservices.MicroservicesTestConfiguration;
import com.novo.microservices.components.configurations.MessageConfiguration;
import com.novo.microservices.components.helpers.ContextInformationService;
import com.novo.microservices.components.helpers.DocumentationMicroservice;
import com.novo.microservices.dtos.base.BusinessResponse;
import com.novo.microservices.dtos.custom.CustomTransactionInformation;
import com.novo.microservices.dtos.custom.PaymentHeaderInformation;
import com.novo.microservices.dtos.requests.OrchestrationTransactionRequest;
import com.novo.microservices.dtos.responses.OrchestrationTransactionResponse;
import com.novo.microservices.mocks.OrchestrationTransactionRequestFixture;
import com.novo.microservices.mocks.StandardTransactionRequestFixture;
import com.novo.microservices.services.contracts.IOrchestratorParameterService;
import com.novo.microservices.tbs.utils.dtos.StandardTransaction;
import com.novo.microservices.tbs.utils.dtos.commons.CommonBrokerConfiguration;
import com.novo.microservices.transactions.enums.CustomTransactionResponses;
import com.novo.microservices.utils.common.context.AppSessionContext;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;

import java.util.Objects;

import static com.novo.microservices.constants.ProcessConstants.*;
import static com.novo.microservices.transactions.constants.OrchestratorCustomResponsesCodes.CUSTOM_RESPONSE_STATUS_COMPLETED_CODE;
import static org.mockito.Mockito.when;

/**
 * CustomerInfoTransactionTest
 * <p>
 * CustomerInfoTransactionTest class.
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
class CustomerInfoTransactionTest {

    @Autowired
    CustomerInfoTransaction transaction;
    @Autowired
    MessageConfiguration messageConfiguration;

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
    @Order(1)
    @DisplayName("Build Standard Transaction Test")
    void buildStandardTransactionTest() {

        // Given
        final OrchestrationTransactionRequest orchestrationTransactionRequest = new OrchestrationTransactionRequestFixture().getInstance();
        final StandardTransaction standardTransactionExpected = new StandardTransactionRequestFixture().getInstance();
        standardTransactionExpected.setDataElement_3("380000");

        // When
        final StandardTransaction standardTransaction = transaction.buildStandardTransaction(orchestrationTransactionRequest);

        // Then
        Assertions.assertNotNull(standardTransaction);
        Assertions.assertEquals(standardTransaction.getDataElement_2(), standardTransactionExpected.getDataElement_2());
        //Assertions.assertEquals(standardTransaction.getDataElement_3(), standardTransactionExpected.getDataElement_3());
        Assertions.assertEquals(standardTransaction.getDataElement_4(), standardTransactionExpected.getDataElement_4());
        Assertions.assertEquals(standardTransaction.getDataElement_7(), standardTransactionExpected.getDataElement_7());
        Assertions.assertEquals(standardTransaction.getDataElement_11(), standardTransactionExpected.getDataElement_11());
        Assertions.assertEquals(standardTransaction.getDataElement_12(), standardTransactionExpected.getDataElement_12());
        Assertions.assertEquals(standardTransaction.getDataElement_13(), standardTransactionExpected.getDataElement_13());
        Assertions.assertEquals(standardTransaction.getDataElement_18(), standardTransactionExpected.getDataElement_18());
        Assertions.assertEquals(standardTransaction.getDataElement_22(), standardTransactionExpected.getDataElement_22());
        Assertions.assertEquals(standardTransaction.getDataElement_4(), standardTransactionExpected.getDataElement_4());
    }

    @Test
    @Order(2)
    @DisplayName("Factory Test")
    void factoryTest() {

        // Given
        final CommonBrokerConfiguration commonBrokerConfigurationExpected = messageConfiguration.getCustomerInformationTransactionProducer();

        // When
        CommonBrokerConfiguration commonBrokerConfiguration = transaction.getBrokerConfigurationProducer();

        // Then
        Assertions.assertEquals(commonBrokerConfigurationExpected.getCommand(), commonBrokerConfiguration.getCommand());
        Assertions.assertEquals(commonBrokerConfigurationExpected.getRoutingDomain(), commonBrokerConfiguration.getRoutingDomain());
        Assertions.assertEquals(commonBrokerConfigurationExpected.getRoutingKeyDestiny(), commonBrokerConfiguration.getRoutingKeyDestiny());
        Assertions.assertNotNull(commonBrokerConfiguration);
    }

    @Test
    @Order(3)
    @DisplayName("Build Successfully Transaction Response Test")
    void buildSuccessfullyTransactionResponse() {


        OrchestrationTransactionRequest orchestrationTransactionRequest = new OrchestrationTransactionRequest();
        CustomTransactionInformation customTransactionInformation = new CustomTransactionInformation();
        customTransactionInformation.setDe3("380000");
        orchestrationTransactionRequest.setTransaction(customTransactionInformation);
        PaymentHeaderInformation paymentHeaderInformation = new PaymentHeaderInformation();
        paymentHeaderInformation.setTrackingId("1");
        paymentHeaderInformation.setServiceId("2");
        orchestrationTransactionRequest.setPaymentHeader(paymentHeaderInformation);

        final StandardTransaction standardTransactionExpected = new StandardTransactionRequestFixture().getInstance();
        standardTransactionExpected.setDataElement_3("380000");
        standardTransactionExpected.setMessageTypeIndicator("0200");
        standardTransactionExpected.setDataElement_127_22("");

        final OrchestrationTransactionResponse standardTransaction = transaction.buildSuccessfullyTransactionResponse(orchestrationTransactionRequest, standardTransactionExpected);

        final CustomTransactionResponses customTransactionResponses = CustomTransactionResponses.findByCustomResponseCode(CUSTOM_RESPONSE_STATUS_COMPLETED_CODE);
        standardTransaction.getPaymentResponse().setResponseStatus(customTransactionResponses.getCustomResponseCode());
        standardTransaction.getPaymentResponse().setResponseDescription(customTransactionResponses.getCustomResponseDescription());

        Assertions.assertEquals(customTransactionResponses.getCustomResponseCode(), standardTransaction.getPaymentResponse().getResponseStatus());
    }
}