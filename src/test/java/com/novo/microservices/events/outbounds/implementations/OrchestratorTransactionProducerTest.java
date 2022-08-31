package com.novo.microservices.events.outbounds.implementations;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.novo.microservices.components.configurations.MessageConfiguration;
import com.novo.microservices.components.helpers.ContextInformationService;
import com.novo.microservices.components.helpers.SagaTransactionalJsonHelper;
import com.novo.microservices.components.helpers.SwitchKeyHelper;
import com.novo.microservices.dtos.OrchestratorProducerResult;
import com.novo.microservices.dtos.OrchestratorTransactionInformation;
import com.novo.microservices.dtos.OrchestratorTransactionMapping;
import com.novo.microservices.dtos.base.BusinessResponse;
import com.novo.microservices.dtos.requests.OrchestrationSagaRequest;
import com.novo.microservices.events.outbounds.contracts.IOrchestratorTransactionProducer;
import com.novo.microservices.mocks.CustomTransactionInformationRequestFixture;
import com.novo.microservices.mocks.PaymentHeaderInformationFixture;
import com.novo.microservices.mocks.StandardTransactionDefaultValuesMapperMockImpl;
import com.novo.microservices.repositories.contracts.IOrchestratorTransactionRepository;
import com.novo.microservices.services.contracts.*;
import com.novo.microservices.services.implementations.*;
import com.novo.microservices.tbs.utils.components.helpers.RoutingKeyHelper;
import com.novo.microservices.tbs.utils.components.validations.implementations.CommonTransactionValidation;
import com.novo.microservices.tbs.utils.dtos.SagaControl;
import com.novo.microservices.tbs.utils.dtos.SagaOutBox;
import com.novo.microservices.tbs.utils.dtos.StandardTransaction;
import com.novo.microservices.tbs.utils.dtos.TransactionMessage;
import com.novo.microservices.tbs.utils.dtos.commons.CommonBrokerConfiguration;
import com.novo.microservices.tbs.utils.dtos.responses.CreateSagaTransactionalStateResponse;
import com.novo.microservices.tbs.utils.dtos.responses.SagaTransactionalStateResponse;
import com.novo.microservices.tbs.utils.events.outbounds.implementations.TransactionProducer;
import com.novo.microservices.tbs.utils.services.contracts.ISagaMongoDatabaseService;
import com.novo.microservices.tbs.utils.services.contracts.ISagaTransactionalBridgeService;
import com.novo.microservices.transactions.contracts.IOrchestratorTransaction;
import com.novo.microservices.transactions.contracts.IOrchestratorTransactionFactory;
import com.novo.microservices.transactions.factories.OrchestratorTransactionFactory;
import com.novo.microservices.transactions.implementations.CustomerInfoTransaction;
import com.novo.microservices.transactions.mappers.contracts.*;
import com.novo.microservices.transactions.mappers.implementations.CustomTransactionCommonMapper;
import com.novo.microservices.transactions.mappers.implementations.StandardTransactionCommonDefaultValuesMapper;
import com.novo.microservices.transactions.mappers.implementations.StandardTransactionCommonMapper;
import com.novo.microservices.transactions.mappers.implementations.StandardTransactionStructureDataMapper;
import com.novo.microservices.transactions.validations.contracts.IStandardTransactionValidation;
import com.novo.microservices.transactions.validations.implementations.StandardTransactionValidation;
import com.novo.utils.messaging.components.enums.EventType;
import com.novo.utils.messaging.dtos.Event;
import com.novo.utils.messaging.services.contrats.IPublisherService;
import com.novo.utils.messaging.services.implementations.PublisherService;
import com.novo.utils.security.abstractions.AlgorithmFactory;
import com.novo.utils.security.services.implementations.EncryptionServices;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockserver.junit.jupiter.MockServerExtension;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Objects;

import static com.novo.microservices.constants.ProcessConstants.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

/**
 * OrchestratorTransactionProducerTest
 * <p>
 * OrchestratorTransactionProducerTest class.
 * <p>
 * <p>
 * ESTE COMPONENTE FUE CONSTRUIDO SIGUIENDO LOS ESTANDARES DE DESARROLLO Y EL PROCEDIMIENTO
 * DE DESARROLLO DE APLICACIONES DE NOVOPAYMENT Y SE ENCUENTRA PROTEGIDO POR LAS LEYES DE
 * PROPIEDAD INTELECTUAL Y DERECHOS DE AUTOR.
 *
 * @author Novopayment Inc.
 * @author jquiroga@novopayment.com
 * @since 5/19/22
 */
@Log4j2
@SpringBootTest(classes = OrchestratorTransactionProducer.class)
@ExtendWith({SpringExtension.class, MockServerExtension.class})
@ContextConfiguration(classes = {
    OrchestratorTransactionProducer.class,
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
    StandardTransactionDefaultValuesMapperMockImpl.class,
    StandardTransactionStructureDataMapper.class,
    CustomTransactionCommonMapper.class,
    StandardTransactionValidation.class,
    MessageConfiguration.class,
    IStandardTransactionMapperImpl.class,
    StandardTransaction.class,
    SwitchKeyHelper.class,
    SagaTransactionalJsonHelper.class,
    OrchestratorCacheService.class,
    OrchestratorEncryptionService.class,
    EncryptionServices.class,
    AlgorithmFactory.class,
    StandardTransactionValidation.class,
    OrchestratorTransactionMappingService.class,
    OrchestratorTransactionStructureDataMappingService.class,
    PublisherService.class,
    ICustomTransactionMapperImpl.class
})
@TestPropertySource(properties = {"spring.config.location=classpath:application.yml"})
class OrchestratorTransactionProducerTest {

    @Autowired
    private IOrchestratorTransactionProducer orchestratorTransactionProducer;


    @MockBean
    private IPublisherService publisherService;

    @MockBean
    private ISagaMongoDatabaseService sagaMongoDatabaseService;

    @MockBean
    private ISagaTransactionalBridgeService sagaTransactionalBridgeService;

    @MockBean
    private IOrchestratorTransactionRepository orchestratorTransactionRepository;

    @MockBean
    private IOrchestratorCrudService orchestratorCrudService;

    @MockBean
    private IOrchestratorEncryptionService orchestratorEncryptionService;

    @MockBean
    private MongoOperations mongoOperations;

    @MockBean
    private IOrchestratorProviderService orchestratorProviderService;

    @MockBean
    private IOrchestratorTransactionFactory orchestratorTransactionFactory;

    @Autowired
    private IStandardTransactionCommonMapper standardTransactionCommonMapper;

    @Autowired
    private IStandardTransactionCommonDefaultValuesMapper standardTransactionCommonDefaultValuesMapper;

    @Autowired
    private IStandardTransactionStructureDataMapper standardTransactionStructureDataMapper;

    @Autowired
    private IOrchestratorTransactionStructureDataMappingService orchestratorTransactionStructureDataMappingRepository;

    @Autowired
    private ICustomTransactionCommonMapper customTransactionCommonMapper;

    @Autowired
    private IStandardTransactionValidation standardTransactionValidation;

    @MockBean
    IOrchestratorParameterService orchestratorParameterService;

    @BeforeEach
    public void setUp() throws JsonProcessingException {
        when(orchestratorParameterService.findTenantParameterSetting(MICROSERVICES_ORCHESTRATOR_HSM_CONFIGURATIONS_PATH)).thenReturn(Objects.requireNonNull(getClass().getResource("/json/microservices-orchestrator-hsm-configurations.json")).getPath());
        when(orchestratorParameterService.findTenantParameterSetting(MICROSERVICES_ORCHESTRATOR_TRANSACTIONS_CODES_PATH)).thenReturn(Objects.requireNonNull(getClass().getResource("/json/microservices-orchestrator-transactions-codes.json")).getPath());
        when(orchestratorParameterService.findTenantParameterSetting(MICROSERVICES_ORCHESTRATOR_TRANSACTIONS_MAPPINGS_PATH)).thenReturn(Objects.requireNonNull(getClass().getResource("/json/microservices-orchestrator-transactions-mappings.json")).getPath());
        when(orchestratorParameterService.findTenantParameterSetting(MICROSERVICES_ORCHESTRATOR_TRANSACTIONS_STRUCTURE_DATA_MAPPINGS_PATH)).thenReturn(Objects.requireNonNull(getClass().getResource("/json/microservices-orchestrator-transactions-structure-data-mappings.json")).getPath());

        String tenantId = "mx-yastas";


        Mockito.when(publisherService.publish(Mockito.any(), Mockito.any()))
            .thenReturn(true);
        OrchestratorTransactionMapping orchestratorTransactionMapping = new OrchestratorTransactionMapping();
        orchestratorTransactionMapping.setStandardTransactionRequest(StandardTransaction.builder()
            .dataElement_25("00")
            .dataElement_26("04")
            .dataElement_123("660050100001119")
            .build());
        when(mongoOperations.findOne(any(), eq(OrchestratorTransactionMapping.class)))
            .thenReturn(orchestratorTransactionMapping);
        Mockito.when(sagaMongoDatabaseService.loadMongoDatabase(tenantId))
            .thenReturn(Mono.empty());
        Mockito.when(sagaTransactionalBridgeService.doOnCreateSagaTransactionState(Mockito.any()))
            .thenReturn(Mono.just(CreateSagaTransactionalStateResponse.builder()
                .successfullyResponse(true)
                .build()));
        Mockito.when(sagaTransactionalBridgeService.doOnFindSagaTransactionState(Mockito.any()))
            .thenReturn(Mono.just(SagaTransactionalStateResponse.builder()
                .sagaControl(SagaControl.builder()
                    .eventState("CUSTOMER_INFO_TRANSACTION_PENDING")
                    .build())
                .build()));
        Mockito.when(orchestratorCrudService.updateEntity(Mockito.any()))
            .thenReturn(Mono.just(OrchestratorTransactionInformation.builder().build()));
        Mockito.when(orchestratorCrudService.getDomainEntityById(Mockito.any()))
            .thenReturn(Mono.just(OrchestratorTransactionInformation.builder()
                .messageId("Message-Id")
                .serviceId("00660035")
                .processingCode("380000")
                .messageTypeIndicator("0200")
                .build()));

    }

    @Test
    void doOnProcessTransaction() {

        CustomTransactionInformationRequestFixture customTransactionInformationRequestFixture = new CustomTransactionInformationRequestFixture();
        PaymentHeaderInformationFixture paymentHeaderInformationFixture = new PaymentHeaderInformationFixture();

        CommonBrokerConfiguration commonBrokerConfiguration = new CommonBrokerConfiguration();

        commonBrokerConfiguration.setCommand("REQUEST");
        commonBrokerConfiguration.setRoutingDomain("TRANSACTION");
        commonBrokerConfiguration.setRoutingKeyOrigin("SWITCHING-BRIDGE-ORCHESTRATOR");
        commonBrokerConfiguration.setRoutingKeyDestiny("CUSTOMER-INFORMATION");


        MessageConfiguration messageConfiguration = new MessageConfiguration();
        messageConfiguration.setCustomerInformationTransactionProducer(commonBrokerConfiguration);

        CustomerInfoTransaction customerInfoTransaction = new CustomerInfoTransaction(standardTransactionCommonMapper,
            standardTransactionCommonDefaultValuesMapper,
            standardTransactionStructureDataMapper,
            customTransactionCommonMapper,
            standardTransactionValidation,
            messageConfiguration);

        String tenantId = "mx-yastas";
        String requestId = "Request-Id";
        String eventSource = "API-RECEIVER";
        String eventState = "CUSTOMER_INFO_TRANSACTION_COMPLETED";

        OrchestrationSagaRequest orchestrationSagaRequest = OrchestrationSagaRequest.builder()
            .sagaOutBox(SagaOutBox.builder()
                .messageId("Message-Id")
                .correlationId("Correlation-Id")
                .eventId("Event-Id")
                .tenantId(tenantId)
                .requestId(requestId)
                .build())
            .currentSagaControl(SagaControl.builder()
                .eventSource(eventSource)
                .eventState(eventState)
                .build())
            .orchestratorTransaction(customerInfoTransaction)
            .build();

        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setContentType("application/octet-stream");
        messageProperties.setContentLength(0);
        messageProperties.setDeliveryMode(MessageDeliveryMode.PERSISTENT);
        messageProperties.setPriority(0);
        messageProperties.setDeliveryTag(0);

        TransactionMessage<StandardTransaction> transactionMessage = TransactionMessage.<StandardTransaction>builder()
            .tenantId(tenantId)
            .requestId(requestId)
            .eventSource(eventSource)
            .eventState(eventState)
                .build();

        StepVerifier.create(orchestratorTransactionProducer.doOnProcessTransaction(orchestrationSagaRequest.getSagaOutBox(),
            orchestrationSagaRequest.getCurrentSagaControl(),
            orchestrationSagaRequest.getOrchestratorTransaction()))
            .expectNext(OrchestratorProducerResult.builder()
                .eventTransactionMessage(Event.<TransactionMessage<StandardTransaction>>builder()
                    .routingKey(RoutingKeyHelper.createRoutingKey(EventType.SERVICE, customerInfoTransaction.getBrokerConfigurationProducer()))
                    .messageProperties(messageProperties)
                    .data(transactionMessage)
                    .build())
                .build())
            .verifyComplete();

    }
}