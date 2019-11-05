package com.sphereon.ms.did.mapping.maps.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sphereon.ms.did.mapping.maps.model.DidMap;

import java.io.Serializable;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DidMappingRequest implements Serializable {

    private List<DidMap> didMaps;

    public DidMappingRequest() {
    }

    public List<DidMap> getDidMaps(){
        return didMaps;
    }
}
