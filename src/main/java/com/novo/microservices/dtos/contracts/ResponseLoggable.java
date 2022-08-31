package com.novo.microservices.dtos.contracts;

import com.novo.microservices.tbs.utils.dtos.responses.BusinessProcessResponse;

import java.io.Serializable;

/**
 * Response
 * <p>
 * Response class.
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
public interface ResponseLoggable extends Serializable {
    BusinessProcessResponse prepareForLogInfo();
}
