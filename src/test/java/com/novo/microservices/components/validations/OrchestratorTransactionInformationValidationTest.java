package com.novo.microservices.components.validations;

import com.novo.microservices.components.enums.ValidateResult;
import com.novo.microservices.dtos.OrchestratorValidationResult;
import com.novo.microservices.repositories.entities.OrchestratorTransactionEntity;
import com.novo.microservices.tbs.utils.dtos.StandardTransaction;
import com.novo.microservices.tbs.utils.dtos.TransactionMessage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

/**
 * OrchestratorTransactionInformationValidationTest
 * <p>
 * OrchestratorTransactionInformationValidationTest class.
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
public class OrchestratorTransactionInformationValidationTest {

    @Test
    @Order(1)
    @DisplayName("Validate Transaction Response Error # 1 Test")
    void validateResponseTest() {

        // Given
        final TransactionMessage<StandardTransaction> transactionMessage = new TransactionMessage<>();
        final OrchestratorValidationResult instanceExpected = OrchestratorValidationResult.builder().validateResult(ValidateResult.NOT_VALID).build();

        // When
        final OrchestratorValidationResult instance = ISagaTransactionResponseValidation.validateTransactionMessage().apply(transactionMessage).block();

        // Then
        Assertions.assertNotNull(instance);
        Assertions.assertEquals(instanceExpected.getValidateResult(), instance.getValidateResult());
    }

    @Test
    @Order(1)
    @DisplayName("Validate Main Request Test")
    void validateMainRequestTest() {

        // Given
        final OrchestratorTransactionEntity orchestratorTransactionEntity = new OrchestratorTransactionEntity();
        final OrchestratorValidationResult instanceExpected = OrchestratorValidationResult.builder().validateResult(ValidateResult.NOT_VALID).build();

        // When
        final OrchestratorValidationResult instance = IOrchestratorTransactionInformationValidation.validateMainRequest().apply(orchestratorTransactionEntity).block();

        // Then
        Assertions.assertNotNull(instance);
        Assertions.assertEquals(instanceExpected.getValidateResult(), instance.getValidateResult());
    }
}