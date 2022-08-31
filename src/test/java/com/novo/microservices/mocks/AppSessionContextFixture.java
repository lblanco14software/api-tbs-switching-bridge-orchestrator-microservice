package com.novo.microservices.mocks;

import com.novo.microservices.utils.common.context.AppSessionContext;

/**
 * AppSessionContextFixture
 * <p>
 * AppSessionContextFixture class.
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
public class AppSessionContextFixture extends BaseFixture<AppSessionContext> {

    public AppSessionContextFixture() {
        setInstance(AppSessionContext.builder()
            .applicationSession(new ApplicationSessionFixture().getInstance())
            .build()
        );
    }
}