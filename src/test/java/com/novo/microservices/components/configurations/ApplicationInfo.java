package com.novo.microservices.components.configurations;

import lombok.Data;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Value;

import java.io.Serializable;

/**
 * ApplicationInfo
 * <p>
 * ApplicationInfo class.
 * <p>
 * ESTE COMPONENTE FUE CONSTRUIDO SIGUIENDO LOS ESTANDARES DE DESARROLLO Y EL PROCEDIMIENTO DE DESARROLLO DE
 * APLICACIONES DE NOVOPAYMENT Y SE ENCUENTRA PROTEGIDO POR LAS LEYES DE PROPIEDAD INTELECTUAL Y DERECHOS DE AUTOR.
 *
 * @author Novopayment Inc.
 * @author lblanco@novopayment.com
 * @since 14/08/2020
 */
@Data
@ToString
public class ApplicationInfo implements Serializable {

    private static final long serialVersionUID = 6671353550231814654L;

    @Value("${configMap.message}")
    private String configMapCheck;

    @Value("${entityUuid}")
    private String entityUuid;

    @Value("${spring.application.name}")
    private String applicationName;

    @Value("${spring.application.version}")
    private String applicationVersion;

    @Value("${spring.version}")
    private String springBootVersion;

    @Value("${configurationDomainPath}")
    private String multicoreDomainPath;

    @Value("${hazelcastYmlPath}")
    private String hazelcastYmlPath;
}
