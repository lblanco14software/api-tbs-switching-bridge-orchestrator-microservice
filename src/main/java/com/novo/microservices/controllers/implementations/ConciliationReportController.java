package com.novo.microservices.controllers.implementations;

import com.novo.microservices.components.aspects.NovoLogEnable;
import com.novo.microservices.components.enums.ResponseCode;
import com.novo.microservices.components.helpers.ControllerLoggerHelper;
import com.novo.microservices.controllers.contracts.IConciliationReportController;
import com.novo.microservices.dtos.base.BusinessResponse;
import com.novo.microservices.services.contracts.IConciliationReportService;
import com.novo.microservices.tbs.utils.components.exceptions.BusinessProcessException;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Scope;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.time.ZoneOffset;
import java.util.Date;

import static com.novo.microservices.constants.ProcessConstants.GENERATE_CONCILIATION_REPORT_PATH;
import static com.novo.microservices.constants.ProcessConstants.MICROSERVICE_PATH_CONTEXT;
import static org.springframework.beans.factory.config.BeanDefinition.SCOPE_PROTOTYPE;

/**
 * ConciliationReportController
 * <p>
 * ConciliationReportController class.
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
@Validated
@Scope(SCOPE_PROTOTYPE)
@RequestMapping(path = MICROSERVICE_PATH_CONTEXT, produces = MediaType.APPLICATION_JSON_VALUE)
public class ConciliationReportController implements IConciliationReportController {

    private final IConciliationReportService service;
    private final BusinessResponse businessResponse;
    private final ControllerLoggerHelper controllerLoggerHelper;

    public ConciliationReportController(final IConciliationReportService service,
                                        final BusinessResponse businessResponse,
                                        final ControllerLoggerHelper controllerLoggerHelper) {
        this.service = service;
        this.businessResponse = businessResponse;
        this.controllerLoggerHelper = controllerLoggerHelper;
    }

    @Override
    @NovoLogEnable
    @GetMapping(path = GENERATE_CONCILIATION_REPORT_PATH, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<Object>> generateReport(@PathVariable @DateTimeFormat(pattern = "yyyyMMdd") Date date) {
        var reportDate = date.toInstant()
            .atZone(ZoneOffset.UTC)
            .toLocalDate();

        return service.generateReport(reportDate)
            .flatMap(processResponse -> {
                controllerLoggerHelper.logResponse(processResponse);

                if (processResponse.isErrorProcessResponse() || processResponse.isEmptySuccessfullyResponse()) {
                    return Mono.just(businessResponse.getResponse(processResponse.getResponseCode().getResponseCodeValue()));
                }
                return Mono.just(businessResponse.getResponse(processResponse.getBusinessResponse(), processResponse.getResponseCode().getResponseCodeValue()));
            })
            .onErrorResume(BusinessProcessException.class, e -> Mono.just(businessResponse.getResponse(e.getResponseCode().getResponseCodeValue())))
            .onErrorResume(e -> Mono.just(businessResponse.getResponse(ResponseCode.INTERNAL_SERVER_ERROR.getResponseCodeValue())));

    }
}