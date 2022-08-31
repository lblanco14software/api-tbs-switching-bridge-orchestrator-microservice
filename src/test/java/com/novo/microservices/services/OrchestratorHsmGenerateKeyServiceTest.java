package com.novo.microservices.services;

import com.novo.microservices.components.helpers.ContextInformationService;
import com.novo.microservices.components.helpers.SagaTransactionalJsonHelper;
import com.novo.microservices.dtos.base.BusinessResponse;
import com.novo.microservices.dtos.requests.HsmGenerateKeyRequest;
import com.novo.microservices.services.implementations.OrchestratorHsmConfigurationService;
import com.novo.microservices.tbs.utils.components.exceptions.BusinessProcessException;
import com.novo.microservices.tbs.utils.components.validations.implementations.CommonTransactionValidation;
import com.novo.microservices.tbs.utils.events.outbounds.implementations.TransactionProducer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockserver.junit.jupiter.MockServerExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

/**
 * OrchestratorHsmGenerateKeyConfigurationServiceTest
 * <p>
 * OrchestratorHsmGenerateKeyConfigurationServiceTest class.
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
@SpringBootTest(classes = OrchestratorHsmConfigurationService.class)
@ExtendWith({SpringExtension.class, MockServerExtension.class})
@ContextConfiguration(classes = {
    OrchestratorHsmConfigurationService.class,
    TransactionProducer.class,
    BusinessResponse.class,
    ContextInformationService.class,
    CommonTransactionValidation.class
})
@TestPropertySource(properties = {"spring.config.location=classpath:application.yml"})
class OrchestratorHsmGenerateKeyServiceTest {

    @Autowired
    private OrchestratorHsmConfigurationService service;
    @MockBean
    private SagaTransactionalJsonHelper sagaTransactionalJsonHelper;


    @Test
    void findByHeaderAndCommandOk() {
        HsmGenerateKeyRequest entity = HsmGenerateKeyRequest.builder()
            .header("example")
            .command("example")
            .build();
        when(sagaTransactionalJsonHelper.loadHsmGenerateKeyParameters()).thenReturn(List.of(entity));

        StepVerifier.create(service.doOnFindByHeaderAndCommand("example", "example"))
            .assertNext(event -> Mono.just(entity))
            .verifyComplete();
    }

    @Test
    void findByHeaderAndCommandOkWithOptionalEmpty() {
        when(sagaTransactionalJsonHelper.loadHsmGenerateKeyParameters()).thenReturn(new ArrayList<>());

        StepVerifier.create(service.doOnFindByHeaderAndCommand("", ""))
            .expectError(BusinessProcessException.class)
            .verify();
    }
}
