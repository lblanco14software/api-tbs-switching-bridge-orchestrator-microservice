package com.novo.microservices.components.helpers;

import com.novo.microservices.repositories.contracts.IOrchestratorTransactionReportRepository;
import com.novo.microservices.repositories.entities.OrchestratorTransactionReportEntity;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith({MockitoExtension.class})
class TransactionCsvWriterTest {

    private TransactionCsvWriter transactionCsvWriter;
    @Mock
    private IOrchestratorTransactionReportRepository reportRepository;

    @BeforeEach
    void setUp() {
        transactionCsvWriter = new TransactionCsvWriter(new CsvWriter(), reportRepository);
    }

    @Test
    void generateCsv() throws IOException {
        String bankCode = "bankCode";
        OrchestratorTransactionReportEntity entity = new OrchestratorTransactionReportEntity();
        entity.setId(ObjectId.get());
        entity.setBankCode(bankCode);

        when(reportRepository.findTransactionsByBankCodeAndDateOrderByIdAsc(
            eq(bankCode),
            any(),
            any(),
            isNull(),
            anyInt())).thenReturn(new ArrayList<>(List.of(entity)));
        var csvBytes = transactionCsvWriter.generateCsv(bankCode, LocalDate.now(), LocalDate.now());
        var csvHeader = "bankCode,messageType,trackingId,serviceId,messageTypeIndicator,transactionAmount,transactionDate";
        var csvBody = String.join(",", bankCode, "", "", "", "", "", "");

        var csv = new String(csvBytes, StandardCharsets.UTF_8).split("\\n");

        assertNotNull(csv);
        assertEquals(2, csv.length);
        assertEquals(csvHeader, csv[0]);
        assertEquals(csvBody, csv[1]);
    }

    @Test
    void getDistinctBankCodes() {
        String bankCode = "bankCode";
        when(reportRepository.findDistinctBankCodes(any(), any())).thenReturn(List.of(bankCode));

        var bankCodes = transactionCsvWriter.getDistinctBankCodes(LocalDate.now(), LocalDate.now());
        assertFalse(bankCodes.isEmpty());
        assertEquals(bankCodes.get(0), bankCode);
    }
}