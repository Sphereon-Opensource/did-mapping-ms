package com.sphereon.ms.did.mapping.config;

public enum ErrorType {
    INVALID_DID_MAP("NEW_DID_MAP-001");

    private final String category;

    ErrorType(final String category) {
        this.category = category;
    }
}
