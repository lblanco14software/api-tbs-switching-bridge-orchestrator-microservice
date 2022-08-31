package com.novo.microservices.components.validations;

import com.novo.microservices.components.enums.ProcessResult;
import com.novo.microservices.dtos.CacheProcessRequest;
import com.novo.microservices.tbs.utils.components.validations.ISagaControlHistoryValidation;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Objects;
import java.util.function.Function;

/**
 * ISagaTransactionResponseValidation
 * <p>
 * ISagaTransactionResponseValidation interface.
 *
 * <p>
 * ESTE COMPONENTE FUE CONSTRUIDO SIGUIENDO LOS ESTANDARES DE DESARROLLO Y EL PROCEDIMIENTO DE DESARROLLO DE
 * APLICACIONES DE NOVOPAYMENT Y SE ENCUENTRA PROTEGIDO POR LAS LEYES DE PROPIEDAD INTELECTUAL Y DERECHOS DE AUTOR.
 *
 * @author Novopayment Inc.
 * @author lblanco@novopayment.com
 * @since 05/02/2021
 */

@FunctionalInterface
public interface ICacheRequestValidation extends Function<CacheProcessRequest<?>, ProcessResult> {

    Logger logger = LogManager.getLogger(ICacheRequestValidation.class);

    /***
     * validate main field request
     * @return {@link ICacheRequestValidation} with result of field validation
     */
    static ICacheRequestValidation validateCacheKey() {

        return cacheProcessRequest -> {

            if (Objects.isNull(cacheProcessRequest)) {
                logger.error("cacheProcessRequest is null");
                return ProcessResult.PROCESS_FAILED;
            }

            if (StringUtils.isEmpty(cacheProcessRequest.getCacheKey())) {
                logger.error("the cache key is invalid");
                return ProcessResult.PROCESS_FAILED;
            }

            return ProcessResult.PROCESS_SUCCESSFULLY_COMPLETED;
        };
    }

    /***
     * validate main field request
     * @return {@link ICacheRequestValidation} with result of field validation
     */
    static ICacheRequestValidation validateCacheEntity() {

        return cacheProcessRequest -> {

            if (Objects.isNull(cacheProcessRequest)) {
                logger.error("cacheProcessRequest is null");
                return ProcessResult.PROCESS_FAILED;
            }

            if (Objects.isNull(cacheProcessRequest.getCacheEntity())) {
                logger.error("cacheEntity is null");
                return ProcessResult.PROCESS_FAILED;
            }

            return ProcessResult.PROCESS_SUCCESSFULLY_COMPLETED;
        };
    }

    /***
     * method concat validations with and condition generates a short circuit if any condition is invalid
     * @param otherValidation other validation to concat in process
     * @return {@link ISagaControlHistoryValidation} with result of field validation
     */
    default ICacheRequestValidation and(ICacheRequestValidation otherValidation) {

        return cacheProcessRequest -> {
            final ProcessResult processResult = this.apply(cacheProcessRequest);
            return ProcessResult.PROCESS_SUCCESSFULLY_COMPLETED.equals(processResult) ? otherValidation.apply(cacheProcessRequest) : processResult;
        };
    }
}