package com.sphereon.ms.did.mapping.maps;

import com.sphereon.ms.did.mapping.maps.model.DidMap;
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

    List<DidMap> storeDidMaps(List<DidMap> didMaps) {
        //ToDo: add a check for didMap formatting and requirements
        return didMaps.stream()
                .peek(didMapRepository::save)
                .collect(Collectors.toList());
    }

    Optional<DidMap> findDidMap(String applicationId, String userId){
        return didMapRepository.findByApplicationIdAndUserId(applicationId, userId);
    }
}
