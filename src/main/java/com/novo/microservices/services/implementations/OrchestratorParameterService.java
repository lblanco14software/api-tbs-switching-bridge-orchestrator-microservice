package com.novo.microservices.services.implementations;

import com.novo.microservices.services.contracts.IOrchestratorParameterService;
import com.novo.microservices.utils.common.context.AppSessionContext;
import com.novo.microservices.utils.common.services.contracts.IMicroservicesConfigurationServices;
import com.novo.microservices.utils.common.services.implementations.TenantConfigurationServices;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static com.novo.microservices.constants.ProcessConstants.MICROSERVICES_CONFIG_NOT_FOUND;
import static org.springframework.beans.factory.config.BeanDefinition.SCOPE_PROTOTYPE;

/**
 * OrchestratorParameterService
 * <p>
 * OrchestratorParameterService class.
 * <p>
 * <p>
 * ESTE COMPONENTE FUE CONSTRUIDO SIGUIENDO LOS ESTANDARES DE DESARROLLO Y EL PROCEDIMIENTO
 * DE DESARROLLO DE APLICACIONES DE NOVOPAYMENT Y SE ENCUENTRA PROTEGIDO POR LAS LEYES DE
 * PROPIEDAD INTELECTUAL Y DERECHOS DE AUTOR.
 *
 * @author Novopayment Inc.
 * @author lblanco@novopayment.com
 * @since 5/11/2022
 */
@Log4j2
@Service
@Scope(SCOPE_PROTOTYPE)
public class OrchestratorParameterService implements IOrchestratorParameterService {

    private final IMicroservicesConfigurationServices microservicesConfigurationServices;
    private final AppSessionContext appSessionContext;
    private final String orchestratorParameterServiceId;

    public OrchestratorParameterService(final IMicroservicesConfigurationServices microservicesConfigurationServices, final AppSessionContext appSessionContext) {
        this.microservicesConfigurationServices = microservicesConfigurationServices;
        this.appSessionContext = appSessionContext;
        this.orchestratorParameterServiceId = UUID.randomUUID().toString();
        log.debug("orchestratorParameterServiceId {}", orchestratorParameterServiceId);
        log.debug("OrchestratorParameterService loaded successfully");
    }

    @Override
    public String findTenantParameterSetting(String parameterKey) {

        try {
            log.debug("orchestratorParameterServiceId {}", orchestratorParameterServiceId);
            final TenantConfigurationServices tenantConfigurationServices = microservicesConfigurationServices.getTenantConfiguration(appSessionContext.getApplicationSession().getTenantId());
            var parameterSetting = tenantConfigurationServices.getParameterSettingOptional(parameterKey);
            if (!tenantConfigurationServices.isErrorConfiguration() && parameterSetting.isPresent() && StringUtils.isNotEmpty((parameterSetting.get()).getValue())) {
                return parameterSetting.get().getValue();
            }
            return MICROSERVICES_CONFIG_NOT_FOUND;
        } catch (Exception e) {
            log.error("error un process findTenantParameterSetting, error: {}", e.getMessage());
            return MICROSERVICES_CONFIG_NOT_FOUND;
        }
    }
}