package com.sphereon.ms.did.mapping.config;

public enum ErrorType {
    INVALID_DID_MAP("NEW_DID_MAP-001"), DUPLICATE_DID_MAP("NEW_DID_MAP-002");

    private final String category;

    ErrorType(final String category) {
        this.category = category;
    }
}
