package com.novo.microservices.transactions.mappers.implementations;

import com.novo.microservices.dtos.OrchestratorTransactionStructureDataMapping;
import com.novo.microservices.dtos.StructureDataField;
import com.novo.microservices.dtos.requests.OrchestrationTransactionRequest;
import com.novo.microservices.services.contracts.IOrchestratorTransactionStructureDataMappingService;
import com.novo.microservices.transactions.mappers.contracts.IStandardTransactionStructureDataMapper;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.stereotype.Component;
import postilion.realtime.sdk.message.bitmap.StructuredData;

import java.lang.reflect.InvocationTargetException;

/**
 * StandardTransactionStructureDataMapper
 * <p>
 * StandardTransactionStructureDataMapper class.
 * <p>
 * <p>
 * ESTE COMPONENTE FUE CONSTRUIDO SIGUIENDO LOS ESTANDARES DE DESARROLLO Y EL PROCEDIMIENTO
 * DE DESARROLLO DE APLICACIONES DE NOVOPAYMENT Y SE ENCUENTRA PROTEGIDO POR LAS LEYES DE
 * PROPIEDAD INTELECTUAL Y DERECHOS DE AUTOR.
 *
 * @author Novopayment Inc.
 * @author duhernandez@novopayment.com
 * @since 8/6/22
 */
@Component
@Log4j2
public class StandardTransactionStructureDataMapper implements IStandardTransactionStructureDataMapper {
    private final IOrchestratorTransactionStructureDataMappingService structureDataMappingService;

    public StandardTransactionStructureDataMapper(IOrchestratorTransactionStructureDataMappingService structureDataMappingService) {
        this.structureDataMappingService = structureDataMappingService;
    }

    @Override
    public StructuredData buildCommonStructuredData(OrchestrationTransactionRequest currentOrchestrationTransactionRequest) {
        var serviceId = currentOrchestrationTransactionRequest.getPaymentHeader().getServiceId();
        var messageTypeIndicator = currentOrchestrationTransactionRequest.getTransaction().getMessageTypeIndicator();
        var processingCode = currentOrchestrationTransactionRequest.getTransaction().getDe3();
        var bankCode = currentOrchestrationTransactionRequest.getPaymentHeader().getBankCode();

        var structuredData = new StructuredData();
        log.debug("Applying global default values to request transaction");
        structureDataMappingService.findGlobalMapping().ifPresent(mapping -> applyMapping(structuredData, currentOrchestrationTransactionRequest, mapping));
        log.debug("Applying global default values for a specific bank code to request transaction");
        structureDataMappingService.findGlobalMappingByBankCode(bankCode).ifPresent(mapping -> applyMapping(structuredData, currentOrchestrationTransactionRequest, mapping));
        log.debug("Applying custom default values to request transaction");
        structureDataMappingService.findTransactionMapping(serviceId, messageTypeIndicator, processingCode).ifPresent(mapping -> applyMapping(structuredData, currentOrchestrationTransactionRequest, mapping));
        log.debug("Applying custom default values for a specific bank code to request transaction");
        structureDataMappingService.findTransactionMappingByBankCode(serviceId, messageTypeIndicator, processingCode, bankCode).ifPresent(mapping -> applyMapping(structuredData, currentOrchestrationTransactionRequest, mapping));
        log.debug("Structure data mapping finished");

        log.debug("Structure data: {}", structuredData.toMsgString());

        return structuredData;
    }

    private void applyMapping(final StructuredData structuredData,
                              final OrchestrationTransactionRequest orchestrationTransactionRequest,
                              final OrchestratorTransactionStructureDataMapping mapping) {
        if (mapping.getStructureDataFields() != null && !mapping.getStructureDataFields().isEmpty()) {
            mapping.getStructureDataFields().forEach(field -> {
                String value = null;
                if (StructureDataField.FieldType.CONSTANT.equals(field.getFieldType())) {
                    value = field.getValue();
                } else if (StructureDataField.FieldType.REFERENCE.equals(field.getFieldType())) {
                    value = getValueFromOrchestrationTransactionRequest(orchestrationTransactionRequest, field.getValue());
                }

                // add value to structured data
                if (value != null && !value.trim().isEmpty()) {
                    structuredData.put(field.getKey(), value.trim());
                }
            });
        }
    }

    private String getValueFromOrchestrationTransactionRequest(OrchestrationTransactionRequest currentOrchestrationTransactionRequest, String field) {
        try {
            var value = PropertyUtils.getNestedProperty(currentOrchestrationTransactionRequest, field);
            if (value != null && !value.toString().trim().isEmpty()) {
                return value.toString().trim();
            }
        } catch (IllegalArgumentException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            log.warn("Error getting property {} from OrchestrationTransactionRequest, msg {}", field, e.getMessage());
        }
        return null;
    }
}