package com.novo.microservices.services.contracts;

import com.novo.microservices.repositories.entities.OrchestratorHsmReportEntity;
import org.bson.types.ObjectId;
import reactor.core.publisher.Mono;

import java.util.Date;

public interface IHsmReportService {
    Mono<OrchestratorHsmReportEntity> save(OrchestratorHsmReportEntity entity);
    Mono<OrchestratorHsmReportEntity> update(ObjectId id, String responseCode, Date transactionDateResponse);
}
