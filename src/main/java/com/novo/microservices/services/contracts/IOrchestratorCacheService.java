package com.novo.microservices.services.contracts;

import com.novo.microservices.dtos.CacheProcessRequest;
import com.novo.microservices.dtos.CacheProcessResponse;
import com.novo.util.microservices.configurations.loader.components.exceptions.CacheProcessException;

/**
 * IOrchestratorCacheService
 * <p>
 * IOrchestratorCacheService interface.
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
public interface IOrchestratorCacheService {

    /***
     * method that persists a cached class or instance for a specific tenant and component identifier
     * @param cacheProcessRequest class that encapsulates the request to persist an object in cache
     * @param <T> the object time that is expected returns the cache
     * @return return cache process response {@link CacheProcessResponse} with the expected object in cache
     */
    <T> CacheProcessResponse<T> putDataInCache(CacheProcessRequest<T> cacheProcessRequest);

    /***
     * method that returns the data found in cache
     * @param cacheProcessRequest class that encapsulates the request to persist an object in cache
     * @param <T> the object time that is expected returns the cache
     * @return return cache process response {@link CacheProcessResponse} with the expected object in cache
     */
    <T> CacheProcessResponse<T> findDataFromCache(CacheProcessRequest<T> cacheProcessRequest);

    /***
     * delete all cache information in microservices
     * @throws CacheProcessException event that is triggered in case of an error
     */
    void deleteAllCaches() throws CacheProcessException;
}