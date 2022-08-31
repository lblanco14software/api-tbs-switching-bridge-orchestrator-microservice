package com.novo.microservices.constants;

import lombok.experimental.UtilityClass;

/**
 * HsmMapperConstants
 * <p>
 * HsmMapperConstants class.
 * <p>
 * ESTE COMPONENTE FUE CONSTRUIDO SIGUIENDO LOS ESTANDARES DE DESARROLLO Y EL PROCEDIMIENTO DE DESARROLLO DE
 * APLICACIONES DE NOVOPAYMENT Y SE ENCUENTRA PROTEGIDO POR LAS LEYES DE PROPIEDAD INTELECTUAL Y DERECHOS DE AUTOR.
 *
 * @author Novopayment Inc.
 * @author jquiroga@novopayment.com
 * @since 6/9/2020
 */
@UtilityClass
public class HsmConstants {
    public static final String RESPONSE_BODY_ERROR_CODE = "error-code";
    public static final String RESPONSE_BODY_IPEK = "ipek";
    public static final String RESPONSE_BODY_KEY_UNDER_ZMK = "key-under-zmk";
    public static final String RESPONSE_BODY_CHECK_VALUE = "check-value";

    public static final String REQUEST_BODY_MODE = "mode";
    public static final String REQUEST_BODY_KEY_TYPE = "key-type";
    public static final String REQUEST_BODY_SCHEMA = "schema";
    public static final String REQUEST_BODY_DERIVE_KEY_MODE = "derive-key-mode";
    public static final String REQUEST_BODY_DUKPT_TYPE = "dukpt-type";
    public static final String REQUEST_BODY_BDK = "bdk";
    public static final String REQUEST_BODY_KSN = "ksn";
    public static final String REQUEST_BODY_DELIMITER_ENCRYPTION_KEY = "delimiter-encryption-key";
    public static final String REQUEST_BODY_ENCRYPTION_KEY_TYPE = "encryption-key-type";
    public static final String REQUEST_BODY_ENCRYPTION_KEY = "encryption-key";
    public static final String REQUEST_BODY_ENCRYPTION_KEY_SCHEMA = "encryption-key-schema";

    public static final String HSM_RESPONSE_MESSAGE_INVALID = "Declined process";

    public static final String HSM_TRANSACTION_STATE_CODE = "HSM_GENERATE_KEY";
}
