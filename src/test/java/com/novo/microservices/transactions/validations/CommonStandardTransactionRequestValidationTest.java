package com.novo.microservices.transactions.validations;

import com.novo.microservices.MicroservicesBrokerTestConfiguration;
import com.novo.microservices.MicroservicesCommonTestConfiguration;
import com.novo.microservices.MicroservicesSagaTestConfiguration;
import com.novo.microservices.MicroservicesTestConfiguration;
import com.novo.microservices.components.enums.ValidateResult;
import com.novo.microservices.components.helpers.ContextInformationService;
import com.novo.microservices.components.helpers.DocumentationMicroservice;
import com.novo.microservices.dtos.OrchestratorValidationResult;
import com.novo.microservices.dtos.base.BusinessResponse;
import com.novo.microservices.mocks.StandardTransactionResponseFixture;
import com.novo.microservices.tbs.utils.dtos.StandardTransaction;
import com.novo.microservices.transactions.validations.contracts.IStandardTransactionValidation;
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
 * ICommonStandardTransactionRequestValidationTest
 * <p>
 * ICommonStandardTransactionRequestValidationTest class.
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
public class CommonStandardTransactionRequestValidationTest {

    @Autowired
    private IStandardTransactionValidation standardTransactionValidation;

    @Test
    @Order(1)
    @DisplayName("Validate Common Required Fields Success Test")
    void validateCommonRequiredFieldsSuccessTest() {

        // Given
        final StandardTransaction standardTransaction = new StandardTransactionResponseFixture().getInstance();
        final OrchestratorValidationResult orchestratorValidationResultExpected = OrchestratorValidationResult.builder().validateResult(ValidateResult.SUCCESSFULLY_VALID).build();

        // When
        final OrchestratorValidationResult orchestratorValidationResult = standardTransactionValidation.validateFields(standardTransaction);

        // Then
        Assertions.assertNotNull(orchestratorValidationResult);
        Assertions.assertEquals(orchestratorValidationResultExpected.getValidateResult(), orchestratorValidationResult.getValidateResult());
    }

    @Test
    @Order(1)
    @DisplayName("Validate Custom Fields Success Test")
    void validateCustomFieldsSuccessTest() {

        // Given
        final StandardTransaction standardTransaction = new StandardTransactionResponseFixture().getInstance();
        final OrchestratorValidationResult orchestratorValidationResultExpected = OrchestratorValidationResult.builder().validateResult(ValidateResult.SUCCESSFULLY_VALID).build();

        // When
        final OrchestratorValidationResult orchestratorValidationResult = standardTransactionValidation.validateFields(standardTransaction);

        // Then
        Assertions.assertNotNull(orchestratorValidationResult);
        Assertions.assertEquals(orchestratorValidationResultExpected.getValidateResult(), orchestratorValidationResult.getValidateResult());
    }

    @Test
    @Order(3)
    @DisplayName("Validate Common Required Fields Success Test")
    void validateCommonRequiredFieldsErrorTest() {

        // Given
        final StandardTransaction standardTransaction = StandardTransaction.builder().build();
        final OrchestratorValidationResult orchestratorValidationResultExpected = OrchestratorValidationResult.builder().validateResult(ValidateResult.NOT_VALID).build();

        // When
        final OrchestratorValidationResult orchestratorValidationResult = standardTransactionValidation.validateFields(standardTransaction);

        // Then
        Assertions.assertNotNull(orchestratorValidationResult);
        Assertions.assertEquals(orchestratorValidationResultExpected.getValidateResult(), orchestratorValidationResult.getValidateResult());
    }
}