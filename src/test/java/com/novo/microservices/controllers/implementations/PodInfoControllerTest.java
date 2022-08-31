package com.novo.microservices.controllers.implementations;

import com.novo.microservices.MicroservicesTestConfiguration;
import com.novo.microservices.components.helpers.ContextInformationService;
import com.novo.microservices.components.helpers.DocumentationMicroservice;
import com.novo.microservices.dtos.base.BaseBusinessResponseDto;
import com.novo.microservices.dtos.base.BusinessResponse;
import com.novo.microservices.tbs.utils.controllers.implementations.PodInfoController;
import com.novo.microservices.utils.common.context.AppSessionContext;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;

import javax.json.bind.JsonbBuilder;

/**
 * PodInfoControllerTest
 * <p>
 *
 * <p>
 * ESTE COMPONENTE FUE CONSTRUIDO SIGUIENDO LOS ESTANDARES DE DESARROLLO Y EL PROCEDIMIENTO DE DESARROLLO DE
 * APLICACIONES DE NOVOPAYMENT Y SE ENCUENTRA PROTEGIDO POR LAS LEYES DE PROPIEDAD INTELECTUAL Y DERECHOS DE AUTOR.
 *
 * @author Novopayment Inc.
 * @author schancay@novopayment.com
 * @since 03/01/2022
 */
@ExtendWith(SpringExtension.class)
@WebFluxTest(controllers = PodInfoController.class)
@Import({
    MicroservicesTestConfiguration.class,
    DocumentationMicroservice.class,
    BusinessResponse.class,
    ContextInformationService.class,
    AppSessionContext.class
})
@SuppressFBWarnings(value = {"NP_NULL_ON_SOME_PATH_FROM_RETURN_VALUE"}, justification = "Testing")
class PodInfoControllerTest {

    private static final Logger LOGGER = LogManager.getLogger(PodInfoControllerTest.class);

    @Autowired
    private WebTestClient webClient;

    @Test
    void getPodInfo() {
        webClient.get()
            .uri("/podInfo")
            .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
            .exchange()
            .expectStatus().isOk()
            .expectBody(String.class)
            .consumeWith(response -> {
                var baseBusinessResponseDto = JsonbBuilder.create().fromJson(response.getResponseBody(), BaseBusinessResponseDto.class);

                Assertions.assertTrue(StringUtils.isNotEmpty(baseBusinessResponseDto.getMessageResponse()));
                Assertions.assertEquals("200.00.000", baseBusinessResponseDto.getMessageResponseCode());
                Assertions.assertNotNull(baseBusinessResponseDto.getDateTransaction());
            });
    }
}
