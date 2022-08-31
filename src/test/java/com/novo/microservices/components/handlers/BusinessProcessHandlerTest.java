package com.novo.microservices.components.handlers;

import com.novo.microservices.MicroservicesBrokerTestConfiguration;
import com.novo.microservices.MicroservicesCommonTestConfiguration;
import com.novo.microservices.MicroservicesSagaTestConfiguration;
import com.novo.microservices.MicroservicesTestConfiguration;
import com.novo.microservices.components.enums.ResponseCode;
import com.novo.microservices.components.helpers.ContextInformationService;
import com.novo.microservices.components.helpers.DocumentationMicroservice;
import com.novo.microservices.dtos.base.BusinessResponse;
import com.novo.microservices.tbs.utils.components.exceptions.BusinessProcessException;
import com.novo.microservices.tbs.utils.components.handlers.BusinessProcessHandler;
import com.novo.microservices.tbs.utils.dtos.responses.BusinessProcessResponse;
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
 * BusinessProcessHandlerTest
 * <p>
 * BusinessProcessHandlerTest class.
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
public class BusinessProcessHandlerTest {

    @Autowired
    BusinessProcessHandler businessProcessHandler;

    @Test
    @Order(1)
    @DisplayName("Process Error Handler Method # 1 Test")
    void processErrorHandlerTest() {

        // Given
        final BusinessProcessResponse businessProcessResponseExpected = BusinessProcessResponse.builder().responseCode(ResponseCode.INTERNAL_SERVER_ERROR).build();

        // When
        final BusinessProcessResponse instance = businessProcessHandler.processErrorHandler(new BusinessProcessException(ResponseCode.INTERNAL_SERVER_ERROR)).block();

        // Then
        Assertions.assertNotNull(instance);
        Assertions.assertEquals(businessProcessResponseExpected.getResponseCode(), instance.getResponseCode());
    }

    @Test
    @Order(2)
    @DisplayName("Process Error Handler Method # 2 Test")
    void processErrorHandlerSecondTest() {

        // Given
        final BusinessProcessResponse businessProcessResponseExpected = BusinessProcessResponse.builder().responseCode(ResponseCode.INTERNAL_SERVER_ERROR).build();

        // When
        final BusinessProcessResponse instance = businessProcessHandler.processErrorHandler(new BusinessProcessException(ResponseCode.INTERNAL_SERVER_ERROR), ResponseCode.INTERNAL_SERVER_ERROR).block();

        // Then
        Assertions.assertNotNull(instance);
        Assertions.assertEquals(businessProcessResponseExpected.getResponseCode(), instance.getResponseCode());
    }
}