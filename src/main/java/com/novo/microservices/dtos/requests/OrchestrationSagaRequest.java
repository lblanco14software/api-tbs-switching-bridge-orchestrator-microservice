package com.novo.microservices.dtos.requests;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.novo.microservices.dtos.OrchestratorTransactionClassification;
import com.novo.microservices.dtos.OrchestratorTransactionInformation;
import com.novo.microservices.dtos.OrchestratorTransactionStates;
import com.novo.microservices.dtos.responses.OrchestrationTransactionResponse;
import com.novo.microservices.tbs.utils.dtos.SagaControl;
import com.novo.microservices.tbs.utils.dtos.SagaOutBox;
import com.novo.microservices.transactions.contracts.IOrchestratorTransaction;
import lombok.*;

import java.io.Serializable;

/**
 * OrchestrationTransactionRequest
 * <p>
 * OrchestrationTransactionRequest class.
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
@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrchestrationSagaRequest implements Serializable {

    private static final long serialVersionUID = 5230901019196076399L;
    private OrchestratorTransactionClassification orchestratorTransactionClassification;
    private OrchestratorTransactionInformation orchestratorTransactionInformation;
    private OrchestrationTransactionResponse orchestrationTransactionResponse;
    private OrchestrationTransactionRequest orchestrationTransactionRequest;
    private OrchestratorTransactionStates orchestratorTransactionStates;
    private transient IOrchestratorTransaction orchestratorTransaction;
    private Boolean transactionResponseReceived;
    private String currentTransactionSagaState;
    private SagaControl currentSagaControl;
    private SagaOutBox sagaOutBox;
}