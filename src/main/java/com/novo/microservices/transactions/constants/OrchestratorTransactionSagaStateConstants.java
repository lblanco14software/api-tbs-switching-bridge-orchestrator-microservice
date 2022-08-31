package com.novo.microservices.transactions.constants;

import lombok.experimental.UtilityClass;

/**
 * OrchestratorTransactionSagaStateConstants
 * <p>
 * OrchestratorTransactionSagaStateConstants class.
 * <p>
 * <p>
 * ESTE COMPONENTE FUE CONSTRUIDO SIGUIENDO LOS ESTANDARES DE DESARROLLO Y EL PROCEDIMIENTO
 * DE DESARROLLO DE APLICACIONES DE NOVOPAYMENT Y SE ENCUENTRA PROTEGIDO POR LAS LEYES DE
 * PROPIEDAD INTELECTUAL Y DERECHOS DE AUTOR.
 *
 * @author Novopayment Inc.
 * @author lblanco@novopayment.com
 * @since 4/2/2022
 */
@UtilityClass
public class OrchestratorTransactionSagaStateConstants {

    // Check Customer Info Microservice Transactions
    public static final String ORCHESTRATOR_TRANSACTION_SAGA_STATE_FORMAT_RECEIVED_CODE = "orchestrator.saga.transaction.state.received";
    public static final String ORCHESTRATOR_TRANSACTION_SAGA_STATE_FORMAT_RECEIVED_FORMAT = "%s_RECEIVED";

    public static final String ORCHESTRATOR_TRANSACTION_SAGA_STATE_FORMAT_PENDING_CODE = "orchestrator.saga.transaction.state.pending";
    public static final String ORCHESTRATOR_TRANSACTION_SAGA_STATE_FORMAT_PENDING_FORMAT = "%s_PENDING";

    public static final String ORCHESTRATOR_TRANSACTION_SAGA_STATE_FORMAT_COMPLETED_CODE = "orchestrator.saga.transaction.state.completed";
    public static final String ORCHESTRATOR_TRANSACTION_SAGA_STATE_FORMAT_COMPLETED_FORMAT = "%s_COMPLETED";

    public static final String ORCHESTRATOR_TRANSACTION_SAGA_STATE_FORMAT_INFORMATION_ERROR_CODE = "orchestrator.saga.transaction.state.information.error";
    public static final String ORCHESTRATOR_TRANSACTION_SAGA_STATE_FORMAT_INFORMATION_ERROR_FORMAT = "%s_INFORMATION_ERROR";

    public static final String ORCHESTRATOR_TRANSACTION_SAGA_STATE_FORMAT_INTERNAL_ERROR_CODE = "orchestrator.saga.transaction.state.internal.error";
    public static final String ORCHESTRATOR_TRANSACTION_SAGA_STATE_FORMAT_INTERNAL_ERROR_FORMAT = "%s_INTERNAL_ERROR";
}