package com.novo.microservices.components.validations;

import com.novo.microservices.components.enums.ValidateResult;
import com.novo.microservices.dtos.OrchestratorValidationResult;
import com.novo.microservices.dtos.requests.OrchestrationGenerateKeyRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

/**
 * IOrchestrationHsmGenerateKeyRequestValidation
 * <p>
 * IOrchestrationHsmGenerateKeyRequestValidation interface.
 *
 * <p>
 * ESTE COMPONENTE FUE CONSTRUIDO SIGUIENDO LOS ESTANDARES DE DESARROLLO Y EL PROCEDIMIENTO DE DESARROLLO DE
 * APLICACIONES DE NOVOPAYMENT Y SE ENCUENTRA PROTEGIDO POR LAS LEYES DE PROPIEDAD INTELECTUAL Y DERECHOS DE AUTOR.
 *
 * @author Novopayment Inc.
 * @author jquiroga@novopayment.com
 * @since 5/19/2021
 */

@FunctionalInterface
public interface IOrchestrationHsmGenerateKeyRequestValidation extends Function<OrchestrationGenerateKeyRequest, OrchestratorValidationResult> {
    Logger logger = LogManager.getLogger(IOrchestrationHsmGenerateKeyRequestValidation.class);

    /***
     * validate main field request
     * @return {@link IOrchestrationHsmGenerateKeyRequestValidation} with result of field validation
     */
    static IOrchestrationHsmGenerateKeyRequestValidation validateRequest() {
        return orchestrationGenerateKeyRequest -> {
            final List<String> errors = new ArrayList<>();
            final OrchestratorValidationResult orchestratorValidationResult = OrchestratorValidationResult.builder()
                .validateResult(ValidateResult.SUCCESSFULLY_VALID)
                .errors(new ArrayList<>())
                .build();

            if (Objects.isNull(orchestrationGenerateKeyRequest.getTrackingId())) {
                logger.error("error the tracking id is required");
                errors.add("error the tracking id is required");
            }

            if (Objects.isNull(orchestrationGenerateKeyRequest.getSerialNumber())) {
                logger.error("error the serial number is required");
                errors.add("error the serial number is required");
            }

            if (!Objects.isNull(orchestrationGenerateKeyRequest.getTrackingId()) && orchestrationGenerateKeyRequest.getTrackingId().length() != 30) {
                logger.error("error the tracking id is invalid");
                errors.add("error the tracking id is invalid");
            }

            if (!Objects.isNull(orchestrationGenerateKeyRequest.getSerialNumber()) && orchestrationGenerateKeyRequest.getSerialNumber().length() != 9) {
                logger.error("error the serial number is invalid");
                errors.add("error the serial number is invalid");
            }

            if (!errors.isEmpty()) {
                orchestratorValidationResult.setValidateResult(ValidateResult.NOT_VALID);
                orchestratorValidationResult.setErrors(errors);
            }

            return orchestratorValidationResult;
        };
    }
}
