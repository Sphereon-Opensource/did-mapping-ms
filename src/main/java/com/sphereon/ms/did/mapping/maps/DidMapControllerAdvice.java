package com.sphereon.ms.did.mapping.maps;

import com.sphereon.ms.did.mapping.config.ErrorType;
import com.sphereon.ms.did.mapping.config.rest.ExceptionResponseBody;
import com.sphereon.ms.did.mapping.maps.exceptions.DuplicateDidMapException;
import com.sphereon.ms.did.mapping.maps.exceptions.InvalidDidMapExcepion;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Map.Entry;

@ControllerAdvice
public class DidMapControllerAdvice {
    @ResponseBody
    @ExceptionHandler(InvalidDidMapExcepion.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ExceptionResponseBody when(InvalidDidMapExcepion e) {
        final String message = e.getMessage();
        return new ExceptionResponseBody(ErrorType.INVALID_DID_MAP, message);
    }

    @ResponseBody
    @ExceptionHandler(DuplicateDidMapException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ExceptionResponseBody when(DuplicateDidMapException e) {
        final StringBuilder message = new StringBuilder(e.getMessage() + " Duplicates: [");
        for(Entry id : e.getIdentifiers()){
            message.append("(").append(id.getKey()).append(", ").append(id.getValue()).append("), ");
        }
        message.append("]");
        return new ExceptionResponseBody(ErrorType.DUPLICATE_DID_MAP, message.toString());
    }


}
