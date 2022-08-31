package com.novo.microservices.components.helpers;

import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.novo.microservices.repositories.contracts.IOrchestratorTransactionReportRepository;
import com.novo.microservices.repositories.entities.OrchestratorTransactionReportEntity;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

/**
 * TransactionCsvWriter
 * <p>
 * TransactionCsvWriter class.
 * <p>
 * <p>
 * ESTE COMPONENTE FUE CONSTRUIDO SIGUIENDO LOS ESTANDARES DE DESARROLLO Y EL PROCEDIMIENTO
 * DE DESARROLLO DE APLICACIONES DE NOVOPAYMENT Y SE ENCUENTRA PROTEGIDO POR LAS LEYES DE
 * PROPIEDAD INTELECTUAL Y DERECHOS DE AUTOR.
 *
 * @author Novopayment Inc.
 * @author duhernandez@novopayment.com
 * @since 13/7/22
 */
@Component
public class TransactionCsvWriter {
    private static final int PAGE_SIZE = 5000;
    private final CsvWriter csvWriter;
    private final IOrchestratorTransactionReportRepository transactionReportRepository;

    public TransactionCsvWriter(CsvWriter csvWriter, IOrchestratorTransactionReportRepository transactionReportRepository) {
        this.csvWriter = csvWriter;
        this.transactionReportRepository = transactionReportRepository;
    }

    public byte[] generateCsv(final String bankCode, final LocalDate dateFrom, final LocalDate dateTo) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        var csvSchema = buildCsvSchema().withHeader(); // generate the csv with header
        var transactions = getTransactions(bankCode, dateFrom, dateTo, null);
        while (!transactions.isEmpty()) {
            outputStream.write(csvWriter.writeObjects(transactions, csvSchema));
            ObjectId lastId = transactions.get(transactions.size() - 1).getId();
            transactions = getTransactions(bankCode, dateFrom, dateTo, lastId);
            csvSchema = csvSchema.withoutHeader(); // remove the header for the next iteration
        }

        return outputStream.toByteArray();
    }

    public List<String> getDistinctBankCodes(final LocalDate dateFrom, final LocalDate dateTo) {
        var from = getFromDate(dateFrom);
        var to = getToDate(dateTo);
        return transactionReportRepository.findDistinctBankCodes(from, to);
    }

    private List<OrchestratorTransactionReportEntity> getTransactions(final String bankCode,
                                                                      final LocalDate dateFrom,
                                                                      final LocalDate dateTo,
                                                                      final ObjectId id) {
        var from = getFromDate(dateFrom);
        var to = getToDate(dateTo);
        return transactionReportRepository.findTransactionsByBankCodeAndDateOrderByIdAsc(bankCode, from, to, id, PAGE_SIZE);
    }

    private CsvSchema buildCsvSchema() {
        return CsvSchema.builder()
            .addColumn("bankCode")
            .addColumn("messageType")
            .addColumn("trackingId")
            .addColumn("serviceId")
            .addColumn("messageTypeIndicator")
            .addColumn("transactionAmount")
            .addColumn("transactionDate")
            .build();
    }

    private Date getFromDate(final LocalDate date) {
        return java.sql.Timestamp.valueOf(date.atTime(0, 0, 0));
    }

    private Date getToDate(final LocalDate date) {
        return java.sql.Timestamp.valueOf(date.plusDays(1L).atTime(0, 0, 0));
    }
}