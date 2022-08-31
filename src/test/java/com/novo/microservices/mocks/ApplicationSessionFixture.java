package com.novo.microservices.mocks;

import com.novo.microservices.utils.common.dtos.ApplicationSession;

/**
 * ApplicationSessionFixture
 * <p>
 * ApplicationSessionFixture class.
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
public class ApplicationSessionFixture extends BaseFixture<ApplicationSession> {

    public ApplicationSessionFixture() {
        setInstance(ApplicationSession.builder()
            .userId("userId")
            .requestId("requestId")
            .tenantId("tenantId")
            .build()
        );
    }
}