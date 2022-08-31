package com.novo.microservices.dtos.requests;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.novo.microservices.dtos.contracts.RequestLoggable;
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
 * @author jquiroga@novopayment.com
 * @since 5/12/2022
 */
@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrchestrationGenerateKeyRequest implements RequestLoggable, Serializable {

    private static final long serialVersionUID = 5230901019196076399L;

    private String trackingId;
    private String serialNumber;

    @Override
    public RequestLoggable prepareForLogInfo() {
        if (this.getSerialNumber() != null)
            return OrchestrationGenerateKeyRequest.builder()
                .serialNumber(this.getSerialNumber())
                .build();

        return OrchestrationGenerateKeyRequest.builder().build();
    }
}