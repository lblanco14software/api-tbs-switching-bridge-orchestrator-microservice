package com.novo.microservices.dtos;

import com.novo.microservices.tbs.utils.dtos.StandardTransaction;
import lombok.*;

import java.io.Serializable;

/**
 * OrchestratorTransactionMapping
 * <p>
 * OrchestratorTransactionMapping class.
 * <p>
 * <p>
 * ESTE COMPONENTE FUE CONSTRUIDO SIGUIENDO LOS ESTANDARES DE DESARROLLO Y EL PROCEDIMIENTO
 * DE DESARROLLO DE APLICACIONES DE NOVOPAYMENT Y SE ENCUENTRA PROTEGIDO POR LAS LEYES DE
 * PROPIEDAD INTELECTUAL Y DERECHOS DE AUTOR.
 *
 * @author Novopayment Inc.
 * @author duhernandez@novopayment.com
 * @since 2/6/22
 */
@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class OrchestratorTransactionMapping implements Serializable {
    private static final long serialVersionUID = -969803528495495543L;

    private String serviceId;
    private String messageTypeIndicator;
    private String processingCode;
    private String bankCode;
    private StandardTransaction standardTransactionRequest;
    private StandardTransaction standardTransactionResponse;
}