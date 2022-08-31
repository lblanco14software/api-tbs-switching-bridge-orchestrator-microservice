package com.novo.microservices.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.novo.microservices.components.enums.ValidateResult;
import lombok.*;

import java.io.Serializable;
import java.util.List;

/**
 * OrchestratorValidationResult
 * <p>
 * OrchestratorValidationResult class.
 * <p>
 * <p>
 * ESTE COMPONENTE FUE CONSTRUIDO SIGUIENDO LOS ESTANDARES DE DESARROLLO Y EL PROCEDIMIENTO
 * DE DESARROLLO DE APLICACIONES DE NOVOPAYMENT Y SE ENCUENTRA PROTEGIDO POR LAS LEYES DE
 * PROPIEDAD INTELECTUAL Y DERECHOS DE AUTOR.
 *
 * @author Novopayment Inc.
 * @author lblanco@novopayment.com
 * @since 3/31/2022
 */
@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrchestratorValidationResult implements Serializable {

    private static final long serialVersionUID = 751809722188783883L;
    private ValidateResult validateResult;
    private List<String> errors;
}