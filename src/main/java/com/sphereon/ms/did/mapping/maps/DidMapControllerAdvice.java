package com.sphereon.ms.did.mapping.maps;

import com.sphereon.ms.did.mapping.config.ErrorType;
import com.sphereon.ms.did.mapping.config.rest.DidMapExceptionResponseBody;
import com.sphereon.ms.did.mapping.config.rest.ExceptionResponseBody;
import com.sphereon.ms.did.mapping.maps.exceptions.DuplicateDidMapException;
import com.sphereon.ms.did.mapping.maps.exceptions.InvalidDidMapException;
import com.sphereon.ms.did.mapping.maps.model.DidMapId;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.dao.DuplicateKeyException;

import java.util.List;

@ControllerAdvice
public class DidMapControllerAdvice {
    @ResponseBody
    @ExceptionHandler(InvalidDidMapException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ExceptionResponseBody when(InvalidDidMapException e) {
        final String message = e.getMessage();
        return new ExceptionResponseBody(ErrorType.INVALID_DID_MAP, message);
    }

    @ResponseBody
    @ExceptionHandler(DuplicateDidMapException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    DidMapExceptionResponseBody when(DuplicateDidMapException e) {
        List<DidMapId> identifiers = DidMapId.listFromDidMaps(e.getDuplicateDidMaps());
        return new DidMapExceptionResponseBody(ErrorType.DUPLICATE_DID_MAP, e.getMessage(), identifiers);
    }

    @ResponseBody
    @ExceptionHandler(DuplicateKeyException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ExceptionResponseBody when(DuplicateKeyException e){
        return new ExceptionResponseBody(ErrorType.DUPLICATE_DID_MAP_FIELD, e.getMessage());
    }
}
