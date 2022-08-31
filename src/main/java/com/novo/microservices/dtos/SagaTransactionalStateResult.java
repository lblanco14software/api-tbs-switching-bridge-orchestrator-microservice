package com.novo.microservices.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

/**
 * SagaTransactionalStateResult
 * <p>
 * SagaTransactionalStateResult class.
 * <p>
 * <p>
 * ESTE COMPONENTE FUE CONSTRUIDO SIGUIENDO LOS ESTANDARES DE DESARROLLO Y EL PROCEDIMIENTO
 * DE DESARROLLO DE APLICACIONES DE NOVOPAYMENT Y SE ENCUENTRA PROTEGIDO POR LAS LEYES DE
 * PROPIEDAD INTELECTUAL Y DERECHOS DE AUTOR.
 *
 * @author Novopayment Inc.
 * @author lblanco@novopayment.com
 * @since 4/11/2022
 */
@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SagaTransactionalStateResult implements Serializable {

    private static final long serialVersionUID = 751809722188783883L;
    private List<String> warnings;
    private SagaStateChangeResult sagaStateChangeResult;

    public Boolean isSuccessfullyResponse() {
        return Optional.ofNullable(warnings).isEmpty();
    }

    public Boolean isErrorInProcess() {
        return Optional.ofNullable(warnings).isPresent();
    }

    public enum SagaStateChangeResult {
        SAGA_EXPECTED_STATE,
        NOT_SAGA_EXPECTED_STATE,
    }
}