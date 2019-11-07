package com.sphereon.ms.did.mapping.maps.exceptions;

import java.util.List;
import java.util.Map.Entry;

public class DuplicateDidMapException extends RuntimeException {
    private final List<Entry<String, String>> identifiers;
    public DuplicateDidMapException(String message, List<Entry<String, String>> identifiers){
        super(message);
        this.identifiers = identifiers;
    }

    public List<Entry<String, String>> getIdentifiers() {
        return identifiers;
    }
}
