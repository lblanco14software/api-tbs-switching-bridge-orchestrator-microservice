package com.novo.microservices.dtos.responses;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.novo.microservices.dtos.contracts.ResponseLoggable;
import com.novo.microservices.dtos.generics.GenericBusinessResponse;
import com.novo.microservices.tbs.utils.dtos.responses.BusinessProcessResponse;
import lombok.*;

import java.io.Serializable;

/**
 * OrchestrationGenerateKeyResponse
 * <p>
 * OrchestrationGenerateKeyResponse class.
 * <p>
 * <p>
 * ESTE COMPONENTE FUE CONSTRUIDO SIGUIENDO LOS ESTANDARES DE DESARROLLO Y EL PROCEDIMIENTO
 * DE DESARROLLO DE APLICACIONES DE NOVOPAYMENT Y SE ENCUENTRA PROTEGIDO POR LAS LEYES DE
 * PROPIEDAD INTELECTUAL Y DERECHOS DE AUTOR.
 *
 * @author Novopayment Inc.
 * @author jquiroga@novopayment.com
 * @since 5/12/2022
 */
@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrchestrationGenerateKeyResponse implements Serializable {

    private static final long serialVersionUID = 5230901019196076399L;
    private GenerateKeyData data;

    @Data
    @Builder
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class GenerateKeyData implements ResponseLoggable, Serializable {
        private static final long serialVersionUID = 5230901019196076399L;

        private String trackingId;
        private String key;
        private String kcv;
        private String ksi;
        private String responseCode;
        private String responseMessage;

        @Override
        public BusinessProcessResponse prepareForLogInfo() {
            OrchestrationGenerateKeyResponse.GenerateKeyData result = OrchestrationGenerateKeyResponse.GenerateKeyData.builder().build();

            if (this.getResponseCode() != null) {
                result.setResponseCode(this.getResponseCode());
                result.setResponseMessage(this.getResponseMessage());
            }

            return BusinessProcessResponse.setEntitySuccessfullyResponse(new GenericBusinessResponse<>(result));
        }
    }
}