package com.novo.microservices.services.implementations;

import com.novo.microservices.components.enums.ResponseCode;
import com.novo.microservices.components.helpers.TransactionCsvWriter;
import com.novo.microservices.dtos.generics.GenericBusinessResponse;
import com.novo.microservices.dtos.responses.Base64EncodeResponse;
import com.novo.microservices.services.contracts.IConciliationReportService;
import com.novo.microservices.tbs.utils.components.exceptions.BusinessProcessException;
import com.novo.microservices.tbs.utils.dtos.responses.BusinessProcessResponse;
import com.novo.microservices.utils.common.context.AppSessionContext;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import reactor.core.Exceptions;
import reactor.core.publisher.Mono;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import static org.apache.commons.lang3.StringUtils.isBlank;

/**
 * ConciliationReportService
 * <p>
 * Generates the zip file with the transactions that where not sent to the core system
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
@Log4j2
@Service
public class ConciliationReportService implements IConciliationReportService {
    private final AppSessionContext appSessionContext;
    private final TransactionCsvWriter transactionCsvWriter;

    public ConciliationReportService(final AppSessionContext appSessionContext,
                                     final TransactionCsvWriter transactionCsvWriter) {
        this.appSessionContext = appSessionContext;
        this.transactionCsvWriter = transactionCsvWriter;
        log.debug("ConciliationReportService loaded successfully");
    }

    @Override
    public Mono<BusinessProcessResponse> generateReport(LocalDate date) {
        return Mono.just(new ByteArrayOutputStream())
            .doFirst(() -> log.info("Generating conciliation report, date: {}", date))
            .flatMap(outputStream -> {
                    var bankCodes = transactionCsvWriter.getDistinctBankCodes(date, date);
                    if (bankCodes.isEmpty()) {
                        return Mono.error(() -> new BusinessProcessException("No data found",
                            ResponseCode.PROCESS_OK_ERROR_NO_RESULTS_FOUND));
                    }

                    // generate a csv file for each bank code
                    try (var zos = new ZipOutputStream(outputStream)) {
                        bankCodes.forEach(bankCode -> generateCsvFile(bankCode, date, zos));

                        return Mono.just(outputStream);
                    } catch (IOException e) {
                        log.error("exception error in process generateReport, error: {}", e.getMessage());
                        return Mono.error(() -> new BusinessProcessException("Error generating zip file",
                            ResponseCode.INTERNAL_SERVER_ERROR));
                    }
                }
            )
            .flatMap(outputStream -> {
                var genericBusinessResponse = new GenericBusinessResponse<>(
                    Base64EncodeResponse.builder()
                        .fileBase64(Base64.getEncoder().encodeToString(outputStream.toByteArray()))
                        .build()
                );
                return Mono.just(genericBusinessResponse);
            })
            .flatMap(genericBusinessResponse -> Mono.just(BusinessProcessResponse.setEntitySuccessfullyResponse(genericBusinessResponse)))
            .doOnSuccess(success -> log.info("Success generateReport, response: {}", success.getResponseCode().getResponseCodeValue()))
            .doOnError(throwable -> log.error("Exception error in process generateReport, error: {}", throwable.getMessage()))
            .log();
    }

    private void generateCsvFile(final String bankCode, final LocalDate date, final ZipOutputStream zos) {
        var csvFileName = String.format("orchestrator-transactions-%s-%s-report-%s.csv",
            appSessionContext.getApplicationSession().getTenantId(),
            isBlank(bankCode) ? "9999" : bankCode,
            getFormattedDate(date));

        log.debug("Generating csv file. bankCode: {}, tenantId: {}, csvFileName: {}",
            bankCode, appSessionContext.getApplicationSession().getTenantId(), csvFileName);

        try {
            zos.putNextEntry(new ZipEntry(csvFileName));
            zos.write(transactionCsvWriter.generateCsv(bankCode, date, date));
            zos.closeEntry();
        } catch (IOException e) {
            log.error("Error generating csv. csvFileName: {}, error: {}", csvFileName, e.getMessage());
            throw Exceptions.propagate(e);
        }
    }

    private String getFormattedDate(final LocalDate date) {
        return date.format(DateTimeFormatter.ofPattern("yyyyMMdd").withZone(ZoneId.systemDefault()));
    }
}