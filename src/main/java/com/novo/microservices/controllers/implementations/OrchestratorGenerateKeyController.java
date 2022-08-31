package com.novo.microservices.controllers.implementations;

import com.novo.microservices.components.aspects.NovoLogEnable;
import com.novo.microservices.components.enums.ResponseCode;
import com.novo.microservices.components.helpers.ControllerLoggerHelper;
import com.novo.microservices.controllers.contracts.IOrchestratorGenerateKeyController;
import com.novo.microservices.dtos.base.BusinessResponse;
import com.novo.microservices.dtos.requests.OrchestrationGenerateKeyRequest;
import com.novo.microservices.services.contracts.IOrchestratorGenerateKeyService;
import com.novo.microservices.tbs.utils.components.exceptions.BusinessProcessException;
import com.novo.microservices.tbs.utils.constants.LoggerConstants;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.context.annotation.Scope;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.support.WebExchangeBindException;
import reactor.core.publisher.Mono;

import java.util.UUID;

import static com.novo.microservices.constants.HsmConstants.HSM_TRANSACTION_STATE_CODE;
import static com.novo.microservices.constants.ProcessConstants.MICROSERVICE_PATH_CONTEXT;
import static com.novo.microservices.constants.ProcessConstants.PROCESS_HSM_GENERATE_KEY_PATH;
import static org.springframework.beans.factory.config.BeanDefinition.SCOPE_PROTOTYPE;

/**
 * OrchestratorGenerateKeyController
 * <p>
 * OrchestratorGenerateKeyController class.
 * <p>
 * <p>
 * ESTE COMPONENTE FUE CONSTRUIDO SIGUIENDO LOS ESTANDARES DE DESARROLLO Y EL PROCEDIMIENTO
 * DE DESARROLLO DE APLICACIONES DE NOVOPAYMENT Y SE ENCUENTRA PROTEGIDO POR LAS LEYES DE
 * PROPIEDAD INTELECTUAL Y DERECHOS DE AUTOR.
 *
 * @author Novopayment Inc.
 * @author lblanco@novopayment.com
 * @since 5/12/2022
 */
@Log4j2
@RestController
@Scope(SCOPE_PROTOTYPE)
@RequestMapping(path = MICROSERVICE_PATH_CONTEXT, produces = MediaType.APPLICATION_JSON_VALUE)
public class OrchestratorGenerateKeyController implements IOrchestratorGenerateKeyController {

    private final IOrchestratorGenerateKeyService orchestratorGenerateKeyService;
    private final BusinessResponse businessResponse;
    private final String orchestratorGenerateKeyControllerId;
    private final ControllerLoggerHelper controllerLoggerHelper;

    public OrchestratorGenerateKeyController(final IOrchestratorGenerateKeyService orchestratorGenerateKeyService,
                                             final BusinessResponse businessResponse,
                                             final ControllerLoggerHelper controllerLoggerHelper) {

        this.orchestratorGenerateKeyService = orchestratorGenerateKeyService;
        this.businessResponse = businessResponse;
        this.orchestratorGenerateKeyControllerId = UUID.randomUUID().toString();
        this.controllerLoggerHelper = controllerLoggerHelper;
        log.debug("orchestratorGenerateKeyControllerId {}", orchestratorGenerateKeyControllerId);
        log.debug("OrchestratorTransactionController loaded successfully");
    }

    @Override
    @NovoLogEnable
    @PostMapping(path = PROCESS_HSM_GENERATE_KEY_PATH, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<Object>> doOnProcessGenerateKey(final OrchestrationGenerateKeyRequest orchestrationGenerateKeyRequest) {

        // log transaction request
        ThreadContext.put(LoggerConstants.CONSTANTS_LOG_METHOD, HSM_TRANSACTION_STATE_CODE);
        controllerLoggerHelper.logRequest(orchestrationGenerateKeyRequest, orchestratorGenerateKeyControllerId);

        return orchestratorGenerateKeyService.doOnProcessGenerateKeyByRest(orchestrationGenerateKeyRequest)
            .flatMap(processResponse -> {
                // log transaction response
                controllerLoggerHelper.logResponse(processResponse);

                if (processResponse.isErrorProcessResponse() || processResponse.isEmptySuccessfullyResponse()) {
                    return Mono.just(businessResponse.getResponse(processResponse.getResponseCode().getResponseCodeValue()));
                }
                return Mono.just(businessResponse.getResponse(processResponse.getBusinessResponse(), processResponse.getResponseCode().getResponseCodeValue()));
            })
            .onErrorResume(WebExchangeBindException.class, Mono::error)
            .onErrorResume(BusinessProcessException.class, e -> {
                ResponseEntity<Object> response = businessResponse.getResponse(e.getResponseCode().getResponseCodeValue());
                controllerLoggerHelper.logErrorResponse(e.getResponseCode());
                return Mono.just(response);
            })
            .onErrorResume(e -> {
                ResponseEntity<Object> response = businessResponse.getResponse(ResponseCode.INTERNAL_SERVER_ERROR.getResponseCodeValue());
                controllerLoggerHelper.logErrorResponse(ResponseCode.INTERNAL_SERVER_ERROR);
                return Mono.just(response);
            })
            .doOnSuccess(success ->
                log.info("finish process doOnProcessGenerateKey")
            )
            .doOnError(throwable ->
                log.error("exception error in process doOnProcessGenerateKey, error: {}", throwable.getMessage())
            )
            .log();
    }
}