package com.novo.microservices.services.contracts;

import com.novo.microservices.tbs.utils.dtos.responses.BusinessProcessResponse;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

/**
 * IConciliationReportService
 * <p>
 * IConciliationReportService interface.
 * <p>
 * <p>
 * ESTE COMPONENTE FUE CONSTRUIDO SIGUIENDO LOS ESTANDARES DE DESARROLLO Y EL PROCEDIMIENTO
 * DE DESARROLLO DE APLICACIONES DE NOVOPAYMENT Y SE ENCUENTRA PROTEGIDO POR LAS LEYES DE
 * PROPIEDAD INTELECTUAL Y DERECHOS DE AUTOR.
 *
 * @author Novopayment Inc.
 * @author duhernandez@novopayment.com
 * @since 4/11/2022
 */
public interface IConciliationReportService {

    /**
     * Generate conciliation report for the date passed as parameter.
     *
     * @param date
     * @return
     */
    Mono<BusinessProcessResponse> generateReport(LocalDate date);
}