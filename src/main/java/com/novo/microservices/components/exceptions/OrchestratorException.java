package com.novo.microservices.components.exceptions;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * OrchestratorException
 * <p>
 * OrchestratorException class.
 * <p>
 * <p>
 * ESTE COMPONENTE FUE CONSTRUIDO SIGUIENDO LOS ESTANDARES DE DESARROLLO Y EL PROCEDIMIENTO
 * DE DESARROLLO DE APLICACIONES DE NOVOPAYMENT Y SE ENCUENTRA PROTEGIDO POR LAS LEYES DE
 * PROPIEDAD INTELECTUAL Y DERECHOS DE AUTOR.
 *
 * @author Novopayment Inc.
 * @author lblanco@novopayment.com
 * @since 4/4/2022
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class OrchestratorException extends RuntimeException {

    private static final long serialVersionUID = 1412040912143293559L;

    /***
     * create a {@link OrchestratorException} instance with message process result
     * @param message process status value
     */
    public OrchestratorException(String message) {
        super(message);
    }
}