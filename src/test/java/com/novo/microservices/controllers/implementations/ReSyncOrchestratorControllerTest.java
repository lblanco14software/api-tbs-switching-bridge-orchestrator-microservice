package com.novo.microservices.controllers.implementations;

import com.novo.microservices.components.helpers.ContextInformationService;
import com.novo.microservices.components.helpers.ControllerLoggerHelper;
import com.novo.microservices.components.helpers.DocumentationMicroservice;
import com.novo.microservices.components.helpers.SagaTransactionalJsonHelper;
import com.novo.microservices.dtos.base.BaseBusinessResponseDto;
import com.novo.microservices.dtos.base.BusinessResponse;
import com.novo.microservices.services.contracts.IOrchestratorCacheService;
import com.novo.microservices.utils.common.context.AppSessionContext;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import javax.json.bind.JsonbBuilder;

import static org.mockito.Mockito.when;

/**
 * ReSyncOrchestratorControllerTest
 * <p>
 * ReSyncOrchestratorControllerTest class.
 * <p>
 * ESTE COMPONENTE FUE CONSTRUIDO SIGUIENDO LOS ESTANDARES DE DESARROLLO Y EL PROCEDIMIENTO DE DESARROLLO DE
 * APLICACIONES DE NOVOPAYMENT Y SE ENCUENTRA PROTEGIDO POR LAS LEYES DE PROPIEDAD INTELECTUAL Y DERECHOS DE AUTOR.
 *
 * @author Novopayment Inc.
 * @author jquiroga@novopayment.com
 * @since 6/27/2022
 */
@Log4j2
@ExtendWith(SpringExtension.class)
@WebFluxTest(controllers = ReSyncOrchestratorController.class)
@Import({
    DocumentationMicroservice.class,
    BusinessResponse.class,
    ContextInformationService.class,
    AppSessionContext.class
})
class ReSyncOrchestratorControllerTest {
    @Autowired
    private WebTestClient webClient;

    @MockBean
    IOrchestratorCacheService componentCacheManagerService;

    @MockBean
    SagaTransactionalJsonHelper sagaTransactionalJsonHelper;

    @MockBean
    ControllerLoggerHelper controllerLoggerHelper;

    @Test
    void resync() {

        webClient.delete()
            .uri("/orchestrator/resync")
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
