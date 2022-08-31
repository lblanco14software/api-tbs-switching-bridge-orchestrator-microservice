package com.novo.microservices.dtos.commons;

import com.novo.microservices.components.enums.ResponseCode;
import com.novo.microservices.dtos.OrchestratorTransactionInformation;
import com.novo.microservices.dtos.generics.GenericBusinessResponse;
import com.novo.microservices.tbs.utils.dtos.responses.BusinessProcessResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * BusinessProcessResponseTest
 * <p>
 * BusinessProcessResponseTest class.
 * <p>
 * <p>
 * ESTE COMPONENTE FUE CONSTRUIDO SIGUIENDO LOS ESTANDARES DE DESARROLLO Y EL PROCEDIMIENTO
 * DE DESARROLLO DE APLICACIONES DE NOVOPAYMENT Y SE ENCUENTRA PROTEGIDO POR LAS LEYES DE
 * PROPIEDAD INTELECTUAL Y DERECHOS DE AUTOR.
 *
 * @author Novopayment Inc.
 * @author lblanco@novopayment.com
 * @since 4/20/2022
 */
@SpringBootTest
@AutoConfigureMockMvc
public class BusinessProcessResponseTest {

    @Test
    @Order(1)
    @DisplayName("Set Entity Success fully Response Test")
    public void setEntitySuccessfullyResponse() {

        // Given
        final String messageIdExpected = "test-message-id";
        final OrchestratorTransactionInformation orchestratorTransactionInformationExpected = OrchestratorTransactionInformation.builder()
            .messageId(messageIdExpected)
            .build();
        final GenericBusinessResponse<OrchestratorTransactionInformation> genericBusinessResponse = new GenericBusinessResponse<>(orchestratorTransactionInformationExpected);

        // When
        final BusinessProcessResponse instance = BusinessProcessResponse.setEntitySuccessfullyResponse(genericBusinessResponse);

        // Then
        Assertions.assertNotNull(instance);
        Assertions.assertTrue(instance.isSuccessfullyResponse());
        Assertions.assertFalse(instance.isErrorProcessResponse());

    }

    @Test
    @Order(2)
    @DisplayName("Set Business Error Response Test")
    public void setBusinessErrorResponseTest() {

        // Given
        final String messageIdExpected = "test-message-id";
        final OrchestratorTransactionInformation orchestratorTransactionInformationExpected = OrchestratorTransactionInformation.builder()
            .messageId(messageIdExpected)
            .build();
        final GenericBusinessResponse<OrchestratorTransactionInformation> genericBusinessResponse = new GenericBusinessResponse<>(orchestratorTransactionInformationExpected);

        // When
        final BusinessProcessResponse instance = BusinessProcessResponse.setBusinessErrorResponse(genericBusinessResponse, ResponseCode.INTERNAL_SERVER_ERROR);

        // Then
        Assertions.assertNotNull(instance);
        Assertions.assertTrue(instance.isSuccessfullyResponse());
        Assertions.assertFalse(instance.isErrorProcessResponse());
        Assertions.assertTrue(instance.existBusinessResponse());
    }
}