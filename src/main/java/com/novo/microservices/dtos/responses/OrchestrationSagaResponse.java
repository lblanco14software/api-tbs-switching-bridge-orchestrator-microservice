package com.novo.microservices.dtos.responses;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.novo.microservices.dtos.OrchestratorTransactionClassification;
import com.novo.microservices.dtos.OrchestratorTransactionInformation;
import com.novo.microservices.dtos.OrchestratorTransactionStates;
import com.novo.microservices.tbs.utils.dtos.SagaControl;
import com.novo.microservices.tbs.utils.dtos.StandardTransaction;
import com.novo.microservices.tbs.utils.dtos.TransactionMessage;
import lombok.*;

import java.io.Serializable;

/**
 * OrchestrationSagaResponse
 * <p>
 * OrchestrationSagaResponse class.
 * <p>
 * <p>
 * ESTE COMPONENTE FUE CONSTRUIDO SIGUIENDO LOS ESTANDARES DE DESARROLLO Y EL PROCEDIMIENTO
 * DE DESARROLLO DE APLICACIONES DE NOVOPAYMENT Y SE ENCUENTRA PROTEGIDO POR LAS LEYES DE
 * PROPIEDAD INTELECTUAL Y DERECHOS DE AUTOR.
 *
 * @author Novopayment Inc.
 * @author lblanco@novopayment.com
 * @since 4/11/2022
 */
@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrchestrationSagaResponse implements Serializable {

    private static final long serialVersionUID = 5230901019196076399L;
    private OrchestratorTransactionClassification orchestratorTransactionClassification;
    private OrchestratorTransactionInformation orchestratorTransactionInformation;
    private OrchestratorTransactionStates orchestratorTransactionStates;
    private TransactionMessage<StandardTransaction> transactionMessage;
    private String nextTransactionSagaState;
    private SagaControl currentSagaControl;
    private String microservicesUuId;
}