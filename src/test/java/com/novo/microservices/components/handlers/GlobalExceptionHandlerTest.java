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
import com.novo.microservices.tbs.utils.components.handlers.GlobalExceptionHandler;
import com.novo.microservices.utils.common.context.AppSessionContext;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.ResponseEntity;

/**
 * GlobalExceptionHandlerTest
 * <p>
 * GlobalExceptionHandlerTest class.
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
public class GlobalExceptionHandlerTest {

    @Autowired
    GlobalExceptionHandler globalExceptionHandler;

    @Test
    @Order(1)
    @DisplayName("Handle Conflict Test")
    void handleConflictTest() {

        // Given
        // When
        final ResponseEntity<Object> instance = globalExceptionHandler.handleConflict(new RuntimeException("error test"));

        // Then
        Assertions.assertNotNull(instance);
    }

    @Test
    @Order(1)
    @DisplayName("Handle AllExceptions Test")
    void handleAllExceptionsTest() {

        // Given
        // When
        final ResponseEntity<Object> instance = globalExceptionHandler.handleAllExceptions(new BusinessProcessException(ResponseCode.INTERNAL_SERVER_ERROR));

        // Then
        Assertions.assertNotNull(instance);
    }
}