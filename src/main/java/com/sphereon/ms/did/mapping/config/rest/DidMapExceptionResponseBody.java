package com.sphereon.ms.did.mapping.config.rest;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sphereon.ms.did.mapping.config.ErrorType;
import com.sphereon.ms.did.mapping.maps.model.DidMapId;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class DidMapExceptionResponseBody {
    @ApiModelProperty(value = "Error code", required = true)
    private final ErrorType type;

    @ApiModelProperty(value = "Human readable error message", required = true)
    private final String message;

    @ApiModelProperty(value = "List of incorrect or duplicate DidMapIds")
    private final List<DidMapId> incorrectDidMapIds;

    public DidMapExceptionResponseBody(final ErrorType type, final String message, final List<DidMapId> incorrectDidMapIds) {
        this.type = type;
        this.message = message;
        this.incorrectDidMapIds = incorrectDidMapIds;
    }

    public String getCode() {
        return type.toString();
    }

    public String getMessage() {
        return message;
    }
}
