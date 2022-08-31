package com.novo.microservices.components.helpers;

import com.novo.microservices.dtos.HsmGenerateKeyRequestBody;
import com.novo.microservices.dtos.requests.HsmGenerateKeyRequest;
import com.novo.microservices.dtos.requests.HsmRequest;
import com.novo.microservices.dtos.responses.HsmGenerateKeyResponse;
import com.novo.microservices.dtos.responses.HsmResponse;
import com.novo.microservices.tbs.utils.dtos.TransactionMessage;
import lombok.experimental.UtilityClass;

import java.util.HashMap;
import java.util.Map;

import static com.novo.microservices.constants.HsmConstants.*;

/**
 * HsmMapper
 * <p>
 * HsmMapper class.
 * <p>
 * ESTE COMPONENTE FUE CONSTRUIDO SIGUIENDO LOS ESTANDARES DE DESARROLLO Y EL PROCEDIMIENTO DE DESARROLLO DE
 * APLICACIONES DE NOVOPAYMENT Y SE ENCUENTRA PROTEGIDO POR LAS LEYES DE PROPIEDAD INTELECTUAL Y DERECHOS DE AUTOR.
 *
 * @author Novopayment Inc.
 * @author jquiroga@novopayment.com
 * @since 6/9/2020
 */
@UtilityClass
public class HsmMapper {

    public static TransactionMessage<HsmGenerateKeyResponse> mapperResponse(TransactionMessage<HsmResponse> transactionMessage) {
        return TransactionMessage.<HsmGenerateKeyResponse>builder()
            .messageId(transactionMessage.getMessageId())
            .correlationId(transactionMessage.getCorrelationId())
            .tenantId(transactionMessage.getTenantId())
            .requestId(transactionMessage.getRequestId())
            .eventSource(transactionMessage.getEventSource())
            .eventState(transactionMessage.getEventState())
            .transactionType(transactionMessage.getTransactionType())
            .data(HsmMapper.mapper(transactionMessage.getData()))
            .build();
    }

    public static TransactionMessage<HsmRequest> mapperRequest(TransactionMessage<HsmGenerateKeyRequest> transactionMessage) {
        return TransactionMessage.<HsmRequest>builder()
            .messageId(transactionMessage.getMessageId())
            .correlationId(transactionMessage.getCorrelationId())
            .tenantId(transactionMessage.getTenantId())
            .requestId(transactionMessage.getRequestId())
            .eventSource(transactionMessage.getEventSource())
            .eventState(transactionMessage.getEventState())
            .transactionType(transactionMessage.getTransactionType())
            .data(HsmMapper.mapper(transactionMessage.getData()))
            .build();
    }

    public static HsmGenerateKeyResponse mapper(HsmResponse response) {
        return HsmGenerateKeyResponse.builder()
            .header(response.getHeader())
            .command(response.getCommand())
            .trailer(response.getTrailer())
            .sessionId(response.getSessionId())
            .message(response.getMessage())
            .responseCode(response.getResponseCode())
            .body(HsmGenerateKeyResponse.Body.builder()
                .errorCode(response.getBody().get(RESPONSE_BODY_ERROR_CODE))
                .ipek(response.getBody().get(RESPONSE_BODY_IPEK))
                .keyUnderZmk(response.getBody().get(RESPONSE_BODY_KEY_UNDER_ZMK))
                .checkValue(response.getBody().get(RESPONSE_BODY_CHECK_VALUE))
                .build())
            .build();
    }

    public static HsmRequest mapper(HsmGenerateKeyRequest request) {
        HsmGenerateKeyRequestBody requestBody = request.getBody();
        Map<String, String> body = new HashMap<>();
        body.put(REQUEST_BODY_MODE, requestBody.getMode());
        body.put(REQUEST_BODY_KEY_TYPE, requestBody.getKeyType());
        body.put(REQUEST_BODY_SCHEMA, requestBody.getSchema());
        body.put(REQUEST_BODY_DERIVE_KEY_MODE, requestBody.getDeriveKeyMode());
        body.put(REQUEST_BODY_DUKPT_TYPE, requestBody.getDukptType());
        body.put(REQUEST_BODY_BDK, requestBody.getBdk());
        body.put(REQUEST_BODY_KSN, requestBody.getKsn());
        body.put(REQUEST_BODY_DELIMITER_ENCRYPTION_KEY, requestBody.getDelimiterEncryptionKey());
        body.put(REQUEST_BODY_ENCRYPTION_KEY_TYPE, requestBody.getEncryptionKeyType());
        body.put(REQUEST_BODY_ENCRYPTION_KEY, requestBody.getEncryptionKey());
        body.put(REQUEST_BODY_ENCRYPTION_KEY_SCHEMA, requestBody.getEncryptionKeySchema());

        return HsmRequest.builder()
            .header(request.getHeader())
            .command(request.getCommand())
            .body(body)
            .build();
    }
}
