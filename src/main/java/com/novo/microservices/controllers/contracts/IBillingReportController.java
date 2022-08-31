package com.novo.microservices.controllers.contracts;

import com.novo.microservices.tbs.utils.dtos.commons.ApplicationInfo;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import reactor.core.publisher.Mono;

import javax.validation.constraints.Past;
import java.util.Date;

/**
 * IBillingReportController
 * <p>
 * IBillingReportController interface.
 * <p>
 * <p>
 * ESTE COMPONENTE FUE CONSTRUIDO SIGUIENDO LOS ESTANDARES DE DESARROLLO Y EL PROCEDIMIENTO
 * DE DESARROLLO DE APLICACIONES DE NOVOPAYMENT Y SE ENCUENTRA PROTEGIDO POR LAS LEYES DE
 * PROPIEDAD INTELECTUAL Y DERECHOS DE AUTOR.
 *
 * @author Novopayment Inc.
 * @author duhernandez@novopayment.com
 * @since 23/5/22
 */
public interface IBillingReportController {
    @Operation(summary = "Generate billing report", description = "Generate a zip file with the billing report encoded in base64")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Successful operation",
            content = @Content(
                schema = @Schema(implementation = ApplicationInfo.class),
                examples = {
                    @ExampleObject(
                        description = "This request must be encrypted with JWE",
                        value = "")
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
                            "}\n")
                }
            )
        )
    })
    @ApiImplicitParams({
        @ApiImplicitParam(name = HttpHeaders.ACCEPT, value = MediaType.APPLICATION_JSON_VALUE, required = true, paramType = "header", dataTypeClass = String.class, defaultValue = MediaType.APPLICATION_JSON_VALUE),
        @ApiImplicitParam(name = HttpHeaders.CONTENT_TYPE, value = MediaType.APPLICATION_JSON_VALUE, required = true, paramType = "header", dataTypeClass = String.class, defaultValue = MediaType.APPLICATION_JSON_VALUE)
    })
    Mono<ResponseEntity<Object>> generateReport(@PathVariable @DateTimeFormat(pattern = "yyyyMMdd") @Past Date dateFrom,
                                                @PathVariable @DateTimeFormat(pattern = "yyyyMMdd") @Past Date dateTo);
}