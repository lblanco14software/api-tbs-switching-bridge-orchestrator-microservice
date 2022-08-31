package com.novo.microservices.services.implementations;

import com.novo.microservices.components.enums.ValidateResult;
import com.novo.microservices.components.validations.IOrchestratorTransactionInformationValidation;
import com.novo.microservices.dtos.OrchestratorTransactionInformation;
import com.novo.microservices.repositories.contracts.IOrchestratorTransactionRepository;
import com.novo.microservices.repositories.entities.OrchestratorTransactionEntity;
import com.novo.microservices.services.contracts.IOrchestratorCrudService;
import com.novo.microservices.tbs.utils.components.exceptions.SagaProcessException;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.UUID;

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
public class OrchestratorCrudService implements IOrchestratorCrudService {

    private final IOrchestratorTransactionRepository orchestratorTransactionRepository;
    private final String orchestratorCrudServiceId;

    public OrchestratorCrudService(final IOrchestratorTransactionRepository orchestratorTransactionRepository) {
        this.orchestratorTransactionRepository = orchestratorTransactionRepository;
        this.orchestratorCrudServiceId = UUID.randomUUID().toString();
        log.debug("orchestratorProviderServiceId: {}", orchestratorCrudServiceId);
        log.debug("OrchestratorCrudService loaded successfully");
    }

    @Override
    public Mono<OrchestratorTransactionInformation> saveEntity(final OrchestratorTransactionInformation entity) {

        log.debug("orchestratorCrudServiceId {}", orchestratorCrudServiceId);
        return Mono.just(new OrchestratorTransactionEntity())
            .flatMap(orchestratorTransactionEntity -> {

                orchestratorTransactionEntity.setMessageId(entity.getMessageId());
                orchestratorTransactionEntity.setServiceId(entity.getServiceId());
                orchestratorTransactionEntity.setProcessingCode(entity.getProcessingCode());
                orchestratorTransactionEntity.setMessageTypeIndicator(entity.getMessageTypeIndicator());
                orchestratorTransactionEntity.setTransactionEncrypted(entity.getTransactionEncrypted());
                final OrchestratorTransactionEntity orchestratorTransaction = orchestratorTransactionRepository.save(orchestratorTransactionEntity);

                return IOrchestratorTransactionInformationValidation
                    .validateMainRequest().apply(orchestratorTransaction)
                    .flatMap(orchestratorValidationResult -> {
                        if (ValidateResult.SUCCESSFULLY_VALID.equals(orchestratorValidationResult.getValidateResult())) {
                            return Mono.just(entity);
                        }
                        return Mono.error(() -> new SagaProcessException("error in saveEntity process by OrchestratorTransactionInformation"));
                    });
            })
            .doOnSuccess(success ->
                log.debug("success saveEntity from OrchestratorTransactionInformation, OrchestratorTransactionInformation: {}", success)
            )
            .doOnError(throwable ->
                log.error("exception error in process saveEntity from OrchestratorTransactionInformation, error: {}", throwable.getMessage())
            )
            .log();
    }

    @Override
    public Mono<OrchestratorTransactionInformation> updateEntity(OrchestratorTransactionInformation entity) {

        log.debug("orchestratorCrudServiceId {}", orchestratorCrudServiceId);
        return Mono.just(orchestratorTransactionRepository.findById(entity.getMessageId()))
            .flatMap(optionalOrchestratorTransactionEntity -> {
                if (optionalOrchestratorTransactionEntity.isPresent()) {
                    OrchestratorTransactionEntity orchestratorTransactionEntity = optionalOrchestratorTransactionEntity.get();
                    orchestratorTransactionEntity.setTransactionEncrypted(entity.getTransactionEncrypted());
                    final OrchestratorTransactionEntity result = orchestratorTransactionRepository.save(orchestratorTransactionEntity);
                    return IOrchestratorTransactionInformationValidation
                        .validateMainRequest().apply(result)
                        .flatMap(orchestratorValidationResult -> {
                            if (ValidateResult.SUCCESSFULLY_VALID.equals(orchestratorValidationResult.getValidateResult())) {
                                return Mono.just(entity);
                            }
                            return Mono.error(() -> new SagaProcessException("error in saveEntity process by OrchestratorTransactionInformation"));
                        });
                }
                return Mono.error(() -> new SagaProcessException("error in updateEntity process by OrchestratorTransactionInformation"));
            })
            .doOnSuccess(success ->
                log.debug("success updateEntity from OrchestratorTransactionInformation, OrchestratorTransactionInformation: {}", success)
            )
            .doOnError(throwable ->
                log.error("exception error in process updateEntity from OrchestratorTransactionInformation, error: {}", throwable.getMessage())
            )
            .log();
    }

    @Override
    public Mono<OrchestratorTransactionInformation> getDomainEntityById(String entityId) {

        log.debug("orchestratorCrudServiceId {}", orchestratorCrudServiceId);
        return Mono.just(orchestratorTransactionRepository.findById(entityId))
            .flatMap(optionalOrchestratorTransaction -> {
                if (optionalOrchestratorTransaction.isPresent()) {
                    final OrchestratorTransactionInformation orchestratorTransactionInformation = OrchestratorTransactionInformation.builder()
                        .messageId(optionalOrchestratorTransaction.get().getMessageId())
                        .serviceId(optionalOrchestratorTransaction.get().getServiceId())
                        .processingCode(optionalOrchestratorTransaction.get().getProcessingCode())
                        .messageTypeIndicator(optionalOrchestratorTransaction.get().getMessageTypeIndicator())
                        .transactionEncrypted(optionalOrchestratorTransaction.get().getTransactionEncrypted())
                        .build();
                    return Mono.just(orchestratorTransactionInformation);
                }
                return Mono.error(() -> new SagaProcessException("error in saveEntity process by OrchestratorTransactionInformation"));
            })
            .doOnSuccess(success ->
                log.debug("success getDomainEntityById from messageId: {}, OrchestratorTransactionInformation: {}", entityId, success)
            )
            .doOnError(throwable ->
                log.error("exception error in process getDomainEntityById from OrchestratorTransactionInformation, error: {}", throwable.getMessage())
            )
            .log();
    }
}