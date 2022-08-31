package com.novo.microservices.components.helpers;

import com.google.gson.Gson;
import com.novo.microservices.components.enums.ResponseCode;
import com.novo.microservices.constants.BaseInterceptorConstants;
import com.novo.microservices.dtos.base.BusinessResponse;
import com.novo.microservices.dtos.contracts.RequestLoggable;
import com.novo.microservices.dtos.contracts.ResponseLoggable;
import com.novo.microservices.dtos.generics.GenericBusinessResponse;
import com.novo.microservices.dtos.requests.OrchestrationTransactionRequest;
import com.novo.microservices.services.contracts.IOrchestratorClassificationService;
import com.novo.microservices.tbs.utils.constants.LoggerConstants;
import com.novo.microservices.tbs.utils.dtos.responses.BusinessProcessResponse;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import static com.novo.microservices.constants.ProcessConstants.ORCHESTRATOR_UNIDENTIFIED_TRANSACTION_METHOD_CONTEXT;

/**
 * ControllerLoggerHelper
 * <p>
 * ControllerLoggerHelper class.
 * <p>
 * <p>
 * ESTE COMPONENTE FUE CONSTRUIDO SIGUIENDO LOS ESTANDARES DE DESARROLLO Y EL PROCEDIMIENTO
 * DE DESARROLLO DE APLICACIONES DE NOVOPAYMENT Y SE ENCUENTRA PROTEGIDO POR LAS LEYES DE
 * PROPIEDAD INTELECTUAL Y DERECHOS DE AUTOR.
 *
 * @author Novopayment Inc.
 * @author lblanco@novopayment.com
 * @since 7/7/2022
 */
@Log4j2
@Component
public class ControllerLoggerHelper {

    private final BusinessResponse businessResponse;
    private final IOrchestratorClassificationService orchestratorClassificationService;
    private final Gson gson;

    public ControllerLoggerHelper(
        BusinessResponse businessResponse,
        IOrchestratorClassificationService orchestratorClassificationService
    ) {
        this.businessResponse = businessResponse;
        this.orchestratorClassificationService = orchestratorClassificationService;
        this.gson = new Gson();
    }

    public void logErrorResponse(final ResponseCode responseCode) {
        var response = businessResponse.getResponse(responseCode.getResponseCodeValue());
        log.info(BaseInterceptorConstants.RESPONSE_MESSAGE, response.getBody());
    }

    public void logRequest(RequestLoggable request, String controllerId) {
        log.info(BaseInterceptorConstants.REQUEST_MESSAGE, gson.toJson(request.prepareForLogInfo()));
        log.debug("controllerId: {}", controllerId);
        log.debug("completeRequest: {}", gson.toJson(request));
    }

    public void logResponse(final BusinessProcessResponse businessProcessResponse) {
        if (businessProcessResponse.isErrorProcessResponse() || businessProcessResponse.isEmptySuccessfullyResponse())
            logErrorResponse(businessProcessResponse);

        var genericResponse = businessProcessResponse.getBusinessResponse();
        if (genericResponse instanceof GenericBusinessResponse) {
            var data = ((GenericBusinessResponse<?>) genericResponse).getData();
            if (data instanceof ResponseLoggable) {
                var response = ((ResponseLoggable) data).prepareForLogInfo();
                log.info(
                    BaseInterceptorConstants.RESPONSE_MESSAGE,
                    businessResponse.getResponse(response.getBusinessResponse(), response.getResponseCode().getResponseCodeValue()).getBody()
                );
            }
        } else log.info(BaseInterceptorConstants.RESPONSE_MESSAGE, gson.toJson(businessProcessResponse.getBusinessResponse()));
    }

    public void putTransactionClassificationInThreadContext(final OrchestrationTransactionRequest orchestrationTransactionRequest) {
        this.orchestratorClassificationService.doOnFindTransactionClassification(orchestrationTransactionRequest)
            .flatMap(orchestratorTransactionClassification -> putMethodContext(orchestratorTransactionClassification.getTransactionStateCode()))
            .onErrorResume(e -> putMethodContext(ORCHESTRATOR_UNIDENTIFIED_TRANSACTION_METHOD_CONTEXT))
            .doOnSuccess(success -> log.debug("success process doOnFindTransactionClassification on ControllerLoggerHelper"))
            .doOnError(throwable -> log.error("exception error in process doOnFindTransactionClassification on ControllerLoggerHelper, error: {}", throwable.getMessage()))
            .subscribe();
    }

//    Private methods

    private void logErrorResponse(final BusinessProcessResponse businessProcessResponse) {
        var response = businessResponse.getResponse(businessProcessResponse.getResponseCode().getResponseCodeValue());
        log.info(BaseInterceptorConstants.RESPONSE_MESSAGE, response.getBody());
    }

    private Mono<Void> putMethodContext(String methodContext) {
        ThreadContext.put(LoggerConstants.CONSTANTS_LOG_METHOD, methodContext);
        return Mono.empty();
    }
}
