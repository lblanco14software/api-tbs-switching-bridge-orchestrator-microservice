package com.novo.microservices.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.novo.microservices.components.enums.ProcessResult;
import lombok.*;

import java.io.Serializable;

/**
 * CreateHsmGenerateResult
 * <p>
 * CreateHsmGenerateResult class.
 * <p>
 * <p>
 * ESTE COMPONENTE FUE CONSTRUIDO SIGUIENDO LOS ESTANDARES DE DESARROLLO Y EL PROCEDIMIENTO
 * DE DESARROLLO DE APLICACIONES DE NOVOPAYMENT Y SE ENCUENTRA PROTEGIDO POR LAS LEYES DE
 * PROPIEDAD INTELECTUAL Y DERECHOS DE AUTOR.
 *
 * @author Novopayment Inc.
 * @author lblanco@novopayment.com
 * @since 6/6/2022
 */
@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CreateHsmConfigurationsResult implements Serializable {

    private static final long serialVersionUID = 5230901019196076399L;
    private ProcessResult processResult;
}