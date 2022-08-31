package com.novo.microservices.services.implementations;

import com.google.gson.Gson;
import com.novo.microservices.dtos.responses.HsmGenerateKeyResponse;
import com.novo.microservices.services.contracts.IOrchestratorEncryptionService;
import com.novo.microservices.tbs.utils.components.exceptions.SagaProcessException;
import com.novo.microservices.tbs.utils.dtos.StandardTransaction;
import com.novo.utils.security.components.algorithms.dtos.AESEncryptionRequest;
import com.novo.utils.security.components.algorithms.dtos.TripleDESEncryptionRequest;
import com.novo.utils.security.dtos.ProcessResponse;
import com.novo.utils.security.services.contracts.IEncryptionServices;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.io.Serializable;
import java.util.UUID;

import static com.novo.microservices.constants.ProcessConstants.MICROSERVICES_ORCHESTRATOR_ENCRYPTION_KEY;
import static org.springframework.beans.factory.config.BeanDefinition.SCOPE_PROTOTYPE;

/**
 * OrchestratorEncryptionService
 * <p>
 * OrchestratorEncryptionService class.
 * <p>
 * <p>
 * ESTE COMPONENTE FUE CONSTRUIDO SIGUIENDO LOS ESTANDARES DE DESARROLLO Y EL PROCEDIMIENTO
 * DE DESARROLLO DE APLICACIONES DE NOVOPAYMENT Y SE ENCUENTRA PROTEGIDO POR LAS LEYES DE
 * PROPIEDAD INTELECTUAL Y DERECHOS DE AUTOR.
 *
 * @author Novopayment Inc.
 * @author lblanco@novopayment.com
 * @since 4/25/2022
 */
@Log4j2
@Service
@Scope(SCOPE_PROTOTYPE)
public class OrchestratorEncryptionService implements IOrchestratorEncryptionService {

    @Value(MICROSERVICES_ORCHESTRATOR_ENCRYPTION_KEY)
    private String orchestratorEncryptionKey;
    private final IEncryptionServices encryptionServices;
    private final String orchestratorEncryptionServiceId;

    public OrchestratorEncryptionService(final IEncryptionServices encryptionServices) {

        this.encryptionServices = encryptionServices;
        this.orchestratorEncryptionServiceId = UUID.randomUUID().toString();
        log.debug("orchestratorEncryptionServiceId: {}", orchestratorEncryptionServiceId);
        log.debug("OrchestratorEncryptionService loaded successfully");
    }

    @Override
    public Mono<String> doOnEncryptTransaction(final StandardTransaction standardTransaction) {
        return this.doOnEncryptGenericTransaction(standardTransaction);
    }

    @Override
    public Mono<StandardTransaction> doOnDecryptTransaction(final String standardTransactionEncrypted) {
        log.debug("orchestratorEncryptionServiceId {}", orchestratorEncryptionServiceId);
        return Mono.just(standardTransactionEncrypted)
            .flatMap(currentStandardTransaction -> {
                final AESEncryptionRequest aesEncryptionRequest = AESEncryptionRequest.builder()
                    .key(orchestratorEncryptionKey)
                    .data(currentStandardTransaction)
                    .build();
                return Mono.just(aesEncryptionRequest);
            })
            .flatMap(aesEncryptionRequest -> {
                try {
                    final ProcessResponse processResponse = encryptionServices.decrypt(aesEncryptionRequest);

                    if (Boolean.TRUE.equals(processResponse.getIsError())) {
                        return Mono.error(() -> new SagaProcessException("error in doOnDecryptTransaction process"));
                    }
                    final String standardTransactionJson = processResponse.getResponse().getGeneratedText();
                    return Mono.just(new Gson().fromJson(standardTransactionJson, StandardTransaction.class));
                } catch (Exception e) {
                    log.error("error in doOnDecryptTransaction process, error: {}", e.getMessage());
                    return Mono.error(() -> new SagaProcessException("error in doOnEncryptTransaction process"));
                }
            })
            .doOnSuccess(success ->
                log.debug("success doOnDecryptTransaction from standardTransactionEncrypted, response: {}", success)
            )
            .doOnError(throwable ->
                log.error("exception error in process doOnDecryptTransaction, error: {}", throwable.getMessage())
            )
            .log();
    }

    @Override
    public <T extends Serializable> Mono<String> doOnEncryptGenericTransaction(T transaction) {
        log.debug("orchestratorEncryptionServiceId {}", orchestratorEncryptionServiceId);
        return Mono.just(transaction)
            .flatMap(currentTransaction -> {
                final AESEncryptionRequest aesEncryptionRequest = AESEncryptionRequest.builder()
                    .key(orchestratorEncryptionKey)
                    .data(new Gson().toJson(transaction))
                    .build();
                return Mono.just(aesEncryptionRequest);
            })
            .flatMap(aesEncryptionRequest -> {
                try {
                    final ProcessResponse processResponse = encryptionServices.encrypt(aesEncryptionRequest);
                    if (Boolean.TRUE.equals(processResponse.getIsError())) {
                        return Mono.error(() -> new SagaProcessException("error in doOnEncryptTransaction process"));
                    }
                    return Mono.just(processResponse.getResponse().getGeneratedText());
                } catch (Exception e) {
                    log.error("error in doOnEncryptTransaction process, error: {}", e.getMessage());
                    return Mono.error(() -> new SagaProcessException("error in doOnEncryptTransaction process"));
                }
            })
            .doOnSuccess(success ->
                log.debug("success doOnEncryptTransaction from generic transaction, response: {}", success)
            )
            .doOnError(throwable ->
                log.error("exception error in process doOnEncryptTransaction, error: {}", throwable.getMessage())
            )
            .log();
    }

    @Override
    public Mono<HsmGenerateKeyResponse> doOnDecryptHsmGenerateKeyResponse(final String encryptTransaction) {
        log.debug("orchestratorEncryptionServiceId {}", orchestratorEncryptionServiceId);
        return Mono.just(encryptTransaction)
            .flatMap(currentStandardTransaction -> {
                final AESEncryptionRequest aesEncryptionRequest = AESEncryptionRequest.builder()
                    .key(orchestratorEncryptionKey)
                    .data(currentStandardTransaction)
                    .build();
                return Mono.just(aesEncryptionRequest);
            })
            .flatMap(aesEncryptionRequest -> {
                try {
                    final ProcessResponse processResponse = encryptionServices.decrypt(aesEncryptionRequest);

                    if (Boolean.TRUE.equals(processResponse.getIsError())) {
                        return Mono.error(() -> new SagaProcessException("error in doOnDecryptTransaction process"));
                    }
                    final String standardTransactionJson = processResponse.getResponse().getGeneratedText();
                    return Mono.just(new Gson().fromJson(standardTransactionJson, HsmGenerateKeyResponse.class));
                } catch (Exception e) {
                    log.error("error in doOnDecryptTransaction process, error: {}", e.getMessage());
                    return Mono.error(() -> new SagaProcessException("error in doOnEncryptTransaction process"));
                }
            })
            .doOnSuccess(success ->
                log.debug("success doOnDecryptTransaction from standardTransactionEncrypted, response: {}", success)
            )
            .doOnError(throwable ->
                log.error("exception error in process doOnDecryptTransaction, error: {}", throwable.getMessage())
            )
            .log();
    }

    @Override
    public String doOnEncryptData(String clearData) {
        log.debug("orchestratorEncryptionServiceId {}", orchestratorEncryptionServiceId);
        try {
            final TripleDESEncryptionRequest tripleDESEncryptionRequest = TripleDESEncryptionRequest.builder()
                .key(orchestratorEncryptionKey)
                .strData(clearData)
                .build();
            final ProcessResponse processResponse = encryptionServices.encrypt(tripleDESEncryptionRequest);
            return processResponse.getResponse().getGeneratedText();
        } catch (Exception ex) {
            log.error(ex.toString());
        }
        return "";
    }
}