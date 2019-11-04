package com.sphereon.ms.did.mapping.maps.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sphereon.ms.did.mapping.maps.model.DidMap;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class DidMappingResponse implements Serializable{
    private List<String> userIds;

    public DidMappingResponse(final List<String> userIds){
        this.userIds = userIds;
    }

    public static DidMappingResponse of(final List<DidMap> didMaps){
        List<String> userIds = didMaps.stream()
                .map(didMap -> didMap.getUserId())
                .collect(Collectors.toList());
        return new DidMappingResponse(userIds);
    }

    public List<String> getUserIds() {
        return userIds;
    }
}
