package com.novo.microservices.services.implementations;

import com.novo.microservices.components.enums.ResponseCode;
import com.novo.microservices.components.helpers.SagaTransactionalJsonHelper;
import com.novo.microservices.dtos.requests.HsmGenerateKeyRequest;
import com.novo.microservices.services.contracts.IOrchestratorHsmConfigurationService;
import com.novo.microservices.tbs.utils.components.exceptions.BusinessProcessException;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import static org.springframework.beans.factory.config.BeanDefinition.SCOPE_PROTOTYPE;

/**
 * IOrchestratorHsmGenerateKeyConfigurationService
 * <p>
 * IOrchestratorHsmGenerateKeyConfigurationService interface.
 * <p>
 * <p>
 * ESTE COMPONENTE FUE CONSTRUIDO SIGUIENDO LOS ESTANDARES DE DESARROLLO Y EL PROCEDIMIENTO
 * DE DESARROLLO DE APLICACIONES DE NOVOPAYMENT Y SE ENCUENTRA PROTEGIDO POR LAS LEYES DE
 * PROPIEDAD INTELECTUAL Y DERECHOS DE AUTOR.
 *
 * @author Novopayment Inc.
 * @author jquiroga@novopayment.com
 * @since 5/18/2022
 */
@Log4j2
@Service
@Scope(SCOPE_PROTOTYPE)
public class OrchestratorHsmConfigurationService implements IOrchestratorHsmConfigurationService {
    private final SagaTransactionalJsonHelper sagaTransactionalJsonHelper;

    @Autowired
    public OrchestratorHsmConfigurationService(final SagaTransactionalJsonHelper sagaTransactionalJsonHelper) {
        this.sagaTransactionalJsonHelper = sagaTransactionalJsonHelper;
    }

    @Override
    public Mono<HsmGenerateKeyRequest> doOnFindByHeaderAndCommand(final String header, final String command) {
        return Mono.just(sagaTransactionalJsonHelper.loadHsmGenerateKeyParameters()
                .stream()
                .filter(request -> request.getHeader().equals(header) && request.getCommand().equals(command))
                .findFirst())
            .flatMap(optional -> optional
                .map(Mono::just)
                .orElseGet(() -> Mono.error(new BusinessProcessException(ResponseCode.INTERNAL_SERVER_ERROR))))
            .doOnSuccess(success -> {
                log.debug("success process findByHeaderAndCommand");
                log.debug("success process findByHeaderAndCommand, response: {}", success.toString());
            })
            .doOnError(throwable ->
                log.error("exception error in process findByHeaderAndCommand, error: {}", throwable.getMessage())
            )
            .log();
    }
}
