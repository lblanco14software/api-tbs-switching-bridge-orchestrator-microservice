package com.novo.microservices;

import com.novo.microservices.tbs.utils.services.implementations.*;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.ConfigDataApplicationContextInitializer;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
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
@ContextConfiguration(initializers = ConfigDataApplicationContextInitializer.class,
    loader = AnnotationConfigContextLoader.class,
    classes = {
        SagaControlHistoryService.class,
        SagaControlService.class,
        SagaMongoDatabaseService.class,
        SagaOutBoxService.class,
        SagaTransactionalBridgeService.class,
        SagaTransactionService.class,
    }
)
@Import({MicroservicesTestConfiguration.class})
//@TestPropertySource(properties = {"spring.config.location=classpath:application.yml"})
public class MicroservicesSagaTestConfiguration {


}
