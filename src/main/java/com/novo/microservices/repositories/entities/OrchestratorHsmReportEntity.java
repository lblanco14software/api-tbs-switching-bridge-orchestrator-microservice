package com.novo.microservices.repositories.entities;

import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

import static com.novo.microservices.constants.ProcessConstants.MONGO_SAGA_ORCHESTRATOR_HSM_REPORT_DOCUMENT;

/**
 * OrchestratorHsmReportEntity
 * <p>
 * OrchestratorHsmReportEntity class.
 * <p>
 * <p>
 * ESTE COMPONENTE FUE CONSTRUIDO SIGUIENDO LOS ESTANDARES DE DESARROLLO Y EL PROCEDIMIENTO
 * DE DESARROLLO DE APLICACIONES DE NOVOPAYMENT Y SE ENCUENTRA PROTEGIDO POR LAS LEYES DE
 * PROPIEDAD INTELECTUAL Y DERECHOS DE AUTOR.
 *
 * @author Novopayment Inc.
 * @author jquiroga@novopayment.com
 * @since 07/14/2022
 */
@Document(MONGO_SAGA_ORCHESTRATOR_HSM_REPORT_DOCUMENT)
@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class OrchestratorHsmReportEntity implements Serializable {

    private static final long serialVersionUID = -1871388170083169592L;

    @Id
    private ObjectId id;
    private String trackingId;
    private String responseCode;
    @Indexed
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private Date transactionDateRequest;
    @Indexed
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private Date transactionDateResponse;
}