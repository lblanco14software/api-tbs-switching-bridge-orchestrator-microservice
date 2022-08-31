package com.novo.microservices.controllers.implementations;

import com.novo.microservices.components.helpers.ContextInformationService;
import com.novo.microservices.components.helpers.ControllerLoggerHelper;
import com.novo.microservices.components.helpers.DocumentationMicroservice;
import com.novo.microservices.dtos.base.BaseBusinessResponseDto;
import com.novo.microservices.dtos.base.BusinessResponse;
import com.novo.microservices.dtos.requests.OrchestrationGenerateKeyRequest;
import com.novo.microservices.events.outbounds.contracts.IOrchestratorGenerateKeyProducer;
import com.novo.microservices.services.contracts.*;
import com.novo.microservices.services.implementations.OrchestratorGenerateKeyService;
import com.novo.microservices.tbs.utils.components.validations.implementations.CommonTransactionValidation;
import com.novo.microservices.tbs.utils.dtos.responses.BusinessProcessResponse;
import com.novo.microservices.tbs.utils.events.outbounds.implementations.TransactionProducer;
import com.novo.microservices.tbs.utils.services.contracts.ISagaMongoDatabaseService;
import com.novo.microservices.tbs.utils.services.contracts.ISagaTransactionalBridgeService;
import com.novo.microservices.utils.common.context.AppSessionContext;
import com.novo.utils.messaging.components.helpers.EnvironmentUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import reactor.core.publisher.Mono;

import java.util.UUID;

import static com.novo.microservices.constants.BaseInterceptorConstants.HEADER_REQUEST_ID;
import static com.novo.microservices.constants.BaseInterceptorConstants.HEADER_TENANT_ID;
import static com.novo.microservices.constants.ProcessConstants.PROCESS_HSM_GENERATE_KEY_PATH;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

/**
 * OrchestratorGenerateKeyControllerTest
 * <p>
 * OrchestratorGenerateKeyControllerTest class.
 * <p>
 * <p>
 * ESTE COMPONENTE FUE CONSTRUIDO SIGUIENDO LOS ESTANDARES DE DESARROLLO Y EL PROCEDIMIENTO
 * DE DESARROLLO DE APLICACIONES DE NOVOPAYMENT Y SE ENCUENTRA PROTEGIDO POR LAS LEYES DE
 * PROPIEDAD INTELECTUAL Y DERECHOS DE AUTOR.
 *
 * @author Novopayment Inc.
 * @author jquiroga@novopayment.com
 * @since 5/19/2022
 */
@ExtendWith(SpringExtension.class)
@WebFluxTest(controllers = OrchestratorGenerateKeyController.class)
@Import({
    AppSessionContext.class,
    ContextInformationService.class,
    DocumentationMicroservice.class,
    TransactionProducer.class,
    EnvironmentUtils.class,
    CommonTransactionValidation.class,
    OrchestratorGenerateKeyService.class,
    BusinessResponse.class
})
@TestPropertySource(properties = {"spring.config.location=classpath:application.yml"})
@AutoConfigureWebTestClient(timeout = "20000")
class OrchestratorGenerateKeyControllerTest {

    public static final String TENANT_ID = "mx-yastas";

    @Autowired
    private WebTestClient webClient;

    @MockBean
    private IOrchestratorGenerateKeyService service;

    @Mock
    private IOrchestratorGenerateKeyProducer producer;

    @Mock
    private IOrchestratorHsmConfigurationService hsmGenerateKeyConfigurationService;

    @Mock
    private ISagaMongoDatabaseService sagaMongoDatabaseService;

    @Mock
    private ISagaTransactionalBridgeService sagaTransactionalBridgeService;

    @Mock
    private IOrchestratorClassificationService orchestratorClassificationService;

    @Mock
    private IOrchestratorStateService orchestratorStateService;

    @Mock
    private AppSessionContext appSessionContext;

    @Mock
    private IOrchestratorCrudService orchestratorCrudService;

    @Mock
    private IOrchestratorParameterService orchestratorParameterService;

    @Mock
    private IOrchestratorEncryptionService orchestratorEncryptionService;

    @MockBean
    private ControllerLoggerHelper controllerLoggerHelper;

    @Test
    void generateKeyWithoutPayload() {
        webClient.post()
            .uri(PROCESS_HSM_GENERATE_KEY_PATH)
            .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
            .header(HEADER_TENANT_ID, TENANT_ID)
            .header(HEADER_REQUEST_ID, UUID.randomUUID().toString())
            .exchange()
            .expectStatus().is4xxClientError();
    }

    @Test
    void generateKeyWithInvalidPayload() {
        OrchestrationGenerateKeyRequest generateKeyRequest = OrchestrationGenerateKeyRequest.builder().build();

        webClient.post()
            .uri(PROCESS_HSM_GENERATE_KEY_PATH)
            .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
            .header(HEADER_TENANT_ID, TENANT_ID)
            .header(HEADER_REQUEST_ID, UUID.randomUUID().toString())
            .body(BodyInserters.fromValue(generateKeyRequest))
            .exchange()
            .expectStatus().is4xxClientError()
            .expectBody(BaseBusinessResponseDto.class);
    }

    @Test
    void generateKeyOk() {
        OrchestrationGenerateKeyRequest generateKeyRequest = OrchestrationGenerateKeyRequest.builder()
            .trackingId("170220221645361300045263732415")
            .serialNumber("123456789")
            .build();

        when(service.doOnProcessGenerateKeyByRest(any())).thenReturn(Mono.just(BusinessProcessResponse.setEmptySuccessfullyResponse()));

        webClient.post()
            .uri(PROCESS_HSM_GENERATE_KEY_PATH)
            .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
            .header(HEADER_TENANT_ID, TENANT_ID)
            .header(HEADER_REQUEST_ID, UUID.randomUUID().toString())
            .body(BodyInserters.fromValue(generateKeyRequest))
            .exchange()
            .expectStatus().isOk()
            .expectBody(BaseBusinessResponseDto.class);
    }
}
