package com.novo.microservices;

import com.novo.microservices.components.helpers.ContextInformationService;
import com.novo.microservices.components.helpers.DocumentationMicroservice;
import com.novo.microservices.dtos.base.BusinessResponse;
import com.novo.microservices.tbs.utils.components.interceptors.AppLoggerInterceptor;
import com.novo.microservices.utils.common.context.AppSessionContext;
import com.novo.microservices.utils.common.dtos.ApplicationSession;
import com.novo.util.microservices.configurations.loader.services.abstractions.implementations.ConfigurationLoaderHttpClientService;
import com.novo.util.microservices.configurations.loader.services.implementations.ComponentLoaderConfigurationService;
import com.novo.util.microservices.configurations.loader.services.implementations.ComponentStandardConfigurationService;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.ConfigDataApplicationContextInitializer;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

/**
 * MicroservicesApplicationTests
 * <p>
 * MicroservicesApplicationTests class.
 * <p>
 * ESTE COMPONENTE FUE CONSTRUIDO SIGUIENDO LOS ESTANDARES DE DESARROLLO Y EL PROCEDIMIENTO
 * DE DESARROLLO DE APLICACIONES DE NOVOPAYMENT Y SE ENCUENTRA PROTEGIDO POR LAS LEYES DE
 * PROPIEDAD INTELECTUAL Y DERECHOS DE AUTOR.
 *
 * @author Novopayment Inc.
 * @author schancay@novopayment.com
 * @since 11/17/2020
 */
@SpringBootTest
@AutoConfigureMockMvc
@ContextConfiguration(initializers = ConfigDataApplicationContextInitializer.class,
    loader = AnnotationConfigContextLoader.class,
    classes = {
        ComponentStandardConfigurationService.class,
        ComponentLoaderConfigurationService.class,
        ConfigurationLoaderHttpClientService.class,
        ApplicationSession.class,
        AppSessionContext.class,
        BusinessResponse.class,
        ContextInformationService.class,
        AppLoggerInterceptor.class,
        DocumentationMicroservice.class,
        MicroservicesApplication.class
    }
)
@Import({MicroservicesTestConfiguration.class})
class MicroservicesApplicationTests {


}
