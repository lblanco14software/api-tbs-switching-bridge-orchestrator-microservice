package com.novo.microservices.services.implementations;

import com.google.gson.Gson;
import com.novo.microservices.dtos.requests.OrchestrationTransactionRequest;
import com.novo.microservices.repositories.contracts.IOrchestratorTransactionReportRepository;
import com.novo.microservices.repositories.entities.OrchestratorTransactionReportEntity;
import com.novo.microservices.services.contracts.IOrchestratorTransactionReportCrudService;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Date;
import java.util.UUID;

import static org.apache.commons.lang3.StringUtils.trimToEmpty;
import static org.springframework.beans.factory.config.BeanDefinition.SCOPE_PROTOTYPE;

/**
 * OrchestratorCrudService
 * <p>
 * OrchestratorCrudService class.
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
@Log4j2
@Service
@Scope(SCOPE_PROTOTYPE)
public class OrchestratorTransactionReportCrudService implements IOrchestratorTransactionReportCrudService {

    private final IOrchestratorTransactionReportRepository transactionReportRepository;
    private final String orchestratorTransactionReportCrudServiceId;

    public OrchestratorTransactionReportCrudService(final IOrchestratorTransactionReportRepository transactionReportRepository) {
        this.transactionReportRepository = transactionReportRepository;
        this.orchestratorTransactionReportCrudServiceId = UUID.randomUUID().toString();
        log.debug("orchestratorTransactionReportCrudServiceId: {}", orchestratorTransactionReportCrudServiceId);
        log.debug("OrchestratorTransactionReportCrudService loaded successfully");
    }

    @Override
    public Mono<OrchestrationTransactionRequest> saveEntity(final OrchestrationTransactionRequest orchestrationTransactionRequest) {
        log.debug("orchestratorTransactionReportCrudServiceId {}", orchestratorTransactionReportCrudServiceId);

        return Mono.just(new OrchestratorTransactionReportEntity())
            .flatMap(transactionReportEntity -> {
                if (orchestrationTransactionRequest.getPaymentHeader() != null) {
                    transactionReportEntity.setBankCode(trimToEmpty(orchestrationTransactionRequest.getPaymentHeader().getBankCode()));
                    transactionReportEntity.setMessageType(trimToEmpty(orchestrationTransactionRequest.getPaymentHeader().getMessageType()));
                    transactionReportEntity.setTrackingId(trimToEmpty(orchestrationTransactionRequest.getPaymentHeader().getTrackingId()));
                    transactionReportEntity.setServiceId(trimToEmpty(orchestrationTransactionRequest.getPaymentHeader().getServiceId()));
                }
                if (orchestrationTransactionRequest.getTransaction() != null) {
                    transactionReportEntity.setMessageTypeIndicator(trimToEmpty(orchestrationTransactionRequest.getTransaction().getMessageTypeIndicator()));
                    transactionReportEntity.setTransactionAmount(trimToEmpty(orchestrationTransactionRequest.getTransaction().getDe4()));
                }
                transactionReportEntity.setTransactionDate(new Date());
                transactionReportEntity.setTransactionRequestPayload(new Gson().toJson(orchestrationTransactionRequest));

                return Mono.just(transactionReportRepository.save(transactionReportEntity));

            })
            .doOnSuccess(success -> log.debug("success saveEntity from OrchestrationTransactionRequest, entity: {}", success))
            .doOnError(throwable -> log.error("exception error in process saveEntity from OrchestrationTransactionRequest, error: {}", throwable.getMessage()))
            .then(Mono.just(orchestrationTransactionRequest))
            .log();
    }
}