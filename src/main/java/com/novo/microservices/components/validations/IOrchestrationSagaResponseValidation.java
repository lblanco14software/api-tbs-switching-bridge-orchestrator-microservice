package com.novo.microservices.components.validations;

import com.novo.microservices.components.enums.ValidateResult;
import com.novo.microservices.dtos.OrchestratorValidationResult;
import com.novo.microservices.dtos.responses.OrchestrationSagaResponse;
import com.novo.microservices.tbs.utils.components.validations.ISagaControlHistoryValidation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

/**
 * IOrchestrationSagaResponseValidation
 * <p>
 * IOrchestrationSagaResponseValidation interface.
 * <p>
 * <p>
 * ESTE COMPONENTE FUE CONSTRUIDO SIGUIENDO LOS ESTANDARES DE DESARROLLO Y EL PROCEDIMIENTO
 * DE DESARROLLO DE APLICACIONES DE NOVOPAYMENT Y SE ENCUENTRA PROTEGIDO POR LAS LEYES DE
 * PROPIEDAD INTELECTUAL Y DERECHOS DE AUTOR.
 *
 * @author Novopayment Inc.
 * @author lblanco@novopayment.com
 * @since 4/15/2022
 */
public interface IOrchestrationSagaResponseValidation extends Function<OrchestrationSagaResponse, OrchestratorValidationResult> {

    Logger logger = LogManager.getLogger(IOrchestrationTransactionRequestValidation.class);

    /***
     * validate main field response
     * @return {@link IOrchestrationSagaResponseValidation} with result of field validation
     */
    static IOrchestrationSagaResponseValidation validateMainResponse() {

        return orchestrationSagaResponse -> {

            final OrchestratorValidationResult orchestratorValidationResult = OrchestratorValidationResult.builder().validateResult(ValidateResult.SUCCESSFULLY_VALID).build();
            final List<String> errors = new ArrayList<>();

            if (Objects.isNull(orchestrationSagaResponse.getCurrentSagaControl())) {
                logger.error("error the field currentSagaControl is null");
                errors.add("error the field currentSagaControl is null");
            }

            if (Objects.isNull(orchestrationSagaResponse.getOrchestratorTransactionStates())) {
                logger.error("error the field orchestratorTransactionStates is null");
                errors.add("error the field orchestratorTransactionStates is null");
            }

            if (!errors.isEmpty()) {
                orchestratorValidationResult.setValidateResult(ValidateResult.NOT_VALID);
                orchestratorValidationResult.setErrors(errors);
            }

            return orchestratorValidationResult;
        };
    }

    /***
     * validate payment header requests
     * @return {@link IOrchestrationSagaResponseValidation} with result of field validation
     */
    static IOrchestrationSagaResponseValidation validateSagaState() {

        return orchestrationSagaResponse -> {

            final OrchestratorValidationResult orchestratorValidationResult = OrchestratorValidationResult.builder().validateResult(ValidateResult.SUCCESSFULLY_VALID).build();
            final List<String> errors = new ArrayList<>();

            final String currentSagaState = orchestrationSagaResponse.getCurrentSagaControl().getEventState();
            final String transactionSagaStatePending = orchestrationSagaResponse.getOrchestratorTransactionStates().getTransactionSagaStatePending();

            if (!currentSagaState.equals(transactionSagaStatePending)) {
                logger.error("error in the saga state, state is expected {}, the current state is {}", transactionSagaStatePending, currentSagaState);
                errors.add(String.format("error in the saga state, state is expected %s, the current state is %s", transactionSagaStatePending, currentSagaState));
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
    default IOrchestrationSagaResponseValidation and(IOrchestrationSagaResponseValidation otherValidation) {

        return createSagaTransactionalRequest -> {
            final OrchestratorValidationResult orchestratorValidationResult = this.apply(createSagaTransactionalRequest);
            return ValidateResult.SUCCESSFULLY_VALID.equals(orchestratorValidationResult.getValidateResult()) ? otherValidation.apply(createSagaTransactionalRequest) : orchestratorValidationResult;
        };
    }
}