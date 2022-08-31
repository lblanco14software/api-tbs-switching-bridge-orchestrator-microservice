package com.novo.microservices.components.validations;

import com.novo.microservices.components.enums.ValidateResult;
import com.novo.microservices.dtos.OrchestratorValidationResult;
import com.novo.microservices.dtos.requests.OrchestrationTransactionRequest;
import com.novo.microservices.tbs.utils.components.validations.ISagaControlHistoryValidation;
import org.apache.commons.lang3.StringUtils;
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
public interface IOrchestrationTransactionRequestValidation extends Function<OrchestrationTransactionRequest, OrchestratorValidationResult> {

    Logger logger = LogManager.getLogger(IOrchestrationTransactionRequestValidation.class);

    /***
     * validate main field request
     * @return {@link IOrchestrationTransactionRequestValidation} with result of field validation
     */
    static IOrchestrationTransactionRequestValidation validateMainRequest() {

        return orchestrationTransactionRequest -> {

            final OrchestratorValidationResult orchestratorValidationResult = OrchestratorValidationResult.builder().validateResult(ValidateResult.SUCCESSFULLY_VALID).errors(new ArrayList<>()).build();
            final List<String> errors = new ArrayList<>();

            if (Objects.isNull(orchestrationTransactionRequest.getTransaction())) {
                logger.error("error the field transaction is required");
                errors.add("error the field transaction is required");
            }

            if (Objects.isNull(orchestrationTransactionRequest.getPaymentHeader())) {
                logger.error("error the field paymentHeader is required");
                errors.add("error the field paymentHeader is required");
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
     * @return {@link IOrchestrationTransactionRequestValidation} with result of field validation
     */
    static IOrchestrationTransactionRequestValidation validatePaymentHeader() {

        return orchestrationTransactionRequest -> {

            final OrchestratorValidationResult orchestratorValidationResult = OrchestratorValidationResult.builder().validateResult(ValidateResult.SUCCESSFULLY_VALID).build();
            final List<String> errors = new ArrayList<>();

            if (StringUtils.isEmpty(orchestrationTransactionRequest.getPaymentHeader().getTrackingId())) {
                logger.error("error the field trackingId in paymentHeader object is required");
                errors.add("error the field trackingId in paymentHeader object is required");
            }

            if (StringUtils.isEmpty(orchestrationTransactionRequest.getPaymentHeader().getBankCode())) {
                logger.error("error the field bankCode in paymentHeader object is required");
                errors.add("error the field bankCode in paymentHeader object is required");
            }

            if (StringUtils.isEmpty(orchestrationTransactionRequest.getPaymentHeader().getServiceId())) {
                logger.error("error the field serviceId in paymentHeader object is required");
                errors.add("error the field serviceId in paymentHeader object required");
            }

            if (StringUtils.isEmpty(orchestrationTransactionRequest.getPaymentHeader().getMessageType())) {
                logger.error("error the field messageType in paymentHeader object is required");
                errors.add("error the field messageType in paymentHeader object required");
            }

            if (!errors.isEmpty()) {
                orchestratorValidationResult.setValidateResult(ValidateResult.NOT_VALID);
                orchestratorValidationResult.setErrors(errors);
            }

            return orchestratorValidationResult;
        };
    }

    /***
     * validate main field request
     * @return {@link IOrchestrationTransactionRequestValidation} with result of field validation
     */
    static IOrchestrationTransactionRequestValidation validateTransactionRequest() {

        return orchestrationTransactionRequest -> {

            final OrchestratorValidationResult orchestratorValidationResult = OrchestratorValidationResult.builder().validateResult(ValidateResult.SUCCESSFULLY_VALID).build();
            final List<String> errors = new ArrayList<>();

            if (StringUtils.isEmpty(orchestrationTransactionRequest.getTransaction().getMessageTypeIndicator())) {
                logger.error("error the field messageTypeIndicator in transaction object is required");
                errors.add("error the field messageTypeIndicator in transaction object required");
            }

            if (StringUtils.isEmpty(orchestrationTransactionRequest.getTransaction().getDe3())) {
                logger.error("error the field de3 in transaction object is required");
                errors.add("error the field de3 in transaction object required");
            }

            if (!errors.isEmpty()) {
                orchestratorValidationResult.setValidateResult(ValidateResult.NOT_VALID);
                orchestratorValidationResult.setErrors(errors);
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
    default IOrchestrationTransactionRequestValidation and(IOrchestrationTransactionRequestValidation otherValidation) {

        return createSagaTransactionalRequest -> {
            final OrchestratorValidationResult orchestratorValidationResult = this.apply(createSagaTransactionalRequest);
            return ValidateResult.SUCCESSFULLY_VALID.equals(orchestratorValidationResult.getValidateResult()) ? otherValidation.apply(createSagaTransactionalRequest) : orchestratorValidationResult;
        };
    }
}