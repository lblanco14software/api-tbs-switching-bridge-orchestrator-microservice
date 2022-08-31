package com.novo.microservices.components.validations;

import com.novo.microservices.components.enums.ValidateResult;
import com.novo.microservices.dtos.OrchestratorValidationResult;
import com.novo.microservices.dtos.responses.OrchestrationSagaResponse;
import com.novo.microservices.mocks.OrchestrationSagaResponseFixture;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * OrchestrationSagaResponseValidationTest
 * <p>
 * OrchestrationSagaResponseValidationTest class.
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
public class OrchestrationSagaResponseValidationTest {

    @Test
    @Order(1)
    @DisplayName("Validate Main Response Test")
    void validateMainResponseTest() {

        // Given
        final OrchestrationSagaResponse orchestrationSagaResponse = new OrchestrationSagaResponseFixture().getInstance();
        final OrchestratorValidationResult instanceExpected = OrchestratorValidationResult.builder().validateResult(ValidateResult.SUCCESSFULLY_VALID).build();

        // When
        final OrchestratorValidationResult instance = IOrchestrationSagaResponseValidation.validateMainResponse().apply(orchestrationSagaResponse);

        // Then
        Assertions.assertNotNull(instance);
        Assertions.assertEquals(instanceExpected.getValidateResult(), instance.getValidateResult());
    }

    @Test
    @Order(2)
    @DisplayName("Validate Saga State Test")
    void validateSagaStateTest() {

        // Given
        final OrchestrationSagaResponse orchestrationSagaResponse = new OrchestrationSagaResponseFixture().getInstance();
        final OrchestratorValidationResult instanceExpected = OrchestratorValidationResult.builder().validateResult(ValidateResult.NOT_VALID).build();

        // When
        final OrchestratorValidationResult instance = IOrchestrationSagaResponseValidation.validateSagaState().apply(orchestrationSagaResponse);

        // Then
        Assertions.assertNotNull(instance);
        Assertions.assertEquals(instanceExpected.getValidateResult(), instance.getValidateResult());
    }

    @Test
    @Order(3)
    @DisplayName("Validate Saga State and Main Response Error # 1 Test")
    void validateSagaStateAndMainResponseTest() {

        // Given
        final OrchestrationSagaResponse orchestrationSagaResponse = new OrchestrationSagaResponseFixture().getInstance();
        final OrchestratorValidationResult instanceExpected = OrchestratorValidationResult.builder().validateResult(ValidateResult.NOT_VALID).build();

        // When
        final OrchestratorValidationResult instance = IOrchestrationSagaResponseValidation.validateSagaState()
            .and(IOrchestrationSagaResponseValidation.validateMainResponse())
            .apply(orchestrationSagaResponse);

        // Then
        Assertions.assertNotNull(instance);
        Assertions.assertEquals(instanceExpected.getValidateResult(), instance.getValidateResult());
    }

    @Test
    @Order(4)
    @DisplayName("Validate Saga State and Main Response Error # 2 Test")
    void validateSagaStateAndMainResponseErrorTest() {

        // Given
        final OrchestrationSagaResponse orchestrationSagaResponse = OrchestrationSagaResponse.builder()

            .build();
        final OrchestratorValidationResult instanceExpected = OrchestratorValidationResult.builder().validateResult(ValidateResult.NOT_VALID).build();

        // When
        final OrchestratorValidationResult instance = IOrchestrationSagaResponseValidation.validateMainResponse()
            .and(IOrchestrationSagaResponseValidation.validateSagaState())
            .apply(orchestrationSagaResponse);

        // Then
        Assertions.assertNotNull(instance);
        Assertions.assertEquals(instanceExpected.getValidateResult(), instance.getValidateResult());
    }
}