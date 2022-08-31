package com.novo.microservices.services.implementations;

import com.novo.microservices.components.helpers.ContextInformationService;
import com.novo.microservices.dtos.base.BusinessResponse;
import com.novo.microservices.services.contracts.IOrchestratorParameterService;
import com.novo.microservices.tbs.utils.components.validations.implementations.CommonTransactionValidation;
import com.novo.microservices.tbs.utils.events.outbounds.implementations.TransactionProducer;
import com.novo.microservices.utils.common.context.AppSessionContext;
import com.novo.microservices.utils.common.dtos.ApplicationSession;
import com.novo.microservices.utils.common.services.contracts.IMicroservicesConfigurationServices;
import com.novo.microservices.utils.common.services.implementations.TenantConfigurationServices;
import com.novo.util.microservices.configurations.loader.constants.Constants;
import com.novo.util.microservices.configurations.loader.objects.parameters.ConfigHashTable;
import com.novo.util.microservices.configurations.loader.services.contracts.IComponentStandardConfigurationService;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockserver.junit.jupiter.MockServerExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static com.novo.microservices.constants.ProcessConstants.MICROSERVICES_CONFIG_NOT_FOUND;
import static org.mockito.Mockito.when;

/**
 * OrchestratorParameterServiceTest
 * <p>
 * OrchestratorParameterServiceTest class.
 * <p>
 * <p>
 * ESTE COMPONENTE FUE CONSTRUIDO SIGUIENDO LOS ESTANDARES DE DESARROLLO Y EL PROCEDIMIENTO
 * DE DESARROLLO DE APLICACIONES DE NOVOPAYMENT Y SE ENCUENTRA PROTEGIDO POR LAS LEYES DE
 * PROPIEDAD INTELECTUAL Y DERECHOS DE AUTOR.
 *
 * @author Novopayment Inc.
 * @author squijano@novopayment.com
 * @since 4/25/2022
 */
@Log4j2
@SpringBootTest(classes = OrchestratorParameterService.class)
@ExtendWith({SpringExtension.class, MockServerExtension.class})
@ContextConfiguration(classes = {
    OrchestratorParameterService.class,
    TransactionProducer.class,
    BusinessResponse.class,
    ContextInformationService.class,
    CommonTransactionValidation.class
})
@TestPropertySource(properties = {"spring.config.location=classpath:application.yml"})
class OrchestratorParameterServiceTest {

    @Autowired
    private IOrchestratorParameterService service;

    @MockBean
    private IMicroservicesConfigurationServices microservicesConfigurationServices;

    @MockBean
    private TenantConfigurationServices tenantConfigurationServices;

    @MockBean
    private AppSessionContext appSessionContext;

    @MockBean
    private IComponentStandardConfigurationService componentStandardConfigurationService;

    @BeforeEach
    public void setUp() {

        when(appSessionContext.getApplicationSession()).thenReturn(ApplicationSession.builder()
            .tenantId("mx-yastas")
            .requestId("12345")
            .build());
    }

    @Test
    void findTenantParameterSettingOk() {
        ConfigHashTable parameter = new ConfigHashTable();
        Constants.ConfigurationType configurationType = Constants.ConfigurationType.PARAMETER;

        parameter.setName("param");
        parameter.setValue("param");
        parameter.setConfigurationType(configurationType);
        when(microservicesConfigurationServices.getTenantConfiguration(Mockito.any()))
            .thenReturn(tenantConfigurationServices);
        when(tenantConfigurationServices.getParameterSettingOptional(Mockito.any()))
            .thenReturn(Optional.of(parameter));

        String data = service.findTenantParameterSetting("");

        Assertions.assertEquals("param", data);
    }

    @Test
    void findTenantParameterSettingNotFound() {
        ConfigHashTable parameter = new ConfigHashTable();
        Constants.ConfigurationType configurationType = Constants.ConfigurationType.PARAMETER;

        parameter.setName("");
        parameter.setValue("");
        parameter.setConfigurationType(configurationType);


        when(microservicesConfigurationServices.getTenantConfiguration(Mockito.any()))
            .thenReturn(tenantConfigurationServices);

        when(tenantConfigurationServices.getParameterSettingOptional(Mockito.any()))
            .thenReturn(Optional.of(parameter));

        String data = service.findTenantParameterSetting("");

        Assertions.assertEquals(MICROSERVICES_CONFIG_NOT_FOUND , data);
    }

    @Test
    void findTenantParameterSettingError() {
        appSessionContext = null;

        ConfigHashTable parameter = new ConfigHashTable();
        Constants.ConfigurationType configurationType = Constants.ConfigurationType.PARAMETER;

        parameter.setName("");
        parameter.setValue("");
        parameter.setConfigurationType(configurationType);


        when(microservicesConfigurationServices.getTenantConfiguration(Mockito.any()))
            .thenReturn(tenantConfigurationServices);

        when(tenantConfigurationServices.getParameterSettingOptional(Mockito.any()))
            .thenReturn(Optional.of(parameter));

        String data = service.findTenantParameterSetting("");

        Assertions.assertEquals(MICROSERVICES_CONFIG_NOT_FOUND , data);
    }
}