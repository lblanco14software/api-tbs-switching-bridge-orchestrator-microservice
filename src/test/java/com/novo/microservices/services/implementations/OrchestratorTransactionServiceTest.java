package com.novo.microservices.services.implementations;

import com.novo.microservices.components.configurations.MessageConfiguration;
import com.novo.microservices.components.helpers.ContextInformationService;
import com.novo.microservices.components.helpers.SagaTransactionalJsonHelper;
import com.novo.microservices.components.helpers.SwitchKeyHelper;
import com.novo.microservices.dtos.*;
import com.novo.microservices.dtos.base.BusinessResponse;
import com.novo.microservices.dtos.custom.CustomPaymentInformation;
import com.novo.microservices.dtos.generics.GenericBusinessResponse;
import com.novo.microservices.dtos.requests.HsmGenerateKeyRequest;
import com.novo.microservices.dtos.requests.OrchestrationTransactionRequest;
import com.novo.microservices.dtos.responses.HsmGenerateKeyResponse;
import com.novo.microservices.dtos.responses.OrchestrationTransactionResponse;
import com.novo.microservices.events.outbounds.contracts.IOrchestratorTransactionProducer;
import com.novo.microservices.mocks.CustomTransactionInformationRequestFixture;
import com.novo.microservices.mocks.PaymentHeaderInformationFixture;
import com.novo.microservices.mocks.StandardTransactionDefaultValuesMapperMockImpl;
import com.novo.microservices.services.contracts.*;
import com.novo.microservices.tbs.utils.components.validations.implementations.CommonTransactionValidation;
import com.novo.microservices.tbs.utils.dtos.SagaControl;
import com.novo.microservices.tbs.utils.dtos.SagaOutBox;
import com.novo.microservices.tbs.utils.dtos.StandardTransaction;
import com.novo.microservices.tbs.utils.dtos.TransactionMessage;
import com.novo.microservices.tbs.utils.dtos.responses.BusinessProcessResponse;
import com.novo.microservices.tbs.utils.dtos.responses.CreateSagaTransactionalResponse;
import com.novo.microservices.tbs.utils.dtos.responses.CreateSagaTransactionalStateResponse;
import com.novo.microservices.tbs.utils.dtos.responses.SagaTransactionalStateResponse;
import com.novo.microservices.tbs.utils.events.outbounds.implementations.TransactionProducer;
import com.novo.microservices.tbs.utils.services.contracts.ISagaMongoDatabaseService;
import com.novo.microservices.tbs.utils.services.contracts.ISagaTransactionalBridgeService;
import com.novo.microservices.transactions.contracts.IOrchestratorTransaction;
import com.novo.microservices.transactions.enums.CustomTransactionResponses;
import com.novo.microservices.transactions.factories.OrchestratorTransactionFactory;
import com.novo.microservices.transactions.implementations.CustomerInfoTransaction;
import com.novo.microservices.transactions.mappers.contracts.*;
import com.novo.microservices.transactions.mappers.implementations.CustomTransactionCommonMapper;
import com.novo.microservices.transactions.mappers.implementations.StandardTransactionCommonDefaultValuesMapper;
import com.novo.microservices.transactions.mappers.implementations.StandardTransactionCommonMapper;
import com.novo.microservices.transactions.mappers.implementations.StandardTransactionStructureDataMapper;
import com.novo.microservices.transactions.validations.contracts.IStandardTransactionValidation;
import com.novo.microservices.transactions.validations.implementations.StandardTransactionValidation;
import com.novo.microservices.utils.common.context.AppSessionContext;
import com.novo.microservices.utils.common.dtos.ApplicationSession;
import com.novo.utils.messaging.dtos.Event;
import com.novo.utils.security.abstractions.AlgorithmFactory;
import com.novo.utils.security.services.contracts.IEncryptionServices;
import com.novo.utils.security.services.implementations.EncryptionServices;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.BeforeEach;
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

import java.util.Optional;

import static com.novo.microservices.transactions.constants.OrchestratorCustomResponsesCodes.CUSTOM_RESPONSE_STATUS_DECLINED_CODE;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

/**
 * OrchestratorTransactionServiceTest
 * <p>
 * OrchestratorTransactionServiceTest class.
 * <p>
 * <p>
 * ESTE COMPONENTE FUE CONSTRUIDO SIGUIENDO LOS ESTANDARES DE DESARROLLO Y EL PROCEDIMIENTO
 * DE DESARROLLO DE APLICACIONES DE NOVOPAYMENT Y SE ENCUENTRA PROTEGIDO POR LAS LEYES DE
 * PROPIEDAD INTELECTUAL Y DERECHOS DE AUTOR.
 *
 * @author Novopayment Inc.
 * @author squijano@novopayment.com
 * @since 4/25/2022
 */
@Log4j2
@SpringBootTest(classes = OrchestratorTransactionService.class)
@ExtendWith({SpringExtension.class, MockServerExtension.class})
@ContextConfiguration(classes = {
    OrchestratorTransactionService.class,
    TransactionProducer.class,
    BusinessResponse.class,
    ContextInformationService.class,
    CommonTransactionValidation.class,
    OrchestratorProviderService.class,
    OrchestratorTransactionFactory.class,
    IOrchestratorTransaction.class,
    CustomerInfoTransaction.class,
    StandardTransactionCommonMapper.class,
    StandardTransactionCommonDefaultValuesMapper.class,
    StandardTransactionStructureDataMapper.class,
    StandardTransactionDefaultValuesMapperMockImpl.class,
    CustomTransactionCommonMapper.class,
    StandardTransactionValidation.class,
    MessageConfiguration.class,
    IStandardTransactionMapperImpl.class,
    StandardTransaction.class,
    SwitchKeyHelper.class,
    OrchestratorEncryptionService.class,
    EncryptionServices.class,
    AlgorithmFactory.class,
    StandardTransactionValidation.class,
    SagaTransactionalJsonHelper.class,
    OrchestratorCacheService.class,
    OrchestratorTransactionMappingService.class,
    OrchestratorTransactionStructureDataMappingService.class
})
@TestPropertySource(properties = {"spring.config.location=classpath:application.yml"})
class OrchestratorTransactionServiceTest {

    @Autowired
    private IOrchestratorTransactionService service;

    @MockBean
    private IOrchestratorTransactionProducer producer;

    @Autowired
    private IOrchestratorProviderService orchestratorProviderService;

    @Autowired
    private IOrchestratorTransaction orchestratorTransaction;

    @Autowired
    private IStandardTransactionCommonMapper standardTransactionCommonMapper;

    @Autowired
    private IStandardTransactionMapper standardTransactionMapper;

    @Autowired
    private SwitchKeyHelper switchKeyHelper;

    @Autowired
    private IOrchestratorEncryptionService orchestratorEncryptionService;

    @Autowired
    private IEncryptionServices encryptionServices;

    @Autowired
    private IStandardTransactionValidation standardTransactionValidation;

    @Autowired
    private IStandardTransactionCommonDefaultValuesMapper standardTransactionDefaultValuesMapper;

    @Autowired
    private IStandardTransactionStructureDataMapper standardTransactionStructureDataMapper;

    @MockBean
    private IOrchestratorTransactionMappingService orchestratorTransactionMappingService;

    @MockBean
    private IOrchestratorTransactionStructureDataMappingService orchestratorTransactionStructureDataMappingService;

    @MockBean
    private ICustomTransactionCommonMapper customTransactionCommonMapper;


    @MockBean
    private MessageConfiguration messageConfiguration;

    @MockBean
    private ISagaMongoDatabaseService sagaMongoDatabaseService;

    @MockBean
    private IOrchestratorTransactionReportCrudService orchestratorTransactionReportCrudService;

    @MockBean
    private ISagaTransactionalBridgeService sagaTransactionalBridgeService;

    @MockBean
    private IOrchestratorClassificationService orchestratorClassificationService;

    @MockBean
    private IOrchestratorStateService orchestratorStateService;

    @MockBean
    private IOrchestratorCrudService orchestratorCrudService;

    @MockBean
    private IOrchestratorParameterService orchestratorParameterService;

    @MockBean
    private AppSessionContext appSessionContext;

    @BeforeEach
    public void setUp() {
        Event<TransactionMessage<HsmGenerateKeyRequest>> eventRequest = Event.<TransactionMessage<HsmGenerateKeyRequest>>builder()
            .data(TransactionMessage.<HsmGenerateKeyRequest>builder()
                .data(HsmGenerateKeyRequest.builder().build())
                .build())
            .build();

        Event<TransactionMessage<HsmGenerateKeyResponse>> eventResponse = Event.<TransactionMessage<HsmGenerateKeyResponse>>builder()
            .data(TransactionMessage.<HsmGenerateKeyResponse>builder()
                .data(HsmGenerateKeyResponse.builder().build())
                .build())
            .build();

        when(producer.doOnProcessTransaction(any(), any(), any()))
            .thenReturn(Mono.just(OrchestratorProducerResult.builder()
                    .errors(null)
                .build()));
        when(sagaTransactionalBridgeService.doOnCreateSagaTransaction(any())).thenReturn(Mono.just(CreateSagaTransactionalResponse.builder()
            .successfullyResponse(true)
            .sagaControl(SagaControl.builder()
                .eventState("HSM_GENERATE_KEY_PEDING")
                .build())
            .sagaOutBox(SagaOutBox.builder().build())
            .build()));
        when(sagaTransactionalBridgeService.doOnCreateSagaTransactionState(any()))
            .thenReturn(Mono.just(CreateSagaTransactionalStateResponse.builder()
            .successfullyResponse(true)
            .build()));
        when(sagaTransactionalBridgeService.doOnFindSagaTransactionState(any())).thenReturn(Mono.just(SagaTransactionalStateResponse.builder()
            .sagaControl(SagaControl.builder()
                .eventState("HSM_GENERATE_KEY_COMPLETED")
                .build())
            .originalSagaOutBox(SagaOutBox.builder().build())
            .build()));

        when(orchestratorClassificationService.doOnFindTransactionClassification(any()))
            .thenReturn(Mono.just(OrchestratorTransactionClassification.builder()
                .transactionCode("orchestrator.customer.info.transaction")
                .serviceId("00660035")
                .messageTypeIndicator("0200")
                .processingCode("380000")
                .transactionStateCode("CUSTOMER_INFO_TRANSACTION")
                .transactionReferenceCode("ConsultaDatosCliente")
                .build()));

        OrchestratorTransactionMapping orchestratorTransactionMapping = new OrchestratorTransactionMapping();
        orchestratorTransactionMapping.setStandardTransactionRequest(StandardTransaction.builder()
                .dataElement_25("00")
                .dataElement_26("04")
                .dataElement_123("660050100001119")
                .build());

        when(orchestratorTransactionMappingService.findGlobalMapping())
            .thenReturn(Optional.of(orchestratorTransactionMapping));

        when(orchestratorStateService.doOnLoadOrchestratorTransactionStates(any())).thenReturn(Mono.just(OrchestratorTransactionStates.builder()
                .transactionSagaStatePending("CUSTOMER_INFO_PENDING")
            .build()));
        when(orchestratorCrudService.getDomainEntityById(any()))
            .thenReturn(Mono.just(OrchestratorTransactionInformation.builder()
                .build()));

        when(orchestratorCrudService.saveEntity(any())).thenReturn(Mono.just(OrchestratorTransactionInformation.builder().build()));
        when(orchestratorCrudService.updateEntity(any())).thenReturn(Mono.just(OrchestratorTransactionInformation.builder().build()));
        when(orchestratorParameterService.findTenantParameterSetting(any())).thenReturn("100");
        when(appSessionContext.getApplicationSession()).thenReturn(ApplicationSession.builder()
            .tenantId("mx-yastas")
            .requestId("12345")
            .build());
    }


    @Test
    void doOnProcessTransactionByRestUnreceived() {
        CustomTransactionInformationRequestFixture customTransactionInformationRequestFixture = new CustomTransactionInformationRequestFixture();
        PaymentHeaderInformationFixture paymentHeaderInformationFixture = new PaymentHeaderInformationFixture();

        OrchestrationTransactionRequest generateTransactionRequest = OrchestrationTransactionRequest.builder()
            .transaction(customTransactionInformationRequestFixture.getInstance())
            .paymentHeader(paymentHeaderInformationFixture.getInstance())
            .build();

        final CustomTransactionResponses customTransactionResponses = CustomTransactionResponses.findByCustomResponseCode(CUSTOM_RESPONSE_STATUS_DECLINED_CODE);

        StepVerifier.create(service.doOnProcessTransactionByRest(generateTransactionRequest))
            .expectNext(BusinessProcessResponse.setEntitySuccessfullyResponse(new GenericBusinessResponse<>(OrchestrationTransactionResponse.builder()
                    .paymentResponse(CustomPaymentInformation.builder()
                        .responseStatus(customTransactionResponses.getCustomResponseCode())
                        .responseDescription(customTransactionResponses.getCustomResponseDescription())
                        .trackingId(generateTransactionRequest.getPaymentHeader().getTrackingId())
                        .serviceId(generateTransactionRequest.getPaymentHeader().getServiceId())
                        .build())
                .build())))
            .verifyComplete();

    }
}