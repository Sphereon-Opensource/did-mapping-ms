package com.sphereon.ms.did.mapping.utils;

import com.sphereon.ms.did.mapping.config.DidConstants;

import java.util.Arrays;
import java.util.List;

public class DidUtils {

    public static Boolean isValidDidFormat(String did) {
        if (did == null) {
            return false;
        }

        List<String> didParts = Arrays.asList(did.split(":"));

        if (didParts.isEmpty() || didParts.size() < 3) {
            return false;
        }
        System.out.println(didParts);
        String scheme = didParts.get(0);
        String method = didParts.get(1);

        return scheme.equals(DidConstants.SCHEME) && isValidDidMethod(method);
    }

    private static Boolean isValidDidMethod(String method) {
        for (DidConstants.DidMethodEnum validMethod : DidConstants.DidMethodEnum.values()) {
            if (validMethod.method.equals(method)) {
                return true;
            }
        }
        return false;
    }
}
