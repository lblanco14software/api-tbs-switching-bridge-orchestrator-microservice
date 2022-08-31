package com.novo.microservices.repositories.contracts;

import com.novo.microservices.repositories.entities.OrchestratorTransactionEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

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
 * @author lblanco@novopayment.com
 * @since 4/11/2022
 */
public interface IOrchestratorTransactionRepository extends MongoRepository<OrchestratorTransactionEntity, String> {

}