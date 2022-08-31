package com.novo.microservices.transactions.factories;

import com.novo.microservices.MicroservicesBrokerTestConfiguration;
import com.novo.microservices.MicroservicesCommonTestConfiguration;
import com.novo.microservices.MicroservicesSagaTestConfiguration;
import com.novo.microservices.MicroservicesTestConfiguration;
import com.novo.microservices.components.configurations.MessageConfiguration;
import com.novo.microservices.components.helpers.ContextInformationService;
import com.novo.microservices.components.helpers.DocumentationMicroservice;
import com.novo.microservices.dtos.base.BusinessResponse;
import com.novo.microservices.tbs.utils.dtos.commons.CommonBrokerConfiguration;
import com.novo.microservices.transactions.contracts.IOrchestratorTransaction;
import com.novo.microservices.utils.common.context.AppSessionContext;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import static com.novo.microservices.transactions.constants.OrchestratorTransactionConstants.ORCHESTRATOR_CHECK_CUSTOMER_INFO_TRANSACTION_CODE;

/**
 * OrchestratorTransactionFactoryTest
 * <p>
 * OrchestratorTransactionFactoryTest class.
 * <p>
 * <p>
 * ESTE COMPONENTE FUE CONSTRUIDO SIGUIENDO LOS ESTANDARES DE DESARROLLO Y EL PROCEDIMIENTO
 * DE DESARROLLO DE APLICACIONES DE NOVOPAYMENT Y SE ENCUENTRA PROTEGIDO POR LAS LEYES DE
 * PROPIEDAD INTELECTUAL Y DERECHOS DE AUTOR.
 *
 * @author Novopayment Inc.
 * @author lblanco@novopayment.com
 * @since 4/18/2022
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
public class OrchestratorTransactionFactoryTest {

    @Autowired
    OrchestratorTransactionFactory orchestratorTransactionFactory;

    @Autowired
    MessageConfiguration messageConfiguration;

    @Test
    @DisplayName("Transaction Factory Test")
    void factoryTest() {

        // Given
        final CommonBrokerConfiguration commonBrokerConfigurationExpected = messageConfiguration.getCustomerInformationTransactionProducer();

        // When
        IOrchestratorTransaction orchestratorTransaction = orchestratorTransactionFactory.factory(ORCHESTRATOR_CHECK_CUSTOMER_INFO_TRANSACTION_CODE);

        // Then
        Assertions.assertEquals(commonBrokerConfigurationExpected.getCommand(), orchestratorTransaction.getBrokerConfigurationProducer().getCommand());
        Assertions.assertEquals(commonBrokerConfigurationExpected.getRoutingDomain(), orchestratorTransaction.getBrokerConfigurationProducer().getRoutingDomain());
        Assertions.assertEquals(commonBrokerConfigurationExpected.getRoutingKeyDestiny(), orchestratorTransaction.getBrokerConfigurationProducer().getRoutingKeyDestiny());
        Assertions.assertNotNull(orchestratorTransaction);
    }
}