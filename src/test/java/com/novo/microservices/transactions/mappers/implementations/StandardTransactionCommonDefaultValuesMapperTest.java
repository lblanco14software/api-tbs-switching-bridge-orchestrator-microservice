package com.novo.microservices.transactions.mappers.implementations;

import com.novo.microservices.dtos.OrchestratorTransactionMapping;
import com.novo.microservices.mocks.StandardTransactionDefaultValuesMapperMockImpl;
import com.novo.microservices.mocks.StandardTransactionRequestFixture;
import com.novo.microservices.services.contracts.IOrchestratorTransactionMappingService;
import com.novo.microservices.tbs.utils.dtos.StandardTransaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StandardTransactionCommonDefaultValuesMapperTest {
    private StandardTransactionCommonDefaultValuesMapper standardTransactionCommonDefaultValuesMapper;
    @Mock
    private IOrchestratorTransactionMappingService requestMappingRepository;
    private OrchestratorTransactionMapping globalMapping;
    private OrchestratorTransactionMapping globalMappingByBankCode;
    private OrchestratorTransactionMapping transactionMapping;
    private OrchestratorTransactionMapping transactionMappingByBankCode;

    @BeforeEach
    void setUp() {
        standardTransactionCommonDefaultValuesMapper = new StandardTransactionCommonDefaultValuesMapper(
            new StandardTransactionDefaultValuesMapperMockImpl(),
            requestMappingRepository);

        globalMapping = getRequestMapping("messageTypeIndic@torGLOBAL");
        globalMappingByBankCode = getRequestMapping("messageTypeIndic@torGLOBAL_BY_BANK_CODE");
        transactionMapping = getRequestMapping("messageTypeIndic@torTRANSACTION");
        transactionMappingByBankCode = getRequestMapping("messageTypeIndic@torTRANSACTION_BY_BANK_CODE");
    }

    private OrchestratorTransactionMapping getRequestMapping(String messageTypeIndicator) {
        var requestMapping = new OrchestratorTransactionMapping();
        var standardTransaction = StandardTransaction.builder()
            .messageTypeIndicator(messageTypeIndicator)
            .build();
        requestMapping.setStandardTransactionRequest(standardTransaction);
        requestMapping.setStandardTransactionResponse(standardTransaction);
        return requestMapping;
    }

    @Test
    void applyDefaultGlobalValues() {
        when(requestMappingRepository.findGlobalMapping()).thenReturn(Optional.ofNullable(globalMapping));
        when(requestMappingRepository.findGlobalMappingByBankCode(anyString())).thenReturn(Optional.empty());
        when(requestMappingRepository.findTransactionMapping(anyString(), anyString(), anyString())).thenReturn(Optional.empty());
        when(requestMappingRepository.findTransactionMappingByBankCode(anyString(), anyString(), anyString(), anyString())).thenReturn(Optional.empty());

        var standardTransaction = new StandardTransactionRequestFixture().getInstance();

        assertNotEquals(standardTransaction.getMessageTypeIndicator(), globalMapping.getStandardTransactionRequest().getMessageTypeIndicator());

        standardTransactionCommonDefaultValuesMapper.applyDefaultValuesToRequestTransaction("serviceId",
            "messageTypeIndicator",
            "processingCode",
            "bankCode",
            standardTransaction);

        assertEquals(standardTransaction.getMessageTypeIndicator(), globalMapping.getStandardTransactionRequest().getMessageTypeIndicator());
    }

    @Test
    void applyDefaultGlobalValuesForResponse() {
        when(requestMappingRepository.findGlobalMapping()).thenReturn(Optional.ofNullable(globalMapping));
        when(requestMappingRepository.findGlobalMappingByBankCode(anyString())).thenReturn(Optional.empty());
        when(requestMappingRepository.findTransactionMapping(anyString(), anyString(), anyString())).thenReturn(Optional.empty());
        when(requestMappingRepository.findTransactionMappingByBankCode(anyString(), anyString(), anyString(), anyString())).thenReturn(Optional.empty());

        var standardTransaction = new StandardTransactionRequestFixture().getInstance();

        assertNotEquals(standardTransaction.getMessageTypeIndicator(), globalMapping.getStandardTransactionResponse().getMessageTypeIndicator());

        standardTransactionCommonDefaultValuesMapper.applyDefaultValuesToResponseTransaction("serviceId",
            "messageTypeIndicator",
            "processingCode",
            "bankCode",
            standardTransaction);

        assertEquals(standardTransaction.getMessageTypeIndicator(), globalMapping.getStandardTransactionResponse().getMessageTypeIndicator());
    }

    @Test
    void applyDefaultGlobalByBankCodeValues() {
        when(requestMappingRepository.findGlobalMapping()).thenReturn(Optional.ofNullable(globalMapping));
        when(requestMappingRepository.findGlobalMappingByBankCode(anyString())).thenReturn(Optional.ofNullable(globalMappingByBankCode));
        when(requestMappingRepository.findTransactionMapping(anyString(), anyString(), anyString())).thenReturn(Optional.empty());
        when(requestMappingRepository.findTransactionMappingByBankCode(anyString(), anyString(), anyString(), anyString())).thenReturn(Optional.empty());

        var standardTransaction = new StandardTransactionRequestFixture().getInstance();

        assertNotEquals(standardTransaction.getMessageTypeIndicator(), globalMappingByBankCode.getStandardTransactionRequest().getMessageTypeIndicator());

        standardTransactionCommonDefaultValuesMapper.applyDefaultValuesToRequestTransaction("serviceId",
            "messageTypeIndicator",
            "processingCode",
            "bankCode",
            standardTransaction);

        assertEquals(standardTransaction.getMessageTypeIndicator(), globalMappingByBankCode.getStandardTransactionRequest().getMessageTypeIndicator());
    }

    @Test
    void applyDefaultGlobalByBankCodeValuesForResponse() {
        when(requestMappingRepository.findGlobalMapping()).thenReturn(Optional.ofNullable(globalMapping));
        when(requestMappingRepository.findGlobalMappingByBankCode(anyString())).thenReturn(Optional.ofNullable(globalMappingByBankCode));
        when(requestMappingRepository.findTransactionMapping(anyString(), anyString(), anyString())).thenReturn(Optional.empty());
        when(requestMappingRepository.findTransactionMappingByBankCode(anyString(), anyString(), anyString(), anyString())).thenReturn(Optional.empty());

        var standardTransaction = new StandardTransactionRequestFixture().getInstance();

        assertNotEquals(standardTransaction.getMessageTypeIndicator(), globalMappingByBankCode.getStandardTransactionResponse().getMessageTypeIndicator());

        standardTransactionCommonDefaultValuesMapper.applyDefaultValuesToResponseTransaction("serviceId",
            "messageTypeIndicator",
            "processingCode",
            "bankCode",
            standardTransaction);

        assertEquals(standardTransaction.getMessageTypeIndicator(), globalMappingByBankCode.getStandardTransactionResponse().getMessageTypeIndicator());
    }

    @Test
    void applyDefaultTransactionValues() {
        when(requestMappingRepository.findGlobalMapping()).thenReturn(Optional.ofNullable(globalMapping));
        when(requestMappingRepository.findGlobalMappingByBankCode(anyString())).thenReturn(Optional.ofNullable(globalMappingByBankCode));
        when(requestMappingRepository.findTransactionMapping(anyString(), anyString(), anyString())).thenReturn(Optional.ofNullable(transactionMapping));
        when(requestMappingRepository.findTransactionMappingByBankCode(anyString(), anyString(), anyString(), anyString())).thenReturn(Optional.empty());

        var standardTransaction = new StandardTransactionRequestFixture().getInstance();

        assertNotEquals(standardTransaction.getMessageTypeIndicator(), transactionMapping.getStandardTransactionRequest().getMessageTypeIndicator());

        standardTransactionCommonDefaultValuesMapper.applyDefaultValuesToRequestTransaction("serviceId",
            "messageTypeIndicator",
            "processingCode",
            "bankCode",
            standardTransaction);

        assertEquals(standardTransaction.getMessageTypeIndicator(), transactionMapping.getStandardTransactionRequest().getMessageTypeIndicator());
    }

    @Test
    void applyDefaultTransactionValuesForResponse() {
        when(requestMappingRepository.findGlobalMapping()).thenReturn(Optional.ofNullable(globalMapping));
        when(requestMappingRepository.findGlobalMappingByBankCode(anyString())).thenReturn(Optional.ofNullable(globalMappingByBankCode));
        when(requestMappingRepository.findTransactionMapping(anyString(), anyString(), anyString())).thenReturn(Optional.ofNullable(transactionMapping));
        when(requestMappingRepository.findTransactionMappingByBankCode(anyString(), anyString(), anyString(), anyString())).thenReturn(Optional.empty());

        var standardTransaction = new StandardTransactionRequestFixture().getInstance();

        assertNotEquals(standardTransaction.getMessageTypeIndicator(), transactionMapping.getStandardTransactionResponse().getMessageTypeIndicator());

        standardTransactionCommonDefaultValuesMapper.applyDefaultValuesToResponseTransaction("serviceId",
            "messageTypeIndicator",
            "processingCode",
            "bankCode",
            standardTransaction);

        assertEquals(standardTransaction.getMessageTypeIndicator(), transactionMapping.getStandardTransactionResponse().getMessageTypeIndicator());
    }

    @Test
    void applyDefaultTransactionByBankCodeValues() {
        when(requestMappingRepository.findGlobalMapping()).thenReturn(Optional.ofNullable(globalMapping));
        when(requestMappingRepository.findGlobalMappingByBankCode(anyString())).thenReturn(Optional.ofNullable(globalMappingByBankCode));
        when(requestMappingRepository.findTransactionMapping(anyString(), anyString(), anyString())).thenReturn(Optional.ofNullable(transactionMapping));
        when(requestMappingRepository.findTransactionMappingByBankCode(anyString(), anyString(), anyString(), anyString())).thenReturn(Optional.ofNullable(transactionMappingByBankCode));

        var standardTransaction = new StandardTransactionRequestFixture().getInstance();

        assertNotEquals(standardTransaction.getMessageTypeIndicator(), transactionMappingByBankCode.getStandardTransactionRequest().getMessageTypeIndicator());

        standardTransactionCommonDefaultValuesMapper.applyDefaultValuesToRequestTransaction("serviceId",
            "messageTypeIndicator",
            "processingCode",
            "bankCode",
            standardTransaction);

        assertEquals(standardTransaction.getMessageTypeIndicator(), transactionMappingByBankCode.getStandardTransactionRequest().getMessageTypeIndicator());
    }

    @Test
    void applyDefaultTransactionByBankCodeValuesForResponse() {
        when(requestMappingRepository.findGlobalMapping()).thenReturn(Optional.ofNullable(globalMapping));
        when(requestMappingRepository.findGlobalMappingByBankCode(anyString())).thenReturn(Optional.ofNullable(globalMappingByBankCode));
        when(requestMappingRepository.findTransactionMapping(anyString(), anyString(), anyString())).thenReturn(Optional.ofNullable(transactionMapping));
        when(requestMappingRepository.findTransactionMappingByBankCode(anyString(), anyString(), anyString(), anyString())).thenReturn(Optional.ofNullable(transactionMappingByBankCode));

        var standardTransaction = new StandardTransactionRequestFixture().getInstance();

        assertNotEquals(standardTransaction.getMessageTypeIndicator(), transactionMappingByBankCode.getStandardTransactionResponse().getMessageTypeIndicator());

        standardTransactionCommonDefaultValuesMapper.applyDefaultValuesToResponseTransaction("serviceId",
            "messageTypeIndicator",
            "processingCode",
            "bankCode",
            standardTransaction);

        assertEquals(standardTransaction.getMessageTypeIndicator(), transactionMappingByBankCode.getStandardTransactionResponse().getMessageTypeIndicator());
    }
}