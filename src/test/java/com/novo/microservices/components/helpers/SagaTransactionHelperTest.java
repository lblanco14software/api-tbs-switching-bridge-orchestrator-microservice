package com.novo.microservices.components.helpers;

import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import static com.novo.microservices.constants.ProcessConstants.*;

/**
 * SagaTransactionHelperTest
 * <p>
 * SagaTransactionHelperTest class.
 * <p>
 * <p>
 * ESTE COMPONENTE FUE CONSTRUIDO SIGUIENDO LOS ESTANDARES DE DESARROLLO Y EL PROCEDIMIENTO
 * DE DESARROLLO DE APLICACIONES DE NOVOPAYMENT Y SE ENCUENTRA PROTEGIDO POR LAS LEYES DE
 * PROPIEDAD INTELECTUAL Y DERECHOS DE AUTOR.
 *
 * @author Novopayment Inc.
 * @author lblanco@novopayment.com
 * @since 4/10/2022
 */
@Log4j2
@SpringBootTest
@AutoConfigureMockMvc
public class SagaTransactionHelperTest {

    @Test
    @Order(1)
    @DisplayName("Get First Two Characters Test")
    void getFirstTwoCharactersTest() {

        // Given
        final String messageTypeIndicator = "0200";
        final String expectedValue = "02";

        // When
        String processMessageTypeIndicator = SagaTransactionHelper.getFirstTwoCharacters(messageTypeIndicator);

        // Then
        Assertions.assertEquals(expectedValue, processMessageTypeIndicator);
    }


    @Test
    @Order(2)
    @DisplayName("Truncate 32 Characters Test")
    void truncate32CharactersTest() {

        // Given
        final String processTrackingId = "17022022164536130004526371234567897887878787424";
        final String expectedValue = "17022022164536130004526371234567";

        // When
        String processMessageTypeIndicator = SagaTransactionHelper.truncateCharacters(processTrackingId, CHARACTER_TRUNCATE_32_LENGTH);

        // Then
        Assertions.assertEquals(expectedValue, processMessageTypeIndicator);
    }

    @Test
    @Order(3)
    @DisplayName("Process TrackingId Test")
    void processTrackingIdTest() {

        // Given
        final String messageTypeIndicator = "0200";
        final String processTrackingId = "17022022164536130004526371234567897887878787424";
        final String expectedValue = "02170220221645361300045263712345";

        // When
        String processMessageTypeIndicator = SagaTransactionHelper.processTrackingId(messageTypeIndicator, processTrackingId);

        // Then
        Assertions.assertEquals(expectedValue, processMessageTypeIndicator);
    }

    @Test
    @Order(4)
    @DisplayName("Process TrackingId Second Test")
    void processTrackingIdSecondTest() {

        // Given
        final String messageTypeIndicator = "0200";
        final String processTrackingId = "1702202216453613";
        final String expectedValue = "021702202216453613";

        // When
        String processMessageTypeIndicator = SagaTransactionHelper.processTrackingId(messageTypeIndicator, processTrackingId);

        // Then
        Assertions.assertEquals(expectedValue, processMessageTypeIndicator);
    }

    @Test
    @Order(5)
    @DisplayName("Transaction TimeOut Test")
    void getOrchestratorTransactionTimeOutTest() {

        // Given
        final String tenantParameterSetting = "invalid";
        final int expectedValue = 0;

        // When
        int tenantParameterValue = SagaTransactionHelper.getOrchestratorTransactionTimeOut(tenantParameterSetting);

        // Then
        Assertions.assertNotEquals(expectedValue, tenantParameterValue);
    }

    @Test
    @Order(6)
    @DisplayName("Transaction TimeOut Error Test")
    void getOrchestratorTransactionTimeOutErrorTest() {

        // Given
        // When
        int tenantParameterValue = SagaTransactionHelper.getOrchestratorTransactionTimeOut(MICROSERVICES_CONFIG_NOT_FOUND);

        // Then
        Assertions.assertEquals(MICROSERVICES_ORCHESTRATOR_TRANSACTION_TIME_MILLISECONDS_DEFAULT_VALUE, tenantParameterValue);
    }

    @Test
    @Order(7)
    @DisplayName("Orchestrator Transaction Delay Test")
    void getOrchestratorTransactionDelayTest() {

        // Given
        final String tenantParameterSetting = "invalid";
        final int expectedValue = 0;

        // When
        int tenantParameterValue = SagaTransactionHelper.getOrchestratorTransactionDelay(tenantParameterSetting);

        // Then
        Assertions.assertNotEquals(expectedValue, tenantParameterValue);
    }

    @Test
    @Order(8)
    @DisplayName("Orchestrator Transaction Delay Error Test")
    void getOrchestratorTransactionDelayErrorTest() {

        // Given
        // When
        int tenantParameterValue = SagaTransactionHelper.getOrchestratorTransactionDelay(MICROSERVICES_CONFIG_NOT_FOUND);

        // Then
        Assertions.assertEquals(MICROSERVICES_ORCHESTRATOR_TRANSACTION_DELAY_MILLISECONDS_DEFAULT_VALUE, tenantParameterValue);
    }

    @Test
    @Order(9)
    @DisplayName("Get First Two Characters Error Test")
    void getFirstTwoCharactersErrorTest() {

        // Given
        // When
        String processMessageTypeIndicator = SagaTransactionHelper.getFirstTwoCharacters(StringUtils.EMPTY);

        // Then
        Assertions.assertNull(processMessageTypeIndicator);
    }

    @Test
    @Order(10)
    @DisplayName("Generate TrackingId Test")
    void generateTrackingIdTest() {

        // Given
        final int lengthExpected = 32;

        // When
        final String generateTrackingId = SagaTransactionHelper.generateTrackingId();
        log.info("generateTrackingId {}", generateTrackingId);

        // Then
        Assertions.assertNotNull(generateTrackingId);
        Assertions.assertEquals(lengthExpected, generateTrackingId.length());
    }
}