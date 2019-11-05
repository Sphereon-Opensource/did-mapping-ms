package com.sphereon.ms.did.mapping.config;

public final class DidConstants {
    public static final String SCHEME = "did";

    public static final class DidMethod {
        public static final String ETHEREUM = "ethr";
        public static final String FACTOM = "factom";
    }

    public enum DidMethodEnum {
        FACTOM(DidMethod.FACTOM), ETHEREUM(DidMethod.ETHEREUM);

        public final String method;

        DidMethodEnum(String method) {
            this.method = method;
        }

    }
}
