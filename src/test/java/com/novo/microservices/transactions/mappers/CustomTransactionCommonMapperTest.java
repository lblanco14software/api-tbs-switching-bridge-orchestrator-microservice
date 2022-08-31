package com.novo.microservices.transactions.mappers;

import com.novo.microservices.MicroservicesBrokerTestConfiguration;
import com.novo.microservices.MicroservicesCommonTestConfiguration;
import com.novo.microservices.MicroservicesSagaTestConfiguration;
import com.novo.microservices.MicroservicesTestConfiguration;
import com.novo.microservices.components.helpers.ContextInformationService;
import com.novo.microservices.components.helpers.DocumentationMicroservice;
import com.novo.microservices.dtos.base.BusinessResponse;
import com.novo.microservices.dtos.custom.CustomTransactionInformation;
import com.novo.microservices.dtos.requests.OrchestrationTransactionRequest;
import com.novo.microservices.mocks.CustomTransactionInformationResponseFixture;
import com.novo.microservices.mocks.OrchestrationTransactionRequestFixture;
import com.novo.microservices.mocks.StandardTransactionResponseFixture;
import com.novo.microservices.tbs.utils.dtos.StandardTransaction;
import com.novo.microservices.transactions.mappers.implementations.CustomTransactionCommonMapper;
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
 * CustomTransactionCommonMapperTest
 * <p>
 * CustomTransactionCommonMapperTest class.
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
public class CustomTransactionCommonMapperTest {

    @Autowired
    CustomTransactionCommonMapper customTransactionCommonMapper;

    @Test
    @Order(1)
    @DisplayName("Build Custom Transaction Information Test")
    void factoryTest() {

        // Given
        final OrchestrationTransactionRequest orchestrationTransactionRequest = new OrchestrationTransactionRequestFixture().getInstance();
        final StandardTransaction standardTransactionResponse = new StandardTransactionResponseFixture().getInstance();
        final CustomTransactionInformation customTransactionInformationExpected = new CustomTransactionInformationResponseFixture().getInstance();

        // When
        final CustomTransactionInformation customTransactionInformation = customTransactionCommonMapper.buildCustomTransactionInformation(orchestrationTransactionRequest, standardTransactionResponse);

        // Then
        Assertions.assertNotNull(customTransactionInformation);
        Assertions.assertEquals(customTransactionInformationExpected.getMessageTypeIndicator(), customTransactionInformation.getMessageTypeIndicator());
        Assertions.assertEquals(customTransactionInformationExpected.getDe2(), customTransactionInformation.getDe2());
        Assertions.assertEquals(customTransactionInformationExpected.getDe3(), customTransactionInformation.getDe3());
        Assertions.assertEquals(customTransactionInformationExpected.getDe4(), customTransactionInformation.getDe4());
        Assertions.assertEquals(customTransactionInformationExpected.getDe7(), customTransactionInformation.getDe7());
        Assertions.assertEquals(customTransactionInformationExpected.getDe11(), customTransactionInformation.getDe11());
        Assertions.assertEquals(customTransactionInformationExpected.getDe12(), customTransactionInformation.getDe12());
        Assertions.assertEquals(customTransactionInformationExpected.getDe13(), customTransactionInformation.getDe13());
        Assertions.assertEquals(customTransactionInformationExpected.getDe18(), customTransactionInformation.getDe18());
        Assertions.assertEquals(customTransactionInformationExpected.getDe22(), customTransactionInformation.getDe22());
        Assertions.assertEquals(customTransactionInformationExpected.getDe32(), customTransactionInformation.getDe32());
        Assertions.assertEquals(customTransactionInformationExpected.getDe35(), customTransactionInformation.getDe35());
        Assertions.assertEquals(customTransactionInformationExpected.getDe37(), customTransactionInformation.getDe37());
        Assertions.assertEquals(customTransactionInformationExpected.getDe38(), customTransactionInformation.getDe38());
        Assertions.assertEquals(customTransactionInformationExpected.getDe41(), customTransactionInformation.getDe41());
        Assertions.assertEquals(customTransactionInformationExpected.getDe42(), customTransactionInformation.getDe42());
    }
}