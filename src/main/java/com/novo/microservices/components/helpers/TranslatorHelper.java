package com.novo.microservices.components.helpers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * TranslatorHelper
 * <p>
 * TranslatorHelper class.
 * <p>
 * ESTE COMPONENTE FUE CONSTRUIDO SIGUIENDO LOS ESTANDARES DE DESARROLLO Y EL PROCEDIMIENTO
 * DE DESARROLLO DE APLICACIONES DE NOVOPAYMENT Y SE ENCUENTRA PROTEGIDO POR LAS LEYES DE
 * PROPIEDAD INTELECTUAL Y DERECHOS DE AUTOR.
 *
 * @author Novopayment Inc.
 * @author schancay@novopayment.com
 * @since 11/16/2020
 */
@Component
public class TranslatorHelper {
    private static final Logger LOGGER = LogManager.getLogger(TranslatorHelper.class);

    public String toLocale(String messageCode) {
        Locale locale = LocaleContextHolder.getLocale();

        try {
            ResourceBundle words = ResourceBundle.getBundle("i18n/messages", locale);
            return words.getString(messageCode);
        } catch (Exception e) {
            LOGGER.warn("Key [{}] not internationalized on Locale [{}]", messageCode, locale);
            return null;
        }
    }
}
