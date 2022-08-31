package com.novo.microservices.repositories.contracts;

import com.novo.microservices.repositories.entities.OrchestratorHsmReportEntity;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Date;
import java.util.List;

/**
 * IOrchestratorHsmReportRepository
 * <p>
 * IOrchestratorHsmReportRepository class.
 * <p>
 * <p>
 * ESTE COMPONENTE FUE CONSTRUIDO SIGUIENDO LOS ESTANDARES DE DESARROLLO Y EL PROCEDIMIENTO
 * DE DESARROLLO DE APLICACIONES DE NOVOPAYMENT Y SE ENCUENTRA PROTEGIDO POR LAS LEYES DE
 * PROPIEDAD INTELECTUAL Y DERECHOS DE AUTOR.
 *
 * @author Novopayment Inc.
 * @author jquiroga@novopayment.com
 * @since 7/14/2022
 */
public interface IOrchestratorHsmReportRepository extends MongoRepository<OrchestratorHsmReportEntity, ObjectId> {
    @Query(
        value = "{ 'id' : { $gt: ?0 }, 'transactionDateRequest' : { $gte: ?1, $lt: ?2 }}",
        exists = true,
        sort = "{ 'id' : 1 }"
    )
    List<OrchestratorHsmReportEntity> findAllByIdGreaterThanAndTransactionDateRequestBetweenAndResponseCodeExistsOrderByIdAsc(ObjectId id, Date startDate, Date endDate, Pageable pageable);
}
