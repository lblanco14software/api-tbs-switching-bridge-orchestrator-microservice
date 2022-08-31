package com.novo.microservices.mocks;

import com.novo.microservices.dtos.OrchestratorTransactionClassification;

/**
 * OrchestratorTransactionClassificationFixture
 * <p>
 * OrchestratorTransactionClassificationFixture class.
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
public class OrchestratorTransactionClassificationFixture extends BaseFixture<OrchestratorTransactionClassification> {

    public OrchestratorTransactionClassificationFixture() {
        setInstance(OrchestratorTransactionClassification.builder()
            .transactionCode("transactionCode")
            .serviceId("serviceId")
            .messageTypeIndicator("messageTypeIndicator")
            .processingCode("processingCode")
            .transactionStateCode("transactionStateCode")
            .transactionReferenceCode("transactionReferenceCode")
            .build()
        );
    }
}