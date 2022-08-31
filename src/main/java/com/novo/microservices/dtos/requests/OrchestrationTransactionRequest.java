package com.novo.microservices.dtos.requests;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.novo.microservices.dtos.contracts.RequestLoggable;
import com.novo.microservices.dtos.custom.CustomTransactionInformation;
import com.novo.microservices.dtos.custom.PaymentHeaderInformation;
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
public class OrchestrationTransactionRequest implements RequestLoggable, Serializable {

    private static final long serialVersionUID = 5230901019196076399L;
    private PaymentHeaderInformation paymentHeader;
    private CustomTransactionInformation transaction;

    @Override
    public RequestLoggable prepareForLogInfo() {
        OrchestrationTransactionRequest requestLogInfo = new OrchestrationTransactionRequest();

        if (this.getPaymentHeader() != null)
            requestLogInfo.setPaymentHeader(
                PaymentHeaderInformation.builder()
                    .serviceId(this.getPaymentHeader().getServiceId())
                    .trackingId(this.getPaymentHeader().getTrackingId())
                    .bankCode(this.getPaymentHeader().getBankCode())
                    .messageType(this.getPaymentHeader().getMessageType())
                    .storeId(this.getPaymentHeader().getStoreId())
                    .build()
            );

        if (this.getTransaction() != null)
            requestLogInfo.setTransaction(
                CustomTransactionInformation.builder()
                    .messageTypeIndicator(this.getTransaction().getMessageTypeIndicator())
                    .de3(this.getTransaction().getDe3())
                    .build()
            );

        return requestLogInfo;
    }
}