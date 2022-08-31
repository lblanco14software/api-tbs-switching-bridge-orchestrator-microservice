package com.novo.microservices.services.implementations;

import com.novo.microservices.components.enums.ResponseCode;
import com.novo.microservices.components.helpers.SagaTransactionalJsonHelper;
import com.novo.microservices.dtos.OrchestratorTransactionClassification;
import com.novo.microservices.dtos.requests.OrchestrationTransactionRequest;
import com.novo.microservices.services.contracts.IOrchestratorClassificationService;
import com.novo.microservices.tbs.utils.components.exceptions.BusinessProcessException;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Optional;
import java.util.UUID;

import static org.springframework.beans.factory.config.BeanDefinition.SCOPE_PROTOTYPE;

/**
 * OrchestratorClassificationService
 * <p>
 * OrchestratorClassificationService class.
 * <p>
 * <p>
 * ESTE COMPONENTE FUE CONSTRUIDO SIGUIENDO LOS ESTANDARES DE DESARROLLO Y EL PROCEDIMIENTO
 * DE DESARROLLO DE APLICACIONES DE NOVOPAYMENT Y SE ENCUENTRA PROTEGIDO POR LAS LEYES DE
 * PROPIEDAD INTELECTUAL Y DERECHOS DE AUTOR.
 *
 * @author Novopayment Inc.
 * @author lblanco@novopayment.com
 * @since 4/25/2022
 */
@Log4j2
@Service
@Scope(SCOPE_PROTOTYPE)
public class OrchestratorClassificationService implements IOrchestratorClassificationService {

    private final SagaTransactionalJsonHelper sagaTransactionalJsonHelper;
    private final String orchestratorClassificationServiceId;

    public OrchestratorClassificationService(final SagaTransactionalJsonHelper sagaTransactionalJsonHelper) {
        this.sagaTransactionalJsonHelper = sagaTransactionalJsonHelper;
        this.orchestratorClassificationServiceId = UUID.randomUUID().toString();
        log.debug("orchestratorClassificationServiceId: {}", orchestratorClassificationServiceId);
        log.debug("OrchestratorClassificationService loaded successfully");
    }

    @Override
    public Mono<OrchestratorTransactionClassification> doOnFindTransactionClassification(final OrchestrationTransactionRequest orchestrationTransactionRequest) {

        log.debug("orchestratorClassificationServiceId: {}", orchestratorClassificationServiceId);
        final String serviceId = orchestrationTransactionRequest.getPaymentHeader().getServiceId();
        final String messageTypeIndicator = orchestrationTransactionRequest.getTransaction().getMessageTypeIndicator();
        final String processingCode = orchestrationTransactionRequest.getTransaction().getDe3();
        return doOnFindTransactionClassification(serviceId, messageTypeIndicator, processingCode);
    }

    @Override
    public Mono<OrchestratorTransactionClassification> doOnFindTransactionClassification(final String serviceId, final String messageTypeIndicator, final String processingCode) {

        log.debug("orchestratorClassificationServiceId {}", orchestratorClassificationServiceId);
        log.debug("doOnFindTransactionClassification process, serviceId: {}", serviceId);
        log.debug("doOnFindTransactionClassification process, messageTypeIndicator: {}", messageTypeIndicator);
        log.debug("doOnFindTransactionClassification process, processingCode: {}", processingCode);

        return Mono.just(sagaTransactionalJsonHelper.loadTransactionClassification())
            .flatMap(listTransactionClassification -> {
                Optional<OrchestratorTransactionClassification> orchestratorTransactionClassification = listTransactionClassification
                    .stream()
                    .filter(e -> e.getServiceId().equals(serviceId) && e.getMessageTypeIndicator().equals(messageTypeIndicator) && e.getProcessingCode().equals(processingCode))
                    .findFirst();
                return orchestratorTransactionClassification.map(Mono::just).orElseGet(() -> Mono.error(() -> new BusinessProcessException("error in findTransactionClassification process", ResponseCode.ERROR_INVALID_PARAMETERS)));
            })
            .doOnSuccess(success ->
                log.debug("success process doOnFindTransactionClassification by the current transaction is: {}", success.getTransactionStateCode())
            )
            .doOnError(throwable -> {
                log.error("error in process doOnFindTransactionClassification there is no transaction matching the following parameters, error: {}", throwable.getMessage());
                log.error("doOnFindTransactionClassification process, serviceId: {}", serviceId);
                log.error("doOnFindTransactionClassification process, messageTypeIndicator: {}", messageTypeIndicator);
                log.error("doOnFindTransactionClassification process, processingCode: {}", processingCode);
            });
    }

    @Override
    public Mono<OrchestratorTransactionClassification> doOnFindTransactionClassificationByCode(final String transactionCode) {

        log.debug("orchestratorClassificationServiceId {}", orchestratorClassificationServiceId);
        return Mono.just(sagaTransactionalJsonHelper.loadTransactionClassification())
            .flatMap(listTransactionClassification -> {
                Optional<OrchestratorTransactionClassification> orchestratorTransactionClassification = listTransactionClassification
                    .stream()
                    .filter(e -> e.getTransactionCode().equals(transactionCode))
                    .findFirst();
                return orchestratorTransactionClassification.map(Mono::just).orElseGet(() -> Mono.error(() -> new BusinessProcessException("error in doOnFindTransactionClassificationByCode process", ResponseCode.ERROR_INVALID_PARAMETERS)));
            })
            .doOnSuccess(success ->
                log.debug("success process doOnFindTransactionClassificationByCode by the current transaction is: {}", success.getTransactionStateCode())
            )
            .doOnError(throwable -> {
                log.error("error in process doOnFindTransactionClassificationByCode there is no transaction matching the following parameters, error: {}", throwable.getMessage());
                log.error("doOnFindTransactionClassificationByCode process, transactionCode: {}", transactionCode);
            });
    }
}