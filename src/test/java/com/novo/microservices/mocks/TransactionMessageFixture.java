package com.novo.microservices.mocks;

import com.novo.microservices.tbs.utils.dtos.StandardTransaction;
import com.novo.microservices.tbs.utils.dtos.TransactionMessage;

/**
 * TransactionMessageFixture
 * <p>
 * TransactionMessageFixture class.
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
public class TransactionMessageFixture extends BaseFixture<TransactionMessage<StandardTransaction>> {

    public TransactionMessageFixture() {
        setInstance(TransactionMessage.<StandardTransaction>builder()
            .messageId("Message-ID")
            .requestId("Request-ID")
            .tenantId("Tenant-ID")
            .data(new StandardTransactionResponseFixture().getInstance())
            .build());
    }
}