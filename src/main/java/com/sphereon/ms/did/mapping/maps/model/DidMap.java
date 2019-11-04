package com.sphereon.ms.did.mapping.maps.model;

import org.springframework.data.annotation.Id;

public class DidMap {

    @Id
    private String id;

    private String userId;
    private String applicationId;
    private DidInfo didInfo;

    public DidMap(){}

    public DidMap(final String id,
                  final String userId,
                  final String applicationId,
                  final DidInfo didInfo){
        this.id = id;
        this.userId = userId;
        this.applicationId = applicationId;
        this.didInfo = didInfo;
    }

    public DidMap(final String userId,
                  final String applicationId,
                  final DidInfo didInfo){
        this(null, userId, applicationId, didInfo);
    }

    public DidInfo getDidInfo() {
        return didInfo;
    }

    public String getApplicationId() {
        return applicationId;
    }

    public String getUserId() {
        return userId;
    }
}
