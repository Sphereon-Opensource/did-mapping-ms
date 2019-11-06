package com.sphereon.ms.did.mapping.maps.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sphereon.ms.did.mapping.maps.model.DidMap;

import java.io.Serializable;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class DidMappingResponse implements Serializable{
    private List<DidMap> didMaps;

    public DidMappingResponse(final List<DidMap> didMaps){
        this.didMaps = didMaps;
    }

    public List<DidMap> getDidMaps() {
        return didMaps;
    }
}
