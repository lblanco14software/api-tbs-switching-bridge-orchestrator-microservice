package com.novo.microservices.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.io.Serializable;

/**
 * HsmGenerateKeyRequestBody
 * <p>
 * HsmGenerateKeyRequestBody class.
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
public class HsmGenerateKeyRequestBody implements Serializable {

    private static final long serialVersionUID = 5230901019196076399L;
    private String mode;
    private String keyType;
    private String schema;
    private String deriveKeyMode;
    private String dukptType;
    private String bdk;
    private String ksn;
    private String delimiterEncryptionKey;
    private String encryptionKeyType;
    private String encryptionKey;
    private String encryptionKeySchema;
}