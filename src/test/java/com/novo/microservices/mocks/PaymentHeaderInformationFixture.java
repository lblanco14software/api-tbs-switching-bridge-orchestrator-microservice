package com.novo.microservices.mocks;

import com.novo.microservices.dtos.custom.PaymentHeaderInformation;

/**
 * PaymentHeaderInformationFixture
 * <p>
 * PaymentHeaderInformationFixture class.
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
public class PaymentHeaderInformationFixture extends BaseFixture<PaymentHeaderInformation> {

    public PaymentHeaderInformationFixture() {
        setInstance(PaymentHeaderInformation.builder()
            .messageType("P")
            .trackingId("1702202216453613000452637")
            .bankCode("0066")
            .serviceId("00660035")
            .storeId("")
            .userId("")
            .externalBranchId("")
            .externalUserId("")
            .channelId("")
            .deviceId("")
            .extraDeviceInformation("")
            .ipAddress("")
            .build()
        );
    }
}