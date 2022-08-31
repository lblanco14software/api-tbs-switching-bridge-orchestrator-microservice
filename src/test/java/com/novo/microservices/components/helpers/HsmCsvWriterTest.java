package com.novo.microservices.components.helpers;

import com.novo.microservices.repositories.contracts.IOrchestratorHsmReportRepository;
import com.novo.microservices.repositories.entities.OrchestratorHsmReportEntity;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith({MockitoExtension.class})
class HsmCsvWriterTest {

    private HsmCsvWriter hsmCsvWriter;
    @Mock
    private IOrchestratorHsmReportRepository reportRepository;

    @BeforeEach
    void setUp() {
        hsmCsvWriter = new HsmCsvWriter(new CsvWriter(), reportRepository);
    }

    @Test
    void generateCsv() throws IOException {
        ObjectId objectId = ObjectId.get();
        LocalDate now = LocalDate.now();

        OrchestratorHsmReportEntity entity = new OrchestratorHsmReportEntity();
        entity.setId(objectId);
        entity.setTrackingId("123456789");
        entity.setResponseCode("200");

        ObjectId objectIdSmallest = ObjectId.getSmallestWithDate(Timestamp.valueOf(now.atTime(0, 0, 0)));
        when(reportRepository.findAllByIdGreaterThanAndTransactionDateRequestBetweenAndResponseCodeExistsOrderByIdAsc(eq(objectIdSmallest), any(), any(), any())).thenReturn(List.of(entity));

        var csvBytes = hsmCsvWriter.generateCsv(LocalDate.now(), LocalDate.now());
        var csvHeader = "trackingId,responseCode,transactionDateRequest,transactionDateResponse";
        var csvBody = String.join(",", "123456789", "200", "", "");

        var csv = new String(csvBytes, StandardCharsets.UTF_8).split("\\n");

        assertNotNull(csv);
        assertEquals(2, csv.length);
        assertEquals(csvHeader, csv[0]);
        assertEquals(csvBody, csv[1]);
    }
}