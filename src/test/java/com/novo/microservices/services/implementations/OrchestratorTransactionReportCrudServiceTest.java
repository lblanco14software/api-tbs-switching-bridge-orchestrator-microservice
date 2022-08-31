package com.novo.microservices.services.implementations;

import com.novo.microservices.mocks.OrchestrationTransactionRequestFixture;
import com.novo.microservices.repositories.contracts.IOrchestratorTransactionReportRepository;
import com.novo.microservices.repositories.entities.OrchestratorTransactionReportEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith({MockitoExtension.class})
class OrchestratorTransactionReportCrudServiceTest {
    @InjectMocks
    private OrchestratorTransactionReportCrudService orchestratorTransactionReportCrudService;
    @Mock
    private IOrchestratorTransactionReportRepository reportRepository;

    @Captor
    private ArgumentCaptor<OrchestratorTransactionReportEntity> reportEntityArgumentCaptor;

    @Test
    void saveEntity() {
        var transactionRequest = new OrchestrationTransactionRequestFixture().getInstance();
        when(reportRepository.save(any())).thenReturn(new OrchestratorTransactionReportEntity());
        orchestratorTransactionReportCrudService.saveEntity(transactionRequest).subscribe();
        verify(reportRepository).save(reportEntityArgumentCaptor.capture());
        assertNotNull(reportEntityArgumentCaptor.getValue());
        assertNotNull(reportEntityArgumentCaptor.getValue().getTransactionDate());
        assertEquals(transactionRequest.getPaymentHeader().getBankCode(), reportEntityArgumentCaptor.getValue().getBankCode());
        assertEquals(transactionRequest.getPaymentHeader().getServiceId(), reportEntityArgumentCaptor.getValue().getServiceId());
        assertEquals(transactionRequest.getPaymentHeader().getMessageType(), reportEntityArgumentCaptor.getValue().getMessageType());
        assertEquals(transactionRequest.getPaymentHeader().getTrackingId(), reportEntityArgumentCaptor.getValue().getTrackingId());
        assertEquals(transactionRequest.getTransaction().getMessageTypeIndicator(), reportEntityArgumentCaptor.getValue().getMessageTypeIndicator());
        assertEquals(transactionRequest.getTransaction().getDe4(), reportEntityArgumentCaptor.getValue().getTransactionAmount());
    }
}