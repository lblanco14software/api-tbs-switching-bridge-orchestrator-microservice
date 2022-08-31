package com.novo.microservices.controllers.implementations;

import com.novo.microservices.controllers.contracts.IOrchestratorWarmUpController;
import com.novo.microservices.dtos.base.BusinessResponse;
import com.novo.microservices.dtos.requests.OrchestrationTransactionRequest;
import com.novo.microservices.events.outbounds.contracts.IOrchestratorTransactionProducer;
import com.novo.microservices.services.contracts.*;
import com.novo.microservices.tbs.utils.dtos.requests.CreateSagaTransactionalStateRequest;
import com.novo.microservices.tbs.utils.services.contracts.ISagaMongoDatabaseService;
import com.novo.microservices.tbs.utils.services.contracts.ISagaTransactionalBridgeService;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

import static com.novo.microservices.constants.ProcessConstants.MICROSERVICE_PATH_CONTEXT;
import static com.novo.microservices.constants.ProcessConstants.PROCESS_WARMUP_PATH;
import static org.springframework.beans.factory.config.BeanDefinition.SCOPE_PROTOTYPE;

/**
 * OrchestratorWarmUpController
 * <p>
 * OrchestratorWarmUpController class.
 * <p>
 * <p>
 * ESTE COMPONENTE FUE CONSTRUIDO SIGUIENDO LOS ESTANDARES DE DESARROLLO Y EL PROCEDIMIENTO
 * DE DESARROLLO DE APLICACIONES DE NOVOPAYMENT Y SE ENCUENTRA PROTEGIDO POR LAS LEYES DE
 * PROPIEDAD INTELECTUAL Y DERECHOS DE AUTOR.
 *
 * @author Novopayment Inc.
 * @author lblanco@novopayment.com
 * @since 8/31/2022
 */
@Log4j2
@RestController
@Scope(SCOPE_PROTOTYPE)
@RequestMapping(path = MICROSERVICE_PATH_CONTEXT, produces = MediaType.APPLICATION_JSON_VALUE)
public class OrchestratorWarmUpController implements IOrchestratorWarmUpController {

    private final IOrchestratorTransactionService orchestratorTransactionService;
    private final IOrchestratorTransactionProducer orchestratorTransactionProducer;
    private final ISagaTransactionalBridgeService sagaTransactionalBridgeService;
    private final IOrchestratorEncryptionService orchestratorEncryptionService;
    private final IOrchestratorParameterService orchestratorParameterService;
    private final IOrchestratorProviderService orchestratorProviderService;
    private final IOrchestratorStateService orchestratorStateService;
    private final ISagaMongoDatabaseService sagaMongoDatabaseService;
    private final IOrchestratorCrudService orchestratorCrudService;

    private final IOrchestratorTransactionReportCrudService orchestratorTransactionReportCrudService;
    private final BusinessResponse businessResponse;
    private final String orchestratorWarmUpControllerId;

    public OrchestratorWarmUpController(IOrchestratorTransactionService orchestratorTransactionService,
                                        IOrchestratorTransactionProducer orchestratorTransactionProducer,
                                        ISagaTransactionalBridgeService sagaTransactionalBridgeService,
                                        IOrchestratorEncryptionService orchestratorEncryptionService,
                                        IOrchestratorParameterService orchestratorParameterService,
                                        IOrchestratorProviderService orchestratorProviderService,
                                        IOrchestratorStateService orchestratorStateService,
                                        ISagaMongoDatabaseService sagaMongoDatabaseService,
                                        IOrchestratorCrudService orchestratorCrudService,
                                        IOrchestratorTransactionReportCrudService orchestratorTransactionReportCrudService,
                                        BusinessResponse businessResponse) {

        this.orchestratorTransactionService = orchestratorTransactionService;
        this.orchestratorTransactionProducer = orchestratorTransactionProducer;
        this.sagaTransactionalBridgeService = sagaTransactionalBridgeService;
        this.orchestratorEncryptionService = orchestratorEncryptionService;
        this.orchestratorParameterService = orchestratorParameterService;
        this.orchestratorProviderService = orchestratorProviderService;
        this.orchestratorStateService = orchestratorStateService;
        this.sagaMongoDatabaseService = sagaMongoDatabaseService;
        this.orchestratorCrudService = orchestratorCrudService;
        this.orchestratorTransactionReportCrudService = orchestratorTransactionReportCrudService;
        this.businessResponse = businessResponse;
        this.orchestratorWarmUpControllerId = UUID.randomUUID().toString();
        log.debug("orchestratorWarmUpControllerId {}", orchestratorWarmUpControllerId);
        log.debug("OrchestratorWarmUpController loaded successfully");
    }

    @Override
    @GetMapping(path = PROCESS_WARMUP_PATH, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> doOnWarmUpMicroservice() {

        try{
            orchestratorTransactionService.doOnProcessTransactionByRest(OrchestrationTransactionRequest.builder().build()).block();
        }catch(Exception e){
            log.error("error in doOnProcessTransactionByRest");
        }

        try{
            sagaTransactionalBridgeService.doOnCreateSagaTransactionState(CreateSagaTransactionalStateRequest.builder().build());
        }catch(Exception e){
            log.error("error in doOnCreateSagaTransactionState");
        }

        try{
            sagaTransactionalBridgeService.doOnCreateSagaTransactionHistory(CreateSagaTransactionalStateRequest.builder().build());
        }catch(Exception e){
            log.error("error in doOnCreateSagaTransactionState");
        }

        try{
            sagaTransactionalBridgeService.doOnFindSagaTransactionState(UUID.randomUUID().toString());
        }catch(Exception e){
            log.error("error in doOnCreateSagaTransactionState");
        }

        try{
            orchestratorEncryptionService.doOnEncryptData(UUID.randomUUID().toString());
        }catch(Exception e){
            log.error("error in doOnCreateSagaTransactionState");
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }
}