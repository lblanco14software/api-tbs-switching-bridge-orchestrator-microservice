package com.novo.microservices.transactions.mappers.implementations;

import com.novo.microservices.services.contracts.IOrchestratorTransactionMappingService;
import com.novo.microservices.tbs.utils.dtos.StandardTransaction;
import com.novo.microservices.transactions.mappers.contracts.IStandardTransactionCommonDefaultValuesMapper;
import com.novo.microservices.transactions.mappers.contracts.IStandardTransactionDefaultValuesMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

/**
 * StandardTransactionCommonDefaultValuesMapper
 * <p>
 * StandardTransactionCommonDefaultValuesMapper class.
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
@Component
@Log4j2
public class StandardTransactionCommonDefaultValuesMapper implements IStandardTransactionCommonDefaultValuesMapper {
    private final IStandardTransactionDefaultValuesMapper standardTransactionDefaultValuesMapper;
    private final IOrchestratorTransactionMappingService transactionMappingService;

    public StandardTransactionCommonDefaultValuesMapper(IStandardTransactionDefaultValuesMapper standardTransactionDefaultValuesMapper,
                                                        IOrchestratorTransactionMappingService transactionMappingService) {
        this.standardTransactionDefaultValuesMapper = standardTransactionDefaultValuesMapper;
        this.transactionMappingService = transactionMappingService;
    }


    @Override
    public void applyDefaultValuesToRequestTransaction(String serviceId, String messageTypeIndicator, String processingCode, String bankCode, StandardTransaction standardTransaction) {
        // apply global default values
        log.debug("Applying global default values to request transaction");
        transactionMappingService.findGlobalMapping().ifPresent(globalMapping -> standardTransactionDefaultValuesMapper.applyDefaultValues(standardTransaction, globalMapping.getStandardTransactionRequest()));
        // apply global default values for a specific bank code
        log.debug("Applying global default values for a specific bank code to request transaction");
        transactionMappingService.findGlobalMappingByBankCode(bankCode).ifPresent(globalMapping -> standardTransactionDefaultValuesMapper.applyDefaultValues(standardTransaction, globalMapping.getStandardTransactionRequest()));
        // apply custom default values
        log.debug("Applying custom default values to request transaction");
        transactionMappingService.findTransactionMapping(serviceId, messageTypeIndicator, processingCode).ifPresent(transactionMapping -> standardTransactionDefaultValuesMapper.applyDefaultValues(standardTransaction, transactionMapping.getStandardTransactionRequest()));
        // apply custom default values for a specific bank code
        log.debug("Applying custom default values for a specific bank code to request transaction");
        transactionMappingService.findTransactionMappingByBankCode(serviceId, messageTypeIndicator, processingCode, bankCode).ifPresent(transactionMapping -> standardTransactionDefaultValuesMapper.applyDefaultValues(standardTransaction, transactionMapping.getStandardTransactionRequest()));
        log.debug("Transaction request default values applied");
    }

    @Override
    public void applyDefaultValuesToResponseTransaction(String serviceId, String messageTypeIndicator, String processingCode, String bankCode, StandardTransaction standardTransaction) {
        // apply global default values
        log.debug("Applying global default values to response transaction");
        transactionMappingService.findGlobalMapping().ifPresent(globalMapping -> standardTransactionDefaultValuesMapper.applyDefaultValues(standardTransaction, globalMapping.getStandardTransactionResponse()));
        // apply global default values for a specific bank code
        log.debug("Applying global default values to response transaction for bank code: {}", bankCode);
        transactionMappingService.findGlobalMappingByBankCode(bankCode).ifPresent(globalMapping -> standardTransactionDefaultValuesMapper.applyDefaultValues(standardTransaction, globalMapping.getStandardTransactionResponse()));
        // apply custom default values
        log.debug("Applying custom default values to response transaction");
        transactionMappingService.findTransactionMapping(serviceId, messageTypeIndicator, processingCode).ifPresent(transactionMapping -> standardTransactionDefaultValuesMapper.applyDefaultValues(standardTransaction, transactionMapping.getStandardTransactionResponse()));
        // apply custom default values for a specific bank code
        log.debug("Applying custom default values to response transaction for bank code: {}", bankCode);
        transactionMappingService.findTransactionMappingByBankCode(serviceId, messageTypeIndicator, processingCode, bankCode).ifPresent(transactionMapping -> standardTransactionDefaultValuesMapper.applyDefaultValues(standardTransaction, transactionMapping.getStandardTransactionResponse()));
    }
}