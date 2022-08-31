package com.novo.microservices.components.validations;

import com.novo.microservices.components.enums.ValidateResult;
import com.novo.microservices.dtos.OrchestratorValidationResult;
import com.novo.microservices.dtos.responses.OrchestrationTransactionResponse;
import com.novo.microservices.tbs.utils.components.validations.ISagaControlHistoryValidation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

import static com.novo.microservices.transactions.enums.CustomTransactionResponses.CUSTOM_RESPONSE_STATUS_DECLINED;

/**
 * IOrchestrationTransactionRequestValidation
 * <p>
 * IOrchestrationTransactionRequestValidation interface.
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
public interface IOrchestrationTransactionResponseValidation extends Function<OrchestrationTransactionResponse, OrchestratorValidationResult> {

    Logger logger = LogManager.getLogger(IOrchestrationTransactionResponseValidation.class);

    /***
     * validate main field response
     * @return {@link IOrchestrationTransactionResponseValidation} with result of field validation
     */
    static IOrchestrationTransactionResponseValidation validateResponse() {

        return orchestrationTransactionResponse -> {

            final OrchestratorValidationResult orchestratorValidationResult = OrchestratorValidationResult.builder().validateResult(ValidateResult.SUCCESSFULLY_VALID).build();

            final List<String> errors = new ArrayList<>();

            if (Objects.isNull(orchestrationTransactionResponse.getTransaction())) {
                logger.error("error the field transaction is null");
                errors.add("error the field transaction is null");
            }

            if (Objects.isNull(orchestrationTransactionResponse.getPaymentResponse())) {
                logger.error("error the field paymentResponse is null");
                errors.add("error the field paymentResponse is null");
            }

            if (!errors.isEmpty()) {
                orchestratorValidationResult.setValidateResult(ValidateResult.NOT_VALID);
                orchestratorValidationResult.setErrors(errors);
            }

            return orchestratorValidationResult;
        };
    }

    /***
     * validate validate transaction approved
     * @return {@link IOrchestrationTransactionResponseValidation} with result of field validation
     */
    static IOrchestrationTransactionResponseValidation validateTransactionApproved() {

        return orchestrationTransactionResponse -> {

            final OrchestratorValidationResult orchestratorValidationResult = OrchestratorValidationResult.builder().validateResult(ValidateResult.SUCCESSFULLY_VALID).build();
            final List<String> errors = new ArrayList<>();

            if (Objects.nonNull(orchestrationTransactionResponse.getPaymentResponse()) && CUSTOM_RESPONSE_STATUS_DECLINED.toString().equals(orchestrationTransactionResponse.getPaymentResponse().getResponseStatus())) {
                logger.warn("error the transaction is declined");
                errors.add("error the transaction is declined");
            }

            if (!errors.isEmpty()) {
                orchestratorValidationResult.setValidateResult(ValidateResult.NOT_VALID);
                orchestratorValidationResult.setErrors(errors);
            }

            return orchestratorValidationResult;
        };
    }

    /***
     * method concat validations with and condition generates a short circuit if any condition is invalid
     * @param otherValidation other validation to concat in process
     * @return {@link ISagaControlHistoryValidation} with result of field validation
     */
    default IOrchestrationTransactionResponseValidation and(IOrchestrationTransactionResponseValidation otherValidation) {

        return createSagaTransactionalRequest -> {
            final OrchestratorValidationResult orchestratorValidationResult = this.apply(createSagaTransactionalRequest);
            return ValidateResult.SUCCESSFULLY_VALID.equals(orchestratorValidationResult.getValidateResult()) ? otherValidation.apply(createSagaTransactionalRequest) : orchestratorValidationResult;
        };
    }
}