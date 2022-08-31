package com.novo.microservices.controllers.contracts;

import com.novo.microservices.dtos.requests.OrchestrationTransactionRequest;
import com.novo.microservices.tbs.utils.dtos.commons.ApplicationInfo;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

/**
 * IOrchestratorTransactionController
 * <p>
 * IOrchestratorTransactionController class.
 * <p>
 * <p>
 * ESTE COMPONENTE FUE CONSTRUIDO SIGUIENDO LOS ESTANDARES DE DESARROLLO Y EL PROCEDIMIENTO
 * DE DESARROLLO DE APLICACIONES DE NOVOPAYMENT Y SE ENCUENTRA PROTEGIDO POR LAS LEYES DE
 * PROPIEDAD INTELECTUAL Y DERECHOS DE AUTOR.
 *
 * @author Novopayment Inc.
 * @author duhernandez@novopayment.com
 * @since 3/9/2022
 */
public interface IOrchestratorTransactionController {

    @Operation(summary = "Status microservices", description = "Get current status")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Successful operation",
            content = @Content(
                schema = @Schema(implementation = ApplicationInfo.class),
                examples = {
                    @ExampleObject(
                        description = "This request must be encrypted with JWE",
                        value = "{\n" +
                            "  \"paymentHeader\": {\n" +
                            "    \"messageType\": \"P\",\n" +
                            "    \"trackingId\": \"1702202216453613000452637\",\n" +
                            "    \"bankCode\": \"0066\",\n" +
                            "    \"serviceId\": \"00660022\",\n" +
                            "    \"storeId\": \"\",\n" +
                            "    \"userId\": \"\",\n" +
                            "    \"externalBranchId\": \"\",\n" +
                            "    \"externalUserId\": \"\",\n" +
                            "    \"channelId\": \"\",\n" +
                            "    \"deviceId\": \"\",\n" +
                            "    \"extraDeviceInformation\": \"\",\n" +
                            "    \"ipAddress\": \"\"\n" +
                            "  },\n" +
                            "  \"transaction\": {\n" +
                            "    \"messageTypeIndicator\": \"0200\",\n" +
                            "    \"de2\": \"4141411515151515\",\n" +
                            "    \"de3\": \"000000\",\n" +
                            "    \"de4\": \"000000000200\",\n" +
                            "    \"de7\": \"0314120000\",\n" +
                            "    \"de11\": \"123456\",\n" +
                            "    \"de12\": \"101520\",\n" +
                            "    \"de13\": \"1210\",\n" +
                            "    \"de17\": \"1205\",\n" +
                            "    \"de18\": \"9999\",\n" +
                            "    \"de22\": \"051\",\n" +
                            "    \"de32\": \"25252522552\",\n" +
                            "    \"de35\": \"4141411515151515=231120111523600000\",\n" +
                            "    \"de37\": \"101520101520\",\n" +
                            "    \"de38\": \"123456\",\n" +
                            "    \"de39\": \"R9\",\n" +
                            "    \"de41\": \"12345678\",\n" +
                            "    \"de42\": \"123456781234567\",\n" +
                            "    \"de43\": \"LOS JORGES CUAUHTEMOC DI MX\",\n" +
                            "    \"de44\": \"300000000000000000000000000000000000\",\n" +
                            "    \"de46\": \"123456\",\n" +
                            "    \"de48\": \"123456\",\n" +
                            "    \"de49\": \"484\",\n" +
                            "    \"de52\": \"0123456789ABCDEF\",\n" +
                            "    \"de55\": \"0123456789ABCDEF\",\n" +
                            "    \"de60\": \"YASTYAST+0000000\",\n" +
                            "    \"de61\": \"YASTYAST+0000000\",\n" +
                            "    \"de62\": \"YASTYAST+0000000\",\n" +
                            "    \"de63\": \" 0000200042! B400020 9015110000000015080 \",\n" +
                            "    \"de90\": \"02001234567891230000000000000000\",\n" +
                            "    \"de100\": \"11010100100\",\n" +
                            "    \"de120\": \"152535\",\n" +
                            "    \"de121\": \"152535\",\n" +
                            "    \"de125\": \"152535\",\n" +
                            "    \"de126\": \"601548\"\n" +
                            "  }\n" +
                            "}")
                }
            )
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Header Params Required",
            content = @Content(
                schema = @Schema(implementation = ApplicationInfo.class),
                examples = {
                    @ExampleObject(
                        description = "This request must be encrypted with JWE",
                        value = "{\n" +
                            "  \"code\": \"400.01.001\",\n" +
                            "  \"message\": \"Header Params Required\",\n" +
                            "  \"datetime\": \"2020-01-03T16:05:56.517Z\",\n" +
                            "  \"data\": {\n" +
                            "    \"paymentResponse\": {\n" +
                            "      \"responseStatus\": \"D\",\n" +
                            "      \"responseDescription\": \"Proceso declinado\",\n" +
                            "      \"trackingId\": \"1702202216453613000452637\",\n" +
                            "      \"serviceId\": \"00660022\"\n" +
                            "    }\n" +
                            "  }\n" +
                            "}\n")
                }
            )
        ),
        @ApiResponse(
            responseCode = "500",
            description = "Internal Server Error",
            content = @Content(
                schema = @Schema(implementation = ApplicationInfo.class),
                examples = {
                    @ExampleObject(
                        description = "This request must be encrypted with JWE",
                        value = "{\n" +
                            "  \"code\": \"500.01.999\",\n" +
                            "  \"message\": \"Internal Server Error\",\n" +
                            "  \"datetime\": \"2020-01-03T16:05:56.517Z\",\n" +
                            "  \"data\": {\n" +
                            "    \"paymentResponse\": {\n" +
                            "      \"responseStatus\": \"D\",\n" +
                            "      \"responseDescription\": \"Proceso declinado\",\n" +
                            "      \"trackingId\": \"1702202216453613000452637\",\n" +
                            "      \"serviceId\": \"00660022\"\n" +
                            "    }\n" +
                            "  }\n" +
                            "}\n")
                }
            )
        )
    })
    @ApiImplicitParams({
        @ApiImplicitParam(name = HttpHeaders.ACCEPT, value = MediaType.APPLICATION_JSON_VALUE, required = true, paramType = "header", dataTypeClass = String.class, defaultValue = MediaType.APPLICATION_JSON_VALUE),
        @ApiImplicitParam(name = HttpHeaders.CONTENT_TYPE, value = MediaType.APPLICATION_JSON_VALUE, required = true, paramType = "header", dataTypeClass = String.class, defaultValue = MediaType.APPLICATION_JSON_VALUE)
    })
    Mono<ResponseEntity<Object>> doOnProcessTransaction(@Valid @RequestBody OrchestrationTransactionRequest orchestrationTransactionRequest);
}
