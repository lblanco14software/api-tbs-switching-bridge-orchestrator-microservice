package com.novo.microservices.services;

import com.novo.microservices.dtos.CacheProcessRequest;
import com.novo.microservices.dtos.CacheProcessResponse;
import com.novo.microservices.dtos.OrchestratorTransactionInformation;
import com.novo.microservices.services.implementations.OrchestratorCacheService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * ComponentCacheManagerServiceTest
 * <p>
 * ComponentCacheManagerServiceTest class.
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
public class OrchestratorCacheServiceTest {

    @Autowired
    OrchestratorCacheService componentCacheManagerService;

    @Test
    @Order(1)
    @DisplayName("Put Data In Cache Test")
    void putDataInCacheTest() {

        // Given
        final String messageIdExpected = "test-message-id";
        final OrchestratorTransactionInformation orchestratorTransactionInformationExpected = OrchestratorTransactionInformation.builder()
            .messageId(messageIdExpected)
            .build();

        final CacheProcessRequest<OrchestratorTransactionInformation> cacheProcessRequest = new CacheProcessRequest<>();
        cacheProcessRequest.setCacheKey("cacheKey");
        cacheProcessRequest.setCacheEntity(orchestratorTransactionInformationExpected);


        // When
        final CacheProcessResponse<OrchestratorTransactionInformation> instance = componentCacheManagerService.putDataInCache(cacheProcessRequest);

        // Then
        Assertions.assertNotNull(instance);
        Assertions.assertNotNull(instance.getCacheEntity());
        Assertions.assertEquals(true, instance.isCachePresent());
        Assertions.assertEquals(false, instance.isError());
        Assertions.assertEquals(messageIdExpected, instance.getCacheEntity().getMessageId());
    }

    @Test
    @Order(2)
    @DisplayName("Find Data From Cache Test")
    void findDataFromCacheTest() {

        // Given
        final String messageIdExpected = "test-message-id";
        final OrchestratorTransactionInformation orchestratorTransactionInformationExpected = OrchestratorTransactionInformation.builder()
            .messageId(messageIdExpected)
            .build();
        final CacheProcessRequest<OrchestratorTransactionInformation> cacheProcessRequest = new CacheProcessRequest<>();
        cacheProcessRequest.setCacheKey("cacheKey");
        cacheProcessRequest.setCacheEntity(orchestratorTransactionInformationExpected);
        final CacheProcessResponse<OrchestratorTransactionInformation> cacheProcessResponse = componentCacheManagerService.putDataInCache(cacheProcessRequest);

        // When
        final CacheProcessResponse<OrchestratorTransactionInformation> instance = componentCacheManagerService.findDataFromCache(cacheProcessRequest);

        // Then
        Assertions.assertNotNull(instance);
        Assertions.assertNotNull(instance.getCacheEntity());
        Assertions.assertEquals(true, instance.isCachePresent());
        Assertions.assertEquals(false, instance.isError());
        Assertions.assertEquals(messageIdExpected, instance.getCacheEntity().getMessageId());
    }

    @Test
    @Order(3)
    @DisplayName("Delete All Caches Test")
    void deleteAllCaches() {

        // Given
        final String messageIdExpected = "test-message-id";
        final OrchestratorTransactionInformation orchestratorTransactionInformationExpected = OrchestratorTransactionInformation.builder()
            .messageId(messageIdExpected)
            .build();

        final CacheProcessRequest<OrchestratorTransactionInformation> cacheProcessRequest = new CacheProcessRequest<>();
        cacheProcessRequest.setCacheKey("cacheKey");
        cacheProcessRequest.setCacheEntity(orchestratorTransactionInformationExpected);


        // When
        componentCacheManagerService.deleteAllCaches();
        final CacheProcessResponse<OrchestratorTransactionInformation> instance = componentCacheManagerService.findDataFromCache(cacheProcessRequest);

        // Then
        Assertions.assertNull(instance.getCacheEntity());
        Assertions.assertEquals(false, instance.isCachePresent());
        Assertions.assertEquals(true, instance.isError());
    }

    @Test
    @Order(1)
    @DisplayName("Put Data In Cache Error # 1 Test")
    void putDataInCacheErrorTest() {

        // Given
        // When
        final CacheProcessResponse<OrchestratorTransactionInformation> instance = componentCacheManagerService.putDataInCache(null);

        // Then
        Assertions.assertNotNull(instance);
        Assertions.assertNull(instance.getCacheEntity());
        Assertions.assertEquals(true, instance.isError());
    }
}