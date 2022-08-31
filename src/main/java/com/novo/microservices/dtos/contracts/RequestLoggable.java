package com.novo.microservices.dtos.contracts;

import java.io.Serializable;

/**
 * Request
 * <p>
 * Request class.
 * <p>
 * <p>
 * ESTE COMPONENTE FUE CONSTRUIDO SIGUIENDO LOS ESTANDARES DE DESARROLLO Y EL PROCEDIMIENTO
 * DE DESARROLLO DE APLICACIONES DE NOVOPAYMENT Y SE ENCUENTRA PROTEGIDO POR LAS LEYES DE
 * PROPIEDAD INTELECTUAL Y DERECHOS DE AUTOR.
 *
 * @author Novopayment Inc.
 * @author jquiroga@novopayment.com
 * @since 7/7/2022
 */
public interface RequestLoggable extends Serializable {
    RequestLoggable prepareForLogInfo();
}
