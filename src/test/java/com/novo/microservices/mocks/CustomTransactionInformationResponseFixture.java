package com.novo.microservices.mocks;

import com.novo.microservices.dtos.custom.CustomTransactionInformation;

/**
 * CustomTransactionInformationResponseFixture
 * <p>
 * CustomTransactionInformationResponseFixture class.
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
public class CustomTransactionInformationResponseFixture extends BaseFixture<CustomTransactionInformation> {

    public CustomTransactionInformationResponseFixture() {
        setInstance(CustomTransactionInformation.builder()
            .messageTypeIndicator("0200")
            .de2("4513010000000043")
            .de3("380000")
            .de4("000000050000")
            .de7("0325104916")
            .de11("111111")
            .de12("104916")
            .de13("0325")
            .de18("5045")
            .de22("000")
            .de32("32323232323")
            .de35("4513010000000043=2602")
            .de37("373737373737")
            .de38("123456789")
            .de41("00000000")
            .de42("000000000000000")
            .build()
        );
    }
}