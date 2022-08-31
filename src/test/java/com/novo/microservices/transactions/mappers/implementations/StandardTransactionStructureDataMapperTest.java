package com.novo.microservices.transactions.mappers.implementations;

import com.novo.microservices.dtos.OrchestratorTransactionStructureDataMapping;
import com.novo.microservices.dtos.StructureDataField;
import com.novo.microservices.dtos.requests.OrchestrationTransactionRequest;
import com.novo.microservices.mocks.OrchestrationTransactionRequestFixture;
import com.novo.microservices.services.contracts.IOrchestratorTransactionStructureDataMappingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StandardTransactionStructureDataMapperTest {
    private static final String IP_ADDRESS_GLOBAL = "IP_ADDRESS_GLOBAL";
    private static final String IP_ADDRESS_GLOBAL_BY_BANK_CODE = "IP_ADDRESS_GLOBAL_BY_BANK_CODE";
    private static final String IP_ADDRESS_TRANSACTION = "IP_ADDRESS_TRANSACTION";
    private static final String IP_ADDRESS_TRANSACTION_BY_BANK_CODE = "IP_ADDRESS_TRANSACTION_BY_BANK_CODE";
    private static final String SERVICE_ID = "serviceId";
    private static final String IP_ADDRESS = "ipAddress";

    @InjectMocks
    private StandardTransactionStructureDataMapper mapper;
    @Mock
    IOrchestratorTransactionStructureDataMappingService mappingRepository;

    private OrchestrationTransactionRequest transactionRequest;
    private OrchestratorTransactionStructureDataMapping globalMapping;
    private OrchestratorTransactionStructureDataMapping globalMappingByBankCode;
    private OrchestratorTransactionStructureDataMapping transactionMapping;
    private OrchestratorTransactionStructureDataMapping transactionMappingByBankCode;

    @BeforeEach
    void setUp() {
        transactionRequest = new OrchestrationTransactionRequestFixture().getInstance();
        globalMapping = getRequestMapping(IP_ADDRESS_GLOBAL);
        globalMappingByBankCode = getRequestMapping(IP_ADDRESS_GLOBAL_BY_BANK_CODE);
        transactionMapping = getRequestMapping(IP_ADDRESS_TRANSACTION);
        transactionMappingByBankCode = getRequestMapping(IP_ADDRESS_TRANSACTION_BY_BANK_CODE);
    }

    private OrchestratorTransactionStructureDataMapping getRequestMapping(String ipAddress) {
        transactionRequest.getTransaction().setDe126(null);
        var requestMapping = new OrchestratorTransactionStructureDataMapping();

        List<StructureDataField> fields = new ArrayList<>();
        // field 1
        fields.add(new StructureDataField(SERVICE_ID, "paymentHeader.serviceId", StructureDataField.FieldType.REFERENCE));
        // field 2
        fields.add(new StructureDataField(IP_ADDRESS, ipAddress, StructureDataField.FieldType.CONSTANT));
        // field 3
        fields.add(new StructureDataField("NULL_CONSTANT", null, StructureDataField.FieldType.CONSTANT));
        // field 4
        fields.add(new StructureDataField("NULL_REFERENCE", "transaction.de126", StructureDataField.FieldType.REFERENCE));

        requestMapping.setStructureDataFields(fields);

        return requestMapping;
    }

    @Test
    void applyDefaultGlobalValues() {
        when(mappingRepository.findGlobalMapping()).thenReturn(Optional.ofNullable(globalMapping));
        when(mappingRepository.findGlobalMappingByBankCode(anyString())).thenReturn(Optional.empty());
        when(mappingRepository.findTransactionMapping(anyString(), anyString(), anyString())).thenReturn(Optional.empty());
        when(mappingRepository.findTransactionMappingByBankCode(anyString(), anyString(), anyString(), anyString())).thenReturn(Optional.empty());

        var structuredData = mapper.buildCommonStructuredData(transactionRequest);

        assertEquals(transactionRequest.getPaymentHeader().getServiceId(), structuredData.get(SERVICE_ID));
        assertEquals(IP_ADDRESS_GLOBAL, structuredData.get(IP_ADDRESS));
        assertNull(structuredData.get("NULL_CONSTANT"));
        assertNull(structuredData.get("NULL_REFERENCE"));
    }

    @Test
    void applyDefaultGlobalByBankCodeValues() {
        when(mappingRepository.findGlobalMapping()).thenReturn(Optional.ofNullable(globalMapping));
        when(mappingRepository.findGlobalMappingByBankCode(anyString())).thenReturn(Optional.ofNullable(globalMappingByBankCode));
        when(mappingRepository.findTransactionMapping(anyString(), anyString(), anyString())).thenReturn(Optional.empty());
        when(mappingRepository.findTransactionMappingByBankCode(anyString(), anyString(), anyString(), anyString())).thenReturn(Optional.empty());

        var structuredData = mapper.buildCommonStructuredData(transactionRequest);

        assertEquals(transactionRequest.getPaymentHeader().getServiceId(), structuredData.get(SERVICE_ID));
        assertEquals(IP_ADDRESS_GLOBAL_BY_BANK_CODE, structuredData.get(IP_ADDRESS));
        assertNull(structuredData.get("NULL_CONSTANT"));
        assertNull(structuredData.get("NULL_REFERENCE"));
    }

    @Test
    void applyDefaultTransactionValues() {
        when(mappingRepository.findGlobalMapping()).thenReturn(Optional.ofNullable(globalMapping));
        when(mappingRepository.findGlobalMappingByBankCode(anyString())).thenReturn(Optional.ofNullable(globalMappingByBankCode));
        when(mappingRepository.findTransactionMapping(anyString(), anyString(), anyString())).thenReturn(Optional.ofNullable(transactionMapping));
        when(mappingRepository.findTransactionMappingByBankCode(anyString(), anyString(), anyString(), anyString())).thenReturn(Optional.empty());

        var structuredData = mapper.buildCommonStructuredData(transactionRequest);

        assertEquals(transactionRequest.getPaymentHeader().getServiceId(), structuredData.get(SERVICE_ID));
        assertEquals(IP_ADDRESS_TRANSACTION, structuredData.get(IP_ADDRESS));
        assertNull(structuredData.get("NULL_CONSTANT"));
        assertNull(structuredData.get("NULL_REFERENCE"));
    }

    @Test
    void applyDefaultTransactionByBankCodeValues() {
        when(mappingRepository.findGlobalMapping()).thenReturn(Optional.ofNullable(globalMapping));
        when(mappingRepository.findGlobalMappingByBankCode(anyString())).thenReturn(Optional.ofNullable(globalMappingByBankCode));
        when(mappingRepository.findTransactionMapping(anyString(), anyString(), anyString())).thenReturn(Optional.ofNullable(transactionMapping));
        when(mappingRepository.findTransactionMappingByBankCode(anyString(), anyString(), anyString(), anyString())).thenReturn(Optional.ofNullable(transactionMappingByBankCode));

        var structuredData = mapper.buildCommonStructuredData(transactionRequest);

        assertEquals(transactionRequest.getPaymentHeader().getServiceId(), structuredData.get(SERVICE_ID));
        assertEquals(IP_ADDRESS_TRANSACTION_BY_BANK_CODE, structuredData.get(IP_ADDRESS));
        assertNull(structuredData.get("NULL_CONSTANT"));
        assertNull(structuredData.get("NULL_REFERENCE"));
    }
}