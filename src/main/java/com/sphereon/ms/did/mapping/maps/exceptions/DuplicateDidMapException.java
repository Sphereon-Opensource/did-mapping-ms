package com.sphereon.ms.did.mapping.maps.exceptions;

import com.sphereon.ms.did.mapping.maps.model.DidMap;

import java.util.List;

public class DuplicateDidMapException extends RuntimeException {
    private final List<DidMap> duplicateDidMaps;
    public DuplicateDidMapException(String message, List<DidMap> duplicateDidMaps){
        super(message);
        this.duplicateDidMaps = duplicateDidMaps;
    }

    public List<DidMap> getDuplicateDidMaps() {
        return duplicateDidMaps;
    }
}
