package com.sphereon.ms.did.mapping.config;

public final class DidConstants {
    public static final String SCHEME = "did";

    public enum DidMethodEnum {
        FACTOM("factom"), ETHEREUM("ethr");

        private final String method;

        DidMethodEnum(String method) {
            this.method = method;
        }

        public String getMethod() {
            return method;
        }
    }
}
