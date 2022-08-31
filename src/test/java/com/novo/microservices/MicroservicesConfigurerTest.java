package com.novo.microservices;

import com.novo.microservices.components.helpers.ContextInformationService;
import com.novo.microservices.components.helpers.DocumentationMicroservice;
import com.novo.microservices.dtos.base.BusinessResponse;
import com.novo.microservices.tbs.utils.components.configurations.MicroservicesConfigurer;
import com.novo.microservices.tbs.utils.components.interceptors.AppLoggerInterceptor;
import com.novo.microservices.utils.common.context.AppSessionContext;
import com.novo.util.microservices.configurations.loader.services.abstractions.implementations.ConfigDecryptionServices;
import com.novo.util.microservices.configurations.loader.services.abstractions.implementations.ConfigurationLoaderHttpClientService;
import com.novo.util.microservices.configurations.loader.services.implementations.ComponentLoaderConfigurationService;
import com.novo.util.microservices.configurations.loader.services.implementations.ComponentStandardConfigurationService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.ConfigDataApplicationContextInitializer;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

/**
 * MicroservicesConfigurer
 * <p>
 * MicroservicesConfigurer class.
 * <p>
 * ESTE COMPONENTE FUE CONSTRUIDO SIGUIENDO LOS ESTANDARES DE DESARROLLO Y EL PROCEDIMIENTO
 * DE DESARROLLO DE APLICACIONES DE NOVOPAYMENT Y SE ENCUENTRA PROTEGIDO POR LAS LEYES DE
 * PROPIEDAD INTELECTUAL Y DERECHOS DE AUTOR.
 *
 * @author Novopayment Inc.
 * @author schancay@novopayment.com
 * @since 01/22/2020
 */
@SpringBootTest
@AutoConfigureMockMvc
@ContextConfiguration(initializers = ConfigDataApplicationContextInitializer.class,
    loader = AnnotationConfigContextLoader.class,
    classes = {
        ComponentStandardConfigurationService.class,
        ComponentLoaderConfigurationService.class,
        ConfigurationLoaderHttpClientService.class,
        BusinessResponse.class,
        ContextInformationService.class,
        AppLoggerInterceptor.class,
        AppSessionContext.class,
        DocumentationMicroservice.class,
        MicroservicesConfigurer.class,
        ConfigDecryptionServices.class,
        ComponentLoaderConfigurationService.class,
        MicroservicesConfigurer.class,
    }
)
@Import({MicroservicesTestConfiguration.class})
//@TestPropertySource(properties = {"spring.config.location=classpath:application.yml"})
class MicroservicesConfigurerTest {

    AppLoggerInterceptor appLoggerInterceptor;
    MicroservicesConfigurer microservicesConfigurer;

    @Autowired
    public MicroservicesConfigurerTest(AppLoggerInterceptor appLoggerInterceptor,
                                       MicroservicesConfigurer microservicesConfigurer) {
        this.appLoggerInterceptor = appLoggerInterceptor;
        this.microservicesConfigurer = microservicesConfigurer;
    }

    @Test
    void testValidateConstructor() {
        Assertions.assertNotNull(appLoggerInterceptor);
    }
}
