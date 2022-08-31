package com.novo.microservices;

import com.novo.microservices.base.MicroservicesBaseApplication;
import com.novo.microservices.components.properties.MicroServicesProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.validation.constraints.NotNull;

/**
 * SpringBootApplication
 * <p>
 * SpringBootApplication class.
 * <p>
 * ESTE COMPONENTE FUE CONSTRUIDO SIGUIENDO LOS ESTANDARES DE DESARROLLO Y EL PROCEDIMIENTO DE DESARROLLO DE
 * APLICACIONES DE NOVOPAYMENT Y SE ENCUENTRA PROTEGIDO POR LAS LEYES DE PROPIEDAD INTELECTUAL Y DERECHOS DE AUTOR.
 *
 * @author Novopayment Inc.
 * @author lblanco@novopayment.com
 * @since 11/16/2020
 */
@SpringBootApplication(
    scanBasePackages = {
        "com.novo.utils.security",
        "com.novo.utils.jwa",
        "com.novo.utils.repository",
        "com.novo.utils.httpclients",
        "com.novo.util.microservices.configurations.loader",
        "novo.microservices.utils",
        "com.novo.microservices"
    }
)
public class MicroservicesApplication extends MicroservicesBaseApplication {

    public MicroservicesApplication(@NotNull MicroServicesProperties microServicesProperties) {
        super(microServicesProperties);
    }

    public static void main(String[] args) {
        SpringApplication.run(MicroservicesApplication.class);
    }
}
