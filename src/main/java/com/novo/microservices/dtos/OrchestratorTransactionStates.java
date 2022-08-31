package com.novo.microservices.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.io.Serializable;

/**
 * OrchestratorTransactionStates
 * <p>
 * OrchestratorTransactionStates class.
 * <p>
 * <p>
 * ESTE COMPONENTE FUE CONSTRUIDO SIGUIENDO LOS ESTANDARES DE DESARROLLO Y EL PROCEDIMIENTO
 * DE DESARROLLO DE APLICACIONES DE NOVOPAYMENT Y SE ENCUENTRA PROTEGIDO POR LAS LEYES DE
 * PROPIEDAD INTELECTUAL Y DERECHOS DE AUTOR.
 *
 * @author Novopayment Inc.
 * @author lblanco@novopayment.com
 * @since 3/31/2022
 */
@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrchestratorTransactionStates implements Serializable {

    private static final long serialVersionUID = -6854291516122369846L;
    private String transactionSagaStateReceived;
    private String transactionSagaStatePending;
    private String transactionSagaStateCompleted;
    private String transactionSagaStateInformationError;
    private String transactionSagaStateInternalError;
}