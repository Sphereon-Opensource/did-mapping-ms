package com.sphereon.ms.did.mapping.maps;

import com.sphereon.ms.did.mapping.config.RestConstants;
import com.sphereon.ms.did.mapping.maps.dto.DidMappingRequest;
import com.sphereon.ms.did.mapping.maps.dto.DidMappingResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.MediaType;

@RestController
public class DidMapController {
    private final DidMapService didMapService;

    @Autowired
    public DidMapController(DidMapService didMapService) {
        this.didMapService = didMapService;
    }

    @PostMapping(value = RestConstants.Endpoints.DidMapping.NAME,
            consumes = {MediaType.APPLICATION_JSON_UTF8_VALUE},
            produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public DidMappingResponse storeDidMaps(@RequestBody DidMappingRequest didMapRequest) {
        return DidMappingResponse.of(didMapService.storeDidMaps(didMapRequest.getDidMaps()));
    }


}
