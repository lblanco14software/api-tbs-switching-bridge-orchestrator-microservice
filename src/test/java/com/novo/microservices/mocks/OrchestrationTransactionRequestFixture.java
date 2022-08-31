package com.novo.microservices.mocks;

import com.novo.microservices.dtos.requests.OrchestrationTransactionRequest;

/**
 * OrchestrationTransactionRequestFixture
 * <p>
 * OrchestrationTransactionRequestFixture class.
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
public class OrchestrationTransactionRequestFixture extends BaseFixture<OrchestrationTransactionRequest> {

    public OrchestrationTransactionRequestFixture() {
        setInstance(OrchestrationTransactionRequest.builder()
            .paymentHeader(new PaymentHeaderInformationFixture().getInstance())
            .transaction(new CustomTransactionInformationRequestFixture().getInstance())
            .build());
    }
}