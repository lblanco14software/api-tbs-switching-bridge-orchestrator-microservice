package com.novo.microservices.dtos.responses;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.io.Serializable;

/**
 * HsmGenerateKeyRequest
 * <p>
 * HsmGenerateKeyRequest class.
 * <p>
 * <p>
 * ESTE COMPONENTE FUE CONSTRUIDO SIGUIENDO LOS ESTANDARES DE DESARROLLO Y EL PROCEDIMIENTO
 * DE DESARROLLO DE APLICACIONES DE NOVOPAYMENT Y SE ENCUENTRA PROTEGIDO POR LAS LEYES DE
 * PROPIEDAD INTELECTUAL Y DERECHOS DE AUTOR.
 *
 * @author Novopayment Inc.
 * @author jquiroga@novopayment.com
 * @since 5/13/2022
 */
@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class HsmGenerateKeyResponse implements Serializable {

    private static final long serialVersionUID = 5230901019196076399L;

    private String header;
    private String command;
    private String trailer;
    private String sessionId;
    private String message;
    private String responseCode;
    private Body body;

    @Data
    @Builder
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Body implements Serializable {

        private static final long serialVersionUID = 5230901019196076399L;
        private String errorCode;
        private String ipek;
        private String keyUnderZmk;
        private String checkValue;
    }
}