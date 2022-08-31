package com.novo.microservices.components.helpers;

import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.novo.microservices.repositories.contracts.IOrchestratorHsmReportRepository;
import com.novo.microservices.repositories.entities.OrchestratorHsmReportEntity;
import org.bson.types.ObjectId;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

/**
 * HsmCsvWriter
 * <p>
 * HsmCsvWriter class.
 * <p>
 * <p>
 * ESTE COMPONENTE FUE CONSTRUIDO SIGUIENDO LOS ESTANDARES DE DESARROLLO Y EL PROCEDIMIENTO
 * DE DESARROLLO DE APLICACIONES DE NOVOPAYMENT Y SE ENCUENTRA PROTEGIDO POR LAS LEYES DE
 * PROPIEDAD INTELECTUAL Y DERECHOS DE AUTOR.
 *
 * @author Novopayment Inc.
 * @author jquiroga@novopayment.com
 * @since 7/14/22
 */
@Component
public class HsmCsvWriter {
    private static final int PAGE_SIZE = 5000;
    private final CsvWriter csvWriter;
    private final IOrchestratorHsmReportRepository hsmReportRepository;

    public HsmCsvWriter(CsvWriter csvWriter, IOrchestratorHsmReportRepository hsmReportRepository) {
        this.csvWriter = csvWriter;
        this.hsmReportRepository = hsmReportRepository;
    }

    public byte[] generateCsv(final LocalDate dateFrom, final LocalDate dateTo) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        CsvSchema csvSchema = buildCsvSchema().withHeader(); // generate the csv with header
        List<OrchestratorHsmReportEntity> transactions = getTransactions(dateFrom, dateTo, null);
        while (!transactions.isEmpty()) {
            outputStream.write(csvWriter.writeObjects(transactions, csvSchema));
            ObjectId lastId = transactions.get(transactions.size() - 1).getId();
            transactions = getTransactions(dateFrom, dateTo, lastId);
            csvSchema = csvSchema.withoutHeader(); // remove the header for the next iteration
        }

        return outputStream.toByteArray();
    }

    private List<OrchestratorHsmReportEntity> getTransactions(
        final LocalDate dateFrom,
        final LocalDate dateTo,
        ObjectId id
    ) {
        Date from = getFromDate(dateFrom);
        Date to = getToDate(dateTo);
        Pageable pageable = PageRequest.of(0, PAGE_SIZE, Sort.by("id").ascending());
        if (id == null) id = ObjectId.getSmallestWithDate(from);
        return hsmReportRepository.findAllByIdGreaterThanAndTransactionDateRequestBetweenAndResponseCodeExistsOrderByIdAsc(id, from, to, pageable);
    }

    private CsvSchema buildCsvSchema() {
        return CsvSchema.builder()
            .addColumn("trackingId")
            .addColumn("responseCode")
            .addColumn("transactionDateRequest")
            .addColumn("transactionDateResponse")
            .build();
    }

    private Date getFromDate(final LocalDate date) {
        return java.sql.Timestamp.valueOf(date.atTime(0, 0, 0));
    }

    private Date getToDate(final LocalDate date) {
        return java.sql.Timestamp.valueOf(date.plusDays(1L).atTime(0, 0, 0));
    }
}