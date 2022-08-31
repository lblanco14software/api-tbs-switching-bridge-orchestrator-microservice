package com.novo.microservices.components.configurations;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

/**
 * DataSourceInformation
 * <p>
 * DataSourceInformation class.
 * <p>
 * ESTE COMPONENTE FUE CONSTRUIDO SIGUIENDO LOS ESTANDARES DE DESARROLLO Y EL PROCEDIMIENTO
 * DE DESARROLLO DE APLICACIONES DE NOVOPAYMENT Y SE ENCUENTRA PROTEGIDO POR LAS LEYES DE
 * PROPIEDAD INTELECTUAL Y DERECHOS DE AUTOR.
 *
 * @author Novopayment Inc.
 * @author schancay@novopayment.com
 * @since 12/10/2021
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DataSourceInformation {

    @Value("${spring.jpa.datasource.jdbcUrl}")
    private String jdbcUrl;

    @Value("${spring.jpa.datasource.url}")
    private String url;

    @Value("${spring.jpa.datasource.username}")
    private String userName;

    @Value("${spring.jpa.datasource.password}")
    private String password;

    @Value("${spring.jpa.datasource.driver-class-name}")
    private String driverClassName;

    String validationQuery;
    String platform;
}
