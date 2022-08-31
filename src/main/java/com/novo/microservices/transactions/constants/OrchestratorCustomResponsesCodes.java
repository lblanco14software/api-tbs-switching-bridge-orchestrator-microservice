package com.novo.microservices.transactions.constants;

import lombok.experimental.UtilityClass;

/**
 * OrchestratorCustomResponsesCodes
 * <p>
 * OrchestratorCustomResponsesCodes class.
 * <p>
 * <p>
 * ESTE COMPONENTE FUE CONSTRUIDO SIGUIENDO LOS ESTANDARES DE DESARROLLO Y EL PROCEDIMIENTO
 * DE DESARROLLO DE APLICACIONES DE NOVOPAYMENT Y SE ENCUENTRA PROTEGIDO POR LAS LEYES DE
 * PROPIEDAD INTELECTUAL Y DERECHOS DE AUTOR.
 *
 * @author Novopayment Inc.
 * @author lblanco@novopayment.com
 * @since 4/2/2022
 */
@UtilityClass
public class OrchestratorCustomResponsesCodes {

    // Reverse Cash Withdrawal Microservice Transactions
    public static final String CUSTOM_RESPONSE_STATUS_COMPLETED_CODE = "C";
    public static final String CUSTOM_RESPONSE_STATUS_COMPLETED_DESCRIPTION = "Proceso completado";

    public static final String CUSTOM_RESPONSE_STATUS_DECLINED_CODE = "D";
    public static final String CUSTOM_RESPONSE_STATUS_DECLINED_DESCRIPTION = "Proceso declinado";
}