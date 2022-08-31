package com.novo.microservices.dtos.custom;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * CustomTransactionInformation
 * <p>
 * CustomTransactionInformation class.
 * <p>
 * <p>
 * ESTE COMPONENTE FUE CONSTRUIDO SIGUIENDO LOS ESTANDARES DE DESARROLLO Y EL PROCEDIMIENTO
 * DE DESARROLLO DE APLICACIONES DE NOVOPAYMENT Y SE ENCUENTRA PROTEGIDO POR LAS LEYES DE
 * PROPIEDAD INTELECTUAL Y DERECHOS DE AUTOR.
 *
 * @author Novopayment Inc.
 * @author lblanco@novopayment.com
 * @since 3/30/2022
 */
@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CustomTransactionInformation implements Serializable {

    private static final long serialVersionUID = 5230901019196076399L;

    @NotBlank
    @NotNull
    private String messageTypeIndicator;

    @NotBlank
    @NotNull
    private String de2;

    private String de3;
    private String de4;
    private String de7;
    private String de11;
    private String de12;
    private String de13;
    private String de17;
    private String de18;
    private String de22;
    private String de32;
    private String de35;
    private String de37;
    private String de38;
    private String de39;
    private String de41;
    private String de42;
    private String de43;
    private String de44;
    private String de46;
    private String de48;
    private String de49;
    private String de52;
    private String de55;
    private String de60;
    private String de61;
    private String de62;
    private String de63;
    private String de90;
    private String de100;
    private String de120;
    private String de121;
    private String de125;
    private String de126;
}