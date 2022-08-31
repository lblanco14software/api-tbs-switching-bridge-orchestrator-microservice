package com.novo.microservices.components.helpers;

import com.novo.microservices.components.enums.ResponseCode;
import com.novo.microservices.dtos.OrchestratorTransactionClassification;
import com.novo.microservices.dtos.base.BusinessResponse;
import com.novo.microservices.mocks.OrchestrationTransactionRequestFixture;
import com.novo.microservices.mocks.OrchestratorTransactionClassificationFixture;
import com.novo.microservices.services.contracts.IOrchestratorClassificationService;
import com.novo.microservices.tbs.utils.components.exceptions.BusinessProcessException;
import com.novo.microservices.tbs.utils.components.validations.implementations.CommonTransactionValidation;
import com.novo.microservices.tbs.utils.constants.LoggerConstants;
import com.novo.microservices.tbs.utils.events.outbounds.implementations.TransactionProducer;
import org.apache.logging.log4j.ThreadContext;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import reactor.core.publisher.Mono;

import static com.novo.microservices.constants.ProcessConstants.ORCHESTRATOR_UNIDENTIFIED_TRANSACTION_METHOD_CONTEXT;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

/**
 * ControllerLoggerHelperTest
 * <p>
 * ControllerLoggerHelperTest class.
 * <p>
 * <p>
 * ESTE COMPONENTE FUE CONSTRUIDO SIGUIENDO LOS ESTANDARES DE DESARROLLO Y EL PROCEDIMIENTO
 * DE DESARROLLO DE APLICACIONES DE NOVOPAYMENT Y SE ENCUENTRA PROTEGIDO POR LAS LEYES DE
 * PROPIEDAD INTELECTUAL Y DERECHOS DE AUTOR.
 *
 * @author Novopayment Inc.
 * @author jquiroga@novopayment.com
 * @since 11/7/2022
 */
@SpringBootTest(classes = ControllerLoggerHelper.class)
@ExtendWith(MockitoExtension.class)
@ContextConfiguration(classes = {
    ControllerLoggerHelper.class,
    TransactionProducer.class,
    BusinessResponse.class,
    ContextInformationService.class,
    CommonTransactionValidation.class
})
@TestPropertySource(properties = {"spring.config.location=classpath:application.yml"})
class ControllerLoggerHelperTest {
    @Autowired
    private ControllerLoggerHelper controllerLoggerHelper;

    @MockBean
    private IOrchestratorClassificationService service;

    @BeforeEach
    void setup() {
        ThreadContext.clearAll();
    }

    @Test
    void findClassificationWhenNotExists() {
        when(service.doOnFindTransactionClassification(any())).thenReturn(Mono.error(() -> new BusinessProcessException("error in findTransactionClassification process", ResponseCode.ERROR_INVALID_PARAMETERS)));
        controllerLoggerHelper.putTransactionClassificationInThreadContext(new OrchestrationTransactionRequestFixture().getInstance());

        Assertions.assertEquals(ORCHESTRATOR_UNIDENTIFIED_TRANSACTION_METHOD_CONTEXT, ThreadContext.get(LoggerConstants.CONSTANTS_LOG_METHOD));
    }

    @Test
    void findClassificationSuccess() {
        OrchestratorTransactionClassification transactionClassification = new OrchestratorTransactionClassificationFixture().getInstance();
        when(service.doOnFindTransactionClassification(any())).thenReturn(Mono.just(transactionClassification));
        controllerLoggerHelper.putTransactionClassificationInThreadContext(new OrchestrationTransactionRequestFixture().getInstance());

        Assertions.assertEquals(transactionClassification.getTransactionStateCode(), ThreadContext.get(LoggerConstants.CONSTANTS_LOG_METHOD));
    }
}
