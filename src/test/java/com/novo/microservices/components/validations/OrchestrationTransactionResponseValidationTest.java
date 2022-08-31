package com.novo.microservices.components.validations;

import com.novo.microservices.components.enums.ValidateResult;
import com.novo.microservices.dtos.OrchestratorValidationResult;
import com.novo.microservices.dtos.custom.CustomPaymentInformation;
import com.novo.microservices.dtos.responses.OrchestrationTransactionResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import static com.novo.microservices.transactions.enums.CustomTransactionResponses.CUSTOM_RESPONSE_STATUS_DECLINED;

/**
 * OrchestrationTransactionResponseValidation
 * <p>
 * OrchestrationTransactionResponseValidation class.
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
public class OrchestrationTransactionResponseValidationTest {

    @Test
    @Order(1)
    @DisplayName("Validate Transaction Response Error # 1 Test")
    void validateResponseTest() {

        // Given
        final OrchestrationTransactionResponse orchestrationTransactionResponse = OrchestrationTransactionResponse.builder().build();
        final OrchestratorValidationResult instanceExpected = OrchestratorValidationResult.builder().validateResult(ValidateResult.NOT_VALID).build();

        // When
        final OrchestratorValidationResult instance = IOrchestrationTransactionResponseValidation.validateResponse().apply(orchestrationTransactionResponse);

        // Then
        Assertions.assertNotNull(instance);
        Assertions.assertEquals(instanceExpected.getValidateResult(), instance.getValidateResult());
    }

    @Test
    @Order(2)
    @DisplayName("Validate Transaction Approved Error # 2 Test")
    void validateTransactionApprovedTest() {

        // Given
        final OrchestrationTransactionResponse orchestrationTransactionResponse = OrchestrationTransactionResponse.builder()
            .paymentResponse(CustomPaymentInformation.builder()
                .responseStatus(CUSTOM_RESPONSE_STATUS_DECLINED.getCustomResponseCode())
                .build())
            .build();
        final OrchestratorValidationResult instanceExpected = OrchestratorValidationResult.builder().validateResult(ValidateResult.NOT_VALID).build();

        // When
        final OrchestratorValidationResult instance = IOrchestrationTransactionResponseValidation.validateTransactionApproved().apply(orchestrationTransactionResponse);

        // Then
        Assertions.assertNotNull(instance);
        Assertions.assertEquals(instanceExpected.getValidateResult(), instance.getValidateResult());
    }

    @Test
    @Order(3)
    @DisplayName("Validate Transaction Approved Error # 3 Test")
    void validateTransactionApprovedAllErrorsTest() {

        // Given
        final OrchestrationTransactionResponse orchestrationTransactionResponse = OrchestrationTransactionResponse.builder()
            .paymentResponse(CustomPaymentInformation.builder()
                .responseStatus(CUSTOM_RESPONSE_STATUS_DECLINED.getCustomResponseCode())
                .build())
            .build();
        final OrchestratorValidationResult instanceExpected = OrchestratorValidationResult.builder().validateResult(ValidateResult.NOT_VALID).build();

        // When
        final OrchestratorValidationResult instance = IOrchestrationTransactionResponseValidation.validateTransactionApproved()
            .and(IOrchestrationTransactionResponseValidation.validateResponse())
            .apply(orchestrationTransactionResponse);

        // Then
        Assertions.assertNotNull(instance);
        Assertions.assertEquals(instanceExpected.getValidateResult(), instance.getValidateResult());
    }
}