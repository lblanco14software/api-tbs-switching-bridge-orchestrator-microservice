package com.novo.microservices;

import com.novo.microservices.components.helpers.ContextInformationService;
import com.novo.microservices.components.helpers.DocumentationMicroservice;
import com.novo.microservices.dtos.base.BusinessResponse;
import com.novo.microservices.mocks.StandardTransactionDefaultValuesMapperMockImpl;
import com.novo.microservices.mocks.StandardTransactionMapperMockImpl;
import com.novo.microservices.tbs.utils.components.configurations.MicroservicesConfigurer;
import com.novo.microservices.tbs.utils.components.interceptors.AppLoggerInterceptor;
import com.novo.microservices.transactions.mappers.contracts.IStandardTransactionDefaultValuesMapper;
import com.novo.microservices.transactions.mappers.contracts.IStandardTransactionMapper;
import com.novo.microservices.utils.common.context.AppSessionContext;
import com.novo.util.microservices.configurations.loader.components.factories.implementations.ConfigurationLoaderFactory;
import com.novo.util.microservices.configurations.loader.services.abstractions.implementations.CacheManagerService;
import com.novo.util.microservices.configurations.loader.services.abstractions.implementations.ConfigDecryptionServices;
import com.novo.util.microservices.configurations.loader.services.abstractions.implementations.ConfigurationLoaderHttpClientService;
import com.novo.util.microservices.configurations.loader.services.abstractions.implementations.ConfigurationRegistryService;
import com.novo.util.microservices.configurations.loader.services.implementations.ComponentCronConfigurationService;
import com.novo.util.microservices.configurations.loader.services.implementations.ComponentLoaderConfigurationService;
import com.novo.util.microservices.configurations.loader.services.implementations.ComponentStandardConfigurationService;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.ConfigDataApplicationContextInitializer;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

/**
 * MicroservicesCommonTestConfiguration
 * <p>
 * MicroservicesCommonTestConfiguration class.
 * <p>
 * ESTE COMPONENTE FUE CONSTRUIDO SIGUIENDO LOS ESTANDARES DE DESARROLLO Y EL PROCEDIMIENTO
 * DE DESARROLLO DE APLICACIONES DE NOVOPAYMENT Y SE ENCUENTRA PROTEGIDO POR LAS LEYES DE
 * PROPIEDAD INTELECTUAL Y DERECHOS DE AUTOR.
 *
 * @author Novopayment Inc.
 * @author lblanco@novopayment.com
 * @since 04/10/2022
 */
@SpringBootTest
@AutoConfigureMockMvc
@ContextConfiguration(initializers = ConfigDataApplicationContextInitializer.class,
    loader = AnnotationConfigContextLoader.class,
    classes = {
        ComponentStandardConfigurationService.class,
        ComponentLoaderConfigurationService.class,
        ConfigurationLoaderHttpClientService.class,
        ComponentCronConfigurationService.class,
        ConfigurationLoaderHttpClientService.class,
        ConfigurationLoaderFactory.class,
        ConfigurationRegistryService.class,
        CacheManagerService.class,
        BusinessResponse.class,
        ContextInformationService.class,
        AppLoggerInterceptor.class,
        AppSessionContext.class,
        DocumentationMicroservice.class,
        MicroservicesConfigurer.class,
        ConfigDecryptionServices.class,
        MicroservicesConfigurer.class,
    }
)
@Import({MicroservicesTestConfiguration.class})
//@TestPropertySource(properties = {"spring.config.location=classpath:application.yml"})
public class MicroservicesCommonTestConfiguration {

    @Bean
    public IStandardTransactionMapper standardTransactionMapper() {
        return new StandardTransactionMapperMockImpl();
    }

    @Bean
    public IStandardTransactionDefaultValuesMapper standardTransactionDefaultValuesMapper() {
        return new StandardTransactionDefaultValuesMapperMockImpl();
    }
}
