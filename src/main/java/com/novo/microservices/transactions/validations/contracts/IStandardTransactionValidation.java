package com.novo.microservices.transactions.validations.contracts;

import com.novo.microservices.dtos.OrchestratorValidationResult;
import com.novo.microservices.tbs.utils.dtos.StandardTransaction;

/**
 * IStandardTransactionValidation
 * <p>
 * IStandardTransactionValidation interface.
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
public interface IStandardTransactionValidation {

    OrchestratorValidationResult validateFields(StandardTransaction standardTransaction);
}