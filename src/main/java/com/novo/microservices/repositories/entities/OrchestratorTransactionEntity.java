package com.novo.microservices.repositories.entities;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

import static com.novo.microservices.constants.ProcessConstants.MONGO_SAGA_ORCHESTRATOR_TRANSACTIONS_DOCUMENT;

/**
 * OrchestratorTransactionEntity
 * <p>
 * OrchestratorTransactionEntity class.
 * <p>
 * <p>
 * ESTE COMPONENTE FUE CONSTRUIDO SIGUIENDO LOS ESTANDARES DE DESARROLLO Y EL PROCEDIMIENTO
 * DE DESARROLLO DE APLICACIONES DE NOVOPAYMENT Y SE ENCUENTRA PROTEGIDO POR LAS LEYES DE
 * PROPIEDAD INTELECTUAL Y DERECHOS DE AUTOR.
 *
 * @author Novopayment Inc.
 * @author lblanco@novopayment.com
 * @since 3/24/2022
 */
@Document(MONGO_SAGA_ORCHESTRATOR_TRANSACTIONS_DOCUMENT)
@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class OrchestratorTransactionEntity implements Serializable {

    private static final long serialVersionUID = -1908989141077044794L;
    @Id
    private String messageId;
    private String serviceId;
    private String messageTypeIndicator;
    private String processingCode;
    private String transactionEncrypted;
}