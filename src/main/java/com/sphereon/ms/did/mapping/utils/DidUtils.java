package com.sphereon.ms.did.mapping.utils;

import com.sphereon.ms.did.mapping.config.DidConstants;
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
}
