package com.novo.microservices.services.implementations;

import com.novo.microservices.components.helpers.CsvWriter;
import com.novo.microservices.components.helpers.TransactionCsvWriter;
import com.novo.microservices.dtos.generics.GenericBusinessResponse;
import com.novo.microservices.dtos.responses.Base64EncodeResponse;
import com.novo.microservices.mocks.ApplicationSessionFixture;
import com.novo.microservices.repositories.contracts.IOrchestratorTransactionReportRepository;
import com.novo.microservices.repositories.entities.OrchestratorTransactionReportEntity;
import com.novo.microservices.tbs.utils.components.exceptions.BusinessProcessException;
import com.novo.microservices.utils.common.context.AppSessionContext;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.test.StepVerifier;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.zip.ZipInputStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * ConciliationReportServiceTest
 * <p>
 * ConciliationReportServiceTest class.
 * <p>
 * <p>
 * ESTE COMPONENTE FUE CONSTRUIDO SIGUIENDO LOS ESTANDARES DE DESARROLLO Y EL PROCEDIMIENTO
 * DE DESARROLLO DE APLICACIONES DE NOVOPAYMENT Y SE ENCUENTRA PROTEGIDO POR LAS LEYES DE
 * PROPIEDAD INTELECTUAL Y DERECHOS DE AUTOR.
 *
 * @author Novopayment Inc.
 * @author duhernandez@novopayment.com
 * @since 4/25/2022
 */
@ExtendWith({MockitoExtension.class})
class ConciliationReportServiceTest {
    @InjectMocks
    private ConciliationReportService reportService;

    @Mock
    private AppSessionContext appSessionContext;

    @Mock
    private IOrchestratorTransactionReportRepository reportRepository;

    @BeforeEach
    void setUp() {
        reportService = new ConciliationReportService(
            appSessionContext,
            new TransactionCsvWriter(new CsvWriter(), reportRepository));
    }

    @Test
    void generateReportWithoutValues() {
        when(reportRepository.findDistinctBankCodes(any(), any())).thenReturn(List.of());
        StepVerifier.create(reportService.generateReport(LocalDate.now()))
            .expectError(BusinessProcessException.class)
            .verify();
    }

    @Test
    void generateReportWithValues() {
        String bankCode = "bankCode";
        OrchestratorTransactionReportEntity entity = new OrchestratorTransactionReportEntity();
        entity.setId(ObjectId.get());
        entity.setBankCode(bankCode);

        when(appSessionContext.getApplicationSession()).thenReturn(new ApplicationSessionFixture().getInstance());
        when(reportRepository.findDistinctBankCodes(any(), any())).thenReturn(List.of(bankCode));
        when(reportRepository.findTransactionsByBankCodeAndDateOrderByIdAsc(
            eq(bankCode),
            any(),
            any(),
            isNull(),
            anyInt())).thenReturn(new ArrayList<>(List.of(entity)));

        StepVerifier.create(reportService.generateReport(LocalDate.now()))
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