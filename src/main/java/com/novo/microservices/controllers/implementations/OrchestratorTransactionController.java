package com.novo.microservices.controllers.implementations;

import com.novo.microservices.components.aspects.NovoLogEnable;
import com.novo.microservices.components.enums.ResponseCode;
import com.novo.microservices.components.helpers.ControllerLoggerHelper;
import com.novo.microservices.controllers.contracts.IOrchestratorTransactionController;
import com.novo.microservices.dtos.base.BusinessResponse;
import com.novo.microservices.dtos.requests.OrchestrationTransactionRequest;
import com.novo.microservices.services.contracts.IOrchestratorTransactionService;
import com.novo.microservices.tbs.utils.components.exceptions.BusinessProcessException;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Scope;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.support.WebExchangeBindException;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.util.UUID;

import static com.novo.microservices.constants.ProcessConstants.MICROSERVICE_PATH_CONTEXT;
import static com.novo.microservices.constants.ProcessConstants.PROCESS_TRANSACTIONS_PATH;
import static org.springframework.beans.factory.config.BeanDefinition.SCOPE_PROTOTYPE;

/**
 * OrchestratorTransactionController
 * <p>
 * OrchestratorTransactionController class.
 * <p>
 * <p>
 * ESTE COMPONENTE FUE CONSTRUIDO SIGUIENDO LOS ESTANDARES DE DESARROLLO Y EL PROCEDIMIENTO
 * DE DESARROLLO DE APLICACIONES DE NOVOPAYMENT Y SE ENCUENTRA PROTEGIDO POR LAS LEYES DE
 * PROPIEDAD INTELECTUAL Y DERECHOS DE AUTOR.
 *
 * @author Novopayment Inc.
 * @author duhernandez@novopayment.com
 * @since 3/9/2022
 */
@Log4j2
@RestController
@Scope(SCOPE_PROTOTYPE)
@RequestMapping(path = MICROSERVICE_PATH_CONTEXT, produces = MediaType.APPLICATION_JSON_VALUE)
public class OrchestratorTransactionController implements IOrchestratorTransactionController {

    private final IOrchestratorTransactionService orchestratorTransactionService;
    private final BusinessResponse businessResponse;
    private final ControllerLoggerHelper controllerLoggerHelper;
    private final String orchestratorTransactionControllerId;

    public OrchestratorTransactionController(final IOrchestratorTransactionService orchestratorTransactionService,
                                             final ControllerLoggerHelper controllerLoggerHelper,
                                             final BusinessResponse businessResponse) {

        this.orchestratorTransactionService = orchestratorTransactionService;
        this.controllerLoggerHelper = controllerLoggerHelper;
        this.businessResponse = businessResponse;
        this.orchestratorTransactionControllerId = UUID.randomUUID().toString();
        log.debug("orchestratorTransactionControllerId {}", orchestratorTransactionControllerId);
        log.debug("OrchestratorTransactionController loaded successfully");
    }

    @Override
    @NovoLogEnable
    @PostMapping(path = PROCESS_TRANSACTIONS_PATH, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<Object>> doOnProcessTransaction(@Valid @RequestBody OrchestrationTransactionRequest orchestrationTransactionRequest) {
        controllerLoggerHelper.putTransactionClassificationInThreadContext(orchestrationTransactionRequest);

        // log transaction request
        controllerLoggerHelper.logRequest(orchestrationTransactionRequest, orchestratorTransactionControllerId);

        return orchestratorTransactionService.doOnProcessTransactionByRest(orchestrationTransactionRequest)
            .flatMap(processResponse -> {
                // log transaction response
                controllerLoggerHelper.logResponse(processResponse);
                if (processResponse.isErrorProcessResponse() || processResponse.isEmptySuccessfullyResponse()) {
                    var response = businessResponse.getResponse(processResponse.getResponseCode().getResponseCodeValue());
                    return Mono.just(response);
                }
                return Mono.just(businessResponse.getResponse(processResponse.getBusinessResponse(), processResponse.getResponseCode().getResponseCodeValue()));
            })
            .onErrorResume(WebExchangeBindException.class, Mono::error)
            .onErrorResume(BusinessProcessException.class, e -> {
                var response = businessResponse.getResponse(e.getResponseCode().getResponseCodeValue());
                controllerLoggerHelper.logErrorResponse(e.getResponseCode());
                return Mono.just(response);
            })
            .onErrorResume(e -> {
                var response = businessResponse.getResponse(ResponseCode.INTERNAL_SERVER_ERROR.getResponseCodeValue());
                controllerLoggerHelper.logErrorResponse(ResponseCode.INTERNAL_SERVER_ERROR);
                return Mono.just(response);
            })
            .doOnSuccess(success ->
                log.info("finish process processTransaction")
            )
            .doOnError(throwable ->
                log.error("exception error in process processTransaction, error: {}", throwable.getMessage())
            )
            .log();
    }
}