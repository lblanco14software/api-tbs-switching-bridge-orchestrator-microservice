package com.novo.microservices;

import com.novo.utils.messaging.components.aspects.BeanDirectorProcessor;
import com.novo.utils.messaging.components.configurations.MicroservicesBrokerConfiguration;
import com.novo.utils.messaging.components.configurations.MicroservicesMessagingConfiguration;
import com.novo.utils.messaging.services.implementations.*;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.ConfigDataApplicationContextInitializer;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

/**
 * MicroservicesBrokerTestConfiguration
 * <p>
 * MicroservicesBrokerTestConfiguration class.
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
@Import({MicroservicesTestConfiguration.class, MicroservicesBrokerConfiguration.class})
@ContextConfiguration(initializers = ConfigDataApplicationContextInitializer.class,
    loader = AnnotationConfigContextLoader.class,
    classes = {
        MicroservicesMessagingConfiguration.class,
        ConsumerRegisterService.class,
        CallBackBuilderService.class,
        CallBackRegisterService.class,
        ConfirmRegisterService.class,
        ConsumerBuilderService.class,
        ConsumerDispatchService.class,
        ConsumerRegisterService.class,
        EventMapperService.class,
        ExchangeRegisterService.class,
        PublisherService.class,
        QueueRegisterService.class,
        BeanDirectorProcessor.class,
    }
)
//@TestPropertySource(properties = {"spring.config.location=classpath:application.yml"})
@TestConfiguration
public class MicroservicesBrokerTestConfiguration {

    @Bean
    @Primary
    public MicroservicesMessagingConfiguration microservicesMessagingConfiguration() {
        final MicroservicesMessagingConfiguration microservicesMessagingConfiguration = new MicroservicesMessagingConfiguration();
        microservicesMessagingConfiguration.setHost("localhost");
        microservicesMessagingConfiguration.setPassword("guest");
        microservicesMessagingConfiguration.setUsername("guest");
        microservicesMessagingConfiguration.setVirtualhost("/");
        microservicesMessagingConfiguration.setPublisherReturns(true);
        microservicesMessagingConfiguration.setPublisherConfirmType("CORRELATED");
        return microservicesMessagingConfiguration;
    }
}
