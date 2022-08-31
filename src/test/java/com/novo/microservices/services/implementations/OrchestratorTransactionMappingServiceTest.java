package com.novo.microservices.services.implementations;

import com.novo.microservices.components.helpers.SagaTransactionalJsonHelper;
import com.novo.microservices.dtos.OrchestratorTransactionMapping;
import com.novo.microservices.mocks.StandardTransactionRequestFixture;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

/**
 * OrchestratorTransactionMappingServiceTest
 * <p>
 * OrchestratorTransactionMappingServiceTest class.
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
@ExtendWith(MockitoExtension.class)
class OrchestratorTransactionMappingServiceTest {
    private static final String SERVICE_ID = "SERVICE_ID";
    private static final String MESSAGE_TYPE_INDICATOR = "MESSAGE_TYPE_INDICATOR";
    private static final String PROCESSING_CODE = "PROCESSING_CODE";
    private static final String BANK_CODE = "BANK_CODE";

    private static final String DATA_ELEMENT_2_GLOBAL = "DATA_ELEMENT_2_GLOBAL";
    private static final String DATA_ELEMENT_2_GLOBAL_BANK_CODE = "DATA_ELEMENT_2_GLOBAL_BANK_CODE";
    private static final String DATA_ELEMENT_2_TRANSACTION = "DATA_ELEMENT_2_TRANSACTION";
    private static final String DATA_ELEMENT_2_TRANSACTION_BANK_CODE = "DATA_ELEMENT_2_TRANSACTION_BANK_CODE";

    @InjectMocks
    private OrchestratorTransactionMappingService service;

    @Mock
    private SagaTransactionalJsonHelper sagaTransactionalJsonHelper;

    private OrchestratorTransactionMapping buildMapping(String serviceId,
                                                        String messageTypeIndicator,
                                                        String processingCode,
                                                        String bankCode,
                                                        String dataElement2) {
        var mapping = new OrchestratorTransactionMapping();
        mapping.setStandardTransactionRequest(new StandardTransactionRequestFixture().getInstance());
        mapping.setServiceId(serviceId);
        mapping.setMessageTypeIndicator(messageTypeIndicator);
        mapping.setProcessingCode(processingCode);
        mapping.setBankCode(bankCode);
        mapping.getStandardTransactionRequest().setDataElement_2(dataElement2);

        return mapping;
    }

    private List<OrchestratorTransactionMapping> buildMappings() {
        var mappings = new ArrayList<OrchestratorTransactionMapping>();
        mappings.add(buildMapping(null, null, null, null, DATA_ELEMENT_2_GLOBAL));
        mappings.add(buildMapping(null, null, null, BANK_CODE, DATA_ELEMENT_2_GLOBAL_BANK_CODE));
        mappings.add(buildMapping(SERVICE_ID, MESSAGE_TYPE_INDICATOR, PROCESSING_CODE, null, DATA_ELEMENT_2_TRANSACTION));
        mappings.add(buildMapping(SERVICE_ID, MESSAGE_TYPE_INDICATOR, PROCESSING_CODE, BANK_CODE, DATA_ELEMENT_2_TRANSACTION_BANK_CODE));

        return mappings;
    }

    @Test
    void findGlobalMapping() {
        var mappings = buildMappings();
        when(sagaTransactionalJsonHelper.loadTransactionsMappings()).thenReturn(mappings);

        var mapping = service.findGlobalMapping();

        assertTrue(mapping.isPresent());
        assertNull(mapping.get().getServiceId());
        assertNull(mapping.get().getMessageTypeIndicator());
        assertNull(mapping.get().getProcessingCode());
        assertNull(mapping.get().getBankCode());
        assertEquals(DATA_ELEMENT_2_GLOBAL, mapping.get().getStandardTransactionRequest().getDataElement_2());
    }

    @Test
    void findGlobalMappingByBankCode() {
        var mappings = buildMappings();
        when(sagaTransactionalJsonHelper.loadTransactionsMappings()).thenReturn(mappings);

        var mapping = service.findGlobalMappingByBankCode(BANK_CODE);

        assertTrue(mapping.isPresent());
        assertNull(mapping.get().getServiceId());
        assertNull(mapping.get().getMessageTypeIndicator());
        assertNull(mapping.get().getProcessingCode());
        assertEquals(BANK_CODE, mapping.get().getBankCode());
        assertEquals(DATA_ELEMENT_2_GLOBAL_BANK_CODE, mapping.get().getStandardTransactionRequest().getDataElement_2());
    }

    @Test
    void findTransactionMapping() {
        var mappings = buildMappings();
        when(sagaTransactionalJsonHelper.loadTransactionsMappings()).thenReturn(mappings);

        var mapping = service.findTransactionMapping(SERVICE_ID, MESSAGE_TYPE_INDICATOR, PROCESSING_CODE);

        assertTrue(mapping.isPresent());
        assertEquals(SERVICE_ID, mapping.get().getServiceId());
        assertEquals(MESSAGE_TYPE_INDICATOR, mapping.get().getMessageTypeIndicator());
        assertEquals(PROCESSING_CODE, mapping.get().getProcessingCode());
        assertNull(mapping.get().getBankCode());
        assertEquals(DATA_ELEMENT_2_TRANSACTION, mapping.get().getStandardTransactionRequest().getDataElement_2());
    }

    @Test
    void findTransactionMappingByBankCode() {
        var mappings = buildMappings();
        when(sagaTransactionalJsonHelper.loadTransactionsMappings()).thenReturn(mappings);

        var mapping = service.findTransactionMappingByBankCode(SERVICE_ID, MESSAGE_TYPE_INDICATOR, PROCESSING_CODE, BANK_CODE);

        assertTrue(mapping.isPresent());
        assertEquals(SERVICE_ID, mapping.get().getServiceId());
        assertEquals(MESSAGE_TYPE_INDICATOR, mapping.get().getMessageTypeIndicator());
        assertEquals(PROCESSING_CODE, mapping.get().getProcessingCode());
        assertEquals(BANK_CODE, mapping.get().getBankCode());
        assertEquals(DATA_ELEMENT_2_TRANSACTION_BANK_CODE, mapping.get().getStandardTransactionRequest().getDataElement_2());
    }
}