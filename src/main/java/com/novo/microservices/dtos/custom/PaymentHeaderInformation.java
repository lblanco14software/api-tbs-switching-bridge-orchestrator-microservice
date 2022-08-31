package com.novo.microservices.dtos.custom;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * PaymentHeaderInformation
 * <p>
 * PaymentHeaderInformation class.
 * <p>
 * <p>
 * ESTE COMPONENTE FUE CONSTRUIDO SIGUIENDO LOS ESTANDARES DE DESARROLLO Y EL PROCEDIMIENTO
 * DE DESARROLLO DE APLICACIONES DE NOVOPAYMENT Y SE ENCUENTRA PROTEGIDO POR LAS LEYES DE
 * PROPIEDAD INTELECTUAL Y DERECHOS DE AUTOR.
 *
 * @author Novopayment Inc.
 * @author lblanco@novopayment.com
 * @since 3/30/2022
 */
@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PaymentHeaderInformation implements Serializable {

    private static final long serialVersionUID = 5230901019196076399L;
    private String messageType;
    private String trackingId;
    private String bankCode;

    @NotBlank
    @NotNull
    private String serviceId;
    private String storeId;
    private String userId;
    private String externalBranchId;
    private String externalUserId;
    private String channelId;
    private String deviceId;
    private String extraDeviceInformation;
    private String ipAddress;
}