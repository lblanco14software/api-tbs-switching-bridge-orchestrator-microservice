package com.novo.microservices.services.implementations;

import com.novo.microservices.components.helpers.SagaTransactionalJsonHelper;
import com.novo.microservices.dtos.OrchestratorTransactionStructureDataMapping;
import com.novo.microservices.services.contracts.IOrchestratorTransactionStructureDataMappingService;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * OrchestratorTransactionRequestMappingRepository
 * <p>
 * OrchestratorTransactionRequestMappingRepository class.
 * <p>
 * <p>
 * ESTE COMPONENTE FUE CONSTRUIDO SIGUIENDO LOS ESTANDARES DE DESARROLLO Y EL PROCEDIMIENTO
 * DE DESARROLLO DE APLICACIONES DE NOVOPAYMENT Y SE ENCUENTRA PROTEGIDO POR LAS LEYES DE
 * PROPIEDAD INTELECTUAL Y DERECHOS DE AUTOR.
 *
 * @author Novopayment Inc.
 * @author duhernandez@novopayment.com
 * @since 2/6/22
 */
@Service
@Log4j2
public class OrchestratorTransactionStructureDataMappingService implements IOrchestratorTransactionStructureDataMappingService {
    private final SagaTransactionalJsonHelper sagaTransactionalJsonHelper;

    public OrchestratorTransactionStructureDataMappingService(SagaTransactionalJsonHelper sagaTransactionalJsonHelper) {
        this.sagaTransactionalJsonHelper = sagaTransactionalJsonHelper;
    }

    @Override
    public Optional<OrchestratorTransactionStructureDataMapping> findGlobalMapping() {
        return sagaTransactionalJsonHelper.loadTransactionsStructureDataMappings()
            .stream()
            .filter(p -> StringUtils.isBlank(p.getServiceId()))
            .filter(p -> StringUtils.isBlank(p.getMessageTypeIndicator()))
            .filter(p -> StringUtils.isBlank(p.getProcessingCode()))
            .filter(p -> StringUtils.isBlank(p.getBankCode()))
            .findFirst();
    }

    @Override
    public Optional<OrchestratorTransactionStructureDataMapping> findGlobalMappingByBankCode(String bankCode) {
        return sagaTransactionalJsonHelper.loadTransactionsStructureDataMappings()
            .stream()
            .filter(p -> StringUtils.isBlank(p.getServiceId()))
            .filter(p -> StringUtils.isBlank(p.getMessageTypeIndicator()))
            .filter(p -> StringUtils.isBlank(p.getProcessingCode()))
            .filter(p -> StringUtils.trimToEmpty(bankCode).equals(p.getBankCode()))
            .findFirst();
    }

    @Override
    public Optional<OrchestratorTransactionStructureDataMapping> findTransactionMapping(String serviceId, String messageTypeIndicator, String processingCode) {
        return sagaTransactionalJsonHelper.loadTransactionsStructureDataMappings()
            .stream()
            .filter(p -> StringUtils.trimToEmpty(serviceId).equals(p.getServiceId()))
            .filter(p -> StringUtils.trimToEmpty(messageTypeIndicator).equals(p.getMessageTypeIndicator()))
            .filter(p -> StringUtils.trimToEmpty(processingCode).equals(p.getProcessingCode()))
            .filter(p -> StringUtils.isBlank(p.getBankCode()))
            .findFirst();
    }

    @Override
    public Optional<OrchestratorTransactionStructureDataMapping> findTransactionMappingByBankCode(String serviceId, String messageTypeIndicator, String processingCode, String bankCode) {
        return sagaTransactionalJsonHelper.loadTransactionsStructureDataMappings()
            .stream()
            .filter(p -> StringUtils.trimToEmpty(serviceId).equals(p.getServiceId()))
            .filter(p -> StringUtils.trimToEmpty(messageTypeIndicator).equals(p.getMessageTypeIndicator()))
            .filter(p -> StringUtils.trimToEmpty(processingCode).equals(p.getProcessingCode()))
            .filter(p -> StringUtils.trimToEmpty(bankCode).equals(p.getBankCode()))
            .findFirst();
    }
}