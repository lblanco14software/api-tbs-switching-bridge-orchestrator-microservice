package com.novo.microservices.constants;

import lombok.experimental.UtilityClass;

/**
 * CacheKeysConstants
 * <p>
 * CacheKeysConstants class.
 * <p>
 * ESTE COMPONENTE FUE CONSTRUIDO SIGUIENDO LOS ESTANDARES DE DESARROLLO Y EL PROCEDIMIENTO DE DESARROLLO DE
 * APLICACIONES DE NOVOPAYMENT Y SE ENCUENTRA PROTEGIDO POR LAS LEYES DE PROPIEDAD INTELECTUAL Y DERECHOS DE AUTOR.
 *
 * @author Novopayment Inc.
 * @author lblanco@novopayment.com
 * @since 6/27/2020
 */
@UtilityClass
public class CacheKeysConstants {
    public static final String ORCHESTRATOR_TRANSACTION_CLASSIFICATION_CACHE_KEY = "orchestrator.transaction.classification.cache.key";
    public static final String ORCHESTRATOR_HSM_CACHE_KEY = "orchestrator.hsm.cache.key";
    public static final String ORCHESTRATOR_TRANSACTION_MAPPING_CACHE_KEY = "orchestrator.transaction.mapping.cache.key";
    public static final String ORCHESTRATOR_TRANSACTION_STRUCTURE_DATA_MAPPING_CACHE_KEY = "orchestrator.transaction.structure.data.mapping.cache.key";
}
