package com.novo.microservices.transactions.enums;

import lombok.Getter;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

import static com.novo.microservices.transactions.constants.OrchestratorCustomResponsesCodes.*;

/**
 * CustomTransactionResponses
 * <p>
 * CustomTransactionResponses enum.
 * <p>
 * <p>
 * ESTE COMPONENTE FUE CONSTRUIDO SIGUIENDO LOS ESTANDARES DE DESARROLLO Y EL PROCEDIMIENTO
 * DE DESARROLLO DE APLICACIONES DE NOVOPAYMENT Y SE ENCUENTRA PROTEGIDO POR LAS LEYES DE
 * PROPIEDAD INTELECTUAL Y DERECHOS DE AUTOR.
 *
 * @author Novopayment Inc.
 * @author lblanco@novopayment.com
 * @since 3/30/2022
 */
@Getter
public enum CustomTransactionResponses {

    CUSTOM_RESPONSE_STATUS_COMPLETED(CUSTOM_RESPONSE_STATUS_COMPLETED_CODE, CUSTOM_RESPONSE_STATUS_COMPLETED_DESCRIPTION),
    CUSTOM_RESPONSE_STATUS_DECLINED(CUSTOM_RESPONSE_STATUS_DECLINED_CODE, CUSTOM_RESPONSE_STATUS_DECLINED_DESCRIPTION);

    private static final Map<String, CustomTransactionResponses> mapCustomTransactionResponses = new HashMap<>();

    static {
        for (final CustomTransactionResponses customTransactionResponses : EnumSet.allOf(CustomTransactionResponses.class)) {
            mapCustomTransactionResponses.put(customTransactionResponses.getCustomResponseCode(), customTransactionResponses);
        }
    }

    private final String customResponseCode;
    private final String customResponseDescription;

    CustomTransactionResponses(String customResponseCode, String customResponseDescription) {
        this.customResponseCode = customResponseCode;
        this.customResponseDescription = customResponseDescription;
    }

    @Override
    public String toString() {
        return customResponseCode;
    }

    public static CustomTransactionResponses findByCustomResponseCode(final String customResponseCode) {
        return mapCustomTransactionResponses.get(customResponseCode);
    }
}
