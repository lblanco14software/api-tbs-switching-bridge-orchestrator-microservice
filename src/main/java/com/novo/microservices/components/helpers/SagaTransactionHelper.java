package com.novo.microservices.components.helpers;

import lombok.experimental.UtilityClass;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;

import java.util.Locale;
import java.util.UUID;

import static com.novo.microservices.constants.ProcessConstants.*;

/**
 * SagaTransactionHelper
 * <p>
 * SagaTransactionHelper class.
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
@UtilityClass
public class SagaTransactionHelper {

    public static String getFirstTwoCharacters(final String currentValue) {
        return StringUtils.isNoneEmpty(currentValue) ? currentValue.substring(0, 2) : null;
    }

    public static String generateTrackingId() {
        final String trackingId = UUID.randomUUID().toString().replace("-", "");
        return truncateCharacters(trackingId, 32);
    }

    public static String generateIdByKey(final int characterTruncateLength) {
        final String id = UUID.randomUUID().toString().replace("-", "").toUpperCase(Locale.ENGLISH);
        return truncateCharacters(id, characterTruncateLength);
    }

    public static String truncateCharacters(final String currentValue, final int characterTruncateLength) {
        if (currentValue.length() > characterTruncateLength) {
            return currentValue.substring(0, characterTruncateLength);
        }
        return currentValue;
    }

    public static String processTrackingId(final String messageTypeIndicator, final String trackingId) {
        final String processMessageTypeIndicator = getFirstTwoCharacters(messageTypeIndicator);
        final String processTrackingId = SagaTransactionHelper.truncateCharacters(String.format("%s%s", processMessageTypeIndicator, trackingId), CHARACTER_TRUNCATE_32_LENGTH);
        log.debug("process processTrackingId: {}", processTrackingId);
        return processTrackingId;
    }

    public static int getOrchestratorTransactionTimeOut(String tenantParameterSetting) {

        try {
            if (tenantParameterSetting.equals(MICROSERVICES_CONFIG_NOT_FOUND)) {
                log.warn("assign the default configuration by transaction timeout, value: {} milliseconds", MICROSERVICES_ORCHESTRATOR_TRANSACTION_TIME_MILLISECONDS_DEFAULT_VALUE);
                return MICROSERVICES_ORCHESTRATOR_TRANSACTION_TIME_MILLISECONDS_DEFAULT_VALUE;
            }

            return Integer.parseInt(tenantParameterSetting);
        } catch (Exception e) {
            log.debug("error in getOrchestratorTransactionTimeOut method, error: {}", e.getMessage());
            log.warn("assign the default configuration by transaction timeout, value: {} milliseconds", MICROSERVICES_ORCHESTRATOR_TRANSACTION_TIME_MILLISECONDS_DEFAULT_VALUE);
            return MICROSERVICES_ORCHESTRATOR_TRANSACTION_TIME_MILLISECONDS_DEFAULT_VALUE;
        }
    }

    public static int getOrchestratorTransactionDelay(String tenantParameterSetting) {

        try {
            if (tenantParameterSetting.equals(MICROSERVICES_CONFIG_NOT_FOUND)) {
                log.warn("assign the default configuration by transaction delay, value: {} milliseconds", MICROSERVICES_ORCHESTRATOR_TRANSACTION_DELAY_MILLISECONDS_DEFAULT_VALUE);
                return MICROSERVICES_ORCHESTRATOR_TRANSACTION_DELAY_MILLISECONDS_DEFAULT_VALUE;
            }

            return Integer.parseInt(tenantParameterSetting);
        } catch (Exception e) {
            log.debug("error in getOrchestratorTransactionDelay method, error: {}", e.getMessage());
            log.warn("assign the default configuration by transaction delay, value: {} milliseconds", MICROSERVICES_ORCHESTRATOR_TRANSACTION_DELAY_MILLISECONDS_DEFAULT_VALUE);
            return MICROSERVICES_ORCHESTRATOR_TRANSACTION_DELAY_MILLISECONDS_DEFAULT_VALUE;
        }
    }
}