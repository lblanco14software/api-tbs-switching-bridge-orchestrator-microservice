package com.novo.microservices.components.configurations;

/**
 * DBContextHolder
 * <p>
 * DBContextHolder class.
 * <p>
 * ESTE COMPONENTE FUE CONSTRUIDO SIGUIENDO LOS ESTANDARES DE DESARROLLO Y EL PROCEDIMIENTO
 * DE DESARROLLO DE APLICACIONES DE NOVOPAYMENT Y SE ENCUENTRA PROTEGIDO POR LAS LEYES DE
 * PROPIEDAD INTELECTUAL Y DERECHOS DE AUTOR.
 *
 * @author Novopayment Inc.
 * @author schancay@novopayment.com
 * @since 12/8/2021
 */
public class DBContextHolder {

    private static final ThreadLocal<String> contextHolder = new ThreadLocal<>();

    private DBContextHolder() {
        throw new IllegalStateException("Utility class");
    }

    public static String getCurrentDb() {
        return contextHolder.get();
    }

    public static void setCurrentDb(String clientId) {
        contextHolder.set(clientId);
    }

    public static void clear() {
        contextHolder.remove();
    }
}
