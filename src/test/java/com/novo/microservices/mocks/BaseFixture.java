package com.novo.microservices.mocks;

import lombok.Getter;
import lombok.Setter;

import static lombok.AccessLevel.PROTECTED;

/**
 * BaseFixture
 * <p>
 * BaseFixture class.
 * <p>
 * <p>
 * ESTE COMPONENTE FUE CONSTRUIDO SIGUIENDO LOS ESTANDARES DE DESARROLLO Y EL PROCEDIMIENTO
 * DE DESARROLLO DE APLICACIONES DE NOVOPAYMENT Y SE ENCUENTRA PROTEGIDO POR LAS LEYES DE
 * PROPIEDAD INTELECTUAL Y DERECHOS DE AUTOR.
 *
 * @author Novopayment Inc.
 * @author aardila@novopayment.com
 * @since 4/18/2022
 */
@Getter
@Setter(PROTECTED)
public class BaseFixture<T> {

    private T instance;
}