package com.novo.microservices.components.helpers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.novo.microservices.dtos.*;
import com.novo.microservices.dtos.requests.HsmGenerateKeyRequest;
import com.novo.microservices.services.contracts.IOrchestratorCacheService;
import com.novo.microservices.services.contracts.IOrchestratorParameterService;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static com.novo.microservices.constants.CacheKeysConstants.*;
import static com.novo.microservices.constants.ProcessConstants.*;

/**
 * SagaTransactionalJsonHelper
 * <p>
 * SagaTransactionalJsonHelper class.
 * <p>
 * <p>
 * ESTE COMPONENTE FUE CONSTRUIDO SIGUIENDO LOS ESTANDARES DE DESARROLLO Y EL PROCEDIMIENTO
 * DE DESARROLLO DE APLICACIONES DE NOVOPAYMENT Y SE ENCUENTRA PROTEGIDO POR LAS LEYES DE
 * PROPIEDAD INTELECTUAL Y DERECHOS DE AUTOR.
 *
 * @author Novopayment Inc.
 * @author lblanco@novopayment.com
 * @since 3/31/2022
 */
@Log4j2
@Component
@SuppressFBWarnings("SIC_INNER_SHOULD_BE_STATIC_ANON")
public class SagaTransactionalJsonHelper {
    private final IOrchestratorCacheService componentCacheManagerService;
    private final IOrchestratorParameterService orchestratorParameterService;
    private final ObjectMapper mapper;

    @Autowired
    public SagaTransactionalJsonHelper(IOrchestratorCacheService componentCacheManagerService, IOrchestratorParameterService orchestratorParameterService) {
        this.componentCacheManagerService = componentCacheManagerService;
        this.orchestratorParameterService = orchestratorParameterService;
        mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public List<OrchestratorTransactionClassification> loadTransactionClassification() {
        try {
            final TypeReference<List<OrchestratorTransactionClassification>> typeReference = new TypeReference<>() {};
            return loadMappings(ORCHESTRATOR_TRANSACTION_CLASSIFICATION_CACHE_KEY, MICROSERVICES_ORCHESTRATOR_TRANSACTIONS_CODES_PATH, typeReference);
        } catch (IOException e) {
            log.error("error in loadTransactionClassification process, error {}", e.getMessage());
            return new ArrayList<>();
        }
    }

    public List<HsmGenerateKeyRequest> loadHsmGenerateKeyParameters() {
        try {
            final TypeReference<List<HsmGenerateKeyRequest>> typeReference = new TypeReference<>() {};
            return loadMappings(ORCHESTRATOR_HSM_CACHE_KEY, MICROSERVICES_ORCHESTRATOR_HSM_CONFIGURATIONS_PATH, typeReference);
        } catch (IOException e) {
            log.error("error in loadHsmGenerateKeyParameters process, error {}", e.getMessage());
            return new ArrayList<>();
        }
    }

    public List<OrchestratorTransactionMapping> loadTransactionsMappings() {
        try {
            final TypeReference<List<OrchestratorTransactionMapping>> typeReference = new TypeReference<>() {};
            return loadMappings(ORCHESTRATOR_TRANSACTION_MAPPING_CACHE_KEY, MICROSERVICES_ORCHESTRATOR_TRANSACTIONS_MAPPINGS_PATH, typeReference);
        } catch (IOException e) {
            log.error("error in loadTransactionsMappings process, error {}", e.getMessage());
            return new ArrayList<>();
        }
    }

    public List<OrchestratorTransactionStructureDataMapping> loadTransactionsStructureDataMappings() {
        try {
            final TypeReference<List<OrchestratorTransactionStructureDataMapping>> typeReference = new TypeReference<>() {};
            return loadMappings(ORCHESTRATOR_TRANSACTION_STRUCTURE_DATA_MAPPING_CACHE_KEY, MICROSERVICES_ORCHESTRATOR_TRANSACTIONS_STRUCTURE_DATA_MAPPINGS_PATH, typeReference);
        } catch (IOException e) {
            log.error("error in loadTransactionsStructureDataMappings process, error {}", e.getMessage());
            return new ArrayList<>();
        }
    }

    private <T> List<T> loadMappings(String key, String nameReference, TypeReference<List<T>> typeReference) throws IOException {
        CacheProcessResponse<List<T>> cacheProcessResponse = getCacheByKey(key);

        boolean cacheIsNotPresent = !Boolean.TRUE.equals(cacheProcessResponse.isCachePresent());
        if (cacheIsNotPresent) {
            String path = orchestratorParameterService.findTenantParameterSetting(nameReference);
            List<T> list = map(path, typeReference);
            cacheProcessResponse = saveCache(key, list);
        }

        return cacheProcessResponse.getCacheEntity();
    }

    private <T> List<T> map(String path, TypeReference<List<T>> typeReference) throws IOException {
        try (InputStream inputStream = new FileInputStream(path)) {
            log.info("Loading configuration from {}", path);
            return mapper.readValue(inputStream, typeReference);
        } catch (IOException e) {
            log.error("Error loading configuration file: {}, error: {}", path, e.getMessage());
            throw e;
        }
    }

    private <T> CacheProcessResponse<List<T>> getCacheByKey(String key) {
        CacheProcessRequest<List<T>> cacheProcessRequest = new CacheProcessRequest<>();
        cacheProcessRequest.setCacheKey(key);

        return componentCacheManagerService.findDataFromCache(cacheProcessRequest);
    }

    private <T> CacheProcessResponse<List<T>> saveCache(String key, List<T> list) {
        CacheProcessRequest<List<T>> cacheProcessRequest = new CacheProcessRequest<>();
        cacheProcessRequest.setCacheKey(key);
        cacheProcessRequest.setCacheEntity(list);

        return componentCacheManagerService.putDataInCache(cacheProcessRequest);
    }
}