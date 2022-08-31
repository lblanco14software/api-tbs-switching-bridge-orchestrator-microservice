package com.novo.microservices.components.abstractions;

import com.novo.microservices.components.exceptions.AbstractFactoryException;

/**
 * IAbstractFactory
 * <p>
 * IAbstractFactory interface.
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
public interface IAbstractFactory<T> {

    T factory(String nameComponent) throws AbstractFactoryException;

    ;
}