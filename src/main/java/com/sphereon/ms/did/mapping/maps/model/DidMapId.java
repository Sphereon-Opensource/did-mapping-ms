package com.sphereon.ms.did.mapping.maps.model;

import java.util.List;

import static java.util.stream.Collectors.toList;

public class DidMapId {
    private final String applicationId;
    private final String userId;

    public DidMapId(DidMap didMap) {
        this.applicationId = didMap.getApplicationId();
        this.userId = didMap.getUserId();
    }

    public static List<DidMapId> listFromDidMaps(List<DidMap> didMaps) {
        return didMaps.stream()
                .map(DidMapId::new)
                .collect(toList());
    }

    public String getApplicationId() {
        return applicationId;
    }

    public String getUserId() {
        return userId;
    }
}
