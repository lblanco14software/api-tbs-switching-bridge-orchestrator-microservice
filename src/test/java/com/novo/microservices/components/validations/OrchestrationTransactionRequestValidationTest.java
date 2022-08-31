package com.novo.microservices.components.validations;

import com.novo.microservices.components.enums.ValidateResult;
import com.novo.microservices.dtos.OrchestratorValidationResult;
import com.novo.microservices.dtos.custom.CustomTransactionInformation;
import com.novo.microservices.dtos.custom.PaymentHeaderInformation;
import com.novo.microservices.dtos.requests.OrchestrationTransactionRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * OrchestrationTransactionRequestValidationTest
 * <p>
 * OrchestrationTransactionRequestValidationTest class.
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
public class OrchestrationTransactionRequestValidationTest {

    @Test
    @Order(1)
    @DisplayName("Validate Transaction Request Error # 1 Test")
    void validateMainRequestErrorTest() {

        // Given
        final OrchestrationTransactionRequest orchestrationTransactionRequest = OrchestrationTransactionRequest.builder().build();
        final OrchestratorValidationResult instanceExpected = OrchestratorValidationResult.builder().validateResult(ValidateResult.NOT_VALID).build();

        // When
        final OrchestratorValidationResult instance = IOrchestrationTransactionRequestValidation.validateMainRequest().apply(orchestrationTransactionRequest);

        // Then
        Assertions.assertNotNull(instance);
        Assertions.assertEquals(instanceExpected.getValidateResult(), instance.getValidateResult());
    }

    @Test
    @Order(2)
    @DisplayName("Validate Transaction Request Error # 2 Test")
    void validatePaymentHeaderErrorTest() {

        // Given
        final OrchestrationTransactionRequest orchestrationTransactionRequest = OrchestrationTransactionRequest.builder().paymentHeader(PaymentHeaderInformation.builder().build()).build();
        final OrchestratorValidationResult instanceExpected = OrchestratorValidationResult.builder().validateResult(ValidateResult.NOT_VALID).build();

        // When
        final OrchestratorValidationResult instance = IOrchestrationTransactionRequestValidation.validatePaymentHeader().apply(orchestrationTransactionRequest);

        // Then
        Assertions.assertNotNull(instance);
        Assertions.assertEquals(instanceExpected.getValidateResult(), instance.getValidateResult());
    }


    @Test
    @Order(3)
    @DisplayName("Validate Transaction Request Error # 3 Test")
    void validateTransactionRequestErrorTest() {

        // Given
        final OrchestrationTransactionRequest orchestrationTransactionRequest = OrchestrationTransactionRequest.builder().transaction(CustomTransactionInformation.builder().build()).build();
        final OrchestratorValidationResult instanceExpected = OrchestratorValidationResult.builder().validateResult(ValidateResult.NOT_VALID).build();

        // When
        final OrchestratorValidationResult instance = IOrchestrationTransactionRequestValidation.validateTransactionRequest().apply(orchestrationTransactionRequest);

        // Then
        Assertions.assertNotNull(instance);
        Assertions.assertEquals(instanceExpected.getValidateResult(), instance.getValidateResult());
    }

    @Test
    @Order(3)
    @DisplayName("Validate Transaction Request Error # 4 Test")
    void validateTransactionRequestAllErrorTest() {

        // Given
        final OrchestrationTransactionRequest orchestrationTransactionRequest = OrchestrationTransactionRequest.builder()
            .paymentHeader(PaymentHeaderInformation.builder().build())
            .transaction(CustomTransactionInformation.builder().build())
            .build();
        final OrchestratorValidationResult instanceExpected = OrchestratorValidationResult.builder().validateResult(ValidateResult.NOT_VALID).build();

        // When
        final OrchestratorValidationResult instance = IOrchestrationTransactionRequestValidation.validateTransactionRequest()
            .and(IOrchestrationTransactionRequestValidation.validateMainRequest())
            .apply(orchestrationTransactionRequest);

        // Then
        Assertions.assertNotNull(instance);
        Assertions.assertEquals(instanceExpected.getValidateResult(), instance.getValidateResult());
    }

}