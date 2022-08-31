package com.novo.microservices.services.implementations;

import com.novo.microservices.repositories.contracts.IOrchestratorHsmReportRepository;
import com.novo.microservices.repositories.entities.OrchestratorHsmReportEntity;
import com.novo.microservices.services.contracts.IHsmReportService;
import com.novo.microservices.tbs.utils.components.exceptions.SagaProcessException;
import lombok.extern.log4j.Log4j2;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Date;
import java.util.UUID;

/**
 * HsmReportService
 * <p>
 * HsmReportService class.
 * <p>
 * <p>
 * ESTE COMPONENTE FUE CONSTRUIDO SIGUIENDO LOS ESTANDARES DE DESARROLLO Y EL PROCEDIMIENTO
 * DE DESARROLLO DE APLICACIONES DE NOVOPAYMENT Y SE ENCUENTRA PROTEGIDO POR LAS LEYES DE
 * PROPIEDAD INTELECTUAL Y DERECHOS DE AUTOR.
 *
 * @author Novopayment Inc.
 * @author jquiroga@novopayment.com
 * @since 7/15/2022
 */
@Service
@Log4j2
public class HsmReportService implements IHsmReportService {
    private final IOrchestratorHsmReportRepository reportRepository;
    private final String hsmReportServiceId;

    @Autowired
    public HsmReportService(IOrchestratorHsmReportRepository reportRepository) {
        this.reportRepository = reportRepository;
        this.hsmReportServiceId = UUID.randomUUID().toString();
    }

    @Override
    public Mono<OrchestratorHsmReportEntity> save(OrchestratorHsmReportEntity entity) {
        log.debug("hsmReportService {}", hsmReportServiceId);
        return Mono.just(reportRepository.save(entity))
            .doOnSuccess(success -> log.debug("success saveHsmReportEntity from HsmReportService"))
            .doOnError(throwable -> log.error("exception error in process saveHsmReportEntity from HsmReportService"))
            .log();
    }

    @Override
    public Mono<OrchestratorHsmReportEntity> update(ObjectId id, String responseCode, Date transactionDateResponse) {
        log.debug("hsmReportService {}", hsmReportServiceId);
        return Mono.just(reportRepository.findById(id))
            .flatMap(optionalEntity -> {
                if (optionalEntity.isPresent()) {
                    OrchestratorHsmReportEntity entity = optionalEntity.get();
                    entity.setResponseCode(responseCode);
                    entity.setTransactionDateResponse(transactionDateResponse);
                    return Mono.just(reportRepository.save(entity));
                }
                return Mono.error(() -> new SagaProcessException("error in updateEntity process by hsmReportService"));
            })
            .doOnSuccess(success ->
                log.debug("success updateEntity from hsmReportService")
            )
            .doOnError(throwable ->
                log.error("exception error in process updateEntity from hsmReportService")
            )
            .log();
    }
}
