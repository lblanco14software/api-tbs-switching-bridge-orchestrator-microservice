package com.novo.microservices.mocks;

import com.novo.utils.messaging.components.enums.EventType;
import com.novo.utils.messaging.dtos.RoutingKey;

/**
 * RoutingKeyFixture
 * <p>
 * RoutingKeyFixture class.
 * <p>
 * <p>
 * ESTE COMPONENTE FUE CONSTRUIDO SIGUIENDO LOS ESTANDARES DE DESARROLLO Y EL PROCEDIMIENTO
 * DE DESARROLLO DE APLICACIONES DE NOVOPAYMENT Y SE ENCUENTRA PROTEGIDO POR LAS LEYES DE
 * PROPIEDAD INTELECTUAL Y DERECHOS DE AUTOR.
 *
 * @author Novopayment Inc.
 * @author aardila@novopayment.com
 * @since 4/18/2022
 */
public class RoutingKeyFixture extends BaseFixture<RoutingKey> {

    public RoutingKeyFixture() {
        setInstance(RoutingKey.builder()
            .eventType(EventType.SERVICE)
            .origin("X-SERVICE")
            .destiny("API-ISO-SENDER")
            .domain("TRANSACTION")
            .command("REQUEST")
            .build()
        );
    }
}