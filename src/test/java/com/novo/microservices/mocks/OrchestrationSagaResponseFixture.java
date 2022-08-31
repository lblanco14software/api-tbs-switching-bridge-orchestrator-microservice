package com.novo.microservices.mocks;

import com.novo.microservices.dtos.responses.OrchestrationSagaResponse;

/**
 * OrchestrationSagaResponseFixture
 * <p>
 * OrchestrationSagaResponseFixture class.
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
public class OrchestrationSagaResponseFixture extends BaseFixture<OrchestrationSagaResponse> {

    public OrchestrationSagaResponseFixture() {
        setInstance(OrchestrationSagaResponse.builder()
            .transactionMessage(new TransactionMessageFixture().getInstance())
            .orchestratorTransactionClassification(new OrchestratorTransactionClassificationFixture().getInstance())
            .orchestratorTransactionStates(new OrchestratorTransactionStatesFixture().getInstance())
            .nextTransactionSagaState("nextTransactionSagaState")
            .currentSagaControl(new SagaControlFixture().getInstance())
            .build()
        );
    }
}