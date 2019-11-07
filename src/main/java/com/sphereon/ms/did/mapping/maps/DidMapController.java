package com.sphereon.ms.did.mapping.maps;

import com.sphereon.ms.did.mapping.config.RestConstants;
import com.sphereon.ms.did.mapping.config.rest.ExceptionResponseBody;
import com.sphereon.ms.did.mapping.maps.dto.DidMappingRequest;
import com.sphereon.ms.did.mapping.maps.dto.DidMappingResponse;
import com.sphereon.ms.did.mapping.maps.exceptions.InvalidDidMapExcepion;
import com.sphereon.ms.did.mapping.maps.model.DidMap;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.MediaType;

import static java.util.Collections.singletonList;

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
    @ApiOperation(value = RestConstants.StoreDidMaps.SHORT_DESCRIPTION,
            nickname = RestConstants.StoreDidMaps.OPERATION_ID,
            notes = RestConstants.StoreDidMaps.LONG_DESCRIPTION)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Did Maps stored.", response = DidMappingResponse.class),
            @ApiResponse(code = 400, message = "One or more DID Maps format invalid.", response = ExceptionResponseBody.class)
    })
    public DidMappingResponse storeDidMaps(@RequestBody DidMappingRequest didMapRequest) {
        final List<DidMap> persistedDidMaps = didMapService.storeDidMaps(didMapRequest.getDidMaps());
        return new DidMappingResponse(persistedDidMaps);
    }

    @GetMapping(value = RestConstants.Endpoints.DidMapping.GET_DID,
            produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    @ApiOperation(value = RestConstants.GetDidMap.SHORT_DESCRIPTION,
            nickname = RestConstants.GetDidMap.OPERATION_ID,
            notes = RestConstants.StoreDidMaps.LONG_DESCRIPTION)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Did Map found.", response = ResponseEntity.class),
            @ApiResponse(code = 404, message = "Did Map not found.", response=ResponseEntity.class)
    })
    public ResponseEntity getDidMap(@PathVariable(value = RestConstants.Param.APPLICATION_ID) String applicationId,
                                    @PathVariable(value = RestConstants.Param.USER_ID) String userId) {
        Optional<DidMap> didMap = didMapService.findDidMap(applicationId, userId);
        if (didMap.isPresent()) {
            return ResponseEntity.ok(new DidMappingResponse(singletonList(didMap.get())));
        }
        return ResponseEntity.notFound().build();
    }
}
