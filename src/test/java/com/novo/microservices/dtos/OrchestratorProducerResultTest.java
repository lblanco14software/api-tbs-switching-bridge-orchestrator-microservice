package com.novo.microservices.dtos;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * OrchestratorProducerResultTest
 * <p>
 * OrchestratorProducerResultTest class.
 * <p>
 * <p>
 * ESTE COMPONENTE FUE CONSTRUIDO SIGUIENDO LOS ESTANDARES DE DESARROLLO Y EL PROCEDIMIENTO
 * DE DESARROLLO DE APLICACIONES DE NOVOPAYMENT Y SE ENCUENTRA PROTEGIDO POR LAS LEYES DE
 * PROPIEDAD INTELECTUAL Y DERECHOS DE AUTOR.
 *
 * @author Novopayment Inc.
 * @author lblanco@novopayment.com
 * @since 4/20/2022
 */
class OrchestratorProducerResultTest {

    @Test
    void isSuccessfullyResponse() {
        var result = OrchestratorProducerResult.builder().build();
        assertTrue(result.isSuccessfullyResponse());
    }

    @Test
    void isErrorInProcess() {
        var result = OrchestratorProducerResult.builder().errors(List.of("e1", "e2")).build();
        assertTrue(result.isErrorInProcess());
    }
}