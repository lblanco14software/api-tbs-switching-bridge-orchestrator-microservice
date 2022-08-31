package com.novo.microservices.components.helpers;

import com.novo.microservices.dtos.requests.OrchestrationTransactionRequest;
import lombok.experimental.UtilityClass;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import postilion.realtime.base24.TokenData;
import postilion.realtime.sdk.message.stream.XStreamBase;

import java.nio.charset.StandardCharsets;

/**
 * ConfirmationPinHelper
 * <p>
 * ConfirmationPinHelper class.
 * <p>
 * <p>
 * ESTE COMPONENTE FUE CONSTRUIDO SIGUIENDO LOS ESTANDARES DE DESARROLLO Y EL PROCEDIMIENTO
 * DE DESARROLLO DE APLICACIONES DE NOVOPAYMENT Y SE ENCUENTRA PROTEGIDO POR LAS LEYES DE
 * PROPIEDAD INTELECTUAL Y DERECHOS DE AUTOR.
 *
 * @author Novopayment Inc.
 * @author duhernandez@novopayment.com
 * @since 5/7/22
 */
@Log4j2
@UtilityClass
public class ConfirmationPinHelper {
    private static final String SECURE_PIN = "01";
    private static final String TOKEN_06 = "06";

    public static String getConfirmationPin(OrchestrationTransactionRequest currentOrchestrationTransactionRequest) {
        var dataElement53 = String.format("%s%s%s",
            SECURE_PIN,
            getNewPinFragment(currentOrchestrationTransactionRequest.getTransaction().getDe63()),
            StringUtils.trimToEmpty(currentOrchestrationTransactionRequest.getTransaction().getDe55())); // KSN

        return StringUtils.rightPad(dataElement53, 96, "0");
    }

    private static String getNewPinFragment(String tokenField) {
        TokenData tokenData = new TokenData();
        try {
            tokenData.fromMsg(StringUtils.trimToEmpty(tokenField).getBytes(StandardCharsets.UTF_8), 0);
            // NEW-PIN-FRMT (LENGTH 16), NEW-PIN-CONF (LENGTH 16)
            String tokenBody = tokenData.getTokenData(TOKEN_06);
            // NEW-PIN-FRMT
            return StringUtils.trimToEmpty(StringUtils.substring(tokenBody, 0, 16));
        }  catch (XStreamBase ex) {
            log.error("Error getting token '{}' from '{}', error: {}", TOKEN_06, tokenField, ex.getMessage());
            return "";
        }
    }
}