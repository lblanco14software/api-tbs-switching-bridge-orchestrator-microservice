package com.novo.microservices.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * CacheProcessResponse
 * <p>
 * CacheProcessResponse class.
 * <p>
 * <p>
 * ESTE COMPONENTE FUE CONSTRUIDO SIGUIENDO LOS ESTANDARES DE DESARROLLO Y EL PROCEDIMIENTO
 * DE DESARROLLO DE APLICACIONES DE NOVOPAYMENT Y SE ENCUENTRA PROTEGIDO POR LAS LEYES DE
 * PROPIEDAD INTELECTUAL Y DERECHOS DE AUTOR.
 *
 * @author Novopayment Inc.
 * @author lblanco@novopayment.com
 * @since 4/6/2022
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CacheProcessResponse<T> implements Serializable {

    private static final long serialVersionUID = 2080637027832578769L;
    private T cacheEntity;
    private Boolean cacheIsPresent;
    private Boolean isError = false;
    private Exception exception;
    private String errorMessage;

    public void setCacheEntity(T cacheEntity) {
        this.cacheEntity = cacheEntity;
        this.cacheIsPresent = true;
    }

    public void setError(Exception exception) {
        this.isError = true;
        this.cacheIsPresent = false;
        this.exception = exception;
    }

    public void setError(String errorMessage) {
        isError = true;
        this.cacheIsPresent = false;
        this.errorMessage = errorMessage;
    }

    public Boolean isError() {
        return Boolean.TRUE.equals(isError);
    }

    public Boolean isCachePresent() {
        return Boolean.FALSE.equals(isError);
    }
}
