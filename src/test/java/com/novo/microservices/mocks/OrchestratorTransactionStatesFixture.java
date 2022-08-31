package com.novo.microservices.mocks;

import com.novo.microservices.dtos.OrchestratorTransactionStates;

/**
 * OrchestratorTransactionStatesFixture
 * <p>
 * OrchestratorTransactionStatesFixture class.
 * <p>
 * <p>
 * ESTE COMPONENTE FUE CONSTRUIDO SIGUIENDO LOS ESTANDARES DE DESARROLLO Y EL PROCEDIMIENTO
 * DE DESARROLLO DE APLICACIONES DE NOVOPAYMENT Y SE ENCUENTRA PROTEGIDO POR LAS LEYES DE
 * PROPIEDAD INTELECTUAL Y DERECHOS DE AUTOR.
 *
 * @author Novopayment Inc.
 * @author lblanco@novopayment.com
 * @since 4/19/2022
 */
public class OrchestratorTransactionStatesFixture extends BaseFixture<OrchestratorTransactionStates> {

    public OrchestratorTransactionStatesFixture() {
        setInstance(OrchestratorTransactionStates.builder()
            .transactionSagaStatePending("transactionSagaStatePending")
            .transactionSagaStateCompleted("transactionSagaStateCompleted")
            .transactionSagaStateInformationError("transactionSagaStateInformationError")
            .transactionSagaStateInternalError("transactionSagaStateInternalError")
            .build()
        );
    }
}