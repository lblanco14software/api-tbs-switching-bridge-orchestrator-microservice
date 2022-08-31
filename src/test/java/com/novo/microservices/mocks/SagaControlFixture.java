package com.novo.microservices.mocks;

import com.novo.microservices.tbs.utils.dtos.SagaControl;

/**
 * SagaControlFixture
 * <p>
 * SagaControlFixture class.
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
public class SagaControlFixture extends BaseFixture<SagaControl> {

    public SagaControlFixture() {
        setInstance(SagaControl.builder()
            .messageId("messageId")
            .eventId("eventId")
            .correlationId("correlationId")
            .eventPriority("eventPriority")
            .eventSource("eventSource")
            .eventDestiny("eventDestiny")
            .eventState("eventState")
            .eventDestinationQueue("eventDestinationQueue")
            .eventStartTime("eventStartTime")
            .eventCompensatory(false)
            .eventPivot(false)
            .build()
        );
    }
}