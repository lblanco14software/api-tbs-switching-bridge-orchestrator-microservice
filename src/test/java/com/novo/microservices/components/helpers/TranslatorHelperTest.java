package com.novo.microservices.components.helpers;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * TranslatorHelperTest
 * <p>
 * TranslatorHelperTest class.
 * <p>
 * <p>
 * ESTE COMPONENTE FUE CONSTRUIDO SIGUIENDO LOS ESTANDARES DE DESARROLLO Y EL PROCEDIMIENTO
 * DE DESARROLLO DE APLICACIONES DE NOVOPAYMENT Y SE ENCUENTRA PROTEGIDO POR LAS LEYES DE
 * PROPIEDAD INTELECTUAL Y DERECHOS DE AUTOR.
 *
 * @author Novopayment Inc.
 * @author lblanco@novopayment.com
 * @since 4/20/2022
 */
@SpringBootTest
@AutoConfigureMockMvc
public class TranslatorHelperTest {

    @Autowired
    TranslatorHelper translatorHelper;

    @Test
    @Order(1)
    @DisplayName("Build Create Saga Transactional Request Test")
    void toLocaleTest() {

        // Given
        final String messageCode = "100";

        // When
        final String instance = translatorHelper.toLocale(messageCode);

        // Then
        Assertions.assertNotNull(instance);
    }
}