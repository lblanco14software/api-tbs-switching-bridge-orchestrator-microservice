package com.novo.microservices.mocks;

import com.novo.microservices.dtos.custom.CustomTransactionInformation;

/**
 * CustomTransactionInformationRequestFixture
 * <p>
 * CustomTransactionInformationRequestFixture class.
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
public class CustomTransactionInformationRequestFixture extends BaseFixture<CustomTransactionInformation> {

    public CustomTransactionInformationRequestFixture() {
        setInstance(CustomTransactionInformation.builder()
            .messageTypeIndicator("0200")
            .de2("4141411515151515")
            .de3("380000")
            .de4("000000000200")
            .de7("0314120000")
            .de11("123456")
            .de12("101520")
            .de13("1210")
            .de17("1205")
            .de18("9999")
            .de22("051")
            .de32("25252522552")
            .de35("4141411515151515=231120111523600000")
            .de37("101520101520")
            .de38("123456")
            .de39("R9")
            .de41("12345678")
            .de42("123456781234567")
            .de43("LOS JORGES CUAUHTEMOC DI MX")
            .de44("300000000000000000000000000000000000")
            .de46("123456")
            .de48("123456")
            .de49("484")
            .de52("0123456789ABCDEF")
            .de55("0123456789ABCDEF")
            .de60("YASTYAST+0000000")
            .de61("YASTYAST+0000000")
            .de62("YASTYAST+0000000")
            .de63(" 0000200042! B400020 9015110000000015080 ")
            .de90("02001234567891230000000000000000")
            .de100("11010100100")
            .de120("152535")
            .de121("152535")
            .de125("152535")
            .de126("601548")
            .build()
        );
    }
}