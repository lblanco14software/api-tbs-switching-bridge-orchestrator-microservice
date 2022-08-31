package com.novo.microservices.controllers.implementations;

import com.novo.microservices.components.aspects.NovoLogEnable;
import com.novo.microservices.components.enums.ResponseCode;
import com.novo.microservices.components.helpers.ControllerLoggerHelper;
import com.novo.microservices.components.helpers.SagaTransactionalJsonHelper;
import com.novo.microservices.controllers.contracts.IReSyncOrchestratorController;
import com.novo.microservices.dtos.base.BusinessResponse;
import com.novo.microservices.services.contracts.IOrchestratorCacheService;
import com.novo.microservices.tbs.utils.controllers.base.ReactorBaseController;
import com.novo.microservices.tbs.utils.dtos.responses.BusinessProcessResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import static com.novo.microservices.constants.ProcessConstants.RESYNC_ORCHESTRATOR_PATH;

/**
 * ReSyncController
 * <p>
 * ReSyncController class.
 * <p>
 * ESTE COMPONENTE FUE CONSTRUIDO SIGUIENDO LOS ESTANDARES DE DESARROLLO Y EL PROCEDIMIENTO DE DESARROLLO DE
 * APLICACIONES DE NOVOPAYMENT Y SE ENCUENTRA PROTEGIDO POR LAS LEYES DE PROPIEDAD INTELECTUAL Y DERECHOS DE AUTOR.
 *
 * @author Novopayment Inc.
 * @author jquiroga@novopayment.com
 * @since 6/27/2022
 */
@Log4j2
@RestController
public class ReSyncOrchestratorController extends ReactorBaseController implements IReSyncOrchestratorController {
    private final IOrchestratorCacheService componentCacheManagerService;
    private final SagaTransactionalJsonHelper sagaTransactionalJsonHelper;
    private final ControllerLoggerHelper controllerLoggerHelper;

    @Autowired
    public ReSyncOrchestratorController(
        final BusinessResponse businessResponse,
        final IOrchestratorCacheService componentCacheManagerService,
        final SagaTransactionalJsonHelper sagaTransactionalJsonHelper,
        final ControllerLoggerHelper controllerLoggerHelper
    ) {
        super(businessResponse);
        this.componentCacheManagerService = componentCacheManagerService;
        this.sagaTransactionalJsonHelper = sagaTransactionalJsonHelper;
        this.controllerLoggerHelper = controllerLoggerHelper;
    }

    @Override
    @NovoLogEnable
    @DeleteMapping(path = RESYNC_ORCHESTRATOR_PATH, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<Object>> reSyncOrchestrator() {
        try {
            componentCacheManagerService.deleteAllCaches();
            sagaTransactionalJsonHelper.loadHsmGenerateKeyParameters();
            sagaTransactionalJsonHelper.loadTransactionClassification();
            sagaTransactionalJsonHelper.loadTransactionsMappings();
            sagaTransactionalJsonHelper.loadTransactionsStructureDataMappings();
            log.info("Delete and reSync cache for mapping configuration");

            BusinessProcessResponse response = BusinessProcessResponse.setEmptySuccessfullyResponse();
            controllerLoggerHelper.logResponse(response);
            return super.getResponseEntity(response);
        } catch (Exception e) {
            log.error("error in controller ReSyncController, error: {}", e.getMessage());
            return super.getResponseEntity(BusinessProcessResponse.setBusinessProcessError(ResponseCode.INTERNAL_SERVER_ERROR));
        }
    }
}
