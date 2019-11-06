package com.sphereon.ms.did.mapping.maps;

import com.sphereon.ms.did.mapping.maps.exceptions.InvalidDidMapExcepion;
import com.sphereon.ms.did.mapping.maps.model.DidMap;
import com.sphereon.ms.did.mapping.utils.DidUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class DidMapService {
    private final DidMapRepository didMapRepository;

    public DidMapService(final DidMapRepository didMapRepository) {
        this.didMapRepository = didMapRepository;
    }

    List<DidMap> storeDidMaps(List<DidMap> didMaps) throws InvalidDidMapExcepion {
        //ToDo: add a check for didMap formatting and requirements
        didMaps.forEach(didMap -> {
            if(!DidUtils.isValidDidMap(didMap)){
                throw new InvalidDidMapExcepion();
            }
        });
        didMapRepository.save(didMaps);
        return didMaps;
    }

    Optional<DidMap> findDidMap(String applicationId, String userId){
        return didMapRepository.findByApplicationIdAndUserId(applicationId, userId);
    }
}
