package com.novo.microservices.dtos.responses;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.novo.microservices.dtos.contracts.ResponseLoggable;
import com.novo.microservices.dtos.custom.CustomPaymentInformation;
import com.novo.microservices.dtos.custom.CustomTransactionInformation;
import com.novo.microservices.dtos.generics.GenericBusinessResponse;
import com.novo.microservices.tbs.utils.dtos.responses.BusinessProcessResponse;
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
public class OrchestrationTransactionResponse implements ResponseLoggable, Serializable {

    private static final long serialVersionUID = 5230901019196076399L;
    private CustomPaymentInformation paymentResponse;
    private CustomTransactionInformation transaction;

    @Override
    public BusinessProcessResponse prepareForLogInfo() {
        OrchestrationTransactionResponse responseLogInfo = new OrchestrationTransactionResponse();

        if (this.getPaymentResponse() != null)
            responseLogInfo.setPaymentResponse(
                CustomPaymentInformation.builder()
                    .serviceId(this.getPaymentResponse().getServiceId())
                    .trackingId(this.getPaymentResponse().getTrackingId())
                    .responseStatus(this.getPaymentResponse().getResponseStatus())
                    .responseDescription(this.getPaymentResponse().getResponseDescription())
                    .build()
            );

        if (this.getTransaction() != null)
            responseLogInfo.setTransaction(
                CustomTransactionInformation.builder()
                    .messageTypeIndicator(this.getTransaction().getMessageTypeIndicator())
                    .de3(this.getTransaction().getDe3())
                    .build()
            );

        return BusinessProcessResponse.setEntitySuccessfullyResponse(new GenericBusinessResponse<>(responseLogInfo));
    }
}