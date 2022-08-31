package com.novo.microservices.services.contracts;

/**
 * IOrchestratorParameterService
 * <p>
 * IOrchestratorParameterService interface.
 * <p>
 * <p>
 * ESTE COMPONENTE FUE CONSTRUIDO SIGUIENDO LOS ESTANDARES DE DESARROLLO Y EL PROCEDIMIENTO
 * DE DESARROLLO DE APLICACIONES DE NOVOPAYMENT Y SE ENCUENTRA PROTEGIDO POR LAS LEYES DE
 * PROPIEDAD INTELECTUAL Y DERECHOS DE AUTOR.
 *
 * @author Novopayment Inc.
 * @author lblanco@novopayment.com
 * @since 5/11/2022
 */
public interface IOrchestratorParameterService {

    String findTenantParameterSetting(String parameterKey);
}