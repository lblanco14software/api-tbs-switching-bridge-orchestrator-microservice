package com.novo.microservices.services.implementations;

import com.novo.microservices.mocks.OrchestratorTransactionInformationFixture;
import com.novo.microservices.repositories.contracts.IOrchestratorTransactionRepository;
import com.novo.microservices.repositories.entities.OrchestratorTransactionEntity;
import com.novo.microservices.tbs.utils.components.exceptions.SagaProcessException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.test.StepVerifier;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * OrchestratorCrudServiceTest
 * <p>
 * OrchestratorCrudServiceTest class.
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
class OrchestratorCrudServiceTest {

    @InjectMocks
    private OrchestratorCrudService service;

    @Mock
    private IOrchestratorTransactionRepository repository;

    @Captor
    private ArgumentCaptor<OrchestratorTransactionEntity> transactionEntityArgumentCaptor;

    private OrchestratorTransactionEntity entity;

    @BeforeEach
    void setUp() {
        entity = new OrchestratorTransactionEntity();
        entity.setMessageId("messageId");
        entity.setServiceId("serviceId");
        entity.setTransactionEncrypted("transactionEncrypted");
        entity.setProcessingCode("processingCode");
        entity.setMessageTypeIndicator("messageTypeIndicator");
    }

    @Test
    void saveEntity() {

        // Given
        when(repository.save(any())).thenReturn(entity);
        var info = new OrchestratorTransactionInformationFixture().getInstance();

        // When
        service.saveEntity(info).subscribe();

        // Then
        verify(repository).save(transactionEntityArgumentCaptor.capture());
        assertNotNull(transactionEntityArgumentCaptor.getValue());
        assertEquals(info.getMessageId(), transactionEntityArgumentCaptor.getValue().getMessageId());
        assertEquals(info.getServiceId(), transactionEntityArgumentCaptor.getValue().getServiceId());
        assertEquals(info.getProcessingCode(), transactionEntityArgumentCaptor.getValue().getProcessingCode());
        assertEquals(info.getMessageTypeIndicator(), transactionEntityArgumentCaptor.getValue().getMessageTypeIndicator());
        assertEquals(info.getTransactionEncrypted(), transactionEntityArgumentCaptor.getValue().getTransactionEncrypted());
    }

    @Test
    void saveEntityWithError() {

        // Given
        entity.setServiceId(null);
        when(repository.save(any())).thenReturn(entity);

        // When
        var info = new OrchestratorTransactionInformationFixture().getInstance();

        // Then
        StepVerifier.create(service.saveEntity(info))
            .expectError(SagaProcessException.class)
            .verify();
    }

    @Test
    void updateEntity() {

        // Given
        var info = new OrchestratorTransactionInformationFixture().getInstance();
        when(repository.findById(any())).thenReturn(Optional.of(entity));
        when(repository.save(any())).thenReturn(entity);
        info.setTransactionEncrypted("newTransactionEncrypted");

        // When
        service.updateEntity(info).subscribe();

        // Then
        verify(repository).save(transactionEntityArgumentCaptor.capture());
        assertNotNull(transactionEntityArgumentCaptor.getValue());
        assertEquals(info.getTransactionEncrypted(), transactionEntityArgumentCaptor.getValue().getTransactionEncrypted());
    }

    @Test
    void updateEntityWithError() {

        // Given
        var info = new OrchestratorTransactionInformationFixture().getInstance();

        // When
        when(repository.findById(any())).thenReturn(Optional.empty());

        // Then
        StepVerifier.create(service.updateEntity(info))
            .expectError(SagaProcessException.class)
            .verify();
    }

    @Test
    void getDomainEntityById() {


        // Given
        when(repository.findById(any())).thenReturn(Optional.of(entity));

        // Then
        StepVerifier.create(service.getDomainEntityById("entity"))
            .assertNext(r -> {
                    assertEquals(entity.getMessageId(), r.getMessageId());
                    assertEquals(entity.getServiceId(), r.getServiceId());
                    assertEquals(entity.getProcessingCode(), r.getProcessingCode());
                    assertEquals(entity.getTransactionEncrypted(), r.getTransactionEncrypted());
                    assertEquals(entity.getMessageTypeIndicator(), r.getMessageTypeIndicator());
                }
            )
            .verifyComplete();
    }

    @Test
    void getDomainEntityByIdNotExits() {

        // Given
        when(repository.findById(any())).thenReturn(Optional.empty());

        // Then
        StepVerifier.create(service.getDomainEntityById("entity"))
            .expectError(SagaProcessException.class)
            .verify();
    }
}