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

    /**
     * #########################################################################
     * # Controller Operations                                                 #
     * #########################################################################
     */
    public static final class StoreDidMaps {
        public static final String SHORT_DESCRIPTION = "Store DID Maps";
        public static final String OPERATION_ID = "storeDidMaps";
        public static final String LONG_DESCRIPTION = "Submit a list of DID Maps, associating a User ID to the necessary DID information for authentication";
    }

    public static final class GetDidMap {
        public static final String SHORT_DESCRIPTION = "Get a stored DID Map";
        public static final String OPERATION_ID = "getDidMap";
        public static final String LONG_DESCRIPTION = "Retrieve a previously stored DID Map based of an App ID and User ID";
    }
}
