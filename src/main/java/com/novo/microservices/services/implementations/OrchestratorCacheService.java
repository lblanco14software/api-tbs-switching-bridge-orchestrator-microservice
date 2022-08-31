package com.novo.microservices.services.implementations;

import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
import com.novo.microservices.components.enums.ProcessResult;
import com.novo.microservices.components.exceptions.OrchestratorException;
import com.novo.microservices.components.validations.ICacheRequestValidation;
import com.novo.microservices.dtos.CacheProcessRequest;
import com.novo.microservices.dtos.CacheProcessResponse;
import com.novo.microservices.services.contracts.IOrchestratorCacheService;
import com.novo.util.microservices.configurations.loader.constants.ExceptionsConstants;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

/**
 * OrchestratorCacheService
 * <p>
 * OrchestratorCacheService class.
 * <p>
 * <p>
 * ESTE COMPONENTE FUE CONSTRUIDO SIGUIENDO LOS ESTANDARES DE DESARROLLO Y EL PROCEDIMIENTO
 * DE DESARROLLO DE APLICACIONES DE NOVOPAYMENT Y SE ENCUENTRA PROTEGIDO POR LAS LEYES DE
 * PROPIEDAD INTELECTUAL Y DERECHOS DE AUTOR.
 *
 * @author Novopayment Inc.
 * @author lblanco@novopayment.com
 * @since 4/6/2022
 */
@Log4j2
@Service
public class OrchestratorCacheService implements IOrchestratorCacheService {

    public static final String MICROSERVICES_CACHE_ID = "microservices-orchestrator-transactions-cache";
    private final HazelcastInstance hazelcastInstance = Hazelcast.newHazelcastInstance();
    private final String orchestratorCacheServiceId;

    public OrchestratorCacheService() {
        this.orchestratorCacheServiceId = UUID.randomUUID().toString();
        log.debug("orchestratorCacheServiceId: {}", orchestratorCacheServiceId);
        log.debug("ComponentCacheManagerService loaded successfully");
    }

    /***
     * method that persists a cached class or instance for a specific tenant and component identifier
     * @param cacheProcessRequest class that encapsulates the request to persist an object in cache
     * @param <T> the object time that is expected returns the cache
     * @return return cache process response {@link CacheProcessResponse} with the expected object in cache
     */
    @Override
    public <T> CacheProcessResponse<T> putDataInCache(CacheProcessRequest<T> cacheProcessRequest) {

        log.debug("orchestratorCacheServiceId {}", orchestratorCacheServiceId);
        final CacheProcessResponse<T> cacheProcessResponse = new CacheProcessResponse<>();

        try {

            if (ProcessResult.PROCESS_SUCCESSFULLY_COMPLETED.equals(ICacheRequestValidation.validateCacheKey().and(ICacheRequestValidation.validateCacheEntity()).apply(cacheProcessRequest))) {
                final IMap<String, T> mapCacheMicroservices = hazelcastInstance.getMap(MICROSERVICES_CACHE_ID);
                mapCacheMicroservices.putIfAbsent(cacheProcessRequest.getCacheKey(), cacheProcessRequest.getCacheEntity());
                cacheProcessResponse.setCacheEntity(cacheProcessRequest.getCacheEntity());
                return cacheProcessResponse;
            }

            cacheProcessResponse.setError("the cache request is invalid");
            return cacheProcessResponse;
        } catch (Exception e) {
            log.error("error in process putDataInCache, error: {}", e.getMessage());
            cacheProcessResponse.setError(e);
            return cacheProcessResponse;
        }
    }

    /***
     * method that returns the data found in cache
     * @param cacheProcessRequest class that encapsulates the request to persist an object in cache
     * @param <T> the object time that is expected returns the cache
     * @return return cache process response {@link CacheProcessResponse} with the expected object in cache
     */
    @Override
    public <T> CacheProcessResponse<T> findDataFromCache(CacheProcessRequest<T> cacheProcessRequest) {

        log.debug("orchestratorCacheServiceId {}", orchestratorCacheServiceId);
        final CacheProcessResponse<T> cacheProcessResponse = new CacheProcessResponse<>();

        try {

            if (ProcessResult.PROCESS_SUCCESSFULLY_COMPLETED.equals(ICacheRequestValidation.validateCacheKey().apply(cacheProcessRequest))) {
                final IMap<String, T> mapCacheMicroservices = hazelcastInstance.getMap(MICROSERVICES_CACHE_ID);
                final T cacheInstance = mapCacheMicroservices.get(cacheProcessRequest.getCacheKey());
                if (Objects.nonNull(cacheInstance)) {
                    cacheProcessResponse.setCacheEntity(cacheInstance);
                } else {
                    cacheProcessResponse.setError("the instance is not present in cache manager");
                }
                return cacheProcessResponse;
            }

            cacheProcessResponse.setError("the cache request is invalid");
            return cacheProcessResponse;
        } catch (Exception e) {
            log.error("error in process findDataFromCache, error: {}", e.getMessage());
            cacheProcessResponse.setError(e);
            return cacheProcessResponse;
        }
    }

    /***
     * delete all cache information in microservices
     */
    @Override
    public void deleteAllCaches() {

        try {
            final IMap<String, ?> mapCacheMicroservices = hazelcastInstance.getMap(MICROSERVICES_CACHE_ID);
            mapCacheMicroservices.clear();
            log.info("all cache remove successfully in process deleteAllCaches");
        } catch (Exception e) {
            throw new OrchestratorException(ExceptionsConstants.ERROR_CACHE_DELETE_ALL_EXCEPTION_MESSAGE);
        }
    }
}