package com.novo.microservices.components.validations;

import com.novo.microservices.components.enums.ProcessResult;
import com.novo.microservices.dtos.CacheProcessRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * CacheRequestValidationTest
 * <p>
 * CacheRequestValidationTest class.
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
public class CacheRequestValidationTest {

    static final String CACHE_KEY = "demo-cache-key";
    static final String expectedValue = "02";

    @Test
    @Order(1)
    @DisplayName("Validate CacheKey Test")
    void validateCacheKeyTest() {

        // Given
        final CacheProcessRequest<String> cacheProcessRequest = new CacheProcessRequest<>();
        cacheProcessRequest.setCacheKey(CACHE_KEY);
        cacheProcessRequest.setCacheEntity(expectedValue);
        ProcessResult processResultExpected = ProcessResult.PROCESS_SUCCESSFULLY_COMPLETED;


        // When
        final ProcessResult instance = ICacheRequestValidation.validateCacheKey().apply(cacheProcessRequest);

        // Then
        Assertions.assertNotNull(instance);
        Assertions.assertEquals(processResultExpected, instance);
    }

    @Test
    @Order(2)
    @DisplayName("Validate Cache Entity Test")
    void validateCacheEntityTest() {

        // Given
        final CacheProcessRequest<String> cacheProcessRequest = new CacheProcessRequest<>();
        cacheProcessRequest.setCacheKey(CACHE_KEY);
        cacheProcessRequest.setCacheEntity(expectedValue);
        ProcessResult processResultExpected = ProcessResult.PROCESS_SUCCESSFULLY_COMPLETED;


        // When
        final ProcessResult instance = ICacheRequestValidation.validateCacheEntity().apply(cacheProcessRequest);

        // Then
        Assertions.assertNotNull(instance);
        Assertions.assertEquals(processResultExpected, instance);
    }

    @Test
    @Order(3)
    @DisplayName("Validate Cache Entity and Key Test")
    void validateCacheEntityAndCacheKeyTest() {

        // Given
        final CacheProcessRequest<String> cacheProcessRequest = new CacheProcessRequest<>();
        cacheProcessRequest.setCacheKey(CACHE_KEY);
        cacheProcessRequest.setCacheEntity(expectedValue);
        ProcessResult processResultExpected = ProcessResult.PROCESS_SUCCESSFULLY_COMPLETED;


        // When
        final ProcessResult instance = ICacheRequestValidation.validateCacheEntity()
            .and(ICacheRequestValidation.validateCacheEntity())
            .apply(cacheProcessRequest);

        // Then
        Assertions.assertNotNull(instance);
        Assertions.assertEquals(processResultExpected, instance);
    }

    @Test
    @Order(4)
    @DisplayName("Validate CacheKey Error Test")
    void validateCacheKeyErrorTest() {

        // Given
        final CacheProcessRequest<String> cacheProcessRequest = new CacheProcessRequest<>();
        cacheProcessRequest.setCacheEntity(expectedValue);
        ProcessResult processResultExpected = ProcessResult.PROCESS_FAILED;


        // When
        final ProcessResult instance = ICacheRequestValidation.validateCacheKey().apply(cacheProcessRequest);

        // Then
        Assertions.assertNotNull(instance);
        Assertions.assertEquals(processResultExpected, instance);
    }

    @Test
    @Order(5)
    @DisplayName("Validate Cache Entity Error Test")
    void validateCacheEntityErrorTest() {

        // Given
        final CacheProcessRequest<String> cacheProcessRequest = new CacheProcessRequest<>();
        cacheProcessRequest.setCacheKey(CACHE_KEY);
        ProcessResult processResultExpected = ProcessResult.PROCESS_FAILED;


        // When
        final ProcessResult instance = ICacheRequestValidation.validateCacheEntity().apply(cacheProcessRequest);

        // Then
        Assertions.assertNotNull(instance);
        Assertions.assertEquals(processResultExpected, instance);
    }
}