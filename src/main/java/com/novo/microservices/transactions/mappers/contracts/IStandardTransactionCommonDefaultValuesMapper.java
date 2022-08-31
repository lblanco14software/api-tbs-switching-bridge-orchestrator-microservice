package com.novo.microservices.transactions.mappers.contracts;

import com.novo.microservices.tbs.utils.dtos.StandardTransaction;

/**
 * IStandardTransactionCommonMapper
 * <p>
 * IStandardTransactionCommonMapper interface.
 * <p>
 * <p>
 * ESTE COMPONENTE FUE CONSTRUIDO SIGUIENDO LOS ESTANDARES DE DESARROLLO Y EL PROCEDIMIENTO
 * DE DESARROLLO DE APLICACIONES DE NOVOPAYMENT Y SE ENCUENTRA PROTEGIDO POR LAS LEYES DE
 * PROPIEDAD INTELECTUAL Y DERECHOS DE AUTOR.
 *
 * @author Novopayment Inc.
 * @author duhernandez@novopayment.com
 * @since 4/4/2022
 */
public interface IStandardTransactionCommonDefaultValuesMapper {
    /**
     * Apply default values to the standard transaction.
     *
     * @param serviceId            service id
     * @param messageTypeIndicator message type indicator
     * @param processingCode       processing code
     * @param bankCode             bank code
     * @param standardTransaction  standard transaction
     */
    void applyDefaultValuesToRequestTransaction(final String serviceId,
                            final String messageTypeIndicator,
                            final String processingCode,
                            final String bankCode,
                            final StandardTransaction standardTransaction);

    void applyDefaultValuesToResponseTransaction(final String serviceId,
                                                final String messageTypeIndicator,
                                                final String processingCode,
                                                final String bankCode,
                                                final StandardTransaction standardTransaction);
}