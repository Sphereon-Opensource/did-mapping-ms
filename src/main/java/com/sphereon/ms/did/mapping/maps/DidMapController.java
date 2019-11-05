package com.sphereon.ms.did.mapping.maps;

import com.sphereon.ms.did.mapping.config.RestConstants;
import com.sphereon.ms.did.mapping.maps.dto.DidMappingRequest;
import com.sphereon.ms.did.mapping.maps.dto.DidMappingResponse;
import com.sphereon.ms.did.mapping.maps.model.DidMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.MediaType;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

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
    public ResponseEntity storeDidMaps(@RequestBody DidMappingRequest didMapRequest) {
        final List<DidMap> persistedDidMaps = didMapService.storeDidMaps(didMapRequest.getDidMaps());
        return ResponseEntity.ok(new DidMappingResponse(persistedDidMaps));
    }

    @GetMapping(value = RestConstants.Endpoints.DidMapping.GET_DID,
            produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public ResponseEntity getDidMap(@PathVariable(value = RestConstants.Param.APPLICATION_ID) String applicationId,
                                    @PathVariable(value = RestConstants.Param.USER_ID) String userId) {
        Optional<DidMap> didMap = didMapService.findDidMap(applicationId, userId);
        if(didMap.isPresent()){
            return ResponseEntity.ok(new DidMappingResponse(Collections.singletonList(didMap.get())));
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
