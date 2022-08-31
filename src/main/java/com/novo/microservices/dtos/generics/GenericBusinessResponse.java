package com.novo.microservices.dtos.generics;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.novo.microservices.dtos.base.BaseBusinessResponseDto;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.json.bind.annotation.JsonbProperty;
import java.io.Serializable;

/**
 * GenericBusinessResponseDto
 * <p>
 * GenericBusinessResponseDto class.
 * <p>
 * ESTE COMPONENTE FUE CONSTRUIDO SIGUIENDO LOS ESTANDARES DE DESARROLLO Y EL PROCEDIMIENTO DE DESARROLLO DE
 * APLICACIONES DE NOVOPAYMENT Y SE ENCUENTRA PROTEGIDO POR LAS LEYES DE PROPIEDAD INTELECTUAL Y DERECHOS DE AUTOR.
 *
 * @author Novopayment Inc.
 * @author lblanco@novopayment.coms
 * @since 11/20/2020
 */
@Getter
@Setter
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@SuppressFBWarnings(value = "EI_EXPOSE_REP2", justification = "Skip by constructor")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GenericBusinessResponse<T> extends BaseBusinessResponseDto implements Serializable {

    private static final long serialVersionUID = -2515878949125281625L;
    @JsonbProperty("data")
    @JsonProperty("data")
    @Schema(name = "data", description = "Business data response")
    private T data;
}
