package com.novo.microservices.services;

import com.novo.microservices.MicroservicesBrokerTestConfiguration;
import com.novo.microservices.MicroservicesCommonTestConfiguration;
import com.novo.microservices.MicroservicesSagaTestConfiguration;
import com.novo.microservices.MicroservicesTestConfiguration;
import com.novo.microservices.components.helpers.ContextInformationService;
import com.novo.microservices.components.helpers.DocumentationMicroservice;
import com.novo.microservices.dtos.base.BusinessResponse;
import com.novo.microservices.mocks.StandardTransactionResponseFixture;
import com.novo.microservices.services.implementations.OrchestratorEncryptionService;
import com.novo.microservices.tbs.utils.dtos.StandardTransaction;
import com.novo.microservices.utils.common.context.AppSessionContext;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

/**
 * OrchestratorEncryptionServiceTest
 * <p>
 * OrchestratorEncryptionServiceTest class.
 * <p>
 * <p>
 * ESTE COMPONENTE FUE CONSTRUIDO SIGUIENDO LOS ESTANDARES DE DESARROLLO Y EL PROCEDIMIENTO
 * DE DESARROLLO DE APLICACIONES DE NOVOPAYMENT Y SE ENCUENTRA PROTEGIDO POR LAS LEYES DE
 * PROPIEDAD INTELECTUAL Y DERECHOS DE AUTOR.
 *
 * @author Novopayment Inc.
 * @author lblanco@novopayment.com
 * @since 4/25/2022
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
public class OrchestratorEncryptionServiceTest {

    @Autowired
    OrchestratorEncryptionService orchestratorEncryptionService;

    @Test
    @Order(1)
    @DisplayName("Encrypt Transaction Test")
    void doOnEncryptTransactionTest() {

        // Given
        final StandardTransaction standardTransaction = new StandardTransactionResponseFixture().getInstance();

        // When
        final String instance = orchestratorEncryptionService.doOnEncryptTransaction(standardTransaction).block();

        // Then
        Assertions.assertNotNull(instance);
    }

    @Test
    @Order(2)
    @DisplayName("Encrypt Transaction Test")
    void doOnDecryptTransactionTest() {

        // Given
        final StandardTransaction standardTransaction = new StandardTransactionResponseFixture().getInstance();
        final String standardTransactionEncrypted = orchestratorEncryptionService.doOnEncryptTransaction(standardTransaction).block();

        // When
        final StandardTransaction instance = orchestratorEncryptionService.doOnDecryptTransaction(standardTransactionEncrypted).block();

        // Then
        Assertions.assertNotNull(instance);
    }
}