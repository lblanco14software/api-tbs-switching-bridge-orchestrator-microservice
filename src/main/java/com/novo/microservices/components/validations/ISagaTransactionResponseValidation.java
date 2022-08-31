package com.novo.microservices.components.validations;

import com.novo.microservices.components.enums.ValidateResult;
import com.novo.microservices.dtos.OrchestratorValidationResult;
import com.novo.microservices.tbs.utils.components.validations.ISagaControlHistoryValidation;
import com.novo.microservices.tbs.utils.dtos.StandardTransaction;
import com.novo.microservices.tbs.utils.dtos.TransactionMessage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

/**
 * ISagaTransactionResponseValidation
 * <p>
 * ISagaTransactionResponseValidation interface.
 *
 * <p>
 * ESTE COMPONENTE FUE CONSTRUIDO SIGUIENDO LOS ESTANDARES DE DESARROLLO Y EL PROCEDIMIENTO DE DESARROLLO DE
 * APLICACIONES DE NOVOPAYMENT Y SE ENCUENTRA PROTEGIDO POR LAS LEYES DE PROPIEDAD INTELECTUAL Y DERECHOS DE AUTOR.
 *
 * @author Novopayment Inc.
 * @author lblanco@novopayment.com
 * @since 05/02/2021
 */

@FunctionalInterface
public interface ISagaTransactionResponseValidation extends Function<TransactionMessage<StandardTransaction>, Mono<OrchestratorValidationResult>> {

    Logger logger = LogManager.getLogger(IOrchestrationTransactionRequestValidation.class);

    static ISagaTransactionResponseValidation validateTransactionMessage() {
        return transactionMessage ->
            Mono.just(OrchestratorValidationResult.builder().validateResult(ValidateResult.SUCCESSFULLY_VALID).build())
                .flatMap(orchestratorValidationResult -> {

                    final List<String> errors = new ArrayList<>();

                    if (Objects.isNull(transactionMessage.getMessageId())) {
                        logger.error("error the field messageId is required");
                        errors.add("error the field messageId is required");
                    }

                    if (Objects.isNull(transactionMessage.getTenantId())) {
                        logger.warn("error the field tenantId is required");
                        errors.add("error the field tenantId is required");
                    }

                    if (Objects.isNull(transactionMessage.getCorrelationId())) {
                        logger.warn("error the field correlationId is required");
                        errors.add("error the field correlationId is required");
                    }

                    if (Objects.isNull(transactionMessage.getData())) {
                        logger.warn("error the field standardTransaction is required");
                        errors.add("error the field standardTransaction is required");
                    }

                    if (!errors.isEmpty()) {
                        orchestratorValidationResult.setValidateResult(ValidateResult.NOT_VALID);
                        orchestratorValidationResult.setErrors(errors);
                    }

                    return Mono.just(orchestratorValidationResult);
                });
    }

    /***
     * method concat validations with and condition generates a short circuit if any condition is invalid
     * @param otherValidation other validation to concat in process
     * @return {@link ISagaControlHistoryValidation} with result of field validation
     */
    default ISagaTransactionResponseValidation and(ISagaTransactionResponseValidation otherValidation) {

        return createSagaTransactionalRequest -> {
            final Mono<OrchestratorValidationResult> orchestratorValidationResult = this.apply(createSagaTransactionalRequest);
            orchestratorValidationResult.flatMap(currentSagaPersistenceResult -> ValidateResult.SUCCESSFULLY_VALID.equals(currentSagaPersistenceResult.getValidateResult()) ? otherValidation.apply(createSagaTransactionalRequest) : Mono.just(orchestratorValidationResult));
            return orchestratorValidationResult;
        };
    }
}