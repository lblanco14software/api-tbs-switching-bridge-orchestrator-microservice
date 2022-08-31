package com.novo.microservices.controllers.implementations;

import com.novo.microservices.components.enums.ResponseCode;
import com.novo.microservices.components.helpers.ContextInformationService;
import com.novo.microservices.components.helpers.ControllerLoggerHelper;
import com.novo.microservices.components.helpers.DocumentationMicroservice;
import com.novo.microservices.dtos.base.BaseBusinessResponseDto;
import com.novo.microservices.dtos.base.BusinessResponse;
import com.novo.microservices.services.contracts.IBillingReportService;
import com.novo.microservices.tbs.utils.components.exceptions.BusinessProcessException;
import com.novo.microservices.utils.common.context.AppSessionContext;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

import static com.novo.microservices.constants.BaseInterceptorConstants.HEADER_REQUEST_ID;
import static com.novo.microservices.constants.BaseInterceptorConstants.HEADER_TENANT_ID;
import static com.novo.microservices.constants.ProcessConstants.GENERATE_BILLING_REPORT_PATH;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

@ExtendWith(SpringExtension.class)
@WebFluxTest(controllers = BillingReportController.class)
@Import({
    AppSessionContext.class,
    ContextInformationService.class,
    DocumentationMicroservice.class,
    BusinessResponse.class
})
class BillingReportControllerTest {
    private static final String TENANT_ID = "tenant-id";
    @Autowired
    private WebTestClient webClient;

    @MockBean
    private IBillingReportService reportService;

    @MockBean
    private ControllerLoggerHelper controllerLoggerHelper;

    @Test
    void generateReportWithInvalidDate() {
        webClient.get()
            .uri(uriBuilder -> uriBuilder
                .path(GENERATE_BILLING_REPORT_PATH)
                .build(
                    LocalDate.now().plusDays(1).format(DateTimeFormatter.ofPattern("yyyyMMdd").withZone(ZoneId.systemDefault())),
                    LocalDate.now().plusDays(1).format(DateTimeFormatter.ofPattern("yyyyMMdd").withZone(ZoneId.systemDefault()))
                )
            )
            .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
            .header(HEADER_TENANT_ID, TENANT_ID)
            .header(HEADER_REQUEST_ID, UUID.randomUUID().toString())
            .exchange()
            .expectStatus().is4xxClientError()
            .expectBody(BaseBusinessResponseDto.class)
            .value(response -> assertEquals("400.00.396", response.getMessageResponseCode()));
    }

    @Test
    void generateReportWithInternalServerError() {
        doReturn(Mono.error(new RuntimeException("Internal Server Error")))
            .when(reportService).generateReport(any(), any());

        webClient.get()
            .uri(uriBuilder -> uriBuilder
                .path(GENERATE_BILLING_REPORT_PATH)
                .build(
                    LocalDate.now().plusDays(-10).format(DateTimeFormatter.ofPattern("yyyyMMdd").withZone(ZoneId.systemDefault())),
                    LocalDate.now().plusDays(-10).format(DateTimeFormatter.ofPattern("yyyyMMdd").withZone(ZoneId.systemDefault()))
                )
            )
            .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
            .header(HEADER_TENANT_ID, TENANT_ID)
            .header(HEADER_REQUEST_ID, UUID.randomUUID().toString())
            .exchange()
            .expectStatus().is5xxServerError()
            .expectBody(BaseBusinessResponseDto.class)
            .value(response -> assertEquals("500.00.999", response.getMessageResponseCode()));
    }

    @Test
    void generateReportWithBusinessError() {
        doReturn(Mono.error(new BusinessProcessException(ResponseCode.ERROR_TENANT_NOT_FOUND)))
            .when(reportService).generateReport(any(), any());

        webClient.get()
            .uri(uriBuilder -> uriBuilder
                .path(GENERATE_BILLING_REPORT_PATH)
                .build(
                    LocalDate.now().plusDays(-10).format(DateTimeFormatter.ofPattern("yyyyMMdd").withZone(ZoneId.systemDefault())),
                    LocalDate.now().plusDays(-10).format(DateTimeFormatter.ofPattern("yyyyMMdd").withZone(ZoneId.systemDefault()))
                )
            )
            .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
            .header(HEADER_TENANT_ID, TENANT_ID)
            .header(HEADER_REQUEST_ID, UUID.randomUUID().toString())
            .exchange()
            .expectStatus().is4xxClientError()
            .expectBody(BaseBusinessResponseDto.class)
            .value(response -> assertEquals("400.00.357", response.getMessageResponseCode()));
    }

}