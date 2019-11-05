package com.sphereon.ms.did.mapping.config;

public final class RestConstants {
    /**
     * #########################################################################
     * # ENDPOINTS                                                             #
     * #########################################################################
     */
    public static final class Endpoints {
        public static final class DidMapping{
            public static final String NAME = "/didmaps";
            public static final String GET_DID = NAME + "/{" + Param.APPLICATION_ID + "}/{" + Param.USER_ID + "}";
        }
    }

    /**
     * #########################################################################
     * # PARAMS                                                             #
     * #########################################################################
     */
    public static final class Param {
        public static final String APPLICATION_ID = "applicationId";
        public static final String USER_ID = "userId";
    }
}
