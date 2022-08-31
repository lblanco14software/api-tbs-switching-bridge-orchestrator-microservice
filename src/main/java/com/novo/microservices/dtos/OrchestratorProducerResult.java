package com.novo.microservices.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.novo.microservices.tbs.utils.dtos.StandardTransaction;
import com.novo.microservices.tbs.utils.dtos.TransactionMessage;
import com.novo.utils.messaging.dtos.Event;
import lombok.*;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

/**
 * OrchestratorProducerResult
 * <p>
 * OrchestratorProducerResult class.
 * <p>
 * <p>
 * ESTE COMPONENTE FUE CONSTRUIDO SIGUIENDO LOS ESTANDARES DE DESARROLLO Y EL PROCEDIMIENTO
 * DE DESARROLLO DE APLICACIONES DE NOVOPAYMENT Y SE ENCUENTRA PROTEGIDO POR LAS LEYES DE
 * PROPIEDAD INTELECTUAL Y DERECHOS DE AUTOR.
 *
 * @author Novopayment Inc.
 * @author lblanco@novopayment.com
 * @since 3/31/2022
 */
@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrchestratorProducerResult implements Serializable {

    private static final long serialVersionUID = 751809722188783883L;
    private Event<TransactionMessage<StandardTransaction>> eventTransactionMessage;
    private List<String> errors;

    public Boolean isSuccessfullyResponse() {
        return Optional.ofNullable(errors).isEmpty();
    }

    public Boolean isErrorInProcess() {
        return Optional.ofNullable(errors).isPresent();
    }
}