package com.novo.microservices.services.contracts;

import com.novo.microservices.dtos.OrchestratorTransactionStructureDataMapping;

import java.util.Optional;

/**
 * IOrchestratorTransactionRequestMappingRepository
 * <p>
 * IOrchestratorTransactionRequestMappingRepository interface.
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
public interface IOrchestratorTransactionStructureDataMappingService {
    /**
     * Retrieves global transaction mapping
     *
     * @return Transaction Request Mapping if exists
     */
    Optional<OrchestratorTransactionStructureDataMapping> findGlobalMapping();

    /**
     * Retrieves global transaction mapping for a specific bankCode
     *
     * @param bankCode bank code
     * @return Transaction Request Mapping if exists
     */
    Optional<OrchestratorTransactionStructureDataMapping> findGlobalMappingByBankCode(String bankCode);

    /**
     * Retrieves custom transaction mapping for
     *
     * @param serviceId            service id
     * @param messageTypeIndicator message type indicator
     * @param processingCode       processing code
     * @return Transaction Request Mapping if exists
     */
    Optional<OrchestratorTransactionStructureDataMapping> findTransactionMapping(String serviceId, String messageTypeIndicator, String processingCode);

    /**
     * Retrieves custom transaction mapping for a specific bankCode
     *
     * @param serviceId            service id
     * @param messageTypeIndicator message type indicator
     * @param processingCode       processing code
     * @param bankCode             bank code
     * @return Transaction Request Mapping if exists
     */
    Optional<OrchestratorTransactionStructureDataMapping> findTransactionMappingByBankCode(String serviceId, String messageTypeIndicator, String processingCode, String bankCode);
}