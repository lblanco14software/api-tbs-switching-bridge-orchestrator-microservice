package com.novo.microservices.repositories.contracts;

import com.novo.microservices.repositories.entities.OrchestratorTransactionReportEntity;
import org.bson.types.ObjectId;

import java.util.Date;
import java.util.List;

/**
 * IOrchestratorTransactionRepository
 * <p>
 * IOrchestratorTransactionRepository class.
 * <p>
 * <p>
 * ESTE COMPONENTE FUE CONSTRUIDO SIGUIENDO LOS ESTANDARES DE DESARROLLO Y EL PROCEDIMIENTO
 * DE DESARROLLO DE APLICACIONES DE NOVOPAYMENT Y SE ENCUENTRA PROTEGIDO POR LAS LEYES DE
 * PROPIEDAD INTELECTUAL Y DERECHOS DE AUTOR.
 *
 * @author Novopayment Inc.
 * @author duhernandez@novopayment.com
 * @since 4/11/2022
 */
public interface IOrchestratorTransactionReportRepository {

    /***
     * save orchestrator transaction report entity
     * @param entity OrchestratorTransactionReportEntity entity to save in mongo
     * @return {@link OrchestratorTransactionReportEntity} OrchestratorTransactionReportEntity entity result
     */
    OrchestratorTransactionReportEntity save(OrchestratorTransactionReportEntity entity);

    /***
     * Retrieve transactions for a specific bankCode, id greater than gtId and transaction date between from and to dates (from inclusive, to exclusive) order by id asc
     * @param bankCode bankCode
     * @param fromDate fromDate inclusive
     * @param toDate toDate exclusive
     * @param gtId id greater than gtId
     * @param limit number of records to retrieve
     * @return {@link List<OrchestratorTransactionReportEntity>} return list of TransactionReportEntity
     */
    List<OrchestratorTransactionReportEntity> findTransactionsByBankCodeAndDateOrderByIdAsc(final String bankCode, final Date fromDate, final Date toDate, final ObjectId gtId, final int limit);

    /***
     * find distinct bank codes
     * Find distinct bank codes for a specific date range
     * @return {@link List<String>} return list of string
     */
    List<String> findDistinctBankCodes(final Date fromDate, final Date toDate);
}