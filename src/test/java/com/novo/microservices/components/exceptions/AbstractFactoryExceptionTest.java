package com.novo.microservices.components.exceptions;

import com.novo.microservices.MicroservicesBrokerTestConfiguration;
import com.novo.microservices.MicroservicesCommonTestConfiguration;
import com.novo.microservices.MicroservicesSagaTestConfiguration;
import com.novo.microservices.MicroservicesTestConfiguration;
import com.novo.microservices.components.helpers.ContextInformationService;
import com.novo.microservices.components.helpers.DocumentationMicroservice;
import com.novo.microservices.dtos.base.BusinessResponse;
import com.novo.microservices.transactions.factories.OrchestratorTransactionFactory;
import com.novo.microservices.utils.common.context.AppSessionContext;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import static com.novo.microservices.constants.ProcessConstants.ORCHESTRATOR_TRANSACTION_EXCEPTION;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * AbstractFactoryExceptionTest
 * <p>
 * AbstractFactoryExceptionTest class.
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
public class AbstractFactoryExceptionTest {

    @Autowired
    OrchestratorTransactionFactory orchestratorTransactionFactory;

    @Test
    @DisplayName("Abstract Factory Exception Test")
    void contextLoads() {

        // Given
        final String transactionId = "invalid-transaction-id";

        // When
        final AbstractFactoryException instance = assertThrows(AbstractFactoryException.class, () -> orchestratorTransactionFactory.factory(transactionId));

        // Then
        Assertions.assertNotNull(instance);
        Assertions.assertEquals(ORCHESTRATOR_TRANSACTION_EXCEPTION, instance.getMessage());
    }
}