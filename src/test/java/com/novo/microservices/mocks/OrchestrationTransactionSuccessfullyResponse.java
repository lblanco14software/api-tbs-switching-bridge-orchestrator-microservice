package com.novo.microservices.mocks;

import com.novo.microservices.dtos.custom.CustomPaymentInformation;
import com.novo.microservices.dtos.responses.OrchestrationTransactionResponse;

import static com.novo.microservices.transactions.constants.OrchestratorCustomResponsesCodes.CUSTOM_RESPONSE_STATUS_COMPLETED_CODE;
import static com.novo.microservices.transactions.constants.OrchestratorCustomResponsesCodes.CUSTOM_RESPONSE_STATUS_COMPLETED_DESCRIPTION;

/**
 * OrchestrationTransactionSuccessfullyResponse
 * <p>
 * OrchestrationTransactionSuccessfullyResponse class.
 * <p>
 * <p>
 * ESTE COMPONENTE FUE CONSTRUIDO SIGUIENDO LOS ESTANDARES DE DESARROLLO Y EL PROCEDIMIENTO
 * DE DESARROLLO DE APLICACIONES DE NOVOPAYMENT Y SE ENCUENTRA PROTEGIDO POR LAS LEYES DE
 * PROPIEDAD INTELECTUAL Y DERECHOS DE AUTOR.
 *
 * @author Novopayment Inc.
 * @author lblanco@novopayment.com
 * @since 4/18/2022
 */
public class OrchestrationTransactionSuccessfullyResponse extends BaseFixture<OrchestrationTransactionResponse> {

    public OrchestrationTransactionSuccessfullyResponse() {
        setInstance(OrchestrationTransactionResponse.builder()
            .paymentResponse(CustomPaymentInformation.builder()
                .responseStatus(CUSTOM_RESPONSE_STATUS_COMPLETED_CODE)
                .responseDescription(CUSTOM_RESPONSE_STATUS_COMPLETED_DESCRIPTION)
                .trackingId("1702202216453613000452637")
                .serviceId("00660035")
                .build()
            )
            .build()
        );
    }
}