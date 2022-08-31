package com.novo.microservices.transactions.validations.implementations;

import com.novo.microservices.components.enums.ValidateResult;
import com.novo.microservices.dtos.OrchestratorValidationResult;
import com.novo.microservices.tbs.utils.dtos.StandardTransaction;
import com.novo.microservices.transactions.validations.contracts.IStandardTransactionValidation;
import lombok.extern.log4j.Log4j2;
import org.hibernate.validator.BaseHibernateValidatorConfiguration;
import org.hibernate.validator.HibernateValidator;
import org.hibernate.validator.cfg.ConstraintMapping;
import org.hibernate.validator.cfg.defs.NotEmptyDef;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.ArrayList;
import java.util.Set;

import static com.novo.microservices.tbs.utils.constants.StandardTransactionConstants.*;

/**
 * StandardTransactionValidation
 * <p>
 * StandardTransactionValidation class.
 * <p>
 * <p>
 * ESTE COMPONENTE FUE CONSTRUIDO SIGUIENDO LOS ESTANDARES DE DESARROLLO Y EL PROCEDIMIENTO
 * DE DESARROLLO DE APLICACIONES DE NOVOPAYMENT Y SE ENCUENTRA PROTEGIDO POR LAS LEYES DE
 * PROPIEDAD INTELECTUAL Y DERECHOS DE AUTOR.
 *
 * @author Novopayment Inc.
 * @author lblanco@novopayment.com
 * @since 4/28/2022
 */
@Log4j2
@Component
public class StandardTransactionValidation implements IStandardTransactionValidation {

    @Override
    public OrchestratorValidationResult validateFields(final StandardTransaction transactionMessage) {

        final OrchestratorValidationResult orchestratorValidationResult = OrchestratorValidationResult.builder()
            .validateResult(ValidateResult.SUCCESSFULLY_VALID).errors(new ArrayList<>())
            .build();
        final BaseHibernateValidatorConfiguration<?> configuration = Validation
            .byProvider(HibernateValidator.class)
            .configure();

        final ConstraintMapping constraintMapping = configuration.createConstraintMapping();

        constraintMapping
            .type(StandardTransaction.class)
            .field(STANDARD_FIELD_MESSAGE_TYPE_INDICATOR)
            .constraint(new NotEmptyDef())
            .field(STANDARD_FIELD_DATA_ELEMENT_3)
            .constraint(new NotEmptyDef())
            .field(STANDARD_FIELD_DATA_ELEMENT_4)
            .constraint(new NotEmptyDef())
            .field(STANDARD_FIELD_DATA_ELEMENT_7)
            .constraint(new NotEmptyDef())
            .field(STANDARD_FIELD_DATA_ELEMENT_11)
            .constraint(new NotEmptyDef())
            .field(STANDARD_FIELD_DATA_ELEMENT_12)
            .constraint(new NotEmptyDef())
            .field(STANDARD_FIELD_DATA_ELEMENT_13)
            .constraint(new NotEmptyDef())
            .field(STANDARD_FIELD_DATA_ELEMENT_22)
            .constraint(new NotEmptyDef())
            .field(STANDARD_FIELD_DATA_ELEMENT_25)
            .constraint(new NotEmptyDef())
            .field(STANDARD_FIELD_DATA_ELEMENT_41)
            .constraint(new NotEmptyDef())
            .field(STANDARD_FIELD_DATA_ELEMENT_42)
            .constraint(new NotEmptyDef())
            .field(STANDARD_FIELD_DATA_ELEMENT_43)
            .constraint(new NotEmptyDef())
            .field(STANDARD_FIELD_DATA_ELEMENT_49)
            .constraint(new NotEmptyDef())
            .field(STANDARD_FIELD_DATA_ELEMENT_123)
            .constraint(new NotEmptyDef())
            .field(STANDARD_FIELD_DATA_ELEMENT_127_2)
            .constraint(new NotEmptyDef())
        ;

        final Validator validator = configuration.addMapping(constraintMapping)
            .buildValidatorFactory()
            .getValidator();

        final Set<ConstraintViolation<StandardTransaction>> validationConstraints = validator.validate(transactionMessage);

        if (validationConstraints != null && !validationConstraints.isEmpty()) {
            final ConstraintViolationException constraintViolationException = new ConstraintViolationException(validationConstraints);
            log.error("Transaction message with validation errors. [{}]", constraintViolationException.getLocalizedMessage());
            orchestratorValidationResult.setValidateResult(ValidateResult.NOT_VALID);
        }

        return orchestratorValidationResult;
    }
}