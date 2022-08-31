package com.novo.microservices.repositories.implementations;

import com.novo.microservices.repositories.contracts.IOrchestratorTransactionReportRepository;
import com.novo.microservices.repositories.entities.OrchestratorTransactionReportEntity;
import lombok.extern.log4j.Log4j2;
import org.bson.types.ObjectId;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * OrchestratorTransactionReportRepository
 * <p>
 * OrchestratorTransactionReportRepository class.
 * <p>
 * <p>
 * ESTE COMPONENTE FUE CONSTRUIDO SIGUIENDO LOS ESTANDARES DE DESARROLLO Y EL PROCEDIMIENTO
 * DE DESARROLLO DE APLICACIONES DE NOVOPAYMENT Y SE ENCUENTRA PROTEGIDO POR LAS LEYES DE
 * PROPIEDAD INTELECTUAL Y DERECHOS DE AUTOR.
 *
 * @author Novopayment Inc.
 * @author duhernandez@novopayment.com
 * @since 19/5/22
 */
@Repository
@Log4j2
public class OrchestratorTransactionReportRepository implements IOrchestratorTransactionReportRepository {

    private static final String MONGO_FIELD_ID = "id";
    private static final String BANK_CODE = "bankCode";
    private static final String TRANSACTION_DATE = "transactionDate";
    private static final int INIT_PAGE = 0;
    private final MongoOperations mongoOperations;

    public OrchestratorTransactionReportRepository(final MongoOperations mongoOperations) {
        this.mongoOperations = mongoOperations;
    }

    @Override
    public OrchestratorTransactionReportEntity save(OrchestratorTransactionReportEntity entity) {
        return mongoOperations.save(entity);
    }

    @Override
    public List<OrchestratorTransactionReportEntity> findTransactionsByBankCodeAndDateOrderByIdAsc(final String bankCode, final Date fromDate, final Date toDate, final ObjectId gtId, final int limit) {

        final var criteria = Criteria.where(BANK_CODE).is(bankCode).and(TRANSACTION_DATE).gte(fromDate).lt(toDate);

        if (Objects.nonNull(gtId)) {
            criteria.and(MONGO_FIELD_ID).gt(gtId);
        }

        final Query query = Query.query(criteria);
        query.with(PageRequest.of(INIT_PAGE, limit, Sort.Direction.ASC, MONGO_FIELD_ID));
        query.with(Sort.by(Sort.Direction.ASC, MONGO_FIELD_ID));

        log.debug("Getting transactions from OrchestratorTransactionReportEntity. pageSize: {}, bankCode: {}, fromDate: {}, toDate: {}", limit, bankCode, fromDate, toDate);
        return mongoOperations.find(query, OrchestratorTransactionReportEntity.class);
    }

    @Override
    public List<String> findDistinctBankCodes(Date fromDate, Date toDate) {

        final Query query = Query.query(Criteria.where(TRANSACTION_DATE).gte(fromDate).lt(toDate));
        log.debug("Getting distinct bank codes from OrchestratorTransactionReportEntity. fromDate: {}, toDate: {}", fromDate, toDate);
        return mongoOperations.findDistinct(query, BANK_CODE, OrchestratorTransactionReportEntity.class, String.class);
    }
}