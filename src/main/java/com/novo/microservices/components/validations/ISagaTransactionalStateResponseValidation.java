package com.novo.microservices.components.validations;

import com.novo.microservices.dtos.SagaTransactionalStateResult;
import com.novo.microservices.tbs.utils.components.validations.ISagaControlHistoryValidation;
import com.novo.microservices.tbs.utils.dtos.responses.SagaTransactionalStateResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

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
public interface ISagaTransactionalStateResponseValidation extends Function<SagaTransactionalStateResponse, SagaTransactionalStateResult> {

    Logger logger = LogManager.getLogger(ISagaTransactionalStateResponseValidation.class);

    /***
     * validate main field request
     * @return {@link ISagaTransactionalStateResponseValidation} with result of field validation
     */
    static ISagaTransactionalStateResponseValidation validateResponse() {

        return sagaTransactionalStateResponse -> {

            final SagaTransactionalStateResult sagaTransactionalStateResult = SagaTransactionalStateResult.builder().sagaStateChangeResult(SagaTransactionalStateResult.SagaStateChangeResult.SAGA_EXPECTED_STATE).build();
            final List<String> warnings = new ArrayList<>();

            if (Objects.isNull(sagaTransactionalStateResponse.getSagaControl())) {
                logger.warn("error the field sagaControl is required");
                warnings.add("error the field sagaControl is required");
            }

            if (Objects.isNull(sagaTransactionalStateResponse.getOriginalSagaOutBox())) {
                logger.warn("error the field originalSagaOutBox is required");
                warnings.add("error the field originalSagaOutBox is required");
            }

            if (!warnings.isEmpty()) {
                sagaTransactionalStateResult.setSagaStateChangeResult(SagaTransactionalStateResult.SagaStateChangeResult.NOT_SAGA_EXPECTED_STATE);
                sagaTransactionalStateResult.setWarnings(warnings);
            }

            return sagaTransactionalStateResult;
        };
    }

    static ISagaTransactionalStateResponseValidation validateChangeState(final String currentState) {

        return sagaTransactionalStateResponse -> {

            final SagaTransactionalStateResult sagaTransactionalStateResult = SagaTransactionalStateResult.builder().sagaStateChangeResult(SagaTransactionalStateResult.SagaStateChangeResult.SAGA_EXPECTED_STATE).build();
            final List<String> warnings = new ArrayList<>();

            if (currentState.equals(sagaTransactionalStateResponse.getSagaControl().getEventState())) {
                warnings.add("error the saga state not changed");
            }

            if (!warnings.isEmpty()) {
                sagaTransactionalStateResult.setSagaStateChangeResult(SagaTransactionalStateResult.SagaStateChangeResult.NOT_SAGA_EXPECTED_STATE);
                sagaTransactionalStateResult.setWarnings(warnings);
            }

            return sagaTransactionalStateResult;
        };
    }

    /***
     * method concat validations with and condition generates a short circuit if any condition is invalid
     * @param otherValidation other validation to concat in process
     * @return {@link ISagaControlHistoryValidation} with result of field validation
     */
    default ISagaTransactionalStateResponseValidation and(ISagaTransactionalStateResponseValidation otherValidation) {

        return createSagaTransactionalRequest -> {
            final SagaTransactionalStateResult orchestratorValidationResult = this.apply(createSagaTransactionalRequest);
            return SagaTransactionalStateResult.SagaStateChangeResult.SAGA_EXPECTED_STATE.equals(orchestratorValidationResult.getSagaStateChangeResult()) ? otherValidation.apply(createSagaTransactionalRequest) : orchestratorValidationResult;
        };
    }
}