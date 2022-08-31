package com.novo.microservices.components.validations;

import com.novo.microservices.components.enums.ValidateResult;
import com.novo.microservices.dtos.OrchestratorValidationResult;
import com.novo.microservices.repositories.entities.OrchestratorTransactionEntity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

/**
 * IOrchestratorTransactionInformationValidation
 * <p>
 * IOrchestratorTransactionInformationValidation class.
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
@FunctionalInterface
public interface IOrchestratorTransactionInformationValidation extends Function<OrchestratorTransactionEntity, Mono<OrchestratorValidationResult>> {

    Logger logger = LogManager.getLogger(IOrchestratorTransactionInformationValidation.class);

    /***
     * validate main field request
     * @return {@link IOrchestratorTransactionInformationValidation} with result of field validation
     */
    static IOrchestratorTransactionInformationValidation validateMainRequest() {

        return orchestratorTransactionEntity ->
            Mono.just(OrchestratorValidationResult.builder().validateResult(ValidateResult.SUCCESSFULLY_VALID).build())
                .flatMap(orchestratorValidationResult -> {

                    final List<String> errors = new ArrayList<>();

                    if (Objects.isNull(orchestratorTransactionEntity.getMessageId())) {
                        logger.error("error the field messageId is required");
                        errors.add("error the field messageId is required");
                    }

                    if (Objects.isNull(orchestratorTransactionEntity.getMessageTypeIndicator())) {
                        logger.error("error the field messageTypeIndicator is required");
                        errors.add("error the field messageTypeIndicator is required");
                    }

                    if (Objects.isNull(orchestratorTransactionEntity.getProcessingCode())) {
                        logger.error("error the field processingCode is required");
                        errors.add("error the field processingCode is required");
                    }

                    if (Objects.isNull(orchestratorTransactionEntity.getServiceId())) {
                        logger.error("error the field serviceId is required");
                        errors.add("error the field serviceId is required");
                    }

                    if (!errors.isEmpty()) {
                        orchestratorValidationResult.setValidateResult(ValidateResult.NOT_VALID);
                        orchestratorValidationResult.setErrors(errors);
                    }

                    return Mono.just(orchestratorValidationResult);
                });
    }
}