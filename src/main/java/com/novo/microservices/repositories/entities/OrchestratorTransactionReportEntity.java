package com.novo.microservices.repositories.entities;

import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

import static com.novo.microservices.constants.ProcessConstants.MONGO_SAGA_ORCHESTRATOR_TRANSACTIONS_REPORT_DOCUMENT;

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
 * @author duhernandez@novopayment.com
 * @since 05/13/2022
 */
@Document(MONGO_SAGA_ORCHESTRATOR_TRANSACTIONS_REPORT_DOCUMENT)
@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class OrchestratorTransactionReportEntity implements Serializable {

    private static final long serialVersionUID = -1871388170083169592L;

    @Id
    private ObjectId id;
    @Indexed
    private String bankCode;
    private String messageType;
    private String trackingId;
    private String serviceId;
    private String messageTypeIndicator;
    private String transactionAmount;
    @Indexed
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private Date transactionDate;
    private String transactionRequestPayload;
}