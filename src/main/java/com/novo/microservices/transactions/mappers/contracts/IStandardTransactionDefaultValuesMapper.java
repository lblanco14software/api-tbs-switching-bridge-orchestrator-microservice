package com.novo.microservices.transactions.mappers.contracts;

import com.novo.microservices.tbs.utils.dtos.StandardTransaction;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

/**
 * IStandardTransactionDefaultValuesMapper
 * <p>
 * IStandardTransactionDefaultValuesMapper class.
 * <p>
 * <p>
 * ESTE COMPONENTE FUE CONSTRUIDO SIGUIENDO LOS ESTANDARES DE DESARROLLO Y EL PROCEDIMIENTO
 * DE DESARROLLO DE APLICACIONES DE NOVOPAYMENT Y SE ENCUENTRA PROTEGIDO POR LAS LEYES DE
 * PROPIEDAD INTELECTUAL Y DERECHOS DE AUTOR.
 *
 * @author Novopayment Inc.
 * @author duhernandez@novopayment.com
 * @since 2/6/22
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface IStandardTransactionDefaultValuesMapper {
    void applyDefaultValues(@MappingTarget StandardTransaction standardTransactionToUpdate, StandardTransaction standardTransactionDefaultMapping);
}