package com.novo.microservices.transactions.enums;

import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static com.novo.microservices.transactions.constants.OrchestratorTransactionSagaStateConstants.*;

/**
 * OrchestratorTransactionCodes
 * <p>
 * OrchestratorTransactionCodes enum.
 * <p>
 * <p>
 * ESTE COMPONENTE FUE CONSTRUIDO SIGUIENDO LOS ESTANDARES DE DESARROLLO Y EL PROCEDIMIENTO
 * DE DESARROLLO DE APLICACIONES DE NOVOPAYMENT Y SE ENCUENTRA PROTEGIDO POR LAS LEYES DE
 * PROPIEDAD INTELECTUAL Y DERECHOS DE AUTOR.
 *
 * @author Novopayment Inc.
 * @author lblanco@novopayment.com
 * @since 3/30/2022
 */
@Getter
public enum TransactionSagaStatesFormats {

    ORCHESTRATOR_TRANSACTION_SAGA_STATE_FORMAT_RECEIVED(ORCHESTRATOR_TRANSACTION_SAGA_STATE_FORMAT_RECEIVED_CODE, ORCHESTRATOR_TRANSACTION_SAGA_STATE_FORMAT_RECEIVED_FORMAT),
    ORCHESTRATOR_TRANSACTION_SAGA_STATE_FORMAT_PENDING(ORCHESTRATOR_TRANSACTION_SAGA_STATE_FORMAT_PENDING_CODE, ORCHESTRATOR_TRANSACTION_SAGA_STATE_FORMAT_PENDING_FORMAT),
    ORCHESTRATOR_TRANSACTION_SAGA_STATE_FORMAT_COMPLETED(ORCHESTRATOR_TRANSACTION_SAGA_STATE_FORMAT_COMPLETED_CODE, ORCHESTRATOR_TRANSACTION_SAGA_STATE_FORMAT_COMPLETED_FORMAT),
    ORCHESTRATOR_TRANSACTION_SAGA_STATE_FORMAT_INFORMATION_ERROR(ORCHESTRATOR_TRANSACTION_SAGA_STATE_FORMAT_INFORMATION_ERROR_CODE, ORCHESTRATOR_TRANSACTION_SAGA_STATE_FORMAT_INFORMATION_ERROR_FORMAT),
    ORCHESTRATOR_TRANSACTION_SAGA_STATE_FORMAT_INTERNAL_ERROR(ORCHESTRATOR_TRANSACTION_SAGA_STATE_FORMAT_INTERNAL_ERROR_CODE, ORCHESTRATOR_TRANSACTION_SAGA_STATE_FORMAT_INTERNAL_ERROR_FORMAT);

    private static final Map<String, TransactionSagaStatesFormats> mapOrchestratorTransactionCodesByCode = new HashMap<>();
    private static final Map<String, TransactionSagaStatesFormats> mapOrchestratorTransactionCodesByCriteria = new HashMap<>();

    static {
        for (final TransactionSagaStatesFormats orchestratorTransactionCodes : EnumSet.allOf(TransactionSagaStatesFormats.class)) {
            mapOrchestratorTransactionCodesByCode.put(orchestratorTransactionCodes.getTransactionSagaCode(), orchestratorTransactionCodes);
        }
    }

    private final String transactionSagaCode;
    private final String transactionSagaFormat;


    TransactionSagaStatesFormats(String transactionSagaCode, String transactionSagaFormat) {
        this.transactionSagaCode = transactionSagaCode;
        this.transactionSagaFormat = transactionSagaFormat;
    }

    @Override
    public String toString() {
        return transactionSagaCode;
    }

    public static Optional<TransactionSagaStatesFormats> findByTransactionSagaCode(final String transactionSagaCode) {
        return StringUtils.isNoneBlank(transactionSagaCode) ? Optional.of(mapOrchestratorTransactionCodesByCode.get(transactionSagaCode)) : Optional.empty();
    }
}
