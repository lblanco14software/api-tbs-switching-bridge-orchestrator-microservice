package com.novo.microservices.dtos;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * CacheProcessResponseTest
 * <p>
 * CacheProcessResponseTest class.
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
public class CacheProcessResponseTest {

    @Test
    @Order(1)
    @DisplayName("Set Cache Entity Test")
    void setCacheEntityTest() {

        // Given
        final String messageIdExpected = "test-message-id";
        final OrchestratorTransactionInformation orchestratorTransactionInformationExpected = OrchestratorTransactionInformation.builder()
            .messageId(messageIdExpected)
            .build();

        // When
        final CacheProcessResponse<OrchestratorTransactionInformation> instance = new CacheProcessResponse<>();
        instance.setCacheEntity(orchestratorTransactionInformationExpected);

        // Then
        Assertions.assertNotNull(instance);
        Assertions.assertNotNull(instance.getCacheEntity());
        Assertions.assertEquals(true, instance.isCachePresent());
        Assertions.assertEquals(false, instance.isError());
        Assertions.assertEquals(messageIdExpected, instance.getCacheEntity().getMessageId());
    }

    @Test
    @Order(2)
    @DisplayName("Set Cache Error Test")
    void setCacheErrorTest() {

        // Given
        final String errorProcess = "error in cache process";
        final String messageIdExpected = "test-message-id";
        final OrchestratorTransactionInformation orchestratorTransactionInformationExpected = OrchestratorTransactionInformation.builder()
            .messageId(messageIdExpected)
            .build();

        // When
        final CacheProcessResponse<OrchestratorTransactionInformation> instance = new CacheProcessResponse<>();
        instance.setCacheEntity(orchestratorTransactionInformationExpected);
        instance.setError(new Exception(errorProcess));

        // Then
        Assertions.assertNotNull(instance);
        Assertions.assertEquals(false, instance.isCachePresent());
        Assertions.assertEquals(true, instance.isError());
    }

    @Test
    @Order(3)
    @DisplayName("Set Cache Error Message Test")
    void setCacheErrorMessageTest() {

        // Given
        final String errorProcess = "error in cache process";
        final String messageIdExpected = "test-message-id";
        final OrchestratorTransactionInformation orchestratorTransactionInformationExpected = OrchestratorTransactionInformation.builder()
            .messageId(messageIdExpected)
            .build();

        // When
        final CacheProcessResponse<OrchestratorTransactionInformation> instance = new CacheProcessResponse<>();
        instance.setCacheEntity(orchestratorTransactionInformationExpected);
        instance.setError(errorProcess);

        // Then
        Assertions.assertNotNull(instance);
        Assertions.assertEquals(false, instance.isCachePresent());
        Assertions.assertEquals(true, instance.isError());
    }
}