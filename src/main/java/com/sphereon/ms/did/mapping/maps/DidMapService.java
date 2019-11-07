package com.sphereon.ms.did.mapping.maps;

import com.sphereon.ms.did.mapping.maps.exceptions.DuplicateDidMapException;
import com.sphereon.ms.did.mapping.maps.exceptions.InvalidDidMapExcepion;
import com.sphereon.ms.did.mapping.maps.model.DidMap;
import com.sphereon.ms.did.mapping.utils.DidUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.AbstractMap.SimpleEntry;
import java.util.Optional;

@Component
public class DidMapService {
    private final DidMapRepository didMapRepository;

    public DidMapService(final DidMapRepository didMapRepository) {
        this.didMapRepository = didMapRepository;
    }

    List<DidMap> storeDidMaps(List<DidMap> didMaps) throws InvalidDidMapExcepion {
        //ToDo: add a check for didMap formatting and requirements
        List<Entry<String, String>> duplicateDidMapIdentifiers = new ArrayList<>();
        didMaps.forEach(didMap -> {
            if (!DidUtils.isValidDidMap(didMap)) {
                String message = "One or more of the submitted DID maps was not formatted properly.";
                throw new InvalidDidMapExcepion(message);
            }
            if (didMapExists(didMap)) {
                duplicateDidMapIdentifiers.add(new SimpleEntry<>(didMap.getApplicationId(), didMap.getUserId()));
            }
        });
        if (!duplicateDidMapIdentifiers.isEmpty()){
            String message = "One or more of the submitted DID maps has already been stored.";
            throw new DuplicateDidMapException(message, duplicateDidMapIdentifiers);
        }
        didMapRepository.save(didMaps);
        return didMaps;
    }

    Optional<DidMap> findDidMap(String applicationId, String userId) {
        return didMapRepository.findByApplicationIdAndUserId(applicationId, userId);
    }

    Boolean didMapExists(DidMap didMap) {
        return didMapRepository.findByApplicationIdAndUserId(didMap.getApplicationId(), didMap.getUserId()).isPresent();
    }
}
