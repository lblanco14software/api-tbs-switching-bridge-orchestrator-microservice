package com.novo.microservices.components.helpers;

import com.novo.microservices.dtos.*;
import com.novo.microservices.mocks.StandardTransactionRequestFixture;
import com.novo.microservices.services.contracts.IOrchestratorCacheService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

/**
 * SagaTransactionalJsonHelperTest
 * <p>
 * SagaTransactionalJsonHelperTest class.
 * <p>
 * <p>
 * ESTE COMPONENTE FUE CONSTRUIDO SIGUIENDO LOS ESTANDARES DE DESARROLLO Y EL PROCEDIMIENTO
 * DE DESARROLLO DE APLICACIONES DE NOVOPAYMENT Y SE ENCUENTRA PROTEGIDO POR LAS LEYES DE
 * PROPIEDAD INTELECTUAL Y DERECHOS DE AUTOR.
 *
 * @author Novopayment Inc.
 * @author jquiroga@novopayment.com
 * @since 6/27/2022
 */
@ExtendWith(MockitoExtension.class)
class SagaTransactionalJsonHelperTest {
    private static final String SERVICE_ID = "SERVICE_ID";
    private static final String MESSAGE_TYPE_INDICATOR = "MESSAGE_TYPE_INDICATOR";
    private static final String PROCESSING_CODE = "PROCESSING_CODE";
    private static final String BANK_CODE = "BANK_CODE";

    private static final String DATA_ELEMENT_2_GLOBAL = "DATA_ELEMENT_2_GLOBAL";
    private static final String DATA_ELEMENT_2_GLOBAL_BANK_CODE = "DATA_ELEMENT_2_GLOBAL_BANK_CODE";
    private static final String DATA_ELEMENT_2_TRANSACTION = "DATA_ELEMENT_2_TRANSACTION";
    private static final String DATA_ELEMENT_2_TRANSACTION_BANK_CODE = "DATA_ELEMENT_2_TRANSACTION_BANK_CODE";

    @InjectMocks
    private SagaTransactionalJsonHelper sagaTransactionalJsonHelper;

    @Mock
    private IOrchestratorCacheService componentCacheManagerService;

    @Mock
    private CacheProcessResponse<List<OrchestratorTransactionMapping>> transactionCacheProcessResponse;

    @Mock
    private CacheProcessResponse<List<OrchestratorTransactionStructureDataMapping>> structureDataCacheProcessResponse;

    private OrchestratorTransactionMapping transactionBuildMapping(String serviceId, String messageTypeIndicator, String processingCode, String bankCode, String dataElement2) {
        OrchestratorTransactionMapping mapping = new OrchestratorTransactionMapping();
        mapping.setStandardTransactionRequest(new StandardTransactionRequestFixture().getInstance());
        mapping.setServiceId(serviceId);
        mapping.setMessageTypeIndicator(messageTypeIndicator);
        mapping.setProcessingCode(processingCode);
        mapping.setBankCode(bankCode);
        mapping.getStandardTransactionRequest().setDataElement_2(dataElement2);

        return mapping;
    }

    private List<OrchestratorTransactionMapping> transactionBuildMappings() {
        List<OrchestratorTransactionMapping> mappings = new ArrayList<>();
        mappings.add(transactionBuildMapping(null, null, null, null, DATA_ELEMENT_2_GLOBAL));
        mappings.add(transactionBuildMapping(null, null, null, BANK_CODE, DATA_ELEMENT_2_GLOBAL_BANK_CODE));
        mappings.add(transactionBuildMapping(SERVICE_ID, MESSAGE_TYPE_INDICATOR, PROCESSING_CODE, null, DATA_ELEMENT_2_TRANSACTION));
        mappings.add(transactionBuildMapping(SERVICE_ID, MESSAGE_TYPE_INDICATOR, PROCESSING_CODE, BANK_CODE, DATA_ELEMENT_2_TRANSACTION_BANK_CODE));

        return mappings;
    }

    private OrchestratorTransactionStructureDataMapping structureDataBuildMapping(String serviceId, String messageTypeIndicator, String processingCode, String bankCode, String dataElement2) {
        OrchestratorTransactionStructureDataMapping mapping = new OrchestratorTransactionStructureDataMapping();

        List<StructureDataField> fields = new ArrayList<>();
        StructureDataField field = new StructureDataField();
        field.setKey("key");
        field.setValue(dataElement2);
        field.setFieldType(StructureDataField.FieldType.CONSTANT);
        fields.add(field);

        mapping.setStructureDataFields(fields);
        mapping.setServiceId(serviceId);
        mapping.setMessageTypeIndicator(messageTypeIndicator);
        mapping.setProcessingCode(processingCode);
        mapping.setBankCode(bankCode);

        return mapping;
    }

    private List<OrchestratorTransactionStructureDataMapping> structureDataBuildMappings() {
        var mappings = new ArrayList<OrchestratorTransactionStructureDataMapping>();
        mappings.add(structureDataBuildMapping(null, null, null, null, DATA_ELEMENT_2_GLOBAL));
        mappings.add(structureDataBuildMapping(null, null, null, BANK_CODE, DATA_ELEMENT_2_GLOBAL_BANK_CODE));
        mappings.add(structureDataBuildMapping(SERVICE_ID, MESSAGE_TYPE_INDICATOR, PROCESSING_CODE, null, DATA_ELEMENT_2_TRANSACTION));
        mappings.add(structureDataBuildMapping(SERVICE_ID, MESSAGE_TYPE_INDICATOR, PROCESSING_CODE, BANK_CODE, DATA_ELEMENT_2_TRANSACTION_BANK_CODE));

        return mappings;
    }

    @Test
    void findGlobalMapping() {
        List<OrchestratorTransactionMapping> mappings = transactionBuildMappings();
        when(componentCacheManagerService.findDataFromCache(Mockito.<CacheProcessRequest<List<OrchestratorTransactionMapping>>>any())).thenReturn(transactionCacheProcessResponse);
        when(transactionCacheProcessResponse.isCachePresent()).thenReturn(Boolean.TRUE);
        when(transactionCacheProcessResponse.getCacheEntity()).thenReturn(mappings);

        List<OrchestratorTransactionMapping> mapping = sagaTransactionalJsonHelper.loadTransactionsMappings();

        assertEquals(mappings.size(), mapping.size());
        assertEquals(DATA_ELEMENT_2_GLOBAL, mapping.get(0).getStandardTransactionRequest().getDataElement_2());
    }

    @Test
    void loadTransactionsStructureDataMappings() {
        List<OrchestratorTransactionStructureDataMapping> mappings = structureDataBuildMappings();
        when(componentCacheManagerService.findDataFromCache(Mockito.<CacheProcessRequest<List<OrchestratorTransactionStructureDataMapping>>>any())).thenReturn(structureDataCacheProcessResponse);
        when(structureDataCacheProcessResponse.isCachePresent()).thenReturn(Boolean.TRUE);
        when(structureDataCacheProcessResponse.getCacheEntity()).thenReturn(mappings);

        List<OrchestratorTransactionStructureDataMapping> mapping = sagaTransactionalJsonHelper.loadTransactionsStructureDataMappings();

        assertEquals(mappings.size(), mapping.size());
        assertEquals(DATA_ELEMENT_2_GLOBAL, mapping.get(0).getStructureDataFields().get(0).getValue());
    }
}