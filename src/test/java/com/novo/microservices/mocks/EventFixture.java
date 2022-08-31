package com.novo.microservices.mocks;

import com.novo.microservices.tbs.utils.dtos.StandardTransaction;
import com.novo.microservices.tbs.utils.dtos.TransactionMessage;
import com.novo.utils.messaging.dtos.Event;
import lombok.Builder;

/**
 * EventFixture
 * <p>
 * EventFixture class.
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
@Builder
public class EventFixture extends BaseFixture<Event<TransactionMessage<StandardTransaction>>> {

    public EventFixture() {
        setInstance(Event.<TransactionMessage<StandardTransaction>>builder()
            .routingKey(new RoutingKeyFixture().getInstance())
            .data(new TransactionMessageFixture().getInstance())
            .build());
    }
}