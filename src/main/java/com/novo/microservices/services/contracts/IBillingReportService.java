package com.novo.microservices.services.contracts;

import com.novo.microservices.tbs.utils.dtos.responses.BusinessProcessResponse;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

/**
 * IBillingReportService
 * <p>
 * IBillingReportService interface.
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
public interface IBillingReportService {

    /**
     * Generate billing report for the date range
     * @param dateFrom
     * @param dateTo
     * @return
     */
    Mono<BusinessProcessResponse> generateReport(LocalDate dateFrom, LocalDate dateTo);
}