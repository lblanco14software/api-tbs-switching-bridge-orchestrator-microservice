package com.novo.microservices.mocks;

import com.novo.microservices.dtos.OrchestratorTransactionInformation;

/**
 * OrchestratorTransactionInformationFixture
 * <p>
 * OrchestratorTransactionInformationFixture class.
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
public class OrchestratorTransactionInformationFixture extends BaseFixture<OrchestratorTransactionInformation> {

    public OrchestratorTransactionInformationFixture() {
        setInstance(OrchestratorTransactionInformation.builder()
            .messageId("messageId")
            .serviceId("serviceId")
            .messageTypeIndicator("messageTypeIndicator")
            .processingCode("processingCode")
            .transactionEncrypted("transactionEncrypted")
            .standardTransactionResponse(new StandardTransactionResponseFixture().getInstance())
            .build()
        );
    }
}