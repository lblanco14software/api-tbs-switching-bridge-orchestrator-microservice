package com.novo.microservices.services.implementations;

import com.novo.microservices.components.helpers.CsvWriter;
import com.novo.microservices.components.helpers.HsmCsvWriter;
import com.novo.microservices.components.helpers.TransactionCsvWriter;
import com.novo.microservices.dtos.generics.GenericBusinessResponse;
import com.novo.microservices.dtos.responses.Base64EncodeResponse;
import com.novo.microservices.mocks.ApplicationSessionFixture;
import com.novo.microservices.repositories.contracts.IOrchestratorHsmReportRepository;
import com.novo.microservices.repositories.contracts.IOrchestratorTransactionReportRepository;
import com.novo.microservices.repositories.entities.OrchestratorHsmReportEntity;
import com.novo.microservices.repositories.entities.OrchestratorTransactionReportEntity;
import com.novo.microservices.utils.common.context.AppSessionContext;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.test.StepVerifier;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.zip.ZipInputStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith({MockitoExtension.class})
class BillingReportServiceTest {
    private BillingReportService reportService;

    @Mock
    private AppSessionContext appSessionContext;

    @Mock
    private IOrchestratorTransactionReportRepository reportRepository;

    @Mock
    private IOrchestratorHsmReportRepository hsmReportRepository;

    @BeforeEach
    void setUp() {
        reportService = new BillingReportService(
            appSessionContext,
            new TransactionCsvWriter(new CsvWriter(), reportRepository),
            new HsmCsvWriter(new CsvWriter(), hsmReportRepository)
        );
    }

    @Test
    void generateReportWithValues() {
        ObjectId objectId = ObjectId.get();
        LocalDate now = LocalDate.now();

        String bankCode = "bankCode";
        OrchestratorTransactionReportEntity entity = new OrchestratorTransactionReportEntity();
        entity.setId(objectId);
        entity.setBankCode(bankCode);

        OrchestratorHsmReportEntity hsmReportEntity = new OrchestratorHsmReportEntity();
        hsmReportEntity.setId(objectId);
        hsmReportEntity.setTrackingId("123456789");
        hsmReportEntity.setResponseCode("200");

        when(appSessionContext.getApplicationSession()).thenReturn(new ApplicationSessionFixture().getInstance());
        when(reportRepository.findDistinctBankCodes(any(), any())).thenReturn(List.of(bankCode));
        when(reportRepository.findTransactionsByBankCodeAndDateOrderByIdAsc(
            eq(bankCode),
            any(),
            any(),
            isNull(),
            anyInt())).thenReturn(new ArrayList<>(List.of(entity)));

        ObjectId objectIdSmallest = ObjectId.getSmallestWithDate(Timestamp.valueOf(now.atTime(0, 0, 0)));
        when(hsmReportRepository.findAllByIdGreaterThanAndTransactionDateRequestBetweenAndResponseCodeExistsOrderByIdAsc(eq(objectIdSmallest), any(), any(), any())).thenReturn(List.of(hsmReportEntity));

        StepVerifier.create(reportService.generateReport(now, LocalDate.now()))
            .assertNext(r -> {
                try {
                    var responseData = ((GenericBusinessResponse<?>) r.getBusinessResponse()).getData();
                    assertNotNull(responseData);
                    var base64 = ((Base64EncodeResponse) responseData).getFileBase64();
                    var zipInputStream = new ZipInputStream(new ByteArrayInputStream(Base64.getDecoder().decode(base64)));
                    var zipEntry = zipInputStream.getNextEntry();
                    assertNotNull(zipEntry); // check if there is a valid zip
                    assertTrue(zipEntry.getName().endsWith(".csv")); // check if the file is a csv
                    var bufferedReader = new BufferedReader(new InputStreamReader(zipInputStream));
                    var csvHeader = "bankCode,messageType,trackingId,serviceId,messageTypeIndicator,transactionAmount,transactionDate";
                    var csvBody = String.join(",", bankCode, "", "", "", "", "", "");
                    assertEquals(bufferedReader.readLine(), csvHeader);
                    assertEquals(bufferedReader.readLine(), csvBody);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            })
            .verifyComplete();
    }
}