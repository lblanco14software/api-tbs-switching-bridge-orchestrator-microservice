package com.novo.microservices.dtos;

import lombok.*;

import java.io.Serializable;
import java.util.List;

/**
 * OrchestratorTransactionMapping
 * <p>
 * OrchestratorTransactionMapping class.
 * <p>
 * <p>
 * ESTE COMPONENTE FUE CONSTRUIDO SIGUIENDO LOS ESTANDARES DE DESARROLLO Y EL PROCEDIMIENTO
 * DE DESARROLLO DE APLICACIONES DE NOVOPAYMENT Y SE ENCUENTRA PROTEGIDO POR LAS LEYES DE
 * PROPIEDAD INTELECTUAL Y DERECHOS DE AUTOR.
 *
 * @author Novopayment Inc.
 * @author duhernandez@novopayment.com
 * @since 2/6/22
 */
@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class OrchestratorTransactionStructureDataMapping implements Serializable {
    private static final long serialVersionUID = 2001614004660437339L;

    private String serviceId;
    private String messageTypeIndicator;
    private String processingCode;
    private String bankCode;
    private List<StructureDataField> structureDataFields;
}