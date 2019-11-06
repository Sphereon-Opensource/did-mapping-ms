package com.sphereon.ms.did.mapping.config.rest;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sphereon.ms.did.mapping.config.ErrorType;
import io.swagger.annotations.ApiModelProperty;

@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class ExceptionResponseBody {
    @ApiModelProperty(value = "Error code", required = true)
    private final ErrorType type;

    @ApiModelProperty(value = "Human readable error message", required = true)
    private final String message;

    public ExceptionResponseBody(final ErrorType type, final String message) {
        this.type = type;
        this.message = message;
    }

    public String getCode() {
        return type.toString();
    }

    public String getMessage() {
        return message;
    }
}
