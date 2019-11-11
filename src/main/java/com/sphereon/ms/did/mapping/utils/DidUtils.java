package com.sphereon.ms.did.mapping.utils;

import com.sphereon.ms.did.mapping.config.DidConstants;
import com.sphereon.ms.did.mapping.maps.exceptions.InvalidDidMapExcepion;
import com.sphereon.ms.did.mapping.maps.model.DidMap;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.List;

public class DidUtils {

    public static Boolean isValidDidFormat(String did) {
        if (StringUtils.isEmpty(did)) {
            return false;
        }

        List<String> didParts = Arrays.asList(did.split(":"));

        if (didParts.size() < 3) {
            return false;
        }

        String scheme = didParts.get(0);
        String method = didParts.get(1);

        return scheme.equals(DidConstants.SCHEME) && isValidDidMethod(method);
    }

    private static Boolean isValidDidMethod(String method) {
        for (DidConstants.DidMethodEnum validMethod : DidConstants.DidMethodEnum.values()) {
            if (validMethod.getMethod().equals(method)) {
                return true;
            }
        }
        return false;
    }

    private static boolean isValidDidMap(DidMap didMap) {
        return isValidDidFormat(didMap.getDidInfo().getDid());
    }

    public static void assertValidDidMap(DidMap didMap) throws InvalidDidMapExcepion {
        if(!isValidDidMap(didMap)){
            String message = "One or more of the submitted DID maps was not formatted properly.";
            throw new InvalidDidMapExcepion(message);
        }
    }
}
